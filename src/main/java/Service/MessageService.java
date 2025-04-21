package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.*;


public class MessageService {
    
    MessageDAO messageDAO;
    
    public MessageService () {
        this.messageDAO = new MessageDAO();
    }
    public Message postMessage (Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()
        || message.getMessage_text().length() >= 255) {
            return null;
        }
        return messageDAO.insertMessage(message); 
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages(); 
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }
    public List<Message> getMessagesByUser(int userId) {
        return messageDAO.getMessagesByUser(userId);
    }
    public Message updateMessage (int id, String newText) {
        if (newText == null || newText.isBlank() || newText.length() >=255) {
            return null;
        }
        return messageDAO.updateMessage(id, newText);
    }
    public int deleteMessage(int id) {
        return messageDAO.deleteMessageById(id);
    }

}
