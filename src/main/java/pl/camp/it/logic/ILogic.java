package pl.camp.it.logic;

public interface ILogic {
    void registerUser(String login, String pass);
    boolean login(String login, String pass);
    boolean makeTransaction(String accountNumber, double ammount);

}
