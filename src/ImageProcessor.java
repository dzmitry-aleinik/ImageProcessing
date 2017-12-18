import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.*;

/**
 * Created by HP on 29.11.2017.
 */
public class ImageProcessor {

    public static void getHistogram(BufferedImage bufferedImage){

        HashMap<Integer,Integer> histogramMap = new HashMap<>();
        for  (int i =0;  i < bufferedImage.getWidth(); i++){
            for (int j =0; j < bufferedImage.getHeight(); j++){
                Integer pixel = bufferedImage.getRGB(i,j);
                Color c = new Color( pixel);
                int redValue = c.getRed();
                int blueValue = c.getBlue();
                int greenValue = c.getGreen();
                int currentNumber = histogramMap.get(redValue);
                if (histogramMap.get(redValue)==null){
                    histogramMap.put(redValue,1);
                }else{
                    currentNumber++;
                    histogramMap.put(redValue,currentNumber);
                }
            }
        }
    }
    public static BufferedImage getGrayscale(BufferedImage image){
        BufferedImage grayImage = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = grayImage.getRaster();
        for ( int i=0; i < image.getWidth(); i++){
            for (int j=0; j < image.getHeight();j++){
                Integer pixel = image.getRGB(i,j);
                Color color = new Color(pixel);
                int redValue = color.getRed();
                int blueValue = color.getBlue();
                int greenValue = color.getGreen();
                int newValue = (int)(redValue * 0.3 +  greenValue * 0.59 +  blueValue * 0.11);
                raster.setPixel(i, j, new int[] { newValue });
            }

        }
        return grayImage;

    }

    public static BufferedImage performBinary(BufferedImage image) {
         BufferedImage grayscale = getGrayscale(image);
        int step = step(grayscale);
        int min = 0;
        int max = 1;
        Raster originalRaster = grayscale.getData();
        BufferedImage binary = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster resultRaster = binary.getRaster();
        for (int x = 0; x < originalRaster.getWidth(); x++) {
            for (int y = 0; y < originalRaster.getHeight(); y++) {
                int pix = originalRaster.getPixel(x, y, (int[]) null)[0];
                if (pix < step) {
                    resultRaster.setPixel(x, y, new int[] { min });
                } else {
                    resultRaster.setPixel(x, y, new int[] { max });
                }
            }
        }
        return binary;
    }
    private static int step(BufferedImage image) {
        return iterationStep(image, commonStep(image));
    }
    private static  int commonStep(BufferedImage image) {
        int min = 255;
        int max = 0;
        Raster raster = image.getData();
        for (int x = 0; x < raster.getWidth(); x++) {
            for (int y = 0; y < raster.getHeight(); y++) {
                int pix = raster.getPixel(x, y, (int[]) null)[0];
                if (pix < min) {
                    min = pix;
                }
                if (pix > max) {
                    max = pix;
                }
            }
        }
        return (min + max) / 2;
    }
    private static int iterationStep(BufferedImage image, int commonStep) {
        java.util.List<Integer> min = new ArrayList<Integer>();
        java.util.List<Integer> max = new ArrayList<Integer>();
        Raster raster = image.getData();
        for (int x = 0; x < raster.getWidth(); x++) {
            for (int y = 0; y < raster.getHeight(); y++) {
                int pix = raster.getPixel(x, y, (int[]) null)[0];
                if (pix >= commonStep) {
                    max.add(pix);
                } else {
                    min.add(pix);
                }
            }
        }
        int step = (average(min) + average(max)) / 2;
        return step == commonStep ? step : iterationStep(image, step);
    }
    private static int average(java.util.List<Integer> list) {
        int result = 0;
        for (Integer pix : list) {
            result += pix;
        }
        return list.size() == 0 ? 0 : result / list.size();
    }
    public static BufferedImage removeNoiseMedianFilter(BufferedImage image) {
        for(int i = 1 ; i < image.getWidth()-1; i++){
           for (int j = 1; j < image.getHeight()-1; j++){
               image.setRGB(i,j, ImageService.medianFilter(i,j,image).getRGB());
           }
        }
        return image;

    }
    public static BufferedImage removeNoiseAverageFilter(BufferedImage image) {
        for(int i = 1 ; i < image.getWidth()-1; i++){
            for (int j = 1; j < image.getHeight()-1; j++){
                image.setRGB(i,j, ImageService.medianFilter(i,j,image).getRGB());
            }
        }
        return image;

    }
}
