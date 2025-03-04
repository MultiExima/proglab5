package lab5.util;

import java.util.Scanner;

import lab5.managers.ConsoleManager;
import lab5.managers.ScannerManager;
import lab5.models.Coordinates;
import lab5.models.Flat;
import lab5.models.House;
import lab5.models.enums.Furnish;
import lab5.models.enums.Transport;
import lab5.models.enums.View;

/**
 * Класс для чтения данных о квартире из консоли.
 * Предоставляет методы для интерактивного ввода всех полей квартиры
 * с валидацией введенных данных.
 * 
 * @author your_name
 * @version 1.0
 */
public class FlatReader {
    private final Scanner scanner = ScannerManager.getScanner();
    private final ConsoleManager consoleManager;

    /**
     * Создает новый экземпляр FlatReader.
     * 
     * @param consoleManager менеджер консоли для вывода сообщений
     */
    public FlatReader(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    /**
     * Читает все данные о квартире из консоли.
     * 
     * @return новый объект Flat с введенными данными
     */
    public Flat readFlat() {
        String name = readName();
        Coordinates coordinates = readCoordinates();
        Long area = readArea();
        int numberOfRooms = readNumberOfRooms();
        Furnish furnish = readFurnish();
        View view = readView();
        Transport transport = readTransport();
        House house = readHouse();

        return new Flat(name, coordinates, area, numberOfRooms,
                furnish, view, transport, house);
    }

    /**
     * Читает название квартиры из консоли.
     * Название не может быть пустым и должно содержать хотя бы один буквенно-цифровой символ.
     * 
     * @return введенное название квартиры
     */
    public String readName() {
        while (true) {
            try {
                consoleManager.print("Введите название квартиры: ");
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    consoleManager.printError("Название не может быть пустым");
                    continue;
                }
                if (!name.matches(".*[a-zA-Zа-яА-Я0-9].*")) {
                    consoleManager.printError("Название должно содержать хотя бы один буквенно-цифровой символ");
                    continue;
                }
                return name;
            } catch (Exception e) {
                consoleManager.printError("Некорректный ввод");
            }
        }
    }

    /**
     * Читает координаты квартиры из консоли.
     * Координата X должна быть больше -371.
     * 
     * @return объект Coordinates с введенными координатами
     */
    public Coordinates readCoordinates() {
        Double x = null;
        Integer y = null;

        while (x == null) {
            try {
                consoleManager.print("Введите координату X (должна быть больше -371): ");
                String input = scanner.nextLine().trim();
                double tempX = Double.parseDouble(input);
                if (tempX <= -371) {
                    consoleManager.printError("Координата X должна быть больше -371");
                    continue;
                }
                x = tempX;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное число");
            }
        }

        while (y == null) {
            try {
                consoleManager.print("Введите координату Y: ");
                String input = scanner.nextLine().trim();
                y = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное целое число");
            }
        }
        return new Coordinates(x, y);
    }

    /**
     * Читает площадь квартиры из консоли.
     * Площадь должна быть положительным числом.
     * 
     * @return площадь квартиры
     */
    public Long readArea() {
        while (true) {
            try {
                consoleManager.print("Введите площадь квартиры (положительное число): ");
                String input = scanner.nextLine().trim();
                long area = Long.parseLong(input);
                if (area <= 0) {
                    consoleManager.printError("Площадь должна быть больше 0");
                    continue;
                }
                return area;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное число");
            }
        }
    }

    /**
     * Читает количество комнат из консоли.
     * Количество комнат должно быть от 1 до 7.
     * 
     * @return количество комнат
     */
    public int readNumberOfRooms() {
        while (true) {
            try {
                consoleManager.print("Введите количество комнат (от 1 до 7): ");
                String input = scanner.nextLine().trim();
                int rooms = Integer.parseInt(input);
                if (rooms <= 0 || rooms > 7) {
                    consoleManager.printError("Количество комнат должно быть больше 0 и не более 7");
                    continue;
                }
                return rooms;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное целое число");
            }
        }
    }

