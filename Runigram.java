import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

    public static void main(String[] args) {
        // Code here is for YOUR testing only. The autograder does not use main.
        Color[][] tinypic = read("tinypic.ppm");
        print(tinypic);

        System.out.println();
        Color[][] flipped = flippedHorizontally(tinypic);
        print(flipped);

        System.out.println();
        Color[][] gray = grayScaled(tinypic);
        print(gray);
    }

    /** Returns a 2D array of Color values, representing the image data
     * stored in the given PPM file. */
    public static Color[][] read(String fileName) {
        In in = new In(fileName);
        in.readString();          // P3
        int numCols = in.readInt();
        int numRows = in.readInt();
        in.readInt();             // max color value (255)

        Color[][] image = new Color[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int r = in.readInt();
                int g = in.readInt();
                int b = in.readInt();
                image[i][j] = new Color(r, g, b);
            }
        }
        return image;
    }

    // Prints the RGB values of a given color.
    private static void print(Color c) {
        System.out.print("(");
        System.out.printf("%3d,%3d,%3d", c.getRed(), c.getGreen(), c.getBlue());
        System.out.print(") ");
    }

    // Prints the pixels of the given image.
    private static void print(Color[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                print(image[i][j]);
            }
            System.out.println();
        }
    }

    /** Returns an image which is the horizontally flipped version of the given image. */
    public static Color[][] flippedHorizontally(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        Color[][] result = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = image[i][width - 1 - j];
            }
        }
        return result;
    }

    /** Returns an image which is the vertically flipped version of the given image. */
    public static Color[][] flippedVertically(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        Color[][] result = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = image[height - 1 - i][j];
            }
        }
        return result;
    }

    // Computes the luminance of a pixel (CAST TO INT, NO ROUNDING)
    private static Color luminance(Color pixel) {
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();

        int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
        return new Color(lum, lum, lum);
    }

    /** Returns an image which is the grayscaled version of the given image. */
    public static Color[][] grayScaled(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        Color[][] result = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = luminance(image[i][j]);
            }
        }
        return result;
    }

    /** Returns an image which is the scaled version of the given image. */
    public static Color[][] scaled(Color[][] image, int width, int height) {
        int originalHeight = image.length;
        int originalWidth = image[0].length;

        Color[][] result = new Color[height][width];

        double heightRatio = (double) originalHeight / height;
        double widthRatio = (double) originalWidth / width;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int srcI = (int) (i * heightRatio);
                int srcJ = (int) (j * widthRatio);
                result[i][j] = image[srcI][srcJ];
            }
        }
        return result;
    }

    /** Blends two colors using alpha (CAST TO INT, NO ROUNDING). */
    public static Color blend(Color c1, Color c2, double alpha) {
        int r = (int) (alpha * c1.getRed()   + (1 - alpha) * c2.getRed());
        int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
        int b = (int) (alpha * c1.getBlue()  + (1 - alpha) * c2.getBlue());
        return new Color(r, g, b);
    }

    /** Blends two images of the same dimensions. */
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        int height = image1.length;
        int width = image1[0].length;
        Color[][] result = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = blend(image1[i][j], image2[i][j], alpha);
            }
        }
        return result;
    }

    /** Morphs source image into target image in n steps. */
    public static void morph(Color[][] source, Color[][] target, int n) {
        int height = source.length;
        int width = source[0].length;

        Color[][] scaledTarget = scaled(target, width, height);

        for (int i = 0; i <= n; i++) {
            double alpha = (double) (n - i) / n;
            Color[][] blended = blend(source, scaledTarget, alpha);
            display(blended);
            StdDraw.pause(500);
        }
    }

    /** Creates a canvas for the given image. */
    public static void setCanvas(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        StdDraw.setTitle("Runigram");
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
    }

    /** Displays the given image on the current canvas. */
    public static void display(Color[][] image) {
        int height = image.length;
        int width = image[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StdDraw.setPenColor(
                        image[i][j].getRed(),
                        image[i][j].getGreen(),
                        image[i][j].getBlue()
                );
                StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
            }
        }
        StdDraw.show();
    }
}
