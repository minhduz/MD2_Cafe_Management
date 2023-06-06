package ra.business.design;

import java.util.List;
import java.util.Scanner;

public interface IProduct<T,E> extends ICafe<T,E> {
    void displayByCatalog(List<T> T);
    List<T> findByProductName(String name);
    List<T> findByProductPrice(double price);
    T updateProductDetail(T t, Scanner scanner);
 }
