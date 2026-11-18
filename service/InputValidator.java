package service;

import java.util.Scanner;

public class InputValidator {

    public static int getInt(Scanner sc) {

        while(true) {

            try {

                return Integer.parseInt(sc.nextLine());

            }
            catch(Exception e) {

                System.out.print("Invalid Input! Enter Number Again : ");

            }

        }

    }

}