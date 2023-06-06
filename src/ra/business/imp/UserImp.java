package ra.business.imp;

import ra.business.design.IUser;
import ra.business.entity.Catalog;
import ra.business.entity.User;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;
import ra.data.FileImp;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserImp implements IUser<User,Integer> {
    @Override
    public boolean create(User user) {
        List<User> listUser = readFromFile();
        if (listUser==null){
            listUser= new ArrayList<>();
        }
        listUser.add(user);
        boolean check = writeToFile(listUser);
        return check;
    }

    @Override
    public boolean update(User user) {
        List<User> listUser = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getUserID() == user.getUserID()){
                listUser.set(i,user);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listUser);
        if(result && returnData){
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return readFromFile();
    }

    @Override
    public User findByID(Integer id) {
        List<User> listUser = readFromFile();
        for (User user:listUser) {
            if(user.getUserID() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public User inputData(Scanner scanner) {
        List<User> listUser = readFromFile();
        if(listUser == null){
            listUser = new ArrayList<>();
        }
        User userNew = new User();

        //UserID automatic set
        if(listUser.size()==0){
            userNew.setUserID(0);
        }else {
            int max = 0;
            for (User user :listUser) {
                if(user.getUserID()>max){
                    max = user.getUserID();
                }
            }
            userNew.setUserID(max+1);
        }

        //Enter username
        do {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            if(ShopValidation.checkValidateUserName(username)){
                if(ShopValidation.checkStringLength(username,20,6)){
                    if(!ShopValidation.checkExistUserName(listUser,username)){
                        userNew.setUserName(username);
                        break;
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_USERNAME_ALREADY_EXIST+ShopConstant.ANSI_RESET);
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_USERNAME_LENGTH+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_USERNAME_FORM+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter password
        String password;
        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if(ShopValidation.checkStringLength(password,20,6)){
                userNew.setUserPassword(password);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PASSWORD_LENGTH+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter confirm password
        do {
            System.out.print("Enter confirm password: ");
            String confirmPassword = scanner.nextLine();
            if(confirmPassword.equals(password)){
                userNew.setUserConfirmPassword(confirmPassword);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCH+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter user full name
        System.out.print("Enter User's full name: ");
        userNew.setUserFullName(scanner.nextLine());

        //Enter user date
        do {
            System.out.print("Enter user creation date (dd/MM/yyyy): ");
            String dateByString = scanner.nextLine();
            if (ShopValidation.checkDateFormat(dateByString)){
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = format.parse(dateByString);
                    userNew.setUserDate(date);
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_DATE_FORMAT+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter user status
        userNew.setUserStatus(true);

        //Enter user email
        do {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            if(ShopValidation.checkEmail(email)){
                userNew.setUserEmail(email);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_EMAIL_FORMAT+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter user phone
        do {
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            if(ShopValidation.checkPhone(phone)){
                userNew.setUserPhone(phone);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PHONE_FORMAT+ShopConstant.ANSI_RESET);
            }
        }while (true);

        return userNew;
    }

    @Override
    public List<User> readFromFile() {
        FileImp<User> fileImp = new FileImp<>();
        return fileImp.readDataFromFile(ShopConstant.URL_USER);
    }

    @Override
    public boolean writeToFile(List<User> list) {
        FileImp<User> fileImp = new FileImp<>();
        return fileImp.writeToFile(list, ShopConstant.URL_USER);
    }

    @Override
    public User updateStatus(User user,Scanner scanner) {
        do {
            System.out.println("Chooser status for user: ");
            System.out.println("1. Active");
            System.out.println("2. Locked");
            System.out.print("Your choice: ");
            String inputMenu = scanner.nextLine();
            if(!Objects.equals(inputMenu,"")&&inputMenu.length()!=0){
                int choice;
                if(ShopValidation.checkInteger(inputMenu)){
                    choice = Integer.parseInt(inputMenu);
                    switch (choice){
                        case 1:
                            user.setUserStatus(true);
                            break;
                        case 2:
                            user.setUserStatus(false);
                            break;
                        default:
                            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                    }
                    break;
                }else{
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }else {
                break;
            }
        }while (true);
        return user;
    }

    @Override
    public void displayByDate(List<User> listUser) {
        if(listUser == null){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LIST_USER_IS_NULL+ShopConstant.ANSI_RESET);
        }else{
            Collections.sort(listUser, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getUserDate().compareTo(o1.getUserDate());
                }
            });
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            for (User user:listUser) {
                String strDate = formatter.format(user.getUserDate());
                String displayStatus;
                if(user.isUserStatus()){
                    displayStatus = "Active";
                } else {
                    displayStatus = "Locked";
                }
                System.out.println("┣━━━━━╋━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━┫");
                System.out.printf("┃%-5d┃%-20s┃%-20s┃%-30s┃%-15s┃%-10s┃%-40s┃%-12s┃\n",user.getUserID(),user.getUserName(),
                        user.getUserPassword(),user.getUserFullName(),strDate,displayStatus,
                        user.getUserEmail(),user.getUserPhone());
            }
        }
    }

    public void displayByDateWithoutPassword(List<User> listUser){
        if(listUser == null){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LIST_USER_IS_NULL+ShopConstant.ANSI_RESET);
        }else{
            Collections.sort(listUser, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getUserDate().compareTo(o1.getUserDate());
                }
            });
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            for (User user:listUser) {
                String strDate = formatter.format(user.getUserDate());
                String displayStatus;
                if(user.isUserStatus()){
                    displayStatus = "Active";
                } else {
                    displayStatus = "Locked";
                }
                System.out.println("┣━━━━━╋━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━┫");
                System.out.printf("┃%-5d┃%-20s┃%-30s┃%-15s┃%-10s┃%-40s┃%-12s┃\n",user.getUserID(),user.getUserName(),
                        user.getUserFullName(),strDate,displayStatus,
                        user.getUserEmail(),user.getUserPhone());
            }
        }
    }

    @Override
    public List<User> findByUserNameOrFullName(String name) {
        List<User> listUser = readFromFile();
        List<User> listUserSearchByNameOrFullName = new ArrayList<>();
        for (User user:listUser) {
            if(user.getUserName().contains(name)||user.getUserFullName().contains(name)){
                listUserSearchByNameOrFullName.add(user);
            }
        }
        return listUserSearchByNameOrFullName;
    }

    @Override
    public User checkLogin(String username, String password) {
        List<User> listUser = readFromFile();
        for (User user:listUser) {
            if(user.getUserName().equals(username)&&user.getUserPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User findByUserName(String username){
        List<User> listUser = readFromFile();
        User userFindByUserName = null;
        for (User user:listUser) {
            if(user.getUserName().equals(username)){
                userFindByUserName=user;
            }
        }
        return userFindByUserName;
    }
}
