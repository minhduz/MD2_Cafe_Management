package ra.config;

import ra.business.entity.Catalog;
import ra.business.entity.Product;
import ra.business.entity.Table;
import ra.business.entity.User;

import java.util.List;
import java.util.regex.Pattern;

public class ShopValidation {
    public static boolean checkValidateUserName(String str) {
        String userNamePattern = "^[A-Za-z0-9]+[A-Za-z0-9]$";
        return Pattern.compile(userNamePattern)
                .matcher(str)
                .matches();
    }

    public static boolean checkStringLength(String str, int max, int min) {
        return str.length() <= max && str.length() >= min;
    }

    public static boolean checkExistUserName(List<User> list, String username) {
        for (User user : list) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistCatalogName(List<Catalog> list, String catalogName) {
        for (Catalog catalog : list) {
            if (catalog.getCatalogName().equals(catalogName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDateFormat(String str) {
        String datePattern = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        return Pattern.compile(datePattern)
                .matcher(str)
                .matches();
    }

    public static boolean checkInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkEmail(String str) {
        String emailPattern = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        return Pattern.compile(emailPattern)
                .matcher(str)
                .matches();
    }

    public static boolean checkPhone(String str) {
        String phonePattern = "^(84|0[3|5|7|8|9])+([0-9]{8})$";
        return Pattern.compile(phonePattern)
                .matcher(str)
                .matches();
    }

    public static boolean checkTableID(String str) {
        String tableIDPattern = "^[A-Z]{2}\\d{3}$";
        return Pattern.compile(tableIDPattern)
                .matcher(str)
                .matches();
    }

    public static boolean checkProductID(String str) {
        String productIDPattern = "^[P]\\d{4}$";
        return Pattern.compile(productIDPattern)
                .matcher(str)
                .matches();
    }

    public static boolean checkExistProductName(List<Product> list, String productName) {
        for (Product product : list) {
            if (product.getProductName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistProductID(List<Product> list, String productID) {
        for (Product product : list) {
            if (product.getProductID().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistTableID(List<Table> list, String tableID) {
        for (Table table : list) {
            if (table.getTableID().equals(tableID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistTableName(List<Table> list, String tableName) {
        for (Table table : list) {
            if (table.getTableName().equals(tableName)) {
                return true;
            }
        }
        return false;
    }
}
