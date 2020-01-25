package pl.camp.it.exceptions;

public class AccountNumberMismatchException extends Exception {
    String accountNumber;

    public AccountNumberMismatchException(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
