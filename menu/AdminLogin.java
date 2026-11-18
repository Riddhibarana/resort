package menu;

import java.util.Scanner;


public class AdminLogin {


    private Scanner scanner;


    private final String username="admin";
    private final String password="admin123";



    public AdminLogin(){

        scanner=new Scanner(System.in);

    }



    public boolean login(){


        System.out.println("\n========== ADMIN LOGIN ==========");



        System.out.print("Enter Username : ");
        String user=scanner.nextLine();



        System.out.print("Enter Password : ");
        String pass=scanner.nextLine();



        if(user.equals(username) &&
                pass.equals(password)) {


            System.out.println(
                    "Admin Login Successful.");

            return true;


        }
        else {


            System.out.println(
                    "Wrong Username or Password.");

            return false;

        }

    }


}