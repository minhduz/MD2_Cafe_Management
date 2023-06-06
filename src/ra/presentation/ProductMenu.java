package ra.presentation;

import ra.business.entity.Catalog;
import ra.business.entity.Product;
import ra.business.imp.ProductImp;
import ra.config.ShopConstant;
import ra.config.ShopMessage;
import ra.config.ShopValidation;

import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    public static final ProductImp proImp = new ProductImp();

    public static void displayProductMenu(Scanner scanner) {
        do {
            System.out.println(ShopConstant.ANSI_PURPLE+"          ╔═══════════════════════════"+ShopConstant.CYAN_BOLD+"PRODUCT MENU"+ShopConstant.ANSI_PURPLE+"═══════════════════════════╗");
            System.out.println("          ║         1. List of products by product catalog                   ║");
            System.out.println("          ║         2. Create more product                                   ║");
            System.out.println("          ║         3. Update product's information                          ║");
            System.out.println("          ║         4. Search for product by product name                    ║");
            System.out.println("          ║         5. Search for product by product price                   ║");
            System.out.println("          ║         6. Update product status                                 ║");
            System.out.println("          ║         7. Delete product (change product status to inactive)    ║");
            System.out.println("          ║         8. Exit                                                  ║");
            System.out.println("          ╚══════════════════════════════════════════════════════════════════╝");
            System.out.print("          Your choice: " + ShopConstant.ANSI_RESET);
            String inputMenu = scanner.nextLine();
            if (ShopValidation.checkInteger(inputMenu)) {
                int choice = Integer.parseInt(inputMenu);
                switch (choice) {
                    case 1:
                        List<Product> listProduct = proImp.findAll();
                        if (listProduct.size() == 0) {
                            System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_LIST_PRODUCT_IS_NULL + ShopConstant.ANSI_RESET);
                        } else {
                            System.out.println("          ┏━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━┓");
                            System.out.printf(ShopConstant.CYAN_BOLD + "          ┃%-10s┃%-20s┃%-15s┃%-20s┃%-10s┃\n" + ShopConstant.ANSI_RESET, "PID", "Product Name", "Price", "Catalog", "Status");
                            proImp.displayByCatalog(listProduct);
                            System.out.println("          ┗━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━┛");
                        }
                        break;
                    case 2:
                        do {
                            System.out.print("Enter number of product you want to create: ");
                            String inputNumber = scanner.nextLine();
                            if (ShopValidation.checkInteger(inputNumber)) {
                                createMultipleProduct(Integer.parseInt(inputNumber), scanner);
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
                        updateProductDetail(scanner);
                        break;
                    case 4:
                        displayProductFindByName(scanner);
                        break;
                    case 5:
                        displayProductFindByPrice(scanner);
                        break;
                    case 6:
                        updateProductStatus(scanner);
                        break;
                    case 7:
                        deleteProduct(scanner);
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_MENU_CHOICE + ShopConstant.ANSI_RESET);
            }
        } while (true);
    }

    public static void createMultipleProduct(int n, Scanner scanner) {
        for (int i = 0; i < n; i++) {
            System.out.println("Product number " + (i + 1) + ": ");
            Product proNew = proImp.inputData(scanner);
            boolean result = proImp.create(proNew);
            if (result) {
                System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_CREATE_PRODUCT_SUCCESSFULLY + ShopConstant.ANSI_RESET);
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_CREATE_PRODUCT_FAIL + ShopConstant.ANSI_RESET);
            }
        }
    }

    public static void updateProductDetail(Scanner scanner) {
        do {
            System.out.print("Enter product ID you want to update: ");
            String inputID = scanner.nextLine();
            if (ShopValidation.checkProductID(inputID)) {
                Product searchProductByID = proImp.findByID(inputID);
                if (searchProductByID == null) {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_NOT_FOUND + ShopConstant.ANSI_RESET);
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
                    Product productUpdatedDetails = proImp.updateProductDetail(searchProductByID, scanner);
                    boolean result = proImp.update(productUpdatedDetails);
                    if (result) {
                        System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_PRODUCT_UPDATE_DETAIL_SUCCESSFULLY + ShopConstant.ANSI_RESET);
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_DETAIL_UPDATED_FAIL + ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            } else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_PRODUCT_ID_FORMAT + ShopConstant.ANSI_RESET);
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

    public static void displayProductFindByName(Scanner scanner) {
        System.out.print("Enter a product name you want to find: ");
        String productName = scanner.nextLine();
        List<Product> listFindByName = proImp.findByProductName(productName);
        if (listFindByName.size() == 0) {
            System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_NAME_NOT_FOUND + ShopConstant.ANSI_RESET);
        } else {
            System.out.printf(ShopConstant.CYAN_BOLD + "%-10s%-20s%-15s%-20s%-10s\n" + ShopConstant.ANSI_RESET, "PID", "Product Name", "Price", "Catalog", "Status");
            proImp.displayByCatalog(listFindByName);
        }
    }

    public static void displayProductFindByPrice(Scanner scanner) {
        System.out.print("Enter product price you want to find: ");
        String productPrice = scanner.nextLine();
        if (ShopValidation.checkDouble(productPrice)) {
            List<Product> listFindByPrice = proImp.findByProductPrice(Double.parseDouble(productPrice));
            if (listFindByPrice.size() == 0) {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_PRICE_NOT_FOUND + ShopConstant.ANSI_RESET);

            } else {
                System.out.printf(ShopConstant.CYAN_BOLD + "%-10s%-20s%-15s%-20s%-10s\n" + ShopConstant.ANSI_RESET, "PID", "Product Name", "Price", "Catalog", "Status");
                proImp.displayByCatalog(listFindByPrice);

            }
        }
    }

    public static void updateProductStatus(Scanner scanner) {
        do {
            System.out.print("Enter product ID you want to update status: ");
            String inputID = scanner.nextLine();
            Product searchProductByID = proImp.findByID(inputID);
            if(ShopValidation.checkProductID(inputID)){
                if (searchProductByID == null) {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_NOT_FOUND + ShopConstant.ANSI_RESET);
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
                    Product productUpdatedStatus = proImp.updateStatus(searchProductByID, scanner);
                    boolean result = proImp.update(productUpdatedStatus);
                    if (result) {
                        System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_PRODUCT_STATUS_UPDATED_SUCCESSFULLY + ShopConstant.ANSI_RESET);
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_STATUS_UPDATED_FAIL + ShopConstant.ANSI_RESET);
                    }
                    break;
                }
            }else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_PRODUCT_ID_FORMAT + ShopConstant.ANSI_RESET);
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

    public static void deleteProduct(Scanner scanner){
        do {
            System.out.print("Enter product ID you want to detele: ");
            String inputID = scanner.nextLine();
            Product searchProductByID = proImp.findByID(inputID);
            if(ShopValidation.checkProductID(inputID)) {
                if (searchProductByID == null) {
                    System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_NOT_FOUND + ShopConstant.ANSI_RESET);
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
                    searchProductByID.setProductStatus(false);
                    boolean result = proImp.update(searchProductByID);
                    if (result) {
                        System.out.println(ShopConstant.ANSI_GREEN + ShopMessage.NOTIFY_PRODUCT_DELETE_SUCCESSFULLY + ShopConstant.ANSI_RESET);
                    } else {
                        System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_PRODUCT_DELETE_FAIL + ShopConstant.ANSI_RESET);
                    }
                    break;

                }
            }else {
                System.out.println(ShopConstant.ANSI_RED + ShopMessage.NOTIFY_INCORRECT_PRODUCT_ID_FORMAT + ShopConstant.ANSI_RESET);
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
        }while (true);
    }

}
