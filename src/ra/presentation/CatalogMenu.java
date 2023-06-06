package ra.presentation;

import ra.business.entity.Catalog;
import ra.business.entity.User;
import ra.business.imp.CatalogImp;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.List;
import java.util.Scanner;

public class CatalogMenu {
    public static final CatalogImp catImp = new CatalogImp();
    public static void displayCatalogMenu(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_PURPLE+"          ╔═══════════════════════════"+ShopConstant.CYAN_BOLD+"CATALOG MENU"+ShopConstant.ANSI_PURPLE+"═══════════════════════════╗");
            System.out.println("          ║        1. List of catalog sorted by ascending priority           ║");
            System.out.println("          ║        2. Create more catalog                                    ║");
            System.out.println("          ║        3. Update catalog's information                           ║");
            System.out.println("          ║        4. Update catalog status                                  ║");
            System.out.println("          ║        5. Delete catalog (change catalog status to inactive)     ║");
            System.out.println("          ║        6. Search for catalog by catalog name                     ║");
            System.out.println("          ║        7. Exit                                                   ║");
            System.out.println("          ╚══════════════════════════════════════════════════════════════════╝");
            System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            if(ShopValidation.checkInteger(inputMenu)){
                int choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        List<Catalog> listCatalog = catImp.findAll();
                        if(listCatalog.size() == 0){
                            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LIST_CATALOG_IS_NULL+ShopConstant.ANSI_RESET);
                        }else {
                            System.out.println("          ┏━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┓");
                            System.out.printf(ShopConstant.CYAN_BOLD+"          ┃%-5s┃%-20s┃%-10s┃%-10s┃\n"+ShopConstant.ANSI_RESET,"CID","Catalog Name","Priority","Status");
                            catImp.displayByPriority(listCatalog);
                            System.out.println("          ┗━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛");
                        }
                        break;
                    case 2:
                        do {
                            System.out.print("Enter number of catalog you want to enter: ");
                            String inputNumber = scanner.nextLine();
                            if(ShopValidation.checkInteger(inputNumber)){
                                createMultipleCatalog(Integer.parseInt(inputNumber),scanner);
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
                        updateCatalogDetail(scanner);
                        break;
                    case 4:
                        updateCatalogStatus(scanner);
                        break;
                    case 5:
                        deleteCatalog(scanner);
                        break;
                    case 6:
                        displayCatalogFindByName(scanner);
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println(ShopConstant.ANSI_RED+ ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);
    }

    public static void createMultipleCatalog(int n,Scanner scanner){
        for (int i = 0; i < n; i++) {
            System.out.println("Catalog number "+(i+1)+": ");
            Catalog catNew = catImp.inputData(scanner);
            boolean result =  catImp.create(catNew);
            if(result){
                System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_CREATE_CATALOG_SUCCESSFULLY+ShopConstant.ANSI_RESET);
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CREATE_CATALOG_FAIL+ShopConstant.ANSI_RESET);
            }
        }
    }

    public static void updateCatalogDetail(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_GREEN+"          ╓────────────────────────────────────────────────────────────");
            System.out.print("          ║Enter catalog ID you want to update: "+ShopConstant.ANSI_RESET);
            String inputNumber = scanner.nextLine();
            if(ShopValidation.checkInteger(inputNumber)){
                Catalog searchCatalogByID = catImp.findByID(Integer.parseInt(inputNumber));
                if(searchCatalogByID == null){
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_NOT_FOUND+ShopConstant.ANSI_RESET);
                    System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
                    System.out.println("          ║1. Enter again");
                    System.out.println("          ║2. Exit");
                    System.out.println("          ╙────────────────────────────────────────────────────────────");
                    System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
                    String inputMenu = scanner.nextLine();
                    if(ShopValidation.checkInteger(inputMenu)){
                        if(Integer.parseInt(inputMenu) == 2){
                            break;
                        }
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                    }
                }else {
                    Catalog catalogUpdatedDetails = catImp.updateCatalogDetail(searchCatalogByID,scanner);
                    boolean result = catImp.update(catalogUpdatedDetails);
                    if(result){
                        System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_CATALOG_UPDATE_DETAIL_SUCCESSFULLY+ShopConstant.ANSI_RESET);
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_DETAIL_UPDATED_FAIL+ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER+ShopConstant.ANSI_RESET);
                System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
                System.out.println("          ║1. Enter again");
                System.out.println("          ║2. Exit");
                System.out.println("          ╙────────────────────────────────────────────────────────────");
                System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
                String inputMenu = scanner.nextLine();
                if(ShopValidation.checkInteger(inputMenu)){
                    if(Integer.parseInt(inputMenu) == 2){
                        break;
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }
        }while (true);
    }

    public static void updateCatalogStatus(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_GREEN+"          ╓────────────────────────────────────────────────────────────");
            System.out.print("          ║Enter catalog ID you want to update status: "+ShopConstant.ANSI_RESET);
            String inputNumber = scanner.nextLine();
            if(ShopValidation.checkInteger(inputNumber)){
                Catalog searchCatalogByID = catImp.findByID(Integer.parseInt(inputNumber));
                if(searchCatalogByID == null){
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_NOT_FOUND+ShopConstant.ANSI_RESET);
                    System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
                    System.out.println("          ║1. Enter again");
                    System.out.println("          ║2. Exit");
                    System.out.println("          ╙────────────────────────────────────────────────────────────");
                    System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
                    String inputMenu = scanner.nextLine();
                    if(ShopValidation.checkInteger(inputMenu)){
                        if(Integer.parseInt(inputMenu) == 2){
                            break;
                        }
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                    }
                }else {
                    Catalog catalogUpdatedStatus = catImp.updateStatus(searchCatalogByID,scanner);
                    boolean result = catImp.update(catalogUpdatedStatus);
                    if(result){
                        System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_CATALOG_STATUS_UPDATED_SUCCESSFULLY+ShopConstant.ANSI_RESET);
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_STATUS_UPDATED_FAIL+ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER+ShopConstant.ANSI_RESET);
                System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
                System.out.println("          ║1. Enter again");
                System.out.println("          ║2. Exit");
                System.out.println("          ╙────────────────────────────────────────────────────────────");
                System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
                String inputMenu = scanner.nextLine();
                if(ShopValidation.checkInteger(inputMenu)){
                    if(Integer.parseInt(inputMenu) == 2){
                        break;
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }
        }while (true);
    }

    public static void displayCatalogFindByName(Scanner scanner){
        System.out.print("Enter a catalog name you want to find: ");
        String catalogName = scanner.nextLine();
        List<Catalog> listFindByName = catImp.findByCatalogName(catalogName);
        if (listFindByName.size() == 0){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_NAME_NOT_FOUND+ShopConstant.ANSI_RESET);
        }else{
            System.out.println("          ┏━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┓");
            System.out.printf(ShopConstant.CYAN_BOLD+"          ┃%-5s┃%-20s┃%-10s┃%-10s┃\n"+ShopConstant.ANSI_RESET,"CID","Catalog Name","Priority","Status");
            catImp.displayByPriority(listFindByName);
            System.out.println("          ┗━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛");

        }
    }

    public static void deleteCatalog(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_GREEN+"          ╓────────────────────────────────────────────────────────────");
            System.out.print("          ║Enter catalog ID you want to delete: "+ShopConstant.ANSI_RESET);
            String inputNumber = scanner.nextLine();
            if(ShopValidation.checkInteger(inputNumber)){
                Catalog searchCatalogByID = catImp.findByID(Integer.parseInt(inputNumber));
                if(searchCatalogByID == null){
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_NOT_FOUND+ShopConstant.ANSI_RESET);
                    System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
                    System.out.println("          ║1. Enter again");
                    System.out.println("          ║2. Exit");
                    System.out.println("          ╙────────────────────────────────────────────────────────────");
                    System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
                    String inputMenu = scanner.nextLine();
                    if(ShopValidation.checkInteger(inputMenu)){
                        if(Integer.parseInt(inputMenu) == 2){
                            break;
                        }
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                    }
                }else {
                    searchCatalogByID.setCatalogStatus(false);
                    boolean result = catImp.update(searchCatalogByID);
                    if(result){
                        System.out.println(ShopConstant.ANSI_GREEN+ShopMessage.NOTIFY_CATALOG_DELETE_SUCCESSFULLY+ShopConstant.ANSI_RESET);
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_CATALOG_DELETE_FAIL+ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER+ShopConstant.ANSI_RESET);
                System.out.println(ShopConstant.ANSI_CYAN+"          ╓────────────────────────────────────────────────────────────");
                System.out.println("          ║1. Enter again");
                System.out.println("          ║2. Exit");
                System.out.println("          ╙────────────────────────────────────────────────────────────");
                System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
                String inputMenu = scanner.nextLine();
                if(ShopValidation.checkInteger(inputMenu)){
                    if(Integer.parseInt(inputMenu) == 2){
                        break;
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }
        }while (true);
    }

}
