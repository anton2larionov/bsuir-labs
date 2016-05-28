package hamiltonian;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ячейка матрицы P
 */
public class Cell {
    /**
     * список элементов, хранящийся в ячейке
     */
    private List<String> list = new LinkedList<>();

    /**
     * @return true если ячейка пуста
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Добавление элемента в ячейку
     *
     * @param el элемент
     */
    public void addElement(String el) {
        list.add(el);
    }

    /**
     * Добавление списка элементов в ячейку
     *
     * @param timesString список элементов
     */
    public void addElements(List<String> timesString) {
        if (timesString == null || timesString.isEmpty()) return;
        list.addAll(timesString.stream().collect(Collectors.toList()));
    }

    public void addElements(String v, List<String> timesString) {
        if (timesString == null || timesString.isEmpty()) return;
        list.addAll(timesString.stream().map(s -> v + s).collect(Collectors.toList()));
    }

    /**
     * @return список элементов, хранящийся в ячейке
     */
    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }

}