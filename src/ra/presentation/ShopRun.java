package ra.presentation;

import ra.business.entity.Table;
import ra.business.entity.User;
import ra.business.imp.UserImp;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ShopRun {
    private static final UserImp userImp = new UserImp();
    public static String inputUsername;
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

//        String inputDate = "22/10/2022";
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = format.parse(inputDate);
//        User user = new User(0,"minhduc","123456","123456","Nguyen Minh Duc",date,true,"minhduc@gmail.com","0989438564");
//        userImp.create(user);

        do {
            System.out.println(ShopConstant.ANSI_YELLOW+"           ╔════════════"+ShopConstant.CYAN_BOLD+"WELCOME TO CAFE SHOP"+ShopConstant.ANSI_YELLOW+"═══════════╗");
            System.out.println("           ║                  1. Login                 ║");
            System.out.println("           ║                  2. Exit                  ║");
            System.out.println("           ╚═══════════════════════════════════════════╝");
            System.out.print("            Your choice: "+ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            int choice;
            if(ShopValidation.checkInteger(inputMenu)){
                choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        System.exit(0);
                    default:
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);
    }

    public static void login(Scanner scanner){
        System.out.print(ShopConstant.YELLOW_BOLD+"     ➤ Username: ");
        String username = scanner.nextLine();
        inputUsername = username;
        System.out.print("     ➤ Password: "+ShopConstant.ANSI_RESET);
        String password = scanner.nextLine();
        User user = userImp.checkLogin(username,password);
        if(user!=null){
            if(user.isUserStatus()){
                displayMainMenu(scanner);
            } else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_USER_ACCOUNT_HAS_BEEN_LOCKED+ShopConstant.ANSI_RESET);
            }
        } else {
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LOGIN_FAIL+ShopConstant.ANSI_RESET);
            System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
            System.out.println("          ║1. Login again");
            System.out.println("          ║2. Exit");
            System.out.println("          ╙────────────────────────────────────────────────────────────");
            System.out.print("Your choice: "+ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            if(ShopValidation.checkInteger(inputMenu)){
                int choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        System.exit(0);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }
    }

    public static void displayMainMenu(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_GREEN+"          ╔══════════════════════"+ShopConstant.CYAN_BOLD+"MAIN MENU"+ShopConstant.ANSI_GREEN+"══════════════════════╗");
            System.out.println("          ║                 1. Catalog Management               ║");
            System.out.println("          ║                 2. Product Management               ║");
            System.out.println("          ║                 3. Table Management                 ║");
            System.out.println("          ║                 4. User Management                  ║");
            System.out.println("          ║                 5. Exit                             ║");
            System.out.println("          ╚═════════════════════════════════════════════════════╝");
            System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            if(ShopValidation.checkInteger(inputMenu)){
                int choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        CatalogMenu.displayCatalogMenu(scanner);
                        break;
                    case 2:
                        ProductMenu.displayProductMenu(scanner);
                        break;
                    case 3:
                        TableMenu.displayTableMenu(scanner);
                        break;
                    case 4:
                        UserMenu.displayUserMenu(scanner);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);
    }
}
