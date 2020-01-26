package pl.camp.it.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.camp.it.logic.ILogic;
import pl.camp.it.utils.IDBUtils;

import java.util.Scanner;

public class Gui {

    @Autowired
    ILogic service;
    @Autowired
    IDBUtils dbUtils;

    public void run() {
        while (true) {
            if(dbUtils.currentUser != null) {
                showLoggedMenu();
            } else {
                showMainMenu();
            }
        }
    }
    public void showLoggedMenu() {
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

    public void showMainMenu() {
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

    public void showRegister() {
        System.out.println("Your Login:");
        Scanner scanner = new Scanner(System.in);

        String login = scanner.nextLine();

        System.out.println("Your Password:");
        String pass = scanner.nextLine();

        service.registerUser(login, pass);
    }

    private void showLogin() {
        System.out.println("Your Login:");
        Scanner scanner = new Scanner(System.in);

        String login = scanner.nextLine();

        System.out.println("Your Password:");
        String pass = scanner.nextLine();

        if(!service.login(login, pass)){
            System.out.println("Logowanie nieudane !!");
        }
    }

    private void showTransaction() {
        System.out.println("Account number:");
        Scanner scanner = new Scanner(System.in);

        String accountNumber = scanner.nextLine();

        System.out.println("Ammount:");
        Double ammount = scanner.nextDouble();

        if(service.makeTransaction(accountNumber, ammount)) {
            System.out.println("Transakcja wykonana !");
        } else {
            System.out.println("Błąd transakcji !");
        }
    }
}
