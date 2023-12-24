package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        Map<String, Double> accounts = AccountReader.readAccounts();
        for (Map.Entry<String, Double> i : accounts.entrySet()) {
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

    /**
     * Выводит транзакции из файла report.txt в заданном временном диапазоне.
     */
    public static void showTransactionsByDateRange() {
        String filePath = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\report.txt";

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Ввод начальной даты с консоли
            Date startDate = readDateFromConsole("Введите начальную дату (yyyy-MM-dd HH:mm:ss): ");

            // Ввод конечной даты с консоли
            Date endDate = readDateFromConsole("Введите конечную дату (yyyy-MM-dd HH:mm:ss): ");

            while ((line = bufferedReader.readLine()) != null) {
                String[] transactionDetails = line.split("\\|");

                if (transactionDetails.length >= 3) {
                    String timestampString = transactionDetails[0].trim();
                    Date timestamp = dateFormat.parse(timestampString);

                    // Проверка, попадает ли дата в заданный диапазон
                    if (isDateInRange(timestamp, startDate, endDate)) {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Считывает дату с консоли с учетом формата "yyyy-MM-dd HH:mm:ss".
     *
     * @param dateFromConsole для ввода
     * @throws IOException    В случае ошибки ввода
     * @throws ParseException В случае ошибки разбора даты
     */
    private static Date readDateFromConsole(String dateFromConsole) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.print(dateFromConsole);
                String dateString = consoleReader.readLine();
                return dateFormat.parse(dateString);
            } catch (ParseException ex) {
                System.out.println("Неверный формат даты. Пожалуйста, введите дату в формате yyyy-MM-dd HH:mm:ss.");
            }
        }
    }

    /**
     * Проверяет, попадает ли дата в заданный временной диапазон.
     *
     * @param date      Проверяемая дата
     * @param startDate Начальная дата диапазона
     * @param endDate   Конечная дата диапазона
     * @return true, если дата входит в диапазон, иначе false
     */
    private static boolean isDateInRange(Date date, Date startDate, Date endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }
}
