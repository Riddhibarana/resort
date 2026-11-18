package menu;

import java.util.Scanner;

public class StartMenu {

    private Scanner scanner;


    public StartMenu() {

        scanner = new Scanner(System.in);

    }



    public void displayMenu() {


        while(true) {


            System.out.println("\n========== RESORT MANAGEMENT SYSTEM ==========");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("0. Exit");


            System.out.print("Enter Choice : ");


            String choice = scanner.nextLine();



            switch(choice) {


                case "1":

                    AdminLogin admin = new AdminLogin();

                    if(admin.login()) {

                        AdminDashboard dashboard = new AdminDashboard();
                        dashboard.displayMenu();

                    }

                    break;

                case "2":

                    CustomerMenu customer = new CustomerMenu();
                    customer.displayMenu();

                    break;


                case "0":

                    System.out.println("Thank You!");
                    return;



                default:

                    System.out.println(
                            "Invalid Choice! Enter 0,1 or 2.");

            }

        }

    }

}