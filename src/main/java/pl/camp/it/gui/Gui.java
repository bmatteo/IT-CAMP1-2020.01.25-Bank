package pl.camp.it.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.camp.it.exceptions.AccountNumberMismatchException;
import pl.camp.it.exceptions.NegativeBalanceException;
import pl.camp.it.model.User;
import pl.camp.it.utils.AccountNumberUtils;
import pl.camp.it.utils.DBUtils;

import java.util.Scanner;

public class Gui {
    public static void showMainMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1":
                showRegister();
                break;
            case "2":
                break;
            case "3":
                System.exit(0);
                default:
                    break;
        }
    }

    public static void showRegister() {
        System.out.println("Your Login:");
        Scanner scanner = new Scanner(System.in);

        String login = scanner.nextLine();

        System.out.println("Your Password:");
        String pass = scanner.nextLine();

        User newUser = new User();

        newUser.setLogin(login);
        newUser.setPass(DigestUtils.md5Hex(pass));
        try {
            newUser.setAccountNumber(AccountNumberUtils.generateAccountNumber());
            newUser.setBalance(0.0);
        } catch (AccountNumberMismatchException | NegativeBalanceException e) {
            System.out.println("Application critical error !!");
        }

        DBUtils.saveUser(newUser);
    }
}
