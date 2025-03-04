package lab5.managers;

/**
 * Менеджер для работы с консолью
 */
public class ConsoleManager {
    public void println(String text) {
        System.out.println(text);
    }

    public void print(String text) {
        System.out.print(text);
    }

    public void printError(String text) {
        System.err.println("Ошибка: " + text);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
    }
}
