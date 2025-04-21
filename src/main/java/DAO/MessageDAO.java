package DAO;

import Util.ConnectionUtil;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Model.Message;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int messageId = resultSet.getInt(1);
                return new Message(messageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        }
        public List<Message> getAllMessages() {
            List<Message> messages = new ArrayList<>();
            Connection conn = ConnectionUtil.getConnection();
            try {
                String sql = "select * from message";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    messages.add(new Message(
                        resultSet.getInt("message_id"), 
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return messages;
        }

        public Message getMessageById(int id) {
            Connection conn = ConnectionUtil.getConnection();
            try {
                String sql = "select * from message where message_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    return new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        public List<Message> getMessagesByUser(int accountId) {
            List<Message> messages = new ArrayList<>();
            Connection conn = ConnectionUtil.getConnection();
            try {
                String sql = "select * from message where posted_by = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1,accountId);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    messages.add(new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                    ));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return messages;
        }
        public int deleteMessageById(int id) {
            Connection conn = ConnectionUtil.getConnection();
            try {
                String sql = "delete from message where message_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        } 
        public Message updateMessage(int id, String newText) {
            Connection conn = ConnectionUtil.getConnection();
            try {
                String sql = "update message set message_text = ? where message_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, newText);
                preparedStatement.setInt(2, id);
                int updated = preparedStatement.executeUpdate();

                if(updated > 0) {
                    return getMessageById(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }