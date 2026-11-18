package menu;

import java.util.Scanner;
import model.FoodOrder;
import service.FoodOrderService;

public class FoodOrderMenu {

    private Scanner scanner;
    private FoodOrderService service;
    private int customerId;

    public FoodOrderMenu(int customerId) {

        scanner = new Scanner(System.in);
        service = new FoodOrderService();
        this.customerId = customerId;
    }

    public void displayMenu() {

        int choice = -1;

        do {

            System.out.println("\n========== FOOD ORDER ==========");
            System.out.println("1. View Food Menu");
            System.out.println("2. Place Food Order");
            System.out.println("3. View My Food Orders");
            System.out.println("4. Cancel My Food Order");
            System.out.println("0. Back");

            System.out.print("Enter Choice : ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {

                System.out.println("Invalid Input! Enter Number Only.");
                continue;
            }

            switch (choice) {

                case 1:
                    service.viewFoodMenu();
                    break;

                case 2:
                    placeOrder();
                    break;

                case 3:
                    service.displayMyOrders(customerId);
                    break;

                case 4:
                    cancelOrder();
                    break;

                case 0:
                    System.out.println("Returning...");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 0);
    }

    // ================= PLACE ORDER =================

    private void placeOrder() {

        FoodOrder order = new FoodOrder();

        service.viewFoodMenu();

        // Food ID
        while (true) {

            System.out.print("Enter Food ID : ");

            try {

                int foodId = Integer.parseInt(scanner.nextLine());

                if (service.checkFoodId(foodId)) {

                    order.setFoodId(foodId);
                    break;

                } else {

                    System.out.println("Food ID Not Found.");
                }

            } catch (Exception e) {

                System.out.println("Enter Number Only.");
            }
        }

        // Quantity
        while (true) {

            System.out.print("Enter Quantity (1-50) : ");

            try {

                int quantity = Integer.parseInt(scanner.nextLine());

                if (quantity >= 1 && quantity <= 50) {

                    order.setQuantity(quantity);
                    break;

                } else {

                    System.out.println("Quantity must be between 1 and 50.");
                }

            } catch (Exception e) {

                System.out.println("Enter Number Only.");
            }
        }

        order.setOrderStatus("Ordered");

        service.addOrder(customerId, order);
    }

    // ================= CANCEL ORDER =================

    private void cancelOrder() {

        FoodOrder latest = service.getLatestActiveOrder(customerId);


        // No active order
        if(latest == null)
        {
            System.out.println("\nNo Active Food Order Available For Cancellation.");
            return;
        }


        // Show latest order

        System.out.println("\n------ Latest Food Order ------");
        System.out.println("Order ID   : " + latest.getOrderId());
        System.out.println("Food Name  : " + latest.getFoodName());
        System.out.println("Quantity   : " + latest.getQuantity());
        System.out.println("Total Bill : ₹" + latest.getTotalBill());
        System.out.println("Status     : " + latest.getOrderStatus());
        System.out.println("-------------------------------");


        int orderId;


        // Order ID Validation

        while(true)
        {

            System.out.print("Enter Order ID To Cancel : ");

            try
            {

                orderId = Integer.parseInt(scanner.nextLine());


                if(orderId <= 0)
                {
                    System.out.println("Invalid Order ID.");
                    continue;
                }


                break;

            }
            catch(Exception e)
            {
                System.out.println("Enter Number Only.");
            }

        }



        // Only latest order cancel

        if(orderId != latest.getOrderId())
        {
            System.out.println("Only Latest Food Order Can Be Cancelled.");
            return;
        }



        // Confirmation Validation

        while(true)
        {

            System.out.print("Are You Sure? (Y/N) : ");

            String choice = scanner.nextLine();


            if(choice.equalsIgnoreCase("Y"))
            {

                boolean result =
                        service.cancelLatestOrder(customerId, orderId);


                if(result)
                {
                    System.out.println("Food Order Cancelled Successfully.");
                }

                break;

            }
            else if(choice.equalsIgnoreCase("N"))
            {

                System.out.println("Cancellation Cancelled.");
                break;

            }
            else
            {

                System.out.println("Enter Y or N.");

            }

        }

    }
}