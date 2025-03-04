package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.House;
import lab5.util.FlatReader;

/**
 * Команда для подсчета элементов с заданным значением поля house
 */
public class CountByHouseCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private final FlatReader flatReader;

    public CountByHouseCommand(ConsoleManager consoleManager, CollectionManager collectionManager, FlatReader flatReader) {
        super("count_by_house", "вывести количество элементов, значение поля house которых равно заданному");
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

        consoleManager.println("Введите данные дома для сравнения:");
        House house = flatReader.readFieldsOfHouse();
        if (house == null) {
            consoleManager.printError("Дом не может быть пустым для данной команды");
            return;
        }

        long count = collectionManager.countByHouse(house);
        consoleManager.println("Количество элементов с заданным домом: " + count);
    }
} 
