package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /*
     *  The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
     * and an Account with that username does not already exist. If all these conditions are met, 
     * the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. 
     * The new account should be persisted to the database.
- If the registration is not successful, the response status should be 400. (Client error)
     */
    public Account register(Account account) {
        if(account.getUsername() == null || account.getPassword() == null || account.getPassword().length()<4 || account.getUsername().isBlank()) { return null; }
        if(accountDAO.getAccountByUserName(account.getUsername()) != null) {return null;}
        return accountDAO.addAccount(account);

    }

    /*
     * Our API should be able to process User logins.

As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. The request body will contain a JSON representation of an Account, not containing an account_id. In the future, this action may generate a Session token to allow the user to securely use the site. We will not worry about this for now.

- The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database.
 If successful, the response body should contain a JSON of the account in the response body, including its account_id. 
 The response status should be 200 OK, which is the default.
- If the login is not successful, the response status should be 401. (Unauthorized)
     */
    public Account login (String username, String password) {
        Account account = accountDAO.getAccountByUserName(username);
        if(account !=null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

}
