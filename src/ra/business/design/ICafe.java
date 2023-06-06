package ra.business.design;

import java.util.List;
import java.util.Scanner;

public interface ICafe<T, E> {
    boolean create(T t);
    boolean update(T t);
    List<T> findAll();
    T findByID(E id);
    T inputData(Scanner scanner);
    List<T> readFromFile();
    boolean writeToFile(List<T> list);
    T updateStatus(T t,Scanner scanner);
}