    /**
     * Читает тип отделки из консоли.
     * Позволяет выбрать значение enum Furnish по номеру или названию.
     * 
     * @return выбранный тип отделки или null
     */
    public Furnish readFurnish() {
        while (true) {
            try {
                consoleManager.println("Выберите значение переменной furnish:");
                Furnish[] values = Furnish.values();
                for (int i = 0; i < values.length; i++) {
                    consoleManager.println((i + 1) + ". " + values[i]);
                }
                consoleManager.println("0. Пропустить");
                
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.equals("0")) {
                    return null;
                }
                

                try {
                    int choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= values.length) {
                        return values[choice - 1];
                    }
                    consoleManager.printError("Выберите число от 0 до " + values.length);
                } catch (NumberFormatException e) {
                    try {
                        return Furnish.valueOf(input.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        consoleManager.printError("Введите корректное значение из списка или его номер");
                    }
                }
            } catch (Exception e) {
                consoleManager.printError("Некорректный ввод");
            }
        }
    }

    /**
     * Читает вид из окна из консоли.
     * Позволяет выбрать значение enum View по номеру или названию.
     * 
     * @return выбранный вид или null
     */
    public View readView() {
        while (true) {
            try {
                consoleManager.println("Выберите значение переменной view:");
                View[] values = View.values();
                for (int i = 0; i < values.length; i++) {
                    consoleManager.println((i + 1) + ". " + values[i]);
                }
                consoleManager.println("0. Пропустить");
                
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || input.equals("0")) {
                    return null;
                }
                
                try {
                    int choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= values.length) {
                        return values[choice - 1];
                    }
                    consoleManager.printError("Выберите число от 0 до " + values.length);
                } catch (NumberFormatException e) {
                    try {
                        return View.valueOf(input.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        consoleManager.printError("Введите корректное значение из списка");
                    }
                }
            } catch (Exception e) {
                consoleManager.printError("Некорректный ввод");
            }
        }
    }

    /**
     * Читает данные о транспортной доступности из консоли.
     * Позволяет выбрать значение enum Transport по номеру или названию.
     * 
     * @return выбранное значение транспортной доступности
     */
    public Transport readTransport() {
        while (true) {
            try {
                consoleManager.println("Выберите значение переменной transport:");
                Transport[] values = Transport.values();
                for (int i = 0; i < values.length; i++) {
                    consoleManager.println((i + 1) + ". " + values[i]);
                }
                
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    consoleManager.printError("Значение не может быть пустым");
                    continue;
                }
                
                try {
                    int choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= values.length) {
                        return values[choice - 1];
                    }
                    consoleManager.printError("Выберите число от 1 до " + values.length);
                } catch (NumberFormatException e) {
                    try {
                        return Transport.valueOf(input.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        consoleManager.printError("Введите корректное значение из списка");
                    }
                }
            } catch (Exception e) {
                consoleManager.printError("Некорректный ввод");
            }
        }
    }

    /**
     * Читает данные о доме из консоли.
     * Позволяет пропустить ввод данных о доме (вернет null).
     * Если данные вводятся, то все поля дома обязательны для заполнения.
     * 
     * @return объект House с введенными данными или null
     */
    public House readHouse() {
        consoleManager.println("Нажмите на любую клавишу, чтобы заполнить информацию о доме:");
        consoleManager.println("Или нажмите Enter для пропуска");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }

        String name = null;
        while (name == null) {
            try {
                consoleManager.print("Введите название дома: ");
                String tempName = scanner.nextLine().trim();
                if (tempName.isEmpty()) {
                    consoleManager.printError("Название не может быть пустым");
                    continue;
                }
                name = tempName;
            } catch (Exception e) {
                consoleManager.printError("Некорректный ввод");
            }
        }

        long year = 0;
        while (year <= 0) {
            try {
                consoleManager.print("Введите год постройки (положительное число): ");
                String yearInput = scanner.nextLine().trim();
                long tempYear = Long.parseLong(yearInput);
                if (tempYear <= 0) {
                    consoleManager.printError("Год должен быть больше 0");
                    continue;
                }
                year = tempYear;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное число");
            }
        }

        Integer floors = null;
        while (floors == null) {
            try {
                consoleManager.print("Введите количество этажей (положительное число): ");
                String floorsInput = scanner.nextLine().trim();
                int tempFloors = Integer.parseInt(floorsInput);
                if (tempFloors <= 0) {
                    consoleManager.printError("Количество этажей должно быть больше 0");
                    continue;
                }
                floors = tempFloors;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное целое число");
            }
        }

        return new House(name, year, floors);
    }

    public House readFieldsOfHouse() {
        String name = null;
        while (name == null) {
            try {
                consoleManager.print("Введите название дома: ");
                String tempName = scanner.nextLine().trim();
                if (tempName.isEmpty()) {
                    consoleManager.printError("Название не может быть пустым");
                    continue;
                }
                name = tempName;
            } catch (Exception e) {
                consoleManager.printError("Некорректный ввод");
            }
        }

        long year = 0;
        while (year <= 0) {
            try {
                consoleManager.print("Введите год постройки (положительное число): ");
                String yearInput = scanner.nextLine().trim();
                long tempYear = Long.parseLong(yearInput);
                if (tempYear <= 0) {
                    consoleManager.printError("Год должен быть больше 0");
                    continue;
                }
                year = tempYear;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное число");
            }
        }

        Integer floors = null;
        while (floors == null) {
            try {
                consoleManager.print("Введите количество этажей (положительное число): ");
                String floorsInput = scanner.nextLine().trim();
                int tempFloors = Integer.parseInt(floorsInput);
                if (tempFloors <= 0) {
                    consoleManager.printError("Количество этажей должно быть больше 0");
                    continue;
                }
                floors = tempFloors;
            } catch (NumberFormatException e) {
                consoleManager.printError("Введите корректное целое число");
            }
        }

        return new House(name, year, floors);
    }
}
