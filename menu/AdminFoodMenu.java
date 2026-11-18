package menu;

import java.util.Scanner;

import model.AdminFood;
import service.AdminFoodService;

public class AdminFoodMenu {

    private Scanner scanner;
    private AdminFoodService service;

    public AdminFoodMenu() {

        scanner = new Scanner(System.in);
        service = new AdminFoodService();

    }

    // ================= FOOD MANAGEMENT =================

    public void displayMenu() {

        while (true) {

            System.out.println("\n========== FOOD MANAGEMENT ==========");
            System.out.println("1. Add Food Item");
            System.out.println("2. View Food Menu");
            System.out.println("3. Update Food Item");
            System.out.println("4. Delete Food Item");
            System.out.println("5. View All Food Orders");
            System.out.println("0. Back");
            System.out.print("Enter Your Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            int choice;

            try {

                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }

            switch (choice) {

                case 1:

                    addFood();

                    break;

                case 2:

                    service.viewFood();

                    break;

                case 3:

                    updateFood();

                    break;

                case 4:

                    deleteFood();

                    break;

                case 5:

                    service.viewAllFoodOrders();

                    break;

                case 0:

                    return;

                default:

                    System.out.println("Enter choice between 0 to 5 please.");
                    break;
            }

        }

    }

    // ================= ADD FOOD =================

    private void addFood() {

        AdminFood food = new AdminFood();

        while (true) {

            System.out.print("Enter Food Name : ");

            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {

                System.out.println("Food Name Cannot Be Empty.");
                continue;

            }

            if (!name.matches("[A-Za-z ]{3,30}")) {

                System.out.println("Food Name Must Contain Only Alphabets.");
                continue;

            }

            if (service.checkFoodName(name)) {

                System.out.println("Food Item Already Exists.");
                System.out.println("Enter Another Food Name.");
                continue;

            }

            food.setFoodName(name);
            break;

        }


        while (true) {

            System.out.print("Enter Price : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Price Cannot Be Empty.");
                continue;

            }

            try {

                double price = Double.parseDouble(input);

                if (price < 20 || price > 1000) {

                    System.out.println("Price Must Be Between 20 And 1000.");
                    continue;

                }

                food.setPrice(price);

                break;

            }

            catch (Exception e) {

                System.out.println("Enter Valid Price.");

            }
        }

        service.addFood(food);

    }

    // ================= UPDATE FOOD =================

    private void updateFood() {

        int foodId;

        while (true) {

            System.out.print("Enter Food ID : ");

            String input = scanner.nextLine().trim();

            try {

                foodId = Integer.parseInt(input);

                break;

            }

            catch (Exception e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        System.out.println("\n1. Food Name");
        System.out.println("2. Price");
        System.out.print("Enter Choice : ");

        int choice;


        try {

            choice = Integer.parseInt(scanner.nextLine());
            if(choice < 1 || choice > 2){

                System.out.println("Invalid Choice.");
                return;

            }

        }

        catch (Exception e) {

            System.out.println("Invalid Choice.");
            return;

        }

        System.out.print("Enter New Value : ");

        String value = scanner.nextLine().trim();
        if (choice == 1) {

            if (service.checkFoodNameForUpdate(foodId, value)) {

                System.out.println("Food Item Already Exists.");
                return;

            }

        }

        if (choice == 2) {

            try {

                double price = Double.parseDouble(value);

                if (price < 20 || price > 1000) {

                    System.out.println("Price Must Be Between 20 And 1000.");
                    return;

                }

            } catch (Exception e) {

                System.out.println("Enter Valid Price.");
                return;

            }

        }

        service.updateFood(foodId, choice, value);
    }

    // ================= DELETE FOOD =================

    private void deleteFood() {

        int foodId;

        while (true) {

            System.out.print("Enter Food ID : ");

            String input = scanner.nextLine().trim();

            try {

                foodId = Integer.parseInt(input);

                break;

            }

            catch (Exception e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        service.deleteFood(foodId);

    }

}