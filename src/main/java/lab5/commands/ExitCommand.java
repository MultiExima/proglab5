package lab5.commands;

import lab5.managers.ConsoleManager;

/**
 * Команда exit - завершает выполнение программы без сохранения в файл.
 */
public class ExitCommand extends Command {
    private final ConsoleManager consoleManager;

    public ExitCommand(ConsoleManager consoleManager) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.consoleManager = consoleManager;
    }

    @Override
    public void execute(String[] args) {
        consoleManager.println("Работа программы завершена");
        System.exit(0);
    }
} 