package lab5;

import java.util.Stack;

import lab5.commands.AddCommand;
import lab5.commands.ClearCommand;
import lab5.commands.CountByHouseCommand;
import lab5.commands.ExecuteScriptCommand;
import lab5.commands.ExitCommand;
import lab5.commands.FilterByFurnishCommand;
import lab5.commands.HelpCommand;
import lab5.commands.InfoCommand;
import lab5.commands.RemoveAllByViewCommand;
import lab5.commands.RemoveByIdCommand;
import lab5.commands.RemoveFirstCommand;
import lab5.commands.RemoveLowerCommand;
import lab5.commands.SaveCommand;
import lab5.commands.ShowCommand;
import lab5.commands.ShuffleCommand;
import lab5.commands.UpdateCommand;
import lab5.managers.CollectionManager;
import lab5.managers.CommandInvoker;
import lab5.managers.ConsoleManager;
import lab5.managers.FileManager;
import lab5.managers.InteractiveModeRunner;
import lab5.models.Flat;
import lab5.util.FlatReader;

public class Main {
    public static void main(String[] args) {
        // Инициализация менеджеров
        ConsoleManager consoleManager = new ConsoleManager();
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(consoleManager, collectionManager);
        FlatReader flatReader = new FlatReader(consoleManager);
        CommandInvoker commandInvoker = new CommandInvoker(consoleManager);
        
        // Загрузка коллекции из файла
        Stack<Flat> loadedCollection = fileManager.loadCollection();
        collectionManager.setCollection(loadedCollection);
        
        // Регистрация команд
        commandInvoker.register(new HelpCommand(consoleManager, commandInvoker));
        commandInvoker.register(new InfoCommand(consoleManager, collectionManager));
        commandInvoker.register(new ShowCommand(consoleManager, collectionManager));
        commandInvoker.register(new AddCommand(consoleManager, collectionManager, flatReader));
        commandInvoker.register(new UpdateCommand(consoleManager, collectionManager, flatReader));
        commandInvoker.register(new RemoveByIdCommand(consoleManager, collectionManager));
        commandInvoker.register(new ClearCommand(consoleManager, collectionManager));
        commandInvoker.register(new RemoveFirstCommand(consoleManager, collectionManager));
        commandInvoker.register(new ShuffleCommand(consoleManager, collectionManager));
        commandInvoker.register(new RemoveLowerCommand(consoleManager, collectionManager, flatReader));
        commandInvoker.register(new RemoveAllByViewCommand(consoleManager, collectionManager));
        commandInvoker.register(new CountByHouseCommand(consoleManager, collectionManager, flatReader));
        commandInvoker.register(new FilterByFurnishCommand(consoleManager, collectionManager));
        commandInvoker.register(new SaveCommand(consoleManager, collectionManager, fileManager));
        commandInvoker.register(new ExecuteScriptCommand(collectionManager, consoleManager, commandInvoker));
        commandInvoker.register(new ExitCommand(consoleManager));
        
        // Запуск интерактивного режима
        InteractiveModeRunner interactiveModeRunner = new InteractiveModeRunner(commandInvoker, consoleManager);
        interactiveModeRunner.start();
    }
} 