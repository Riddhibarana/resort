package menu;
import java.util.*;
import java.util.Scanner;

public class CustomerMainMenu {

    private Scanner scanner;
    private int customerId;

    // Constructor
    public CustomerMainMenu(int customerId) {

        scanner = new Scanner(System.in);
        this.customerId = customerId;

    }

    // Display Main Menu
    public void displayMenu() {


        int choice ;

        while(true)

        {

            System.out.println("\n===== RESORT MANAGEMENT SYSTEM =====");
            System.out.println("1. Customer Management");
            System.out.println("2. Room Management");
            System.out.println("3. Booking Management");
            System.out.println("4. Check In / Check Out");
            System.out.println("5. food order");
            System.out.println("6. Game Zone Management");
            System.out.println("7. Payment Management");
            System.out.println("8. Staff Management");
            System.out.println("9. Feedback Management");
            System.out.println("0. Exit");
            System.out.print("Enter Choice: ");

            try {

                choice = scanner.nextInt();

            }
            catch(InputMismatchException e)

            {

                System.out.println("Invalid input! Please enter number only.");
                scanner.nextLine(); // wrong input clear karega
                continue;

            }

            switch(choice) {

                case 1:
                    CustomerMenu customerMenu = new CustomerMenu();
                    customerMenu.displayMenu();
                    break;


                case 2:
                    RoomMenu roomMenu = new RoomMenu();
                    roomMenu.displayMenu();
                    break;


                case 3:
                    System.out.println("Please Login From Customer Portal.");
                    break;


                    case 4:
                    CheckInOutMenu checkMenu = new CheckInOutMenu();
                    checkMenu.displayMenu();
                    break;

                case 5:
                    System.out.println("Please Login From Customer Portal To Access Food Order.");
                    break;
                case 6:
                    GameMenu gameMenu = new GameMenu(customerId);
                    gameMenu.displayMenu();
                    break;
                case 7:

                    PaymentMenu paymentMenu = new PaymentMenu(customerId);

                    paymentMenu.displayMenu();

                    break;

                case 8:
                    StaffMenu.displayMenu();
                    break;

                case 9:
                    FeedbackMenu feedbackMenu = new FeedbackMenu(customerId);
                    feedbackMenu.displayMenu();
                    break;

                case 0:
                    System.out.println("Thank You!");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }


        }
    }
}