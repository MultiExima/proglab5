package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;
import lab5.util.FlatReader;

/**
 * Команда для обновления элемента коллекции по id
 */
public class UpdateCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private final FlatReader flatReader;
    private Flat oldFlat;

    public UpdateCommand(ConsoleManager consoleManager, CollectionManager collectionManager, FlatReader flatReader) {
        super("update", "обновить значение элемента коллекции по его id");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
        this.flatReader = flatReader;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length != 1) {
                consoleManager.printError("Использование: update id");
                return;
            }

            long id = Long.parseLong(args[0]);
            oldFlat = collectionManager.getById(id);
            
            if (oldFlat == null) {
                consoleManager.printError("Элемент с id=" + id + " не найден");
                return;
            }

            consoleManager.println("Введите новые значения для квартиры с id=" + id);
            Flat newFlat = flatReader.readFlat();
            newFlat.setId(id);
            newFlat.setCreationDate(oldFlat.getCreationDate());
            
            if (collectionManager.update(id, newFlat)) {
                consoleManager.println("Элемент успешно обновлен");
            } else {
                consoleManager.printError("Не удалось обновить элемент");
            }
            
        } catch (NumberFormatException e) {
            consoleManager.printError("id должен быть целым числом");
        } catch (Exception e) {
            consoleManager.printError(e.getMessage());
        }
    }
} 
