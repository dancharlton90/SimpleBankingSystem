import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = {scanner.nextInt(), scanner.nextInt(), scanner.nextInt()};

        // write your code here
        int sum = Arrays.stream(arr).sum();

        System.out.println(sum);
    }
}