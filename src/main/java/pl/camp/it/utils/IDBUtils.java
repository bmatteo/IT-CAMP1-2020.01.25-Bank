package pl.camp.it.utils;

import pl.camp.it.model.User;

public abstract class IDBUtils {
    public User currentUser = null;
    abstract public void saveUser(User user);
    abstract public User getUserByLogin(String login);
    abstract public User getUserByAccountNumber(String accountNumber);
}
