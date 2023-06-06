package ra.business.imp;

import ra.business.design.ITable;
import ra.business.entity.Table;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;
import ra.data.FileImp;

import java.util.*;

public class TableImp implements ITable<Table, String> {
    @Override
    public boolean create(Table table) {
        List<Table> listTable = readFromFile();
        if (listTable == null) {
            listTable = new ArrayList<>();
        }
        listTable.add(table);
        boolean check = writeToFile(listTable);
        return check;
    }

    @Override
    public boolean update(Table table) {
        List<Table> listTable = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listTable.size(); i++) {
            if (Objects.equals(listTable.get(i).getTableID(), table.getTableID())) {
                listTable.set(i, table);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listTable);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Table> findAll() {
        return readFromFile();
    }

    @Override
    public Table findByID(String id) {
        List<Table> listTable = readFromFile();
        for (Table table : listTable) {
            if (Objects.equals(table.getTableID(), id)) {
                return table;
            }
        }
        return null;
    }

    @Override
    public Table inputData(Scanner scanner) {
        List<Table> listTable = readFromFile();
        if (listTable == null) {
            listTable = new ArrayList<>();
        }
        Table taNew = new Table();

        //Enter table id
        do {
            System.out.print("Enter table ID: ");
            String tableID = scanner.nextLine();
            if (ShopValidation.checkTableID(tableID)) {
                if (!ShopValidation.checkExistTableID(listTable, tableID)) {
                    taNew.setTableID(tableID);
                    break;
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_ID_ALREADY_EXIST + ShopConstant.ANSI_RESET);
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_ID_FORMAT + ShopConstant.ANSI_RESET);
            }
        } while (true);

        //Enter table name
        do {
            System.out.print("Enter table name: ");
            String tableName = scanner.nextLine();
            if (!ShopValidation.checkExistTableName(listTable, tableName)) {
                taNew.setTableName(tableName);
                break;
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_NAME_ALREADY_EXIST + ShopConstant.ANSI_RESET);
            }
        } while (true);

        //Enter table sits
        do {
            System.out.print("Enter table sits: ");
            String tableSits = scanner.nextLine();
            if (ShopValidation.checkInteger(tableSits)) {
                if (Integer.parseInt(tableSits) > 0) {
                    taNew.setTableSits(Integer.parseInt(tableSits));
                    break;
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_SITS_VALUE + ShopConstant.ANSI_RESET);
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_SITS_FORMAT + ShopConstant.ANSI_RESET);
            }
        } while (true);

        //Set table status automatically
        taNew.setTableStatus("1.Empty");

        return taNew;
    }

    @Override
    public List<Table> readFromFile() {
        FileImp<Table> fileImp = new FileImp<>();
        return fileImp.readDataFromFile(ShopConstant.URL_TABLE);
    }

    @Override
    public boolean writeToFile(List<Table> list) {
        FileImp<Table> fileImp = new FileImp<>();
        return fileImp.writeToFile(list, ShopConstant.URL_TABLE);
    }

    @Override
    public Table updateStatus(Table table, Scanner scanner) {
        do {
            System.out.println("Choose table status: ");
            System.out.println("1. Empty");
            System.out.println("2. Using");
            System.out.println("3. Combining");
            System.out.println("4. Broken");
            System.out.print("Your choice: ");
            String inputMenu = scanner.nextLine();
            if (ShopValidation.checkInteger(inputMenu)) {
                int choice = Integer.parseInt(inputMenu);
                if (choice == 1) {
                    table.setTableStatus("1.Empty");
                    break;
                } else if (choice == 2) {
                    table.setTableStatus("2.Using");
                    break;
                } else if (choice == 3) {
                    combineTable(table,scanner);
                    break;
                } else if (choice == 4) {
                    table.setTableStatus("4.Broken");
                    break;
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
            }
        } while (true);

        return table;
    }

    @Override
    public void displayByStatus(List<Table> listTable) {
        if (listTable == null) {
            System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_LIST_TABLE_IS_NULL + ShopConstant.ANSI_RESET);
        } else {
            Collections.sort(listTable, new Comparator<Table>() {
                @Override
                public int compare(Table o1, Table o2) {
                    return o1.getTableStatus().compareTo(o2.getTableStatus());
                }
            });
            for (Table table : listTable) {
                System.out.println("          ┣━━━━━━━━━━╋━━━━━━━━━━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.printf("          ┃%-10s┃%-15s┃%-10d┃%-25s┃\n", table.getTableID(), table.getTableName(),
                        table.getTableSits(),table.getTableStatus());
            }
        }
    }

    @Override
    public Table updateTableDetail(Table table,Scanner scanner) {
        List<Table> listTable = readFromFile();
        do {
            System.out.print("Enter again table name: ");
            String tableName = scanner.nextLine();
            if (!Objects.equals(tableName,"")&&tableName.length()!=0){
                if (!ShopValidation.checkExistTableName(listTable, tableName)) {
                    table.setTableName(tableName);
                    break;
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_TABLE_NAME_ALREADY_EXIST + ShopConstant.ANSI_RESET);
                }
            }else break;
        } while (true);

        do {
            System.out.print("Enter again table sits: ");
            String tableSits = scanner.nextLine();
            if(!Objects.equals(tableSits,"")&&tableSits.length()!=0){
                if (ShopValidation.checkInteger(tableSits)) {
                    if (Integer.parseInt(tableSits) > 0) {
                        table.setTableSits(Integer.parseInt(tableSits));
                        break;
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_SITS_VALUE + ShopConstant.ANSI_RESET);
                    }
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_TABLE_SITS_FORMAT + ShopConstant.ANSI_RESET);
                }
            }else break;
        } while (true);

        return table;
    }

    public void combineTable(Table table,Scanner scanner){
        List<Table> listTable = readFromFile();
        List<Table> listReturnTable = new ArrayList<>();
        table.setTableStatus("3.Combining");
        update(table);
        for (int i = 0; i < listTable.size(); i++) {
            if (listTable.get(i).getTableID().equals(table.getTableID())){
                listTable.remove(i);
                break;
            }
        }
        for (int i = 0; i < listTable.size(); i++) {
            if(!listTable.get(i).getTableStatus().equals("1.Empty")){
                listReturnTable.add(listTable.get(i));
                listTable.remove(i);
            }
        }
        listReturnTable.add(table);
        do {
            System.out.print("Enter number of table you to combine with "+table.getTableName()+": ");
            String inputNumber = scanner.nextLine();
            if(ShopValidation.checkInteger(inputNumber)){
                int loop = Integer.parseInt(inputNumber);
                System.out.println("List of table: ");
                for (int i = 0; i < loop; i++) {
                    System.out.println("Choose table "+(i+1)+" to combine:");
                    for (int j = 0; j < listTable.size(); j++) {
                        System.out.printf("%d. %s\n",(j+1),listTable.get(j).getTableName());
                    }
                    do {
                        System.out.print("Your choice: ");
                        String inputNum = scanner.nextLine();
                        if(ShopValidation.checkInteger(inputNum)){
                            int choice = Integer.parseInt(inputNum);
                            listTable.get(choice-1).setTableStatus("3.Combine with "+table.getTableName());
                            listReturnTable.add(listTable.get(choice-1));
                            listTable.remove(listTable.get(choice-1));
                            break;
                        }else {
                            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER+ShopConstant.ANSI_RESET);
                        }
                    }while (true);
                }
                listReturnTable.addAll(listTable);
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_INPUT_NUMBER+ShopConstant.ANSI_RESET);
            }
        }while (true);
        writeToFile(listReturnTable);
    }
}
