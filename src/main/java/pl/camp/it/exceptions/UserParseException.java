package pl.camp.it.exceptions;

public class UserParseException extends Exception {
    String line;

    public UserParseException(String line) {
        this.line = line;
    }
}
