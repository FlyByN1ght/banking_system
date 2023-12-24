package model;

import exception.EmptyDirectoryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс для обработки файлов с транзакциями и их перемещения в архив.
 *
 * @author Даниил
 * version 1.0
 */
public class FileParse {
    private static final String INPUT_DIRECTORY = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\Input";
    private static final String ARCHIVE_DIRECTORY = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\Archive";
    private final Map<String, Integer> ACCOUNTS;

    /**
     * Конструктор класса, инициализирует переменную ACCOUNTS данными из файла аккаунтов.
     */
    public FileParse() {
        this.ACCOUNTS = AccountReader.readAccounts();
    }

    /**
     * Метод для чтения и обработки файлов с транзакциями.
     */
    public void readAndProcessFiles() {
        File inputDirectory = new File(INPUT_DIRECTORY);
        File archiveDirectory = new File(ARCHIVE_DIRECTORY);

        File[] files = inputDirectory.listFiles();

        if (files == null || files.length == 0) {
            throw new EmptyDirectoryException("Каталог 'input' пуст или произошла ошибка при получении списка файлов.");
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                processFile(file);
                moveFileToArchive(file, archiveDirectory);
            }
        }
    }

    /**
     * Метод для обработки отдельного файла с транзакциями.
     *
     * @param file Файл с транзакциями
     */
    private void processFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\\|");

                if (fields.length == 3) {
                    String senderAccount = fields[0].trim();
                    String receiverAccount = fields[1].trim();
                    int transferAmount = Integer.parseInt(fields[2].trim());

                    boolean success = performTransfer(senderAccount, receiverAccount, transferAmount, ACCOUNTS);

                    if (!success) {
                        String failureReason = getFailureReason(senderAccount, receiverAccount, transferAmount, ACCOUNTS);
                        ReportFileHandler.logTransaction(file.getName(), senderAccount, receiverAccount, transferAmount, false, failureReason);
                    } else {
                        ReportFileHandler.logTransaction(file.getName(), senderAccount, receiverAccount, transferAmount, true, null);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + file.getAbsolutePath());
        }
    }

    /**
     * Метод для выполнения транзакции между счетами.
     *
     * @param senderAccount    Номер счета отправителя
     * @param receiverAccount  Номер счета получателя
     * @param transferAmount   Сумма транзакции
     * @param accounts         Map с данными аккаунтов
     * @return true, если транзакция успешна, иначе false
     */
    private boolean performTransfer(String senderAccount, String receiverAccount, int transferAmount, Map<String, Integer> accounts) {
        String failureReason = getFailureReason(senderAccount, receiverAccount, transferAmount, accounts);

        if (failureReason != null) {
            return false;
        }

        int senderBalance = accounts.get(senderAccount);

        accounts.put(senderAccount, senderBalance - transferAmount);
        accounts.put(receiverAccount, accounts.get(receiverAccount) + transferAmount);

        AccountReader.updateAccountsFile(accounts);
        return true;
    }

    /**
     * Метод для получения причины неудачной транзакции.
     *
     * @param senderAccount    Номер счета отправителя
     * @param receiverAccount  Номер счета получателя
     * @param transferAmount   Сумма транзакции
     * @param accounts         Map с данными аккаунтов
     * @return Строка с причиной неудачной транзакции или null, если транзакция успешна
     */
    private String getFailureReason(String senderAccount, String receiverAccount, int transferAmount, Map<String, Integer> accounts) {
        if (!accounts.containsKey(senderAccount)) {
            return "отсутствует счет отправителя";
        }

        if (!accounts.containsKey(receiverAccount)) {
            return "отсутствует счет получателя";
        }

        if (transferAmount <= 0) {
            return "неверная сумма перевода";
        }

        if (accounts.get(senderAccount) < transferAmount) {
            return "недостаточно средств";
        }

        return null;
    }

    /**
     * Метод для перемещения обработанного файла в архив.
     *
     * @param file             Файл для перемещения
     * @param archiveDirectory Директория для архивации
     */
    private void moveFileToArchive(File file, File archiveDirectory) {
        File destination = new File(archiveDirectory, file.getName());

        if (file.renameTo(destination)) {
            System.out.println("Файл перемещен в архив: " + destination.getAbsolutePath());
        } else {
            System.out.println("Не удалось переместить файл в архив.");
        }
    }
}
