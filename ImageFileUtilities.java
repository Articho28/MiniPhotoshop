//The following class will allow us to read pnm and pgm files and write new ones. 
//The scanner and the filewriter and buffered writers will be needed. 
import java.util.Scanner;
import java.io.*;

public class ImageFileUtilities{
  public static Image read(String filename) throws IOException{
    Scanner sc = new Scanner(new File(filename));
    //Determine the format of the image.
    String format = sc.nextLine();
    //Throw an exeception if the format is not supported.
    if (!format.equals("P3") && !format.equals("P2")){
      throw new IOException("The format of the file is not of type PGM or PNM: it is not supported by this program.");
    }
    //To verify what the method reads, we print it on the screen. 
    System.out.println(format);
    //While the scanner reads a # sign, we add all of the strings to the comments. 
    String metadata = sc.nextLine();
    while (sc.hasNext("#")) {
      metadata = metadata + "\n" + sc.nextLine();
    }
    
    System.out.println(metadata);
    //Next, the scanner should record the width of the image and the height of the image. 
    int width = sc.nextInt();
    System.out.println(width);
    int height = sc.nextInt();
    System.out.println(height);
    //The maximum range of intensity must also be recorded. 
    int maxRange = sc.nextInt();
    System.out.println(maxRange);
    //Row by row, we store and position each pixel according to their format. 
    Pixel[][] pixelArray = new Pixel[height][width];
    //If the format is P3 (pnm), we will take the integers in groups of three to store each pixel. 
    if (format.equals("P3")){
      for (int i = 0; i < height ; i++) {
        for (int j = 0; j < width ; j++) {
          int red = sc.nextInt();
          int green = sc.nextInt();
          int blue = sc.nextInt();
          Pixel pixel = new Pixel (red, green, blue);
          pixelArray[i][j] = pixel;
        }
      }     
    }
    //If the format is P2 (pgm), we take each integer as an individual grey pixel. 
    else {
      for (int i = 0; i < height ; i++) {
        for (int j = 0; j < width ; j++) {
          int greyValue = sc.nextInt(); 
          Pixel pixel = new Pixel (greyValue);
          pixelArray[i][j] = pixel;
        }
      }     
    }
    sc.close();
    //Finally, we create our image object by using the acquired information. 
    Image picture = new Image (metadata, maxRange, pixelArray);
    return picture;
  }
  //The following method will write a file of format P3 (pnm) which is a colour image. 
  public static void writePnm(Image subject, String filename) throws IOException{
    //We open the FileWriter and the BufferedWriter, and we begin by writing the file. 
    FileWriter fw = new FileWriter(filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("P3 \n");
    //We use our get methods defined in the Image class to acquire the needed information to create a pnm file. 
    //After writing the format, we need to get the comments, the height and width, and the max range. 
    bw.write(subject.getMetadata() + "\n");
    bw.write(subject.getWidth() + " " + subject.getHeight() + "\n");
    bw.write(subject.getMaxRange() + "\n");
    //Then, we get the intensity of each colour of each pixel of the given image, and write them.
    //NOTE: I move to the next line with each integer because this is how the provided example, catskype.pnm, would 
    //appear when I'd open it with a text editor. I am just following the provided format. 
    for (int i = 0; i < subject.getHeight(); i++) {
      for (int j = 0; j < subject.getWidth(); j++) {
        Pixel temp = subject.getPixel(i,j);
        bw.write(temp.getRed() + "\n");
        bw.write(temp.getGreen() + "\n");
        bw.write(temp.getBlue() + "\n");
      }
    }
    //Once we reach the end of the file, we close the BufferedWriter and Filewriter. 
    bw.close();
    fw.close();
  }
  //To write a pgm file, the process is essentially the same as for the pnm file, except for one thing.
  public static void writePgm(Image subject, String filename) throws IOException{
    FileWriter fw = new FileWriter(filename);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("P2 \n");
    bw.write(subject.getMetadata() + "\n");
    bw.write(subject.getWidth() + " " + subject.getHeight() + "\n");
    bw.write(subject.getMaxRange() + "\n");
    //We must take the average intensity of each pixel and store only that value. It will create a greyscale image. 
    for (int i = 0; i < subject.getHeight(); i++) {
      for (int j = 0; j < subject.getWidth(); j++) {
        Pixel temp = subject.getPixel(i,j);
        bw.write(temp.grey() + "\n");
      }
    }
    bw.close();
    fw.close();
 }
}
