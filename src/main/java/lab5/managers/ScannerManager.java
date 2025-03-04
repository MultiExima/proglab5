package lab5.managers;

import java.util.Scanner;

/**
 * Менеджер для работы со Scanner.
 */
public class ScannerManager {
    private static Scanner scanner = new Scanner(System.in);
    private ScannerManager() {}

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        ScannerManager.scanner = scanner;
    }
}

