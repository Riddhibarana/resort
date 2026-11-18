package menu;

import java.util.Scanner;

import model.GameBooking;
import service.GameService;
import service.InputValidator;


public class GameMenu {


    private Scanner scanner;
    private GameService service;
    private int customerId;



    public GameMenu(int customerId) {

        scanner = new Scanner(System.in);
        service = new GameService();
        this.customerId = customerId;

    }





    public void displayMenu() {


        int choice;


        do {


            System.out.println("\n========== GAME ZONE ==========");
            System.out.println("1. View Games");
            System.out.println("2. Book Game");
            System.out.println("3. View My Game Bookings");
            System.out.println("4. Cancel My Game Booking");
            System.out.println("0. Back");


            System.out.print("Enter Choice : ");


            choice = InputValidator.getInt(scanner);



            switch(choice) {


                case 1:

                    service.displayGames();

                    break;



                case 2:

                    bookGame();

                    break;



                case 3:

                    service.displayCustomerGameBookings(customerId);

                    break;



                case 4:

                    cancelBooking();

                    break;



                case 0:

                    System.out.println("Returning...");

                    break;



                default:

                    System.out.println("Invalid Choice.");

            }



        }while(choice != 0);


    }







    // ================= BOOK GAME =================


    private void bookGame() {


        service.displayGames();



        int gameId;


        while(true) {


            System.out.print(
                    "Enter Game ID : ");


            gameId =
                    InputValidator.getInt(scanner);



            if(service.checkGameId(gameId)) {

                break;

            }


            System.out.println(
                    "Invalid Game ID.");

        }






        int players;


        while(true) {


            System.out.print(
                    "Enter Number Of Players (1-15) : ");


            players =
                    InputValidator.getInt(scanner);



            if(players >=1 && players <=15) {

                break;

            }



            System.out.println(
                    "Players must be between 1 and 15.");

        }






        service.bookGame(customerId,gameId,players);



    }








    // ================= CANCEL BOOKING =================


    private void cancelBooking() {



        GameBooking latest =
                service.getLatestGameBooking(customerId);





        if(latest == null) {


            System.out.println(
                    "No Active Game Booking Available For Cancellation.");

            return;

        }







        System.out.println(
                "\n------ Latest Game Booking ------");


        System.out.println(
                "Game Booking ID : "
                        + latest.getGameBookingId());


        System.out.println(
                "Players : "
                        + latest.getPlayers());


        System.out.println(
                "Total Amount : ₹"
                        + latest.getTotalAmount());


        System.out.println(
                "--------------------------------");






        int id;


        while(true) {


            System.out.print(
                    "Enter Game Booking ID To Cancel : ");


            id =
                    InputValidator.getInt(scanner);



            if(id == latest.getGameBookingId()) {

                break;

            }



            System.out.println(
                    "Only Latest Game Booking Can Be Cancelled.");

        }






        while(true) {


            System.out.print(
                    "Are You Sure? (Y/N) : ");


            String choice =
                    scanner.nextLine();




            if(choice.equalsIgnoreCase("Y")) {



                boolean result =
                        service.cancelLatestGameBooking(customerId,id);




                if(result) {

                    System.out.println(
                            "Game Booking Cancelled Successfully.");

                }


                return;



            }
            else if(choice.equalsIgnoreCase("N")) {


                System.out.println(
                        "Cancellation Cancelled.");

                return;


            }
            else {

                System.out.println(
                        "Enter Y or N.");

            }


        }


    }


}