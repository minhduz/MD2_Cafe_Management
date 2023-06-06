package ra.business.design;

import java.util.List;
import java.util.Scanner;

public interface ITable<T,E> extends ICafe<T,E> {
    void displayByStatus(List<T> list);
    T updateTableDetail(T t, Scanner scanner);
}
