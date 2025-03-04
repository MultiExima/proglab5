package lab5.commands;

import java.util.Stack;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;

/**
 * Команда для перемешивания элементов коллекции в случайном порядке
 */
public class ShuffleCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private Stack<Flat> previousState;

    public ShuffleCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getSize() <= 1) {
            consoleManager.println("Коллекция пуста или содержит только один элемент");
            return;
        }

        // Сохраняем текущее состояние для возможности отмены
        previousState = new Stack<>();
        previousState.addAll(collectionManager.getCollection());

        collectionManager.shuffle();
        consoleManager.println("Коллекция успешно перемешана");
    }
} 
