package model;

public class GameBooking {


    private int gameBookingId;
    private int customerId;
    private int bookingId;
    private int gameId;
    private int players;
    private double totalAmount;
    private String bookingStatus;



    public int getGameBookingId() {
        return gameBookingId;
    }


    public void setGameBookingId(int gameBookingId) {
        this.gameBookingId = gameBookingId;
    }



    public int getCustomerId() {
        return customerId;
    }


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }



    public int getBookingId() {
        return bookingId;
    }


    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }



    public int getGameId() {
        return gameId;
    }


    public void setGameId(int gameId) {
        this.gameId = gameId;
    }



    public int getPlayers() {
        return players;
    }


    public void setPlayers(int players) {
        this.players = players;
    }



    public double getTotalAmount() {
        return totalAmount;
    }


    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }



    public String getBookingStatus() {
        return bookingStatus;
    }


    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

}