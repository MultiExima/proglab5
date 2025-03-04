package lab5.commands;

import lab5.managers.CommandInvoker;
import lab5.managers.ConsoleManager;

public class HelpCommand extends Command {
    private final ConsoleManager consoleManager;
    private final CommandInvoker commandInvoker;

    public HelpCommand(ConsoleManager consoleManager, CommandInvoker commandInvoker) {
        super("help", "вывести справку по доступным командам");
        this.consoleManager = consoleManager;
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute(String[] args) {
        consoleManager.println("Доступные команды:");
        for (Command command : commandInvoker.getCommands().values()) {
            consoleManager.println(command.getName() + " : " + command.getDescription());
        }
    }

} 
