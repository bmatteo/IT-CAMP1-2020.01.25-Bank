package pl.camp.it.model;

import pl.camp.it.exceptions.AccountNumberMismatchException;
import pl.camp.it.exceptions.NegativeBalanceException;

public class User {
    private String login;
    private String pass;
    private String accountNumber;
    private double balance;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
            throws AccountNumberMismatchException {
        String regex = "^[0-9]{4}$";
        if(accountNumber.matches(regex)) {
            this.accountNumber = accountNumber;
        } else {
            throw new AccountNumberMismatchException(accountNumber);
        }

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) throws NegativeBalanceException {
        if(balance >= 0.0) {
            this.balance = balance;
        } else {
            throw new NegativeBalanceException(balance);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.login)
                .append(";")
                .append(this.pass)
                .append(";")
                .append(this.accountNumber)
                .append(";")
                .append(this.balance);

        return sb.toString();
    }
}
