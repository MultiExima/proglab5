package lab5.commands;

import lab5.managers.CollectionManager;
import lab5.managers.ConsoleManager;
import lab5.managers.FileManager;

/**
 * Команда для сохранения коллекции в файл
 */
public class SaveCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(ConsoleManager consoleManager, CollectionManager collectionManager, FileManager fileManager) {
        super("save", "сохранить коллекцию в файл");
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String[] args) {
        fileManager.saveCollection(collectionManager.getCollection());
    }
} 
