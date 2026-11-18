package main;

import menu.StartMenu;
import database.createtable;
import database.DBConnection;
import threading.BookingBuffer;


public class main
{

    public static void main(String[] args)
    {


        DBConnection.connect();

        createtable.createTables(DBConnection.connection);


        System.out.println("Welcome to Resort Management System");


        BookingBuffer buffer = new BookingBuffer();


        System.out.println("====================================");
        System.out.println("      RESORT MANAGEMENT SYSTEM");
        System.out.println("====================================");



        StartMenu startMenu = new StartMenu();

        startMenu.displayMenu();



        System.out.println(
                "Application Closed Successfully.");

    }
}