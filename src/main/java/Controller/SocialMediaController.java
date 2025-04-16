package Controller;

import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;


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

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
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

    


}