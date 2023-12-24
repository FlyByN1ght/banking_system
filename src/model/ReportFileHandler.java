package model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportFileHandler {
    private static final String REPORT_FILE = "C:\\Users\\Daniil\\IdeaProjects\\banking_system\\src\\report.txt";

    /**
     * Логирует транзакцию в файл отчета.
     *
     * @param fileName      Имя файла, с которым связана транзакция.
     * @param senderAccount Номер счета отправителя.
     * @param receiverAccount Номер счета получателя.
     * @param transferAmount Сумма перевода.
     * @param success       Успешность транзакции.
     * @param failureReason Причина ошибки, если транзакция не успешна.
     */
    public static void logTransaction(String fileName, String senderAccount, String receiverAccount, double transferAmount, boolean success, String failureReason) {
        String status;
        if (success) {
            status = "успешно обработан";
        } else {
            status = "ошибка во время обработки";
            if (failureReason != null) {
                status += ": " + failureReason;
            }
        }
        String transactionDetails = String.format("перевод с %s на %s %.2f | %s",
                senderAccount, receiverAccount, transferAmount, status);

        try {
            updateReportFile(fileName, status, transactionDetails);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл-отчет: " + e.getMessage());
        }
    }

    /**
     * Обновляет файл отчета новой транзакцией.
     *
     * @param fileName Имя файла, с которым связана транзакция.
     * @param status   Статус транзакции.
     * @param details  Детали транзакции.
     * @throws IOException В случае ошибки при записи в файл.
     */
    private static void updateReportFile(String fileName, String status, String details) throws IOException {
        try (FileWriter writer = new FileWriter(REPORT_FILE, true)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            writer.write(timestamp + " | " + fileName + " | " + details + "\n");
        }
    }
}