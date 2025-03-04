package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;

/**
 * Команда для вывода информации о коллекции
 */
public class InfoCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;

    public InfoCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        consoleManager.println("Информация о коллекции:");
        consoleManager.println("Тип: " + collectionManager.getCollectionType());
        consoleManager.println("Дата инициализации: " + collectionManager.getInitializationDate());
        consoleManager.println("Количество элементов: " + collectionManager.getSize());
    }
} 
