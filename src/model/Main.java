package model;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileParse fileParse = new FileParse();

        while (true) {
            System.out.println("1. Парсинг файлов");
            System.out.println("2. Вывод истории переводов");
            System.out.println("3. Вывод всех счетов");
            System.out.println("4. Выход");
            System.out.print("Выберите действие:");
            int choice = new Scanner(System.in).nextInt();

            switch (choice) {
                case 1 -> fileParse.readAndProcessFiles();
                case 2 -> FileRead.showTransaction();
                case 3 -> FileRead.showAccounts();
                case 4 -> System.exit(0);
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }
}
