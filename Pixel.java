//This class will define the Pixel object.

public class Pixel{
  private int red;
  private int green;
  private int blue; 
  
  // This constructor takes three inputs to define the intensity of red, green and blue of the pixel. 
  //The input must be positive and under 255. 
  public Pixel(int initRed, int initGreen, int initBlue){
    if (initRed < 0 || initRed > 255  || initGreen < 0 || initGreen > 255 || initBlue < 0 || initBlue > 255){
      throw new IllegalArgumentException ("At least one of the colour intensities are outside of the [0, 255] range.");
    }
    this.red = initRed;
    this.green = initGreen;
    this.blue = initBlue;
  }
  // This constructor takes only one intensity and assigns it to all three colours to create a shade of grey. 
  public Pixel(int intensity){
    if (intensity < 0 || intensity > 255){
      throw new IllegalArgumentException ("The intensity is outside of the [0, 255] range.");
    }
    this.red = intensity;
    this.green = intensity;
    this.blue = intensity;
  }
  //The following three methods are get methods that will acquire each intensity of the given pixel. 
  public int getRed(){
    return this.red;
  }
  public int getGreen(){
    return this.green;
  }
  public int getBlue(){
    return this.blue;
  }
  //This method returns the average of the three colour intensities to return a greyscale intenisty. 
  public int grey(){
    return (this.red + this.blue + this.green)/3;
  }
  
}
  
  

