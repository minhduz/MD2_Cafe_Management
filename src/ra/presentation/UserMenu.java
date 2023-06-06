package ra.presentation;

import ra.business.entity.User;
import ra.business.imp.UserImp;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    public static UserImp userImp = new UserImp();
    public static void displayUserMenu(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_PURPLE+"          ╔════════════════════════════════════"+ShopConstant.CYAN_BOLD+"USER MENU"+ShopConstant.ANSI_PURPLE+"════════════════════════════════════╗");
            System.out.println("          ║          1. List of accounts sorted by account creation date descending         ║");
            System.out.println("          ║          2. Create more account                                                 ║");
            System.out.println("          ║          3. Update admin account status                                         ║");
            System.out.println("          ║          4. Search for accounts by username or account holder full name         ║");
            System.out.println("          ║          5. Change user's password                                              ║");
            System.out.println("          ║          6. Exit                                                                ║");
            System.out.println("          ╚═════════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            if(ShopValidation.checkInteger(inputMenu)){
                int choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        List<User> listUser = userImp.findAll();
                        User user = userImp.findByUserName(ShopRun.inputUsername);
                            if(user.getUserID() == 0){
                                displayByDate(listUser);
                            }else {
                                displayWithoutPassword(listUser);
                            }
                        break;
                    case 2:
                        do {
                            System.out.print("Enter number of users you want to create: ");
                            String inputNumber = scanner.nextLine();
                            if(ShopValidation.checkInteger(inputNumber)){
                                createMultipleUser(Integer.parseInt(inputNumber),scanner);
                                break;
                            }else {
                                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER+ShopConstant.ANSI_RESET);
                                System.out.println("1. Enter again");
                                System.out.println("2. Exit");
                                System.out.print("Your choice: ");
                                String inputString = scanner.nextLine();
                                if (ShopValidation.checkInteger(inputString)) {
                                    if (Integer.parseInt(inputString) == 2) {
                                        break;
                                    }
                                } else {
                                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                                }
                            }
                        }while (true);
                        break;
                    case 3:
                        updateUserStatus(scanner);
                        break;
                    case 4:
                        displaySearchUserName(scanner);
                        break;
                    case 5:
                        changePassword(ShopRun.inputUsername,scanner);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);
    }

    public static void createMultipleUser(int n,Scanner scanner){
        for (int i = 0; i < n; i++) {
            System.out.println("User number "+(i+1)+": ");
            User userNew = userImp.inputData(scanner);
            boolean result =  userImp.create(userNew);
            if(result){
                System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_CREATE_USER_SUCCESSFULLY+ShopConstant.ANSI_RESET);
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CREATE_USER_FAIL+ShopConstant.ANSI_RESET);
            }
        }
    }

    public static void updateUserStatus(Scanner scanner){
        do {
            System.out.print("Enter user's ID you want to change status: ");
            String inputID = scanner.nextLine();
            boolean checkInteger = ShopValidation.checkInteger(inputID);
            if(checkInteger){
                int ID = Integer.parseInt(inputID);
                User searchUserByID = userImp.findByID(ID);
                if (searchUserByID == null){
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_USER_NOT_FOUND+ShopConstant.ANSI_RESET);
                    System.out.println("1. Enter again");
                    System.out.println("2. Exit");
                    System.out.print("Your choice: ");
                    String inputNumber = scanner.nextLine();
                    if(ShopValidation.checkInteger(inputNumber)){
                        if(Integer.parseInt(inputNumber) == 2){
                            break;
                        }
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                    }
                }else {
                    User userUpdatedStatus = userImp.updateStatus(searchUserByID,scanner);
                    boolean result = userImp.update(userUpdatedStatus);
                    if(result){
                        System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_USER_STATUS_UPDATED_SUCCESSFULLY+ShopConstant.ANSI_RESET);
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_USER_STATUS_UPDATED_FAIL+ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_ID_FORM+ShopConstant.ANSI_RESET);
                System.out.println("1. Enter again");
                System.out.println("2. Exit");
                System.out.print("Your choice: ");
                String inputNumber = scanner.nextLine();
                if(ShopValidation.checkInteger(inputNumber)){
                    if(Integer.parseInt(inputNumber) == 2){
                        break;
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }
        }while (true);
    }

    public static void displaySearchUserName(Scanner scanner){
        System.out.print("Enter a name you want to find: ");
        String name = scanner.nextLine();
        List<User> listFindByName = userImp.findByUserNameOrFullName(name);
        if (listFindByName.size() == 0){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_USER_NAME_NOT_FOUND+ShopConstant.ANSI_RESET);
        }else{
            System.out.printf("%s%-5s%-20s%-20s%-30s%-15s%-10s%-40s%-12s%s\n",ShopConstant.CYAN_BOLD,"ID","Username","Password",
                    "Full Name","Created Date","Status","Email","Phone",ShopConstant.ANSI_RESET);
            userImp.displayByDate(listFindByName);
        }
    }

    public static void changePassword(String username,Scanner scanner){
        User userFindByUserName = userImp.findByUserName(username);
        do {
            System.out.print("Enter old password: ");
            String oldPassword = scanner.nextLine();
            if(ShopValidation.checkStringLength(oldPassword,20,6)){
                if(userFindByUserName.getUserPassword().equals(oldPassword)){
                    break;
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_OLD_PASSWORD+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PASSWORD_LENGTH+ShopConstant.ANSI_RESET);
            }
        }while (true);
        String password;
        do {
            System.out.print("Enter new password: ");
            password = scanner.nextLine();
            if(ShopValidation.checkStringLength(password,20,6)){
                userFindByUserName.setUserPassword(password);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PASSWORD_LENGTH+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter confirm password
        do {
            System.out.print("Enter confirm new password: ");
            String confirmPassword = scanner.nextLine();
            if(confirmPassword.equals(password)){
                userFindByUserName.setUserConfirmPassword(confirmPassword);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCH+ShopConstant.ANSI_RESET);
            }
        }while (true);
        System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_PASSWORD_CHANGE_SUCCESSFULLY+ShopConstant.ANSI_RESET);
        userImp.update(userFindByUserName);
    }

    public static void displayByDate(List<User> listUser){
        if(listUser.size() == 0){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LIST_CATALOG_IS_NULL+ShopConstant.ANSI_RESET);
        }else {
            System.out.println("┏━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━┓");
            System.out.printf(ShopConstant.CYAN_BOLD+"┃%-5s┃%-20s┃%-20s┃%-30s┃%-15s┃%-10s┃%-40s┃%-12s┃\n"+ShopConstant.ANSI_RESET,"UID","Username","Password",
                    "Full Name","Created Date","Status","Email","Phone");
            userImp.displayByDate(listUser);
            System.out.println("┗━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━┛");
        }
    }

    public static void displayWithoutPassword(List<User> listUser){
        if(listUser.size() == 0){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LIST_CATALOG_IS_NULL+ShopConstant.ANSI_RESET);
        }else {
            System.out.println("┏━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━┓");
            System.out.printf(ShopConstant.CYAN_BOLD+"┃%-5s┃%-20s┃%-30s┃%-15s┃%-10s┃%-40s┃%-12s┃\n"+ShopConstant.ANSI_RESET,"UID","Username",
                    "Full Name","Created Date","Status","Email","Phone");
            userImp.displayByDateWithoutPassword(listUser);
            System.out.println("┗━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━┛");
        }
    }
}
