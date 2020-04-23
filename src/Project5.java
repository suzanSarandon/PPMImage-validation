
import java.util.*;
import java.io.*;

public class Project5 {

    private static void menu() {
        System.out.println();
        System.out.println("\tCreate image     (C/c)");
        System.out.println("\tLoad image       (L/l)");
        System.out.println("\tSet pixel        (S/s)");
        System.out.println("\tGrayScale image  (G/g)");
        System.out.println("\tDouble size      (D/d)");
        System.out.println("\tHalf size        (H/h)");
        System.out.println("\tPrint to file    (P/p)");
        System.out.println("\tQuit             (Q/q)");
        System.out.print("\t> ");
    }

    public static void main(String[] args) {
        boolean imgLoaded = false;
        Scanner sc = new Scanner(System.in);
        String option;
        PPMImage img = null;
        do {  
            menu();
            option = sc.next().toUpperCase();
            switch (option.charAt(0)) {
                case 'C':
                    System.out.println("Image width?: ");
                    int width = sc.nextInt();
                    System.out.println("Image height?: ");
                    int height = sc.nextInt();
                    System.out.println("Image color depth?: ");
                    int colordepth = sc.nextInt();
                    try {
                        img = new PPMImage(width, height, colordepth);
                        imgLoaded = true;
                    } catch (InvalidPPMDimensions ex) {
                        System.out.println("## Invalid PPM File: Error in img dimensions ##");
                        System.out.println("Image creation failed. Try again..");
                        imgLoaded = false;
                    } catch (InvalidPPMLuminocity ex) {
                        System.out.println("## Invalid PPM File: Error in img luminocity (actual: " 
                                + ex.getInvalidLuminocity() + ", max: " + ex.getMaxLuminocity() + ") ##");
                        System.out.println("Image creation failed. Try again..");
                        imgLoaded = false;
                    }
                    break;
                case 'L':
                    System.out.print("Image filepath?: ");
                    String filepath = sc.next();
                    try {
                        img = new PPMImage(new File(filepath));
                        imgLoaded = true;
                    } catch (FileNotFoundException ex) {
                        System.out.println("Unable to open file \"" + filepath + "\" for reading!");
                        imgLoaded = false;
                    } catch (InvalidPPMLuminocity ex) {
                        System.out.println("## Invalid PPM File: Error in img luminocity (actual: " 
                                + ex.getInvalidLuminocity() + ", max: " + ex.getMaxLuminocity() + ") ##");
                        System.out.println("Loading image failed. Try again..");
                        imgLoaded = false;
                    } catch (InvalidPPMDimensions ex) {
                        System.out.println("## Invalid PPM File: Error in img dimensions ##");
                        System.out.println("Loading image failed. Try again..");
                        imgLoaded = false;
                    } catch (InvalidPPMImage ex) {
                        System.out.println("## Invalid PPM File ##");
                        System.out.println("Loading image failed. Try again..");
                        imgLoaded = false;
                    }
                    break;
                case 'S':
                    if (!imgLoaded) {
                        System.out.println("You must first create or load an image. Try again..");
                        break;
                    }
                    System.out.println("Row?: ");
                    int row = sc.nextInt();
                    System.out.println("Col?: ");
                    int col = sc.nextInt();
                    System.out.println("Red?: ");
                    int red = sc.nextInt();
                    System.out.println("Green?: ");
                    int green = sc.nextInt();
                    System.out.println("Blue?: ");
                    int blue = sc.nextInt();
                    img.setPixel(row, col, new RGBPixel(red, green, blue));
                    break;
                case 'G':
                    if (!imgLoaded) {
                        System.out.println("You must first create or load an image. Try again..");
                        break;
                    }
                    img.grayscale();
                    break;
                case 'D':
                    if (!imgLoaded) {
                        System.out.println("You must first create or load an image. Try again..");
                        break;
                    }
                    img.doublesize();
                    break;
                case 'H':
                    if (!imgLoaded) {
                        System.out.println("You must first create or load an image. Try again..");
                        break;
                    }
                    img.halfsize();
                    break;
                case 'P':
                    if (!imgLoaded) {
                        System.out.println("You must first create or load an image. Try again..");
                        break;
                    }
                    System.out.print("Filepath to print image (or SCR to print to screen)?: ");
                    filepath = sc.next();
                    if (filepath.equals("SCR")) {
                        System.out.println("\n===");
                        System.out.println(img);
                        break;
                    }
                    try {
                        img.toFile(new File(filepath));
                        System.out.println("File written successfully!");
                    } catch (FileNotFoundException ex) {
                        System.out.println("Unable to open file \"" + filepath + "\" for writing!");
                    }
                    break;
                case 'Q':
                    break;
                default:
                    System.out.println("Invalid option \"" + option + "\". Try again.");
            }
        } while (option.charAt(0) != 'Q');

    }
}
