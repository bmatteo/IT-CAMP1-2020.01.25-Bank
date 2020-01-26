package pl.camp.it.utils;

import org.springframework.stereotype.Component;
import pl.camp.it.model.User;

@Component
public class SQLUtils extends IDBUtils {
    @Override
    public void saveUser(User user) {
        System.out.println("zasejwowany !!!");
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public User getUserByAccountNumber(String accountNumber) {
        return null;
    }
}
