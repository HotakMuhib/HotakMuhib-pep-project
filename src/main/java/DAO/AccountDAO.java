package DAO;


import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    
    public Account addAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account (username, password) values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                int id = resultSet.getInt(1);
                return new Account(id, account.getUsername(), account.getPassword());
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
            return null;
        }
    public Account getAccountByUserName(String username) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new Account(
                resultSet.getInt("account_id"),
                resultSet.getString("username"),
                resultSet.getString("password"));
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }  
    
    public Account getAccountById(int accountId) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new Account(
                    resultSet.getInt("account_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    }
    
