package lab5.commands;

import java.util.Stack;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;
import lab5.util.FlatReader;

/**
 * Команда для удаления элементов, меньших чем заданный
 */
public class RemoveLowerCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private final FlatReader flatReader;
    private Stack<Flat> removedFlats;

    public RemoveLowerCommand(ConsoleManager consoleManager, CollectionManager collectionManager, FlatReader flatReader) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
        this.flatReader = flatReader;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getSize() == 0) {
            consoleManager.printError("Коллекция пуста");
            return;
        }

        consoleManager.println("Введите данные квартиры для сравнения:");
        Flat compareFlat = flatReader.readFlat();

        // Сохраняем удаляемые элементы для возможности отмены
        removedFlats = new Stack<>();
        
        int initialSize = collectionManager.getSize();
        collectionManager.removeLower(compareFlat, removedFlats);
        int removedCount = initialSize - collectionManager.getSize();

        if (removedCount > 0) {
            consoleManager.println("Удалено элементов: " + removedCount);
        } else {
            consoleManager.println("Нет элементов, меньших чем заданный");
        }
    }
} 
