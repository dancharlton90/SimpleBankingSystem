package banking;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Account {

    private String cardNumber;      //Using String to keep leading 0's
    private String accountNumber;
    private String PIN;
    private int balance;

    Random random = new Random();

    public Account() {
        accountNumber = generateAccountNumber();
        cardNumber = generateCardNumber();
        PIN = generatePIN();
        balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    private String generateAccountNumber() {
        return String.format("%09d", random.nextInt(1000000000));
    }

    private String generateCardNumber() {
        String BIN = "400000";
        String cardNumber = BIN + accountNumber;
        String checksum = createCheckSum(cardNumber);
        return cardNumber + checksum;
    }

    private String generatePIN() {
        return String.format("%04d", random.nextInt(10000));
    }

    public static String createCheckSum(String cardNumber) {
        int[] arr = new int[cardNumber.length()];
        int sum = 0;
        //Luhns Algorithim:
        //Transform card number string to Array
        for (int i = 0; i < cardNumber.length(); i++) {
            arr[i] = Character.getNumericValue(cardNumber.charAt(i));
        }
        //Multiply all odd numbers by 2
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                arr[i] = arr[i]*2;
            }
        }
        //Subtract all numbers over 9 by 9
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 9) {
                arr[i] = arr[i]-9;
            }
        }
        //Add all numbers
        for (int i = 0; i < arr.length; i++) {
            sum = sum + arr[i];
        }
        //Calculate checksum from modulus
        int remainder = sum % 10;
        remainder = 10 - remainder;

        if(remainder == 10) {
            remainder = 0;
        }
        return String.valueOf(remainder);
    }
}
