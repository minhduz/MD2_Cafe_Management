package ra.business.imp;

import ra.business.design.IProduct;
import ra.business.entity.Catalog;
import ra.business.entity.Product;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;
import ra.data.FileImp;

import java.util.*;


public class ProductImp implements IProduct<Product, String> {
    CatalogImp catalogImp = new CatalogImp();
    List<Catalog> listCatalog = catalogImp.readFromFile();

    @Override
    public boolean create(Product product) {
        List<Product> listProduct = readFromFile();
        if (listProduct==null){
            listProduct= new ArrayList<>();
        }
        listProduct.add(product);
        boolean check = writeToFile(listProduct);
        return check;
    }

    @Override
    public boolean update(Product product) {
        List<Product> listProduct = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < listProduct.size(); i++) {
            if (Objects.equals(listProduct.get(i).getProductID(), product.getProductID())){
                listProduct.set(i,product);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(listProduct);
        if(result && returnData){
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findAll() {
        return readFromFile();
    }

    @Override
    public Product findByID(String id) {
        List<Product> listProduct = readFromFile();
        for (Product product:listProduct) {
            if(Objects.equals(product.getProductID(), id)){
                return product;
            }
        }
        return null;
    }

    @Override
    public Product inputData(Scanner scanner) {
        List<Product> listProduct = readFromFile();
        if(listProduct == null){
            listProduct = new ArrayList<>();
        }
        Product proNew = new Product();

        //Enter product id
        do {
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.print("          │➩ Enter product ID: ");
            String productID = scanner.nextLine();
            if(ShopValidation.checkProductID(productID)){
                if(!ShopValidation.checkExistProductID(listProduct,productID)){
                    proNew.setProductID(productID);
                    break;
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_PRODUCT_ID_ALREADY_EXIST+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PRODUCT_ID_FORMAT+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter product name
        do {
            System.out.println("          ├────────────────────────────────────────────────");
            System.out.print("          │➩ Enter product name: ");
            String productName = scanner.nextLine();
            if(ShopValidation.checkStringLength(productName,30,6)){
                if(!ShopValidation.checkExistProductName(listProduct,productName)){
                    proNew.setProductName(productName);
                    break;
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_PRODUCT_NAME_ALREADY_EXIST+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PRODUCT_NAME_LENGTH+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter product price
        do {
            System.out.println("          ├────────────────────────────────────────────────");
            System.out.print("          │➩ Enter product price: ");
            String productPrice = scanner.nextLine();
            if(ShopValidation.checkDouble(productPrice)){
                if(Double.parseDouble(productPrice) > 0){
                    proNew.setProductPrice(Double.parseDouble(productPrice));
                    break;
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PRODUCT_PRICE_VALUE+ShopConstant.ANSI_RESET);
                }
            }else {
                System.out.println(ShopConstant.RED_BOLD+ShopMessage.NOTIFY_INCORRECT_PRODUCT_PRICE_FORMAT+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter product catalog
        Collections.sort(listCatalog, new Comparator<Catalog>() {
            @Override
            public int compare(Catalog o1, Catalog o2) {
                return o1.getCatalogPriority()-o2.getCatalogPriority();
            }
        });
        int count = 1;
        do {
            System.out.println("          ├────────────────────────────────────────────────");
            System.out.println("          │Choose catalog for product: ");
            for (Catalog catalog:listCatalog) {
                if(catalog.isCatalogStatus()){
                    System.out.printf("          │➩ %d. %s\n",count,catalog.getCatalogName());
                }
                count++;
            }
            System.out.print("          Your choice: ");
            String inputMenu = scanner.nextLine();
            if(ShopValidation.checkInteger(inputMenu)){
                int choice = Integer.parseInt(inputMenu);
                proNew.setProductCatalog(listCatalog.get(choice-1));
                break;
            }else {
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);

        //Enter product status
        proNew.setProductStatus(true);

        return proNew;
    }

    @Override
    public List<Product> readFromFile() {
        FileImp<Product> fileImp = new FileImp<>();
        return fileImp.readDataFromFile(ShopConstant.URL_PRODUCT);
    }

    @Override
    public boolean writeToFile(List<Product> list) {
        FileImp<Product> fileImp = new FileImp<>();
        return fileImp.writeToFile(list, ShopConstant.URL_PRODUCT);
    }

    @Override
    public Product updateStatus(Product product,Scanner scanner) {
        do {
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.println("          │Chooser status for product: ");
            System.out.println("          │1. Active");
            System.out.println("          │2. Inactive");
            System.out.println("          └────────────────────────────────────────────────");
            System.out.print("          Your choice: ");
            String inputMenu = scanner.nextLine();
            int choice;
            if(ShopValidation.checkInteger(inputMenu)){
                choice = Integer.parseInt(inputMenu);
                switch (choice){
                    case 1:
                        product.setProductStatus(true);
                        break;
                    case 2:
                        product.setProductStatus(false);
                        break;
                    default:
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
                break;
            }else{
                System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
            }
        }while (true);
        return product;
    }

    @Override
    public void displayByCatalog(List<Product> listProduct) {
        if(listProduct == null){
            System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_LIST_PRODUCT_IS_NULL+ShopConstant.ANSI_RESET);
        }else{
            Collections.sort(listProduct, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getProductCatalog().getCatalogPriority()-o2.getProductCatalog().getCatalogPriority();
                }
            });
            for (Product product:listProduct) {
                String displayStatus;
                if(product.isProductStatus()){
                    displayStatus = "Active";
                } else {
                    displayStatus = "Inactive";
                }
                if(product.getProductCatalog().isCatalogStatus()){
                    System.out.println("          ┣━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━┫");
                    System.out.printf("          ┃%-10s┃%-20s┃%-15.2f┃%-20s┃%-10s┃\n",product.getProductID(),product.getProductName(),
                            product.getProductPrice(),product.getProductCatalog().getCatalogName(),displayStatus);
                }
            }
        }
    }

    @Override
    public List<Product> findByProductName(String name) {
        List<Product> listProduct = readFromFile();
        List<Product> listProductSearchByName = new ArrayList<>();
        for (Product product:listProduct) {
            if(product.getProductName().contains(name)){
                listProductSearchByName.add(product);
            }
        }
        return listProductSearchByName;
    }

    @Override
    public List<Product> findByProductPrice(double price) {
        List<Product> listProduct = readFromFile();
        List<Product> listProductSearchByPrice = new ArrayList<>();
        double minPrice = price*80/100;
        double maxPrice = price*120/100;
        for (Product product:listProduct) {
            if(product.getProductPrice() >= minPrice && product.getProductPrice() <= maxPrice){
                listProductSearchByPrice.add(product);
            }
        }
        return listProductSearchByPrice;
    }

    @Override
    public Product updateProductDetail(Product product,Scanner scanner) {
        List<Product> listProduct = readFromFile();
        do {
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.print("          │➩ Enter again product name: ");
            String productName = scanner.nextLine().trim();
            if(!Objects.equals(productName,"")&&productName.length()!=0){
                if(ShopValidation.checkStringLength(productName,30,6)){
                    if(!ShopValidation.checkExistProductName(listProduct,productName)){
                        product.setProductName(productName);
                        break;
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_PRODUCT_NAME_ALREADY_EXIST+ShopConstant.ANSI_RESET);
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PRODUCT_NAME_LENGTH);
                }
            }else break;
        }while (true);

        do{
            System.out.println("          ┌────────────────────────────────────────────────");
            System.out.print("          │➩ Enter again product price: ");
            String productPrice = scanner.nextLine();
            if(!Objects.equals(productPrice,"")&&productPrice.length()!=0){
                if(ShopValidation.checkDouble(productPrice)){
                    if(Double.parseDouble(productPrice)>0){
                        product.setProductPrice(Double.parseDouble(productPrice));
                        break;
                    }else {
                        System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PRODUCT_PRICE_VALUE+ShopConstant.ANSI_RESET);
                    }
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_PRODUCT_PRICE_FORMAT+ShopConstant.ANSI_RESET);
                }
            }else break;
        }while (true);

        Collections.sort(listCatalog, new Comparator<Catalog>() {
            @Override
            public int compare(Catalog o1, Catalog o2) {
                return o1.getCatalogPriority()-o2.getCatalogPriority();
            }
        });
        int count = 1;
        do {
            System.out.println("          ├────────────────────────────────────────────────");
            System.out.println("          │Choose catalog again for product: ");
            for (Catalog catalog:listCatalog) {
                if(catalog.isCatalogStatus()){
                    System.out.printf("          │➩ %d. %s\n",count,catalog.getCatalogName());
                }
                count++;
            }
            System.out.print("          Your choice: ");
            String inputMenu = scanner.nextLine();
            if(!Objects.equals(inputMenu,"")&&inputMenu.length()!=0){
                if(ShopValidation.checkInteger(inputMenu)){
                    int choice = Integer.parseInt(inputMenu);
                    product.setProductCatalog(listCatalog.get(choice-1));
                    break;
                }else {
                    System.out.println(ShopConstant.ANSI_RED+ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE+ShopConstant.ANSI_RESET);
                }
            } else break;
        }while (true);
        return product;
    }
}
