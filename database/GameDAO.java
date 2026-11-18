package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class GameDAO {



    // ================= VIEW ALL GAMES =================

    public void displayGames() {


        String query = "SELECT * FROM game";


        try {


            Connection con =
                    DBConnection.getConnection();


            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(query);

            boolean found = false;

            System.out.println(
                    "\n========== AVAILABLE GAMES ==========");

            System.out.printf("%-10s %-25s %-15s %-10s%n",
                    "Game ID",
                    "Game Name",
                    "Game Type",
                    "Price");

            System.out.println(
                    "--------------------------------------------------------------");

            while(rs.next()) {

                found = true;

                System.out.printf(
                        "%-10d %-25s %-15s ₹%-10.2f%n",
                        rs.getInt("gameId"),
                        rs.getString("gameName"),
                        rs.getString("gameType"),
                        rs.getDouble("price")
                );
            }

            if(!found) {
                System.out.println("No Games Available.");
            }

        }
        catch(Exception e) {


            e.printStackTrace();


        }


    }







    // ================= CHECK GAME ID =================


    public boolean checkGameId(int gameId) {


        String query =
                "SELECT gameId FROM game WHERE gameId=?";


        try {


            Connection con =
                    DBConnection.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(query);


            ps.setInt(1, gameId);



            ResultSet rs =
                    ps.executeQuery();



            return rs.next();



        }
        catch(Exception e) {


            e.printStackTrace();


        }



        return false;


    }








    // ================= GET GAME PRICE =================


    public double getGamePrice(int gameId) {


        double price = 0;



        String query =
                "SELECT price FROM game WHERE gameId=?";



        try {


            Connection con =
                    DBConnection.getConnection();



            PreparedStatement ps =
                    con.prepareStatement(query);



            ps.setInt(1, gameId);



            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()) {


                price =
                        rs.getDouble("price");


            }



        }
        catch(Exception e) {


            e.printStackTrace();


        }



        return price;


    }



}