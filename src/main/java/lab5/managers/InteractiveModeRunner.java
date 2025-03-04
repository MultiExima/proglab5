package lab5.managers;

import java.util.Scanner;

/**
 * Класс для управления интерактивным режимом работы программы.
 */
public class InteractiveModeRunner {
    private final CommandInvoker commandInvoker;
    private final ConsoleManager consoleManager;
    private final Scanner scanner;

    public InteractiveModeRunner(CommandInvoker commandInvoker, ConsoleManager consoleManager) {
        this.commandInvoker = commandInvoker;
        this.consoleManager = consoleManager;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Запускает интерактивный режим работы программы.
     * Считывает команды из консоли и выполняет их через CommandInvoker.
     */
    public void start() {
        consoleManager.println("Добро пожаловать! Введите help для получения списка команд.");
        
        String input;
        while (true) {
            consoleManager.print("> ");
            input = scanner.nextLine().trim();
            
            if (input.equals("exit")) {
                consoleManager.print("Вы действительно хотите выйти из программы? (y/n): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y") || confirmation.equals("yes")) {
                    commandInvoker.execute("exit", new String[0]);
                }
                continue;
            }
            
            String[] parts = input.split("\\s+", 2);
            String command = parts[0];
            String[] args = parts.length > 1 ? parts[1].split("\\s+") : new String[0];
            
            commandInvoker.execute(command, args);
        }
    }
} 