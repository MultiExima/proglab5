package lab5.commands;

/**
 * Абстрактный класс, представляющий команду.
 * Все команды должны наследоваться от этого класса.
 */
public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Название команды
    public String getName() {
        return name;
    }

    // Описание команды
    public String getDescription() {
        return description;
    }

    // Выполняет команду
    public abstract void execute(String[] args);
} 
