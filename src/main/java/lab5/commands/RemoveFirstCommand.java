package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;

/**
 * Команда для удаления первого элемента коллекции
 */
public class RemoveFirstCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private Flat removedFlat;

    public RemoveFirstCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getSize() == 0) {
            consoleManager.printError("Коллекция пуста");
            return;
        }

        removedFlat = collectionManager.removeFirst();
        if (removedFlat != null) {
            consoleManager.println("Первый элемент успешно удален");
        } else {
            consoleManager.printError("Не удалось удалить первый элемент");
        }
    }

} 
