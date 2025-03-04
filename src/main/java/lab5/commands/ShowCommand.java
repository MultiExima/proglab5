package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;

/**
 * Команда для вывода всех элементов коллекции
 */
public class ShowCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;

    public ShowCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getSize() == 0) {
            consoleManager.println("Коллекция пуста");
            return;
        }

        collectionManager.getCollection().stream()
            .map(Object::toString)
            .forEach(consoleManager::println);
    }
} 
