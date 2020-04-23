
import java.io.*;
import java.util.*;
public class PPMImage implements Image{
    int imageWidth;
    int imageHeight;
    int colordepth;
    RGBPixel[][]pixels;
    
    
    public PPMImage(int width, int height, int colordepth) throws InvalidPPMLuminocity,InvalidPPMDimensions{
        int i,j;
        this.imageWidth=width;
        this.imageHeight=height;
        this.colordepth=colordepth;
        if (width<0 || height<0){
            throw new InvalidPPMDimensions();
        }
        
        if (colordepth<0){
            throw new InvalidPPMLuminocity(colordepth,255);
        }
        else{
            pixels=new RGBPixel[height][width];
            for (i=0;i<height;i++){
                for (j=0;j<width;j++){
                    pixels[i][j]=(new RGBPixel(0,0,0));
                }
            }
        }
    }
        
    
    
    public PPMImage(java.io.File file)throws InvalidPPMImage,java.io.FileNotFoundException,InvalidPPMLuminocity,InvalidPPMDimensions{
        Scanner sc;
        String magic_number;
        int colour=-1;
        int red;
        int green;
        int blue;
        try{
            sc=new Scanner(file);
        }
        catch(FileNotFoundException ex){
              throw new FileNotFoundException();
        }
        
        magic_number=sc.next();
        imageWidth=sc.nextInt();
        imageHeight=sc.nextInt();
        colordepth = sc.nextInt();
        if ((!(magic_number.equals("P3"+"")))){
            throw new InvalidPPMImage();
        
        }
        if (imageWidth<0 || imageHeight<0){
            throw new InvalidPPMDimensions();
        }
        
        if (colordepth<0){
            throw new InvalidPPMLuminocity(colordepth,255);
        }
        
        
        pixels=new RGBPixel[imageHeight][imageWidth];
        for (int i=0;i<imageHeight;i++){
            for (int j=0;j<imageWidth;j++){
                try{
                
                colour=sc.nextInt();
                if ((colour>this.colordepth) || (colour<0)){
                    throw new InvalidPPMLuminocity (colour,this.colordepth);
                }
                red=colour;
                colour=sc.nextInt();
                if ((colour>this.colordepth) || (colour<0)){
                    throw new InvalidPPMLuminocity (colour,this.colordepth);
                }
                green=colour;
                colour=sc.nextInt();
                if ((colour>this.colordepth) || (colour<0)){
                    throw new InvalidPPMLuminocity (colour,this.colordepth);
                }
                blue=colour;
                }
                catch(InvalidPPMLuminocity ex){
                    throw new InvalidPPMLuminocity (colour,this.colordepth); 
                }
                catch(NoSuchElementException exx){
                    throw new InvalidPPMDimensions();
                }
                
                try{
                    pixels[i][j]=new RGBPixel(red,green,blue);
                }
                catch(Exception e){
                    throw new InvalidPPMDimensions();
                }
            }
        }
        sc.close();
    }     
    @Override
    public void grayscale(){
        int i,j;
        
        int gray;
        for (i=0;i<imageHeight;i++){
            for (j=0;j<imageWidth;j++){
                gray = (int)(pixels[i][j].getRed() * 0.3 + pixels[i][j].getGreen() * 0.59 + pixels[i][j].getBlue() * 0.11);
                pixels[i][j]=new RGBPixel(gray,gray,gray);
                
            }
        }
    }
    @Override
    public void doublesize(){
       int i,j;
       
       int imageDoubleHeight=imageHeight*2;
       int imageDoubleWidth=imageWidth*2;
       RGBPixel doublePixels[][]=new RGBPixel[imageDoubleHeight][imageDoubleWidth];
       for (i=0;i<imageHeight;i++){
           for(j=0;j<imageWidth;j++){
           doublePixels[2*i][2*j]=pixels[i][j];
           doublePixels[2*i+1][2*j]=pixels[i][j];
           doublePixels[2*i][2*j+1]=pixels[i][j];
           doublePixels[2*i+1][2*j+1]=pixels[i][j];
           }
       }
       imageHeight=imageDoubleHeight;
       imageWidth=imageDoubleWidth;
       pixels=doublePixels;
       
    }
        
    
    @Override
    public void halfsize(){
        
        int imageHalfHeight=imageHeight/2;
        int imageHalfWidth=imageWidth/2;
        int red,green,blue;
        RGBPixel halfPixels[][]=new RGBPixel[imageHalfHeight][imageHalfWidth];
        for (int i=0;i<imageHalfHeight;i++){
           for(int j=0;j<imageHalfWidth;j++){
           
           red=((int)((pixels[2*i][2*j].getRed()+pixels[2*i+1][2*j].getRed()+pixels[2*i][2*j+1].getRed()+pixels[2*i+1][2*j+1].getRed())/4));
           green=((int)((pixels[2*i][2*j].getGreen()+pixels[2*i+1][2*j].getGreen()+pixels[2*i][2*j+1].getGreen()+pixels[2*i+1][2*j+1].getGreen())/4));
           blue=((int)((pixels[2*i][2*j].getBlue()+pixels[2*i+1][2*j].getBlue()+pixels[2*i][2*j+1].getBlue()+pixels[2*i+1][2*j+1].getBlue())/4));
           halfPixels[i][j]=new RGBPixel (red,green,blue);
           }
        }
        pixels=halfPixels;
        imageHeight=imageHalfHeight;
        imageWidth=imageHalfWidth;
        
    }
    int getWidth(){
        return this.imageWidth;
    }
    
    int getHeight(){
        return this.imageHeight;
    }
    int getColorDepth(){
        return this.colordepth;
    }
    RGBPixel getPixel(int row, int col)throws IllegalArgumentException{
       
        try{
            return pixels[row][col];
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Row or column is out of image bounds.");
        }
        
    }
    void setPixel(int row, int col, RGBPixel pixel){
        /*if ((pixel.getRed()<0)||(pixel.getGreen()<0)||(pixel.getBlue()<0)|| (pixel.getRed()>colordepth)
                ||(pixel.getGreen()>colordepth)||(pixel.getBlue()>colordepth)){
            return;
        }*/
        try{
            pixels[row][col]=pixel;
        }
        catch (Exception ex){
        }
        
    } 
    
    public void toFile(File file)throws FileNotFoundException{
        FileWriter compileWriter;
        
        try{
        compileWriter = new FileWriter(file,false);
        //compileWriter.write("P3"+"\n");
        compileWriter.write(this.toString());
        compileWriter.close();
        }
        catch(Exception ex){
            throw new FileNotFoundException();
        }
    }
    

    @Override
    public String toString(){
        
        StringWriter writer=new StringWriter();
        writer.append("P3"+"\n"+imageWidth+ "\n"+imageHeight +"\n"+colordepth+"\n" ) ;
        for (int i=0;i<imageHeight;i++){
            for (int j=0;j<imageWidth;j++){
                writer.append(pixels[i][j].getRed()+"\n");
                writer.append(pixels[i][j].getGreen()+"\n");
                writer.append(pixels[i][j].getBlue()+"\n");
            }
            
        }
        
        return writer.toString();
    }
    
    
}
