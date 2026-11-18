package service;

import database.GameDAO;
import database.GameBookingDAO;
import model.GameBooking;


public class GameService {


    private GameDAO gameDAO;
    private GameBookingDAO gameBookingDAO;



    public GameService() {

        gameDAO = new GameDAO();
        gameBookingDAO = new GameBookingDAO();

    }





    // ================= BOOK GAME =================

    public void bookGame(int customerId,
                         int gameId,
                         int players) {



        // Customer Check

        if(!gameBookingDAO.checkCustomerId(customerId)) {

            System.out.println(
                    "Invalid Customer.");

            return;

        }






        // Booking Optional

        int bookingId =
                gameBookingDAO.getBookingIdByCustomerId(customerId);

        if(bookingId == -1) {
            bookingId = 0;
        }







        // Game Check

        if(!gameDAO.checkGameId(gameId)) {


            System.out.println(
                    "Invalid Game ID.");

            return;

        }






        // Player Validation

        if(players < 1 || players > 15) {


            System.out.println(
                    "Players must be between 1 and 15.");

            return;

        }




// ================= AMOUNT CALCULATION =================

        double price =
                gameDAO.getGamePrice(gameId);

        if(price <= 0) {

            System.out.println("Invalid Game Price.");
            return;

        }

        double totalAmount =
                price * players;

        GameBooking gb =
                new GameBooking();



        gb.setCustomerId(customerId);

        gb.setBookingId(bookingId);

        gb.setGameId(gameId);

        gb.setPlayers(players);

        gb.setTotalAmount(totalAmount);

        gb.setBookingStatus("Booked");






        gameBookingDAO.addGameBooking(gb);




        System.out.println(
                "Game Booking Successful.");

        System.out.println(
                "Total Game Amount : ₹" + totalAmount);



    }









    // ================= DISPLAY GAMES =================

    public void displayGames() {


        gameDAO.displayGames();


    }









    // ================= VIEW MY BOOKINGS =================

    public void displayCustomerGameBookings(int customerId) {



        gameBookingDAO
                .displayGameBookingsByCustomer(customerId);



    }









    // ================= GET LATEST BOOKING =================

    public GameBooking getLatestGameBooking(int customerId) {


        return gameBookingDAO
                .getLatestActiveGameBooking(customerId);


    }









    // ================= CANCEL LATEST BOOKING =================

    public boolean cancelLatestGameBooking(int customerId,
                                           int id) {



        GameBooking latest =
                gameBookingDAO
                        .getLatestActiveGameBooking(customerId);






        if(latest == null) {


            System.out.println(
                    "No Active Game Booking Available.");

            return false;


        }







        if(latest.getGameBookingId() != id) {


            System.out.println(
                    "Only Latest Game Booking Can Be Cancelled.");

            return false;


        }




       return gameBookingDAO.cancelGameBooking(customerId, id);




    }









    // ================= CHECK GAME ID =================

    public boolean checkGameId(int gameId) {


        return gameDAO.checkGameId(gameId);


    }



}