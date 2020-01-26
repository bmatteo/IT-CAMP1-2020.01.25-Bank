package pl.camp.it.logic;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.camp.it.exceptions.AccountNumberMismatchException;
import pl.camp.it.exceptions.NegativeBalanceException;
import pl.camp.it.model.User;
import pl.camp.it.utils.AccountNumberUtils;
import pl.camp.it.utils.IDBUtils;

@Component
public class Service implements ILogic {
    @Autowired
    IDBUtils dbUtils;
    public void registerUser(String login, String pass) {
        User newUser = new User();

        newUser.setLogin(login);
        newUser.setPass(DigestUtils.md5Hex(pass));
        try {
            newUser.setAccountNumber(AccountNumberUtils.generateAccountNumber());
            newUser.setBalance(0.0);
        } catch (AccountNumberMismatchException | NegativeBalanceException e) {
            System.out.println("Application critical error !!");
        }

        dbUtils.saveUser(newUser);
    }

    public boolean login(String login, String pass) {
        User user = dbUtils.getUserByLogin(login);

        if(user != null && user.getPass().equals(DigestUtils.md5Hex(pass))) {
            dbUtils.currentUser = user;
            return true;
        } else {
            return false;
        }
    }

    public boolean makeTransaction(String accountNumber, double ammount) {
        if(dbUtils.currentUser.getBalance() >= ammount) {
            User userToTransfer = dbUtils.getUserByAccountNumber(accountNumber);

            if(userToTransfer != null && !dbUtils.currentUser.getLogin()
                    .equals(userToTransfer.getLogin())) {
                try {
                    dbUtils.currentUser
                            .setBalance(dbUtils.currentUser.getBalance() - ammount);
                    dbUtils.saveUser(dbUtils.currentUser);

                    userToTransfer.setBalance(userToTransfer.getBalance() + ammount);
                    dbUtils.saveUser(userToTransfer);

                    return true;
                } catch (NegativeBalanceException e) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
