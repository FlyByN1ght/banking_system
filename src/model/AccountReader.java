package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Даниил
 * verion 1.1
 */
public class AccountReader {

    private static final String ACCOUNTS_FILE = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\accounts.txt";

    /**
     * Читает аккаунты из файла и возвращает их в виде Map.
     *
     * @return Map, где ключ - номер счета, значение - баланс
     */
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

    /**
     * Обновляет файл с аккаунтами на основе переданной Map.
     *
     * @param accounts Map с аккаунтами для записи в файл
     */
    public static void updateAccountsFile(Map<String, Integer> accounts) {
        try (FileWriter writer = new FileWriter(AccountReader.ACCOUNTS_FILE)) {
            for (Map.Entry<String, Integer> entry : accounts.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл accounts.txt: " + e.getMessage());
        }
    }
}