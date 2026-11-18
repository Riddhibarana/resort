package filehandling;

import java.io.*;

public class Filemanger
{


    private static final String FILE_NAME = "resort_history.txt";


    // Write data into file
    public void writeData(String data) {

        try {

            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);


            bw.write(data);
            bw.newLine();


            bw.close();
            fw.close();


            System.out.println("Data saved successfully!");

        }
        catch(IOException e) {

            System.out.println("File writing error: " + e.getMessage());
        }
    }



    // Read data from file
    public void readData() {

        try {

            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fr);


            String line;


            System.out.println("\n===== RESORT HISTORY =====");


            while((line = br.readLine()) != null) {

                System.out.println(line);

            }


            br.close();
            fr.close();


        }
        catch(FileNotFoundException e) {

            System.out.println("File not found!");

        }
        catch(IOException e) {

            System.out.println("File reading error: " + e.getMessage());

        }

    }



    // Delete file data
    public void clearFile() {

        try {

            FileWriter fw = new FileWriter(FILE_NAME);

            fw.write("");

            fw.close();


            System.out.println("File cleared successfully!");

        }
        catch(IOException e) {

            System.out.println("Error clearing file");

        }

    }



    // Check file exists
    public void checkFile() {

        File file = new File(FILE_NAME);


        if(file.exists()) {

            System.out.println("File exists");

        }
        else {

            System.out.println("File does not exist");

        }

    }



}