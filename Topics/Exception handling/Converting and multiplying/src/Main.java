import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            try {
                if (Integer.parseInt(input) == 0) {
                    break;
                } else {
                    System.out.println(Integer.parseInt(input) * 10);
                }
            } catch (Exception e) {
                System.out.println("Invalid user input: " + input);
            }
        }
    }
}