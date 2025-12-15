import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);

		image = flippedHorizontally(image); // הופך את התמונה שהיפוך אנכית, בחזרה אנכית (היפוך כפול = מקור)
        System.out.println();
        print(image);

		Color[][] grayImage = grayScaled(tinypic);
        System.out.println();
        System.out.println("--- Grayscale Image (tinypic) ---");
        print(grayImage);
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j= 0; j < numCols; j++) {
				int r= in.readInt();
				int g= in.readInt();
				int b= in.readInt();
				image[i][j] = new Color(r, g, b);	
					}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		int numRows=image.length;
		int numCols=image[0].length;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        Color[][] flippedImage = new Color[height][width];
        
        for (int i = 0; i < height; i++) { // עובר על השורות
            for (int j = 0; j < width; j++) { // עובר על העמודות
                flippedImage[i][j] = image[i][width - 1 - j]; 
            }
        }
        return flippedImage;
    }
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
	   int height = image.length;
        int width = image[0].length;
        Color[][] flippedImage = new Color[height][width];
        
        for (int i = 0; i < height; i++) { // עובר על השורות
            for (int j = 0; j < width; j++) { // עובר על העמודות
                flippedImage[i][j] = image[height-1-i][j]; 
            }
        }
        return flippedImage;
    }
	
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int r= pixel.getRed();
		int g=pixel.getRed();
		int b=pixel.getBlue();
		double lumDouble= 0.299*r+ 0.587 * g + 0.114 * b;
		int lum = (int) Math.round(lumDouble);
		return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	/**
     * Returns an image which is the grayscaled version of the given image.
     */
    public static Color[][] grayScaled(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        
        Color[][] grayImage = new Color[height][width];
            for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color originalPixel = image[i][j];
                Color grayPixel = luminance(originalPixel);
                grayImage[i][j] = grayPixel;
            }
        }
        return grayImage;
    }
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	/**
     * Returns an image which is the scaled version of the given image. 
     * The image is scaled (resized) to have the given width and height.
     */
    public static Color[][] scaled(Color[][] image, int width, int height) {
        
        int originalHeight = image.length;
        int originalWidth = image[0].length;
        
        Color[][] scaledImage = new Color[height][width];
        
        double heightRatio = (double) originalHeight / height;
        double widthRatio = (double) originalWidth / width;

        for (int iPrime = 0; iPrime < height; iPrime++) {
            for (int jPrime = 0; jPrime < width; jPrime++) {
                
                    int i = (int) (iPrime * heightRatio);
                
                    int j = (int) (jPrime * widthRatio);
                
                scaledImage[iPrime][jPrime] = image[i][j];
            }
        }
        
        return scaledImage;
    }
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	/**
     * Computes and returns a blended color which is a linear combination of the two given
     * colors. Each r, g, b, value v in the returned color is calculated using the formula 
     * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
     * values in the two input color.
     */
    public static Color blend(Color c1, Color c2, double alpha) {
        
        double oneMinusAlpha = 1.0 - alpha;
        
        double rBlendDouble = alpha * c1.getRed() + oneMinusAlpha * c2.getRed();
        int rBlend = (int) Math.round(rBlendDouble);
        
        double gBlendDouble = alpha * c1.getGreen() + oneMinusAlpha * c2.getGreen();
        int gBlend = (int) Math.round(gBlendDouble);
        
        double bBlendDouble = alpha * c1.getBlue() + oneMinusAlpha * c2.getBlue();
        int bBlend = (int) Math.round(bBlendDouble);
        
        return new Color(rBlend, gBlend, bBlend);
    }
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	/**
     * Cosntructs and returns an image which is the blending of the two given images.
     * The blended image is the linear combination of (alpha) part of the first image
     * and (1 - alpha) part the second image.
     * The two images must have the same dimensions.
     */
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        
        int height = image1.length;
        int width = image1[0].length;
        
        Color[][] blendedImage = new Color[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                
                Color c1 = image1[i][j];
                Color c2 = image2[i][j];
                
                Color blendedPixel = blend(c1, c2, alpha);
                
                blendedImage[i][j] = blendedPixel;
            }
        }
        
        return blendedImage;
    }

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	/**
     * Creates a an animated transition from the source image to the target image, 
     * by blending the two images in n steps.
     * The target image is first scaled to the dimensions of the source image.
     */
    public static void morph(Color[][] source, Color[][] target, int n) {
        
        int height = source.length;
        int width = source[0].length;
        
        Color[][] scaledTarget = scaled(target, width, height);
        
        for (int i = 0; i <= n; i++) {

            double alpha = (double) (n - i) / n;
 
            Color[][] blendedImage = blend(source, scaledTarget, alpha);

            Runigram.display(blendedImage);

        }
    }
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

