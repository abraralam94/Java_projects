/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author XXXXX
 */
public class FileOperations {

    public static String readSpecificLine(String fileName, int lineNumber) throws IOException {
        String specific_line_text = Files.readAllLines(Paths.get(fileName + ".txt")).get(lineNumber);
        return specific_line_text;
    }

    public static void deleteSpecificLine(String fileName, String lineContent) { //filename in xxxx format, without the .txt extension
        try {
// Write the code here
            String j;
            BufferedReader in = new BufferedReader(
                    new FileReader(fileName + ".txt"));
            while (!((j = in.readLine()).equals(lineContent))) {
                write("new" + fileName, j);
            }
            while ((j = in.readLine()) != null) {
                write("new" + fileName, j);
            }

            //File myObj = new File("new" + fileName + ".txt");
            //File myObj2 = new File(fileName + ".txt");
            //myObj.renameTo(myObj2);     
            //myObj2.delete();
            in.close();
            File foo1 = new File("log.txt");//recent added to debug
            foo1.createNewFile();//recent added to debug
            foo1.delete();//recent added to debug
            File foo2 = new File("log.txt");//recent added to debug
            File foo3 = new File("newlog.txt");//recent added to debug
            foo3.createNewFile();//recent added to debug
            foo3.renameTo(foo2);//recent added to debug
            //File myFile1 = new File(fileName + ".txt");//recent removed to debug
            //myFile1.createNewFile();//recent added to debug
            //myFile1.delete();//recent added to debug
            //File myFile2 = new File("new" + fileName + ".txt");//recent removed to debug
            //myFile2.createNewFile();//recent removed to debug
            // myFile1.delete();//recent removed to debug
            //myFile2.renameTo(myFile1);//recent romoved to debug

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void write(String fileName, String msg) {
        BufferedWriter out = null;
        try {
            // Write the code here
           // BufferedWriter out = new BufferedWriter(// commented out for debugging
                  //  new FileWriter(fileName + ".txt", true));// commented out for debugging
            out =  new BufferedWriter(new FileWriter(fileName + ".txt", true));//added for debugging
            out.write(msg+"\n");
            //out.newLine();
            //out.close();// commented out for debugging

        } catch (IOException e) {
            System.out.println("An error occurred in write(String filename, string msg).");
            e.printStackTrace();
        }
        finally{// added finally block to debug
            try{
            out.close();
            }
            catch(Exception e){  
                System.out.println("An error occurred in write(String filename, string msg).");
            }
        }
    }

    public static void write(String msg) {
        try {
            // Write the code here
            BufferedWriter out = new BufferedWriter(// commented out for debugging
                new FileWriter("writeTester2.txt", true));// commented out for debugging
            out.write(msg);
            out.close();// commented out for debugging

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void editLineOfFile(int lineNumber, String data, String fileName) throws IOException {
        Path path = Paths.get(fileName + ".txt");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(lineNumber, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public static boolean varifyExistanceOfCertainLine(String ID, String fileName) {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(fileName + ".txt"));
            String j;
            while ((j = in.readLine()) != null) {
                //System.out.println(j);
                if (j.equals(ID)) {
                    in.close();
                    return true;
                }
            }
            in.close();
            return false;
        } catch (Exception e) {
            System.out.println("Exception occured in varifyExistanceOfCertainLine(String ID, String fileName) method");
            return false;
        }
    }

    public static void readEachLine(String fileName) {
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(fileName + ".txt"));
            String j;
            while ((j = in.readLine()) != null) {
                System.out.println(j);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void deleteSpecificLineVer2(String fileName, String lineContent) {
        //File filez = new File(fileName + ".txt");
        //String charset = "UTF-8";
        //BufferedReader readerk = new BufferedReader(new InputStreamReader(new FileInputStream(filez), charset));
        File file = new File(fileName + ".txt");
        String charset = "UTF-8";
        String delete = lineContent;
        BufferedReader reader;
        PrintWriter writer;
        try {
            //File file = new File(fileName + ".txt");
            File temp = File.createTempFile("file", ".txt", file.getParentFile());
           // String charset = "UTF-8";
           // String delete = lineContent;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
            String line;
            for (line = null; (line = reader.readLine()) != null;) {
                // ...
                
            }
            line = line.replace(delete, "");
            writer.println(line);
            //line = line.replace(delete, "");
            reader.close();
            writer.close();
            file.delete();
            temp.renameTo(file);
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    
}
