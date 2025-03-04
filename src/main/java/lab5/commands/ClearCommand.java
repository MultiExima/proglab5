package lab5.commands;

import java.util.Stack;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;

/**
 * Команда для очистки коллекции
 */
public class ClearCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private Stack<Flat> clearedFlats;

    public ClearCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getSize() == 0) {
            consoleManager.println("Коллекция уже пуста");
            return;
        }

        clearedFlats = new Stack<>();
        clearedFlats.addAll(collectionManager.getCollection());
        
        collectionManager.clear();
        consoleManager.println("Коллекция успешно очищена");
    }
} 
