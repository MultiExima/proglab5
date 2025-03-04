package lab5.managers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

import lab5.models.Flat;
import lab5.models.House;
import lab5.models.enums.Furnish;
import lab5.models.enums.View;

/**
 * Класс для управления коллекцией объектов типа Flat.
 * Предоставляет методы для добавления, получения, обновления и удаления элементов коллекции.
 */
public class CollectionManager {
    private Stack<Flat> collection;
    private final LocalDateTime initializationDate;
    private Long nextId = 1L;

    public CollectionManager() {
        this.collection = new Stack<>();
        this.initializationDate = LocalDateTime.now();
    }

    // Получить следующий доступный id
    private Long getNextId() {
        Long maxId = collection.stream()
                .map(Flat::getId)
                .max(Long::compareTo)
                .orElse(0L);
        return maxId + 1;
    }

    // Добавить квартиру в коллекцию
    public void addFlat(Flat flat) {
        flat.setId(getNextId());
        collection.push(flat);
    }

    // Получить коллекцию
    public Stack<Flat> getCollection() {
        return collection;
    }

    // Получить дату инициализации коллекции
    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    // Получить тип коллекции
    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }

    // Получить дату инициализации коллекции в виде строки
    public String getInitializationDateString() {
        return initializationDate.toString();
    }

    // Получить количество элементов в коллекции
    public int getSize() {
        return collection.size();
    }

    // Получить элемент по id
    public Flat getById(long id) {
        return collection.stream()
                .filter(flat -> flat.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Обновить элемент по id
    public boolean update(long id, Flat newFlat) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId().equals(id)) {
                newFlat.setId(id);
                collection.set(i, newFlat);
                return true;
            }
        }
        return false;
    }

    // Удалить элемент по id
    public boolean removeById(long id) {
        return collection.removeIf(flat -> flat.getId().equals(id));
    }

    // Очистить коллекцию
    public void clear() {
        collection.clear();
    }

    // Удалить первый элемент коллекции
    public Flat removeFirst() {
        if (!collection.isEmpty()) {
            return collection.remove(0);
        }
        return null;
    }

    // Перемешать элементы коллекции
    public void shuffle() {
        java.util.Collections.shuffle(collection);
    }

    // Установить новую коллекцию
    public void setCollection(Stack<Flat> newCollection) {
        this.collection = newCollection;
        nextId = getNextId();
    }

    
    // Удалить элементы, меньшие чем заданный
    public void removeLower(Flat compareFlat, Stack<Flat> removedFlats) {
        Stack<Flat> newCollection = new Stack<>();
        
        for (Flat flat : collection) {
            if (flat.compareTo(compareFlat) >= 0) {
                newCollection.push(flat);
            } else {
                removedFlats.push(flat);
            }
        }
        
        collection = newCollection;
    }

    // Удалить элементы с заданным значением поля view
    public void removeAllByView(View view, Stack<Flat> removedFlats) {
        Stack<Flat> newCollection = new Stack<>();
        
        for (Flat flat : collection) {
            if (!view.equals(flat.getView())) {
                newCollection.push(flat);
            } else {
                removedFlats.push(flat);
            }
        }
        
        collection = newCollection;
    }

    // Подсчитать количество элементов с заданным значением поля house
    public long countByHouse(House house) {
        return collection.stream()
                .filter(flat -> house.equals(flat.getHouse()))
                .count();
    }

    // Отфильтровать элементы по значению поля furnish
    public List<Flat> filterByFurnish(Furnish furnish) {
        return collection.stream()
                .filter(flat -> furnish.equals(flat.getFurnish()))
                .collect(java.util.stream.Collectors.toList());
    }
}
