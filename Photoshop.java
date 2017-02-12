/*This is our main method: it will read a file, perform an operation to it and create a new edited file
of type pgm or pnm. It will able to convert from one format to another.*/

//We will need the Scanner tool and the java.io* import. 

import java.util.Scanner;
import java.io.*;

public class Photoshop{
  public static void main(String[] args){
    //We include our code in a try block to catch any IO exceptions. 
    //The first 4 arguments will indicate the image to be read, the name of the file to be created, 
    //the format of the new file, and the operation which will be perfomed on the input file. 
    try{
      if (args.length < 4) {
        //If the user did not provide enough input:
        //The error message prints and once the reader finished reading, he presses a key for 
        //the program to exit and reset.
        System.err.print("Please provide all the following respectively: the input file, the name of the output file, ");
        System.err.println("the output file format and the operation to be performed on the input file.");
        System.err.println("Press the E key followed by Enter to exit and try again.");
        Scanner sc = new Scanner(System.in);
        sc.hasNext();
        sc.close();
        //The System.exit(1) operation exits runtime and resets the Interactions pane. 
        System.exit(1);
      }   
      String inputFile = args[0];
      String outputFile = args[1];
      String newFormat = args[2];
      String operation = args[3];
      //If the user provided a wrong output format, indicate so and wait for him to respond for the program to reset. 
      if (!newFormat.equalsIgnoreCase("pnm") && !newFormat.equalsIgnoreCase("pgm")) {
        System.err.println("The output format provided is not supported. Please provide either pgm or pnm as format.");
        System.err.println("Press the E key followed by Enter to exit and try again.");
        Scanner sc = new Scanner(System.in);
        sc.hasNext();
        sc.close();
        System.exit(1);
      }
      //If the user provided a wrong operation input:
      if (!operation.equals("-fv") && !operation.equals("-fh") && !operation.equals("-gs") && !operation.equals("-cr")) {
        System.err.print("The provided operation is not valid. Please insert -fh for a horizontal flip, -fv for ");
        System.err.println("a vertical flip, -gs for a greyscale conversion, or -cr for a crop.");
        System.err.println("Press the E key followed by Enter to exit and try again.");
        Scanner sc = new Scanner(System.in);
        sc.hasNext();
        sc.close();
        System.exit(1);
      }
      //Now that we are sure that we have been provided the proper information, we begin to fullfill the operation. 
      //The image is read with the help of the read method. 
      Image testImage = ImageFileUtilities.read(inputFile);
      //If the operation indicates a horizontal flip, we use our flip method with boolean horizontal == true.
      //If the operation indicates a veritcal flip, we use our flip method with boolean hozitontal == false.
      /*if (operation.equals("-fh")){
        testImage.flip(true);
      }*/
      if (operation.equals("-fh")){
        testImage.flipSecond(true);
      }
      if (operation.equals("-fv")){
        testImage.flip(false);
      }
      //If the operation indicates to a greyscale conversion, we turn each pixel to a greyscale value. 
      if (operation.equals("-gs")){
        testImage.toGrey();
      }
      //If the operation indicates to crop the image, we must provide the start and end indexes of the crop for both 
      //the X-axis and the Y-axis in the specified order. 
      if (operation.equals("-cr")){
        if (args.length < 8) {
          //If the user did not provide enough input once indicating a crop operation:
          //The error message prints and once the reader finished reading, he presses a key for 
          //the program to exit and reset.
          System.err.print("Please provide all the following indexes respectively: start of horizontal crop, start of vertical crop, ");
          System.err.println("end of horizontal crop, and end of vertical crop.");
          System.err.println("Press the E key followed by Enter to exit and try again.");
          Scanner sc = new Scanner(System.in);
          sc.hasNext();
          sc.close();
          System.exit(1);
        }   
        int startX = Integer.parseInt(args[4]);
        int startY = Integer.parseInt(args[5]);
        int endX = Integer.parseInt(args[6]);
        int endY = Integer.parseInt(args[7]);
        testImage.crop(startX, startY, endX, endY);
      }
      //Depending on the specified format, we use our writing methods to write a pnm or pgm file. 
      if (newFormat.equals("pnm")){
        ImageFileUtilities.writePnm(testImage, outputFile);
      }
      else {
        ImageFileUtilities.writePgm(testImage, outputFile);
      }
      System.out.println("The file is ready.");
    } catch(IOException e){
      System.err.println("The file was not found. Please make sure to provide the right directory.");
      e.printStackTrace();   
    } catch(IllegalArgumentException e){ //To catch the mistake that can happen when we use the crop method.
      System.err.print("Please provide inputs for the start and finish of the crop between 0 and 255 in the following order: ");
      System.err.println("Start of crop along X, Start of crop along Y, End of crop along X, End of crop along Y.");
    }
      
    
  }


}