package pl.camp.it.utils;

import pl.camp.it.exceptions.AccountNumberMismatchException;
import pl.camp.it.exceptions.NegativeBalanceException;
import pl.camp.it.exceptions.UserParseException;
import pl.camp.it.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static User currentUser = null;

    private static String dbFilePath =
            "/home/mateusz/ITCamp-Kraków/2020.01.25-context/src/main/resources/baza.txt";

    public static void saveUser(User user) {
        List<User> usersList = loadData();
        boolean flag = false;

        for (User userFromDb : usersList) {
            if(userFromDb.getLogin().equals(user.getLogin())) {
                usersList.remove(userFromDb);
                usersList.add(user);
                flag = true;
                break;
            }
        }

        if(!flag) {
            usersList.add(user);
        }

        saveData(usersList);
    }

    public static User getUserByLogin(String login) {
        List<User> usersList = loadData();

        for (User userFromDb : usersList) {
            if(userFromDb.getLogin().equals(login)) {
                return userFromDb;
            }
        }

        return null;
    }

    public static User getUserByAccountNumber(String accountNumber) {
        List<User> usersList = loadData();

        for (User userFromDb : usersList) {
            if(userFromDb.getAccountNumber().equals(accountNumber)) {
                return userFromDb;
            }
        }

        return null;
    }

    private static List<User> loadData() {
        List<User> result = new ArrayList<>();

        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(dbFilePath))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    result.add(convertDBLineToObject(line));
                } catch (UserParseException e) {
                    e.printStackTrace();
                }
            }

            return result;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Database broken !!");
            return new ArrayList<>();
        }
    }

    public static void saveData(List<User> usersList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dbFilePath))) {

            for (User user : usersList) {
                bufferedWriter.write(user.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static User convertDBLineToObject(String dbLine) throws UserParseException {
        String[] fields = dbLine.split(";");

        User user = new User();
        user.setLogin(fields[0]);
        user.setPass(fields[1]);
        try {
            user.setAccountNumber(fields[2]);
            user.setBalance(Double.parseDouble(fields[3]));
        } catch (AccountNumberMismatchException e) {
            System.out.println("Account number in DB is incorrect !");
            throw new UserParseException(dbLine);
        } catch (NegativeBalanceException e) {
            System.out.println("Balance in DB is incorrect !");
            throw new UserParseException(dbLine);
        }

        return user;
    }
}
