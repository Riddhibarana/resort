package menu;

import java.util.Scanner;
import service.CheckInOutService;

public class CheckInOutMenu {

    private Scanner scanner;
    private CheckInOutService service;


    public CheckInOutMenu() {

        scanner = new Scanner(System.in);
        service = new CheckInOutService();

    }



    public void displayMenu() {


        int choice;


        while(true) {


            System.out.println("\n===== CHECK-IN / CHECK-OUT MANAGEMENT =====");
            System.out.println("1. View All Check-In Records");
            System.out.println("2. Check-Out Guest");
            System.out.println("0. Back");


            System.out.print("Enter Choice : ");


            try {

                choice = Integer.parseInt(scanner.nextLine());

            }
            catch(Exception e) {

                System.out.println("Enter Number Only.");
                continue;

            }



            switch(choice) {


                case 1:

                    // Admin can see all records

                    service.viewRecords();

                    break;



                case 2:

                    checkOut();

                    break;



                case 0:

                    return;



                default:

                    System.out.println("Invalid Choice");

            }


        }


    }





    private void checkOut() {


        System.out.print("\nEnter Check-In ID : ");


        int checkId;


        try {


            checkId =
                    Integer.parseInt(scanner.nextLine());


        }
        catch(Exception e) {


            System.out.println("Invalid ID");
            return;

        }



        service.checkOut(checkId);


    }


}