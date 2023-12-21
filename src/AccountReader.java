import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccountReader {

    private static final String ACCOUNTS_FILE = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\accounts.txt";

    public static Map<String, Integer> readAccounts() {
        Map<String, Integer> accounts = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(ACCOUNTS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(":");

                if (fields.length == 2) {
                    String accountNumber = fields[0];
                    int balance = Integer.parseInt(fields[1]);
                    accounts.put(accountNumber, balance);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + ACCOUNTS_FILE);
        }
        return accounts;
    }
}