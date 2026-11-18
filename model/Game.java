package model;

public class Game {

    private int gameId;
    private String gameName;
    private String gameType;
    private double price;

    // Default Constructor
    public Game() {

    }

    // Parameterized Constructor
    public Game(int gameId, String gameName, String gameType, double price) {

        this.gameId = gameId;
        this.gameName = gameName;
        this.gameType = gameType;
        this.price = price;

    }

    // Getter and Setter

    public int getGameId() {

        return gameId;

    }

    public void setGameId(int gameId) {

        this.gameId = gameId;

    }

    public String getGameName() {

        return gameName;

    }

    public void setGameName(String gameName) {

        this.gameName = gameName;

    }

    public String getGameType() {

        return gameType;

    }

    public void setGameType(String gameType) {

        this.gameType = gameType;

    }

    public double getPrice() {

        return price;

    }

    public void setPrice(double price) {

        this.price = price;

    }

    // Display

    public void displayGame() {

        System.out.println("------------------------------------");
        System.out.println("Game ID      : " + gameId);
        System.out.println("Game Name    : " + gameName);
        System.out.println("Game Type    : " + gameType);
        System.out.println("Price        : ₹" + price);
        System.out.println("------------------------------------");

    }

}