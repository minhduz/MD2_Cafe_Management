package ra.business.design;

import java.util.List;

public interface IUser<T,E> extends ICafe<T,E> {
    void displayByDate(List<T> list);
    List<T> findByUserNameOrFullName(String name);
    T checkLogin(String username, String password);
}
