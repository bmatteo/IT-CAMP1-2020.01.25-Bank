package pl.camp.it.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.camp.it.exceptions.AccountNumberMismatchException;
import pl.camp.it.exceptions.NegativeBalanceException;
import pl.camp.it.model.User;
import pl.camp.it.utils.AccountNumberUtils;
import pl.camp.it.utils.DBUtils;

import java.util.Scanner;

public class Gui {

    public static void run() {
        while (true) {
            if(DBUtils.currentUser != null) {
                showLoggedMenu();
            } else {
                showMainMenu();
            }
        }
    }
    public static void showLoggedMenu() {
        System.out.println("1. Transaction");
        System.out.println("2. Exit");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1":
                showTransaction();
                break;
            case "2":
                System.exit(0);
            default:
                break;
        }
    }

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
                showLogin();
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

    private static void showLogin() {
        System.out.println("Your Login:");
        Scanner scanner = new Scanner(System.in);

        String login = scanner.nextLine();

        System.out.println("Your Password:");
        String pass = scanner.nextLine();

        User user = DBUtils.getUserByLogin(login);

        if(user != null && user.getPass().equals(DigestUtils.md5Hex(pass))) {
            DBUtils.currentUser = user;
        } else {
            System.out.println("Logowanie nieudane !!");
        }
    }

    private static void showTransaction() {
        System.out.println("Account number:");
        Scanner scanner = new Scanner(System.in);

        String accountNumber = scanner.nextLine();

        System.out.println("Ammount:");
        Double ammount = scanner.nextDouble();

        if(DBUtils.currentUser.getBalance() >= ammount) {
            User userToTransfer = DBUtils.getUserByAccountNumber(accountNumber);

            if(userToTransfer != null && !DBUtils.currentUser.getLogin()
                    .equals(userToTransfer.getLogin())) {
                DBUtils.currentUser
                        .setBalance(DBUtils.currentUser.getBalance() - ammount);
                DBUtils.saveUser(DBUtils.currentUser);

                userToTransfer.setBalance(userToTransfer.getBalance() + ammount);
                DBUtils.saveUser(userToTransfer);
            }
        } else {
            System.out.println("Brak środków na koncie !!");
        }
    }
}
