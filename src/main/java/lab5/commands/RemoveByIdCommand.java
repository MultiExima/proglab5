package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.models.Flat;

/**
 * Команда для удаления элемента коллекции по id
 */
public class RemoveByIdCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private Flat removedFlat;

    public RemoveByIdCommand(ConsoleManager consoleManager, CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length != 1) {
                consoleManager.printError("Использование: remove_by_id id");
                return;
            }

            long id = Long.parseLong(args[0]);
            removedFlat = collectionManager.getById(id);
            
            if (removedFlat == null) {
                consoleManager.printError("Элемент с id=" + id + " не найден");
                return;
            }

            if (collectionManager.removeById(id)) {
                consoleManager.println("Элемент с id=" + id + " успешно удален");
            } else {
                consoleManager.printError("Не удалось удалить элемент");
            }
            
        } catch (NumberFormatException e) {
            consoleManager.printError("id должен быть целым числом");
        } catch (Exception e) {
            consoleManager.printError(e.getMessage());
        }
    }

} 
