package ra.business.imp;

import ra.business.design.ICatalog;
import ra.business.entity.Catalog;
import ra.business.entity.Product;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;
import ra.data.FileImp;

import java.util.*;

public class  CatalogImp implements ICatalog<Catalog, Integer> {
    @Override
    public boolean create(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        listCatalog.add(catalog);
        boolean check = writeToFile(listCatalog);
        return check;
    }

    @Override
    public boolean update(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        boolean returnData = false;
        Catalog changeCatalog = null;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogID() == catalog.getCatalogID()) {
                listCatalog.set(i, catalog);
                changeCatalog = catalog;
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listCatalog);

        //Cap nhat thong tin cua Product chua Catalog bi doi status
        if (changeCatalog != null) {
            //Lay danh sach san pham
            ProductImp productImp = new ProductImp();
            List<Product> listPro = productImp.findAll();
            for (Product pro : listPro) {
                if (pro.getProductCatalog().getCatalogID()== changeCatalog.getCatalogID()){
                    pro.setProductCatalog(changeCatalog);
                }
            }
            productImp.writeToFile(listPro);
        }
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Catalog> findAll() {
        return readFromFile();
    }

    @Override
    public Catalog findByID(Integer id) {
        List<Catalog> listCatalog = readFromFile();
        for (Catalog catalog : listCatalog) {
            if (catalog.getCatalogID() == id) {
                return catalog;
            }
        }
        return null;
    }

    @Override
    public Catalog inputData(Scanner scanner) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        Catalog catNew = new Catalog();

        //CatalogID automatic set
        if (listCatalog.size() == 0) {
            catNew.setCatalogID(0);
        } else {
            int max = 0;
            for (Catalog catalog : listCatalog) {
                if (catalog.getCatalogID() > max) {
                    max = catalog.getCatalogID();
                }
            }
            catNew.setCatalogID(max + 1);
        }

        //Enter catalogName
        do {
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.print("          │➩ Enter catalog name: ");
            String catalogName = scanner.nextLine();
            if (ShopValidation.checkStringLength(catalogName, 30, 6)) {
                if (!ShopValidation.checkExistCatalogName(listCatalog, catalogName)) {
                    catNew.setCatalogName(catalogName);
                    break;
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_CATALOG_NAME_ALREADY_EXIST + ShopConstant.ANSI_RESET);
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_CATALOG_NAME_LENGTH + ShopConstant.ANSI_RESET);
            }
        } while (true);

        //Enter catalogPriority
        do {
            System.out.println("          ├────────────────────────────────────────────────");
            System.out.print("          │➩ Enter catalog priority: ");
            String inputPriority = scanner.nextLine();
            if (ShopValidation.checkInteger(inputPriority)) {
                catNew.setCatalogPriority(Integer.parseInt(inputPriority));
                break;
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_CATALOG_PRIORITY_FORM + ShopConstant.ANSI_RESET);
            }
        } while (true);

        //Enter catalogStatus
        catNew.setCatalogStatus(true);

        return catNew;
    }

    @Override
    public List<Catalog> readFromFile() {
        FileImp<Catalog> fileImp = new FileImp<>();
        return fileImp.readDataFromFile(ShopConstant.URL_CATALOG);
    }

    @Override
    public boolean writeToFile(List<Catalog> list) {
        FileImp<Catalog> fileImp = new FileImp<>();
        return fileImp.writeToFile(list, ShopConstant.URL_CATALOG);
    }

    @Override
    public Catalog updateStatus(Catalog catalog, Scanner scanner) {
        do {
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.println("          │Chooser status for catalog: ");
            System.out.println("          │1. Active");
            System.out.println("          │2. Inactive");
            System.out.println("          └────────────────────────────────────────────────");
            System.out.print("          Your choice: ");

            String inputMenu = scanner.nextLine();
            if (ShopValidation.checkInteger(inputMenu)) {
                int choice = Integer.parseInt(inputMenu);
                switch (choice) {
                    case 1:
                        catalog.setCatalogStatus(true);
                        break;
                    case 2:
                        catalog.setCatalogStatus(false);
                        break;
                    default:
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                }
                break;
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
            }

        } while (true);
        return catalog;
    }

    @Override
    public List<Catalog> findByCatalogName(String name) {
        List<Catalog> listCatalog = readFromFile();
        List<Catalog> listCatalogSearchByName = new ArrayList<>();
        for (Catalog catalog : listCatalog) {
            if (catalog.getCatalogName().contains(name)) {
                listCatalogSearchByName.add(catalog);
            }
        }
        return listCatalogSearchByName;
    }

    @Override
    public void displayByPriority(List<Catalog> listCatalog) {
        if (listCatalog == null) {
            System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_LIST_CATALOG_IS_NULL + ShopConstant.ANSI_RESET);
        } else {
            Collections.sort(listCatalog, new Comparator<Catalog>() {
                @Override
                public int compare(Catalog o1, Catalog o2) {
                    return o1.getCatalogPriority() - o2.getCatalogPriority();
                }
            });

            for (Catalog catalog : listCatalog) {
                String displayStatus;
                if (catalog.isCatalogStatus()) {
                    displayStatus = "Active";
                } else {
                    displayStatus = "Inactive";
                }
                System.out.println("          ┣━━━━━╋━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━┫");
                System.out.printf("          ┃%-5d┃%-20s┃%-10d┃%-10s┃\n", catalog.getCatalogID(), catalog.getCatalogName(),
                        catalog.getCatalogPriority(), displayStatus);
            }
        }
    }

    @Override
    public Catalog updateCatalogDetail(Catalog catalog, Scanner scanner) {
        List<Catalog> listCatalog = readFromFile();
        do {
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.print("          │➩ Enter again catalog name: ");
            String catalogName = scanner.nextLine().trim();
            if (!Objects.equals(catalogName, "") && catalogName.length() != 0) {
                if (ShopValidation.checkStringLength(catalogName, 30, 6)) {
                    if (!ShopValidation.checkExistCatalogName(listCatalog, catalogName)) {
                        catalog.setCatalogName(catalogName);
                        break;
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_CATALOG_NAME_ALREADY_EXIST + ShopConstant.ANSI_RESET);
                    }
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_CATALOG_NAME_LENGTH + ShopConstant.ANSI_RESET);
                }
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("          ├────────────────────────────────────────────────");
            System.out.print("          │➩ Enter again catalog priority: ");
            String inputPriority = scanner.nextLine().trim();
            if (!Objects.equals(inputPriority, "") && inputPriority.length() != 0) {
                if (ShopValidation.checkInteger(inputPriority)) {
                    catalog.setCatalogPriority(Integer.parseInt(inputPriority));
                    break;
                } else {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_CATALOG_PRIORITY_FORM + ShopConstant.ANSI_RESET);
                }
            } else {
                break;
            }
        } while (true);
        return catalog;
    }
}
