package ra.presentation;

import javafx.scene.control.Tab;
import ra.business.entity.Product;
import ra.business.entity.Table;
import ra.business.imp.TableImp;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.List;
import java.util.Scanner;

public class TableMenu {
    public static TableImp taImp = new TableImp();
    public static void displayTableMenu(Scanner scanner){
        do {
            System.out.println(ShopConstant.ANSI_PURPLE+"          ╔══════════════════════"+ShopConstant.CYAN_BOLD+"TABLE MENU"+ShopConstant.ANSI_PURPLE+"══════════════════════╗");
            System.out.println("          ║           1. List of table sorted by status          ║");
            System.out.println("          ║           2. Create more table                       ║");
            System.out.println("          ║           3. Update table's information              ║");
            System.out.println("          ║           4. Update table's status                   ║");
            System.out.println("          ║           5. Exit                                    ║");
            System.out.println("          ╚══════════════════════════════════════════════════════╝");
            System.out.print("          Your choice: "+ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            if(ShopValidation.checkInteger(inputMenu)){
                int choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        List<Table> listTable = taImp.findAll();
                        if (listTable.size() == 0) {
                            System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_LIST_TABLE_IS_NULL + ShopConstant.ANSI_RESET);
                        } else {
                            System.out.println("          ┏━━━━━━━━━━┳━━━━━━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                            System.out.printf(ShopConstant.CYAN_BOLD + "          ┃%-10s┃%-15s┃%-10s┃%-25s┃\n" + ShopConstant.ANSI_RESET, "TID", "Table Name", "Sits", "Status");
                            taImp.displayByStatus(listTable);
                            System.out.println("          ┗━━━━━━━━━━┻━━━━━━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                        }
                        break;
                    case 2:
                        do {
                            System.out.print("Enter number of table you want to create: ");
                            String inputNumber = scanner.nextLine();
                            if (ShopValidation.checkInteger(inputNumber)) {
                                createMultipleTable(Integer.parseInt(inputNumber), scanner);
                                break;
                            } else {
                                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER + ShopConstant.ANSI_RESET);
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
                        } while (true);
                        break;
                    case 3:
                        updateTableDetail(scanner);
                        break;
                    case 4:
                        updateTableStatus(scanner);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println(ShopConstant.ANSI_RED+ ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);
    }

    public static void createMultipleTable(int n,Scanner scanner){
        for (int i = 0; i < n; i++) {
            System.out.println("Table number " + (i + 1) + ": ");
            Table taNew = taImp.inputData(scanner);
            boolean result = taImp.create(taNew);
            if (result) {
                System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_CREATE_TABLE_SUCCESSFULLY + ShopConstant.ANSI_RESET);
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_CREATE_TABLE_FAIL + ShopConstant.ANSI_RESET);
            }
        }
    }

    public static void updateTableDetail(Scanner scanner) {
        do {
            System.out.print("Enter table ID you want to update: ");
            String inputID = scanner.nextLine();
            if (ShopValidation.checkTableID(inputID)) {
                Table searchTableByID = taImp.findByID(inputID);
                if (searchTableByID == null) {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_NOT_FOUND + ShopConstant.ANSI_RESET);
                    System.out.println("1. Enter again");
                    System.out.println("2. Exit");
                    System.out.print("Your choice: ");
                    String inputMenu = scanner.nextLine();
                    if (ShopValidation.checkInteger(inputMenu)) {
                        if (Integer.parseInt(inputMenu) == 2) {
                            break;
                        }
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                    }
                } else {
                    Table tableUpdatedDetails = taImp.updateTableDetail(searchTableByID, scanner);
                    boolean result = taImp.update(tableUpdatedDetails);
                    if (result) {
                        System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_TABLE_UPDATE_DETAIL_SUCCESSFULLY + ShopConstant.ANSI_RESET);
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_DETAIL_UPDATED_FAIL + ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_ID_FORMAT + ShopConstant.ANSI_RESET);
                System.out.println("1. Enter again");
                System.out.println("2. Exit");
                System.out.print("Your choice: ");
                String inputMenu = scanner.nextLine();
                if (ShopValidation.checkInteger(inputMenu)) {
                    if (Integer.parseInt(inputMenu) == 2) {
                        break;
                    }
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                }
            }
        } while (true);
    }

    public static void updateTableStatus(Scanner scanner) {
        do {
            System.out.print("Enter table ID you want to update status: ");
            String inputID = scanner.nextLine();
            Table searchTableByID = taImp.findByID(inputID);
            if(ShopValidation.checkTableID(inputID)){
                if (searchTableByID == null) {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_NOT_FOUND + ShopConstant.ANSI_RESET);
                    System.out.println("1. Enter again");
                    System.out.println("2. Exit");
                    System.out.print("Your choice: ");
                    String inputMenu = scanner.nextLine();
                    if (ShopValidation.checkInteger(inputMenu)) {
                        if (Integer.parseInt(inputMenu) == 2) {
                            break;
                        }
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                    }
                } else {
                    Table tableUpdatedStatus = taImp.updateStatus(searchTableByID, scanner);
                    boolean result = taImp.update(tableUpdatedStatus);
                    if (result) {
                        System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_TABLE_STATUS_UPDATED_SUCCESSFULLY + ShopConstant.ANSI_RESET);
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_STATUS_UPDATED_FAIL + ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_ID_FORMAT + ShopConstant.ANSI_RESET);
                System.out.println("1. Enter again");
                System.out.println("2. Exit");
                System.out.print("Your choice: ");
                String inputMenu = scanner.nextLine();
                if (ShopValidation.checkInteger(inputMenu)) {
                    if (Integer.parseInt(inputMenu) == 2) {
                        break;
                    }
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                }
            }
        } while (true);
    }
}
