package lab5.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import lab5.commands.Command;

/**
 * Класс, управляющий командами и их выполнением
 */
public class CommandInvoker {
    private final Map<String, Command> commands = new HashMap<>();
    private final Stack<Command> commandHistory = new Stack<>();
    private final ConsoleManager consoleManager;

    public CommandInvoker(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    // Регистрирует команду
    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    //Выполняет команду по её имени
    public void execute(String commandName, String[] args) {
        Command command = commands.get(commandName);
        if (command == null) {
            consoleManager.printError("Команда '" + commandName + "' не найдена");
            return;
        }
        
        try {
            command.execute(args);
            commandHistory.push(command);
            if (commandHistory.size() > 6) {
                commandHistory.remove(0);
            }
        } catch (Exception e) {
            consoleManager.printError("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    // Возвращает map зарегистрированных команд
    public Map<String, Command> getCommands() {
        return commands;
    }

    // Возвращает историю выполненных команд
    public Stack<Command> getCommandHistory() {
        return commandHistory;
    }
}
