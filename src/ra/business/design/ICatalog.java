package ra.business.design;

import java.util.List;
import java.util.Scanner;

public interface ICatalog<T,E> extends ICafe<T,E> {
    List<T> findByCatalogName(String name);
    void displayByPriority(List<T> list);
    T updateCatalogDetail(T t, Scanner scanner);
}
