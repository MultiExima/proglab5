package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;
import lab5.util.FlatReader;

/**
 * Команда для добавления нового элемента в коллекцию
 */
public class AddCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private final FlatReader flatReader;

    public AddCommand(ConsoleManager consoleManager, CollectionManager collectionManager, FlatReader flatReader) {
        super("add", "добавить новый элемент в коллекцию");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
        this.flatReader = flatReader;
    }

    @Override
    public void execute(String[] args) {
        try {
            Flat flat = flatReader.readFlat();
            collectionManager.addFlat(flat);
            consoleManager.println("Квартира успешно добавлена в коллекцию");
        } catch (Exception e) {
            consoleManager.printError("Ошибка при добавлении квартиры: " + e.getMessage());
        }
    }
}
