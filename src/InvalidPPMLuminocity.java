public class InvalidPPMLuminocity extends InvalidPPMImage{
    int invalidLuminocity;
    int max_colorDepth=255;
    
    
    public InvalidPPMLuminocity(int invalidLuminocity, int colorDepth){
        max_colorDepth=colorDepth;
        this.invalidLuminocity=invalidLuminocity;
    }
    
    
    public int getInvalidLuminocity(){
        return this.invalidLuminocity;
        
    }
    public int getMaxLuminocity(){
        return max_colorDepth;
    }
    
}
