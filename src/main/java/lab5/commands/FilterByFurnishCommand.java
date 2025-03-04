package lab5.commands;

import java.util.List;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;
import lab5.models.enums.Furnish;

/**
 * Команда для вывода элементов, значение поля furnish которых равно заданному
 */
public class FilterByFurnishCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;

    public FilterByFurnishCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("filter_by_furnish", "вывести элементы, значение поля furnish которых равно заданному");
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
            consoleManager.printError("Использование: filter_by_furnish furnish");
            return;
        }

        Furnish furnish;
        try {
            furnish = Furnish.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            consoleManager.printError("Некорректное значение furnish. Доступные значения: " + 
                java.util.Arrays.toString(Furnish.values()));
            return;
        }

        List<Flat> filtered = collectionManager.filterByFurnish(furnish);
        
        if (filtered.isEmpty()) {
            consoleManager.println("Элементы с заданным значением furnish не найдены");
        } else {
            consoleManager.println("Элементы с furnish = " + furnish + ":");
            filtered.forEach(flat -> consoleManager.println(flat.toString()));
        }
    }
} 
