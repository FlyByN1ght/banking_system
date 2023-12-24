package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Даниил
 * version 1.0
 */
public class FileRead {

    /**
     * Выводит информацию о счетах из файла.
     */
    public static void showAccounts() {
        Map<String, Integer> accounts = AccountReader.readAccounts();
        for (Map.Entry<String, Integer> i : accounts.entrySet()) {
            System.out.println("Счет: " + i.getKey() + ", Баланс: " + i.getValue());
        }
    }

    /**
     * Выводит содержимое файла транзакций.
     */
    public static void showTransaction() {
        String filePath = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\report.txt";

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
