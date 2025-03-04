package lab5.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lab5.managers.CollectionManager;
import lab5.managers.CommandInvoker;
import lab5.managers.ConsoleManager;

/**
 * Команда execute_script: считать и исполнить скрипт из указанного файла
 */
public class ExecuteScriptCommand extends Command {
    private final CommandInvoker commandInvoker;
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private static final Set<String> executedScripts = new HashSet<>();

    public ExecuteScriptCommand(CollectionManager collectionManager, ConsoleManager consoleManager, CommandInvoker commandInvoker) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.commandInvoker = commandInvoker;
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleManager.printError("Использование: execute_script file_name");
            return;
        }

        String fileName = args[0];

        // Проверка на рекурсию
        if (!executedScripts.add(fileName)) {
            consoleManager.printError("Обнаружена рекурсия! Скрипт " + fileName + " уже выполняется");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    consoleManager.println("$ " + line); // Показываем, какая команда выполняется
                    String[] commandParts = line.split("\\s+", 2);
                    String commandName = commandParts[0];
                    String[] commandArgs = commandParts.length > 1 ? 
                        commandParts[1].split("\\s+") : new String[0];
                    commandInvoker.execute(commandName, commandArgs);
                }
            }
            consoleManager.println("Скрипт " + fileName + " выполнен");
        } catch (IOException e) {
            consoleManager.printError("Ошибка при чтении файла скрипта: " + e.getMessage());
        } finally {
            executedScripts.remove(fileName); // Удаляем файл из списка выполняемых скриптов
        }
    }
} 