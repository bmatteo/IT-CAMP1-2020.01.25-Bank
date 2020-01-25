package pl.camp.it.exceptions;

public class NegativeBalanceException extends Exception {
    double balance;

    public NegativeBalanceException(double balance) {
        this.balance = balance;
    }
}
