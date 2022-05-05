package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = args[1];
        Connect connect = new Connect(url);
        connect.createDataBase();

        //Map<String, Account> accountMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        mainMenu();

        while (!finished && scanner.hasNext()) {

            int input = scanner.nextInt();

            switch (input) {
                case 0:
                    System.out.println("Bye!");
                    finished = true;
                    break;
                case 1:
                    Account account = createCard();
                    //accountMap.put(account.getCardNumber(), account);   // Add card to storage
                    connect.insertAccount(account);

                    mainMenu();
                    break;
                case 2:
                    System.out.println("Enter your card number");
                    String inputCardNumber = scanner.next();
                    System.out.println("Enter your PIN");
                    String inputPIN = scanner.next();

                    if (connect.loginPossible(inputCardNumber, inputPIN)) {    // Check PIN
                        System.out.println("You have successfully logged in!");
                        System.out.println("");
                        loginMenu();


                        boolean loginFinished = false;

                        while (!loginFinished) {
                            loginMenu();
                            int loginInput = scanner.nextInt();
                            switch (loginInput) {
                                case 0:
                                    System.out.println("Bye!");
                                    loginFinished = true;
                                    finished = true;
                                    break;
                                case 1:
                                    //System.out.println("Balance: " + accountMap.get(inputCardNumber).getBalance());
                                    System.out.println(connect.getBalance(inputCardNumber));
                                    loginMenu();
                                    break;
                                case 2:
                                    System.out.println("Enter income:");
                                    int income = scanner.nextInt();
                                    connect.addIncome(inputCardNumber, income);
                                    System.out.println("Income was added!");
                                    break;
                                case 3:
                                    System.out.println("Transfer");
                                    System.out.println("Enter Card Number:");
                                    String targetCard = scanner.next();
                                    if (luhnValidation(targetCard)) {
                                        if (connect.cardExists(targetCard)) {
                                            System.out.println("Enter how much money you want to transfer:");
                                            int targetAmount = scanner.nextInt();
                                            if (targetAmount < connect.getBalance(inputCardNumber)) {
                                                connect.transfer(inputCardNumber, targetCard, targetAmount);
                                                System.out.println("Success!");
                                            } else {
                                                System.out.println("Not enough money!");
                                            }
                                        } else {
                                            System.out.println("Such a card does not exist.");
                                        }
                                    } else {
                                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                                        break;
                                    }
                                    break;
                                case 4:
                                    connect.closeAccount(inputCardNumber);
                                    System.out.println("The account has been closed!");
                                    break;
                                case 5:
                                    System.out.println("You have successfully logged out!");
                                    mainMenu();
                                    break;
                            }
                        }

                    } else {
                        System.out.println("Wrong card number or PIN!");
                        break;
                    }

            }

        }

    }


    public static void mainMenu() {
        System.out.println();
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        System.out.println();
    }

    public static void loginMenu() {
        System.out.println();
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        System.out.println();
    }

    public static Account createCard() {
        Account anAccount = new Account();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(anAccount.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(anAccount.getPIN());
        return anAccount;
    }

    public static boolean luhnValidation(String cardNumber) {
        boolean validCard = false;

        // Get last number
        String lastNumber = cardNumber.substring(cardNumber.length() - 1);
        String shortNumber = cardNumber.substring(0, cardNumber.length() - 1);

        if(lastNumber.equals(Account.createCheckSum(shortNumber))) {
            validCard = true;
        }

        return validCard;
    }


}
