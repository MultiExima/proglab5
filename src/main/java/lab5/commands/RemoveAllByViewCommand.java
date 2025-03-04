package lab5.commands;

import java.util.Stack;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;
import lab5.models.enums.View;

/**
 * Команда для удаления элементов с заданным значением поля view
 */
public class RemoveAllByViewCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private Stack<Flat> removedFlats;

    public RemoveAllByViewCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("remove_all_by_view", "удалить из коллекции все элементы, значение поля view которого эквивалентно заданному");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getSize() == 0) {
            consoleManager.printError("Коллекция пуста");
            return;
        }

        if (args.length != 1) {
            consoleManager.printError("Использование: remove_all_by_view view");
            return;
        }

        View view;
        try {
            view = View.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            consoleManager.printError("Некорректное значение view. Доступные значения: " + 
                java.util.Arrays.toString(View.values()));
            return;
        }

        // Сохраняем удаляемые элементы для возможности отмены
        removedFlats = new Stack<>();
        
        int initialSize = collectionManager.getSize();
        collectionManager.removeAllByView(view, removedFlats);
        int removedCount = initialSize - collectionManager.getSize();

        if (removedCount > 0) {
            consoleManager.println("Удалено элементов: " + removedCount);
        } else {
            consoleManager.println("Нет элементов с заданным значением view");
        }
    }

} 
