package pl.camp.it.utils;

import java.util.Random;

public class AccountNumberUtils {
    public static String generateAccountNumber() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
