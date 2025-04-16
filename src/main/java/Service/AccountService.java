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

    public Account login (String username, String password) {
        Account account = accountDAO.getAccountByUserName(username);
        if(account !=null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

}
