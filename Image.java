//The following class defines Image object. 

//The three attributes are private. They define a small description, the max intensity a 2D array of Pixels. 
public class Image{
  private String metadata;
  private int maxRange;
  private Pixel[][] pixelValues;
  //The follwing constructor assigns the description, the maximum intenisty range and the pixel values to 
  //create an image. 
  public Image(String initMetadata, int initMaxRange, Pixel [][] initPixelValues){
    //The max range must be positive. 
    if (initMaxRange < 0){
      throw new IllegalArgumentException("The max range value is negative. Please insert value between 0 and 255.");
    }
    this.metadata = initMetadata;
    this.maxRange = initMaxRange;
    //The height will define the number of rows in the matrix and the width will define the number of columns.
    //Height is the Y-axis, Width is the X-axis. 
    int height = initPixelValues.length;
    int width = initPixelValues[0].length;
    Pixel[][] tempArray = new Pixel[height][width];
    //This nested loop fills up the matrix of pixels. It gets the intensity for each colour and stores it into a Pixel
    //object, which in turn is stored into the Pixel[][] attribute named pixelValues.
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int tempRed = initPixelValues[i][j].getRed();
        int tempGreen = initPixelValues[i][j].getGreen();
        int tempBlue = initPixelValues[i][j].getBlue();
        Pixel tempPixel = new Pixel (tempRed, tempGreen, tempBlue);
        tempArray[i][j] = tempPixel;
      }
    }
    this.pixelValues = tempArray;
  }
  //The following methods acquire the comment, max range, width of the matrix, and height of the matrix, and 
  //a specific Pixel of a given Image. 
  public String getMetadata() {
    return this.metadata;
  }
  public int getMaxRange() {
    return this.maxRange;
  }
  public int getWidth() {
    return pixelValues[0].length;
  }
  public int getHeight() {
    return pixelValues.length;
  }
  public Pixel getPixel(int i, int j){
    return pixelValues[i][j];
  }
  //This method flips an image horizontally if the boolean horizontal == true and vertically if horizontal==false. 
  public void flip (boolean horizontal){
    Pixel[][] flipped = this.pixelValues;
    //Quick access to the X and Y axis. 
    int h = getHeight();
    int w = getWidth();
    if (horizontal) {
      //The following algorithm reverses the order of an array. If we reverse the order of a Pixel array, the image 
      //will appear flipped. 
      //If we want a horizontal flip, we reverse the order of the array in the X direction: we use the width. 
     for (int i = 0; i < h; i++){
      for (int j = 0; j < w/2; j++){
        Pixel temp = flipped[i][w-j-1];
        flipped[i][w-1-j] = flipped[i][j];
        flipped[i][j] = temp;
      }      
     }
    }
    //The algorithm is applied to height to flip the image vertically. 
    else {
      for (int i = 0; i < h/2; i++) {
        for (int j = 0; j < w; j++){
          Pixel temp = flipped[h-i-1][j];
          flipped[h-1-i][j] = flipped[i][j];
          flipped[i][j] = temp;
        }      
      }  
    }
  }
  public void flipSecond(boolean horizontal){
    Pixel[][] flipped = new Pixel[getHeight()][getWidth()];
    if (horizontal) {
      for (int i = 0; i < getHeight(); i++) {
        for (int j = 0; j < getWidth(); j++) { 
          flipped[i][j] = this.pixelValues[i][getWidth()-j-1];
        }
      }
      this.pixelValues = flipped;
    }
  }
      
    
        
  //To convert an image to grey, we must take the average of the intensities of each Pixel and re-initialize the given
  //Pixel to that average. 
  public void toGrey(){
    for (int i = 0; i<this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        this.pixelValues[i][j] = new Pixel(this.pixelValues[i][j].grey());
      }
    }
  }
  //To crop an image, we will first check if the given inputs within the bounds of the image. 
  //We begin by taking the starting column and the start row (inclusively), and then the ending
  //column and row exclusively).
  //NOTE: Here, X addresses the COLUMNS and Y addresses the ROWS. 
  // The following examples is an image cropped with the inputs crop(3, 3, 9, 6).
  //Height is 3 and width is 6. (3 rows and 6 columns).
  
//Y axis
  // 6
  // 5     * * * * * *
  // 4     * * * * * *
  // 3     * * * * * *
  // 2
  // 1
  // 0 1 2 3 4 5 6 7 8 9 
  // X axis
  
  public void crop(int startX, int startY, int endX, int endY){
    if (startX < 0 || startX >= this.getWidth() || startY < 0 || startY >= this.getHeight() || endX < 0 ||
        endX >= this.getWidth() || endY < 0 || endY >= this.getHeight()) {
      throw new IllegalArgumentException ("At least one of the given inputs of the crop is out of bounds.");
    }
    if (startX > endX || startY > endY) {
      throw new IllegalArgumentException ("Please start by putting the lower index first for both X and Y.");
    }
    int newHeight = endY - startY;
    int newWidth = endX - startX;
    Pixel[][] temp = new Pixel[newHeight][newWidth];
    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth ; j++){
        //We take the intensities of a Pixel, and move along from left to right until the end of the line. 
        //Then we go to the next line. 
        temp[i][j] = this.pixelValues[startY + i][startX +j];       
      }
    }
    this.pixelValues = temp;
  }
    
   //The main method lower was solely used for tests and verifications. 
  
  
  
  
  
  
 /* public static void main(String[] args){
   /* int[][] testMatrix = {{1, 2, 3 ,4, 5,6},{-1,-2,-3,-4,-5,-6},{10,20,30,40,50,60}};
    int[][] flipped = testMatrix;
    int h = testMatrix.length;
    int w = testMatrix[0].length;
    if (true) {
     for (int i = 0; i < h; i++){
      for (int j = 0; j < w/2; j++){
        int temp = flipped[i][w-j-1];
        flipped[i][w-1-j] = flipped[i][j];
        flipped[i][j] = temp;
      }      
     }
    }
    else {
      for (int i = 0; i < h/2; i++) {
        for (int j = 0; j < w; j++){
          int temp = flipped[h-i-1][j];
          flipped[h-1-i][j] = flipped[i][j];
          flipped[i][j] = temp;
        }      
      }  
    }

    for (int i = 0; i < h; i++){
      for (int j = 0; j < w; j++){
        System.out.print(flipped[i][j]);
      }
      System.out.println();
    }
    int[][] testMatrix = {{1, 2, 3 ,4, 5,6},{-1,-2,-3,-4,-5,-6},{10,20,30,40,50,60}};
    int startY = 0;
    int endY = 2;
    int startX = 0;
    int endX = 5;
    
    int newHeight = endY - startY;
    int newWidth = endX - startX;
    int[][] temp = new int[newHeight][newWidth];
    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth ; j++){
        temp[i][j] = testMatrix[startY + i][startX +j];       
      }
    }
    
    for (int i = 0; i < newHeight; i++){
      for (int j = 0; j < newWidth; j++){
        System.out.print(temp[i][j]);
      }
      System.out.println();
  
  }
  }*/
      
}
  
  