package lab5.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Stack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lab5.models.Flat;
import lab5.models.enums.Furnish;
import lab5.models.enums.Transport;
import lab5.models.enums.View;
/**
 * Менеджер файлов.
 */
public class FileManager {
    private static final String ENV_FILE_PATH = "LAB5_FILE_PATH";
    private static final String DEFAULT_FILE_PATH = "collection.xml";
    
    private final String inputFileName;
    private final ConsoleManager console;
    private CollectionManager collectionManager;

    private XmlMapper createConfiguredMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        return mapper;
    }

    public FileManager(ConsoleManager console) {
        this.console = console;
        this.inputFileName = getFilePath();
        this.collectionManager = null;
    }

 
    public FileManager(ConsoleManager console, CollectionManager collectionManager) {
        this.console = console;
        this.inputFileName = getFilePath();
        this.collectionManager = collectionManager;
    }

    private String getFilePath() {
        String filePath = System.getenv(ENV_FILE_PATH);
        if (filePath == null || filePath.isEmpty()) {
            filePath = DEFAULT_FILE_PATH;
        }
        return filePath;
    }

    // Загрузить коллекцию из файла.
    public Stack<Flat> loadCollection() {
        Stack<Flat> collection = new Stack<>();
        File file = new File(inputFileName);
        if (!canRead(file, console)) {
            return collection;
        }

        try {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                
                StringBuilder xmlContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    xmlContent.append(line).append("\n");
                }
                
                String xml = xmlContent.toString();
                
                if (xml.trim().equals("<Stack/>")) {
                    console.println("Загружена пустая коллекция");
                    return collection;
                }
                
                XmlMapper xmlMapper = createConfiguredMapper();
                List<Flat> loadedList = xmlMapper.readValue(xml, new TypeReference<List<Flat>>() {});

                for (Flat flat : loadedList) {
                    if (isValidFlat(flat)) {
                        collection.push(flat);
                    } else {
                        console.printError("Квартира с id " + flat.getId() + " не прошла валидацию");
                    }
                }
            }
        } catch (Exception e) {
            console.printError("Ошибка чтения файла: " + e.getMessage());
        }
        
        return collection;
    }
    
    // Проверить валидность квартиры.
    private boolean isValidFlat(Flat flat) {
        if (flat == null) return false;

        try {
            // Проверка id
            if (flat.getId() == null || flat.getId() <= 0) {
                console.printError("id должен быть больше 0");
                return false;
            }

            // Проверка name
            if (flat.getName() == null || flat.getName().trim().isEmpty()) {
                console.printError("name не может быть null или пустым");
                return false;
            }

            // Проверка coordinates
            if (flat.getCoordinates() == null) {
                console.printError("coordinates не может быть null");
                return false;
            }
            if (flat.getCoordinates().getX() == null || flat.getCoordinates().getX() <= -371) {
                console.printError("coordinates.x должен быть больше -371");
                return false;
            }
            if (flat.getCoordinates().getY() == null) {
                console.printError("coordinates.y не может быть null");
                return false;
            }

            // Проверка creationDate
            if (flat.getCreationDate() == null) {
                console.printError("creationDate не может быть null");
                return false;
            }

            // Проверка area
            if (flat.getArea() == null || flat.getArea() <= 0) {
                console.printError("area должна быть больше 0");
                return false;
            }

            // Проверка numberOfRooms
            if (flat.getNumberOfRooms() <= 0 || flat.getNumberOfRooms() > 7) {
                console.printError("numberOfRooms должен быть больше 0 и не больше 7");
                return false;
            }

            // Проверка transport
            if (flat.getTransport() == null) {
                console.printError("transport не может быть null");
                return false;
            }
            try {
                Transport.valueOf(flat.getTransport().toString());
            } catch (IllegalArgumentException e) {
                console.printError("Недопустимое значение для transport");
                return false;
            }

            // Проверка house (если есть)
            if (flat.getHouse() != null) {
                if (flat.getHouse().getName() == null) {
                    console.printError("house.name не может быть null");
                    return false;
                }
                if (flat.getHouse().getYear() <= 0) {
                    console.printError("house.year должен быть больше 0");
                    return false;
                }
                if (flat.getHouse().getNumberOfFloors() == null || flat.getHouse().getNumberOfFloors() <= 0) {
                    console.printError("house.numberOfFloors должен быть больше 0");
                    return false;
                }
            }
            
            if (flat.getFurnish() != null) {
                try {
                    Furnish.valueOf(flat.getFurnish().toString());
                } catch (IllegalArgumentException e) {
                    console.printError("Недопустимое значение для furnish");
                    return false;
                }
            }
            if (flat.getView() != null) {
                try {
                    View.valueOf(flat.getView().toString());
                } catch (IllegalArgumentException e) {
                    console.printError("Недопустимое значение для view");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            console.printError("Ошибка при валидации: " + e.getMessage());
            return false;
        }
    }

    // Проверить, можно ли читать файл.
    public static boolean canRead(File file, ConsoleManager console) {
        if (!file.exists()) {
            console.printError("Файл не найден");
            return false;
        }

        if (!file.canRead()) {
            console.printError("Нет прав на чтение файла");
            return false;
        }

        if (!file.isFile()) {
            console.printError("Указанный путь не является файлом");
            return false;
        }

        return true;
    }

    //Сохранить коллекцию в файл.
    public void saveCollection(Stack<Flat> newCollection) {
        try {
            Stack<Flat> existingCollection = new Stack<>();
            File file = new File(inputFileName);
        
            if (file.exists() && file.length() > 0) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                    
                    StringBuilder xmlContent = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        xmlContent.append(line).append("\n");
                    }
                    
                    String xml = xmlContent.toString();
                    if (!xml.trim().equals("<Stack/>")) {
                        XmlMapper xmlMapper = createConfiguredMapper();
                        List<Flat> loadedList = xmlMapper.readValue(xml, new TypeReference<List<Flat>>() {});
                        existingCollection.addAll(loadedList);
            }
        } catch (Exception e) {
                    console.printError("Ошибка чтения существующего файла: " + e.getMessage());
                    return;
                }
            }
            
            existingCollection.addAll(newCollection);
            
            XmlMapper mapper = createConfiguredMapper();
            String xmlResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(existingCollection);
            
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.write(xmlResult);
                writer.flush();
            }
            
            console.println("Коллекция успешно добавлена в файл " + inputFileName);
        } catch (Exception e) {
            console.printError("Ошибка сохранения коллекции в файл: " + e.getMessage());
        }
    }
    
    // Сохранить коллекцию из collectionManager в файл.
    public void saveCollection() {
        if (collectionManager != null) {
            saveCollection(collectionManager.getCollection());
        } else {
            console.printError("CollectionManager не установлен");
        }
    }
}
