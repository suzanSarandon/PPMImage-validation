public class RGBPixel {
  int red;
  int green;
  int blue;  
    
    
    public RGBPixel(int red,int green,int blue){
       this.red=red;
       this.green=green;
       this.blue=blue;
    }
    
    public RGBPixel(RGBPixel pixel){
        this.red=pixel.getRed();
        this.green=pixel.getGreen();
        this.blue=pixel.getBlue();
    }
    
    public int getRed(){
        return this.red;
       
    }
    public int getGreen(){
        return this.green;
    }
    public int getBlue(){
        return this.blue;
    }
    public void setRed(int red){
        this.red=red;
    }
    public void setGreen(int green){
        this.green=green;
    }
    public void setBlue(int blue){
        this.blue=blue;
    }
    
    
    
    
}
