package Controller;

import java.util.List;
import java.util.Map;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        app.post("/messages", this::createMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);

        return app;
    }
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) {
        Account newAccount = context.bodyAsClass(Account.class);
        Account registeredAccount = accountService.register(newAccount);
        if(registeredAccount != null) {
            context.status(200).json(registeredAccount);
        }
        else {
            context.status(400);
            
        }
    }
    
    private void loginHandler(Context context) {
        Account accountCredentials = context.bodyAsClass(Account.class);
        Account login = accountService.login(accountCredentials.getUsername(), accountCredentials.getPassword());
        if(login !=null) {
            context.status(200).json(login);
        }
        else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context ctx) {
        Message msg = ctx.bodyAsClass(Message.class);
        Message posted = messageService.postMessage(msg);
        if (posted != null) {
            ctx.status(200).json(posted);
        } else {
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.getMessageById(messageId);

        if (deleted == null) {
            context.status(200).json("");
        } else {
            messageService.deleteMessage(messageId);
            context.status(200).json(deleted);
        }
    }

    private void getAllMessageHandler (Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    
    private void getMessagesByUserHandler (Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        try {
            int messageId = Integer.parseInt(context.pathParam("message_id"));
            Message message = messageService.getMessageById(messageId);
        if (message !=null) {
            context.json(message);
        }else {
            context.status(200);
        }
        } catch (NumberFormatException e) {
            context.status(400);    
        }
        
    }

    private void updateMessageHandler (Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message incoming = context.bodyAsClass(Message.class);

        Message updated = messageService.updateMessage(messageId, incoming.getMessage_text());
        if (updated !=null) {
            context.status(200).json(updated);
        } else {
            context.status(400);
        }
    }

}