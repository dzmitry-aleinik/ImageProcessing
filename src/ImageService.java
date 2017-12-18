import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class ImageService {
    public static Color medianFilter(int x, int y, BufferedImage image){
        ArrayList<Integer> reds = new ArrayList<>();
        ArrayList<Integer> greens = new ArrayList<>();
        ArrayList <Integer>  blues = new ArrayList<>();
        for (int i= -1 ; i<2 ; i++){
            int upPixel = image.getRGB(x + i, y + 1);
            int downPixel = image.getRGB(x + i, y - 1);
            int middlePixel = image.getRGB(x,y+i);
            Color upColor = new Color(upPixel);
            Color downColor = new Color(downPixel);
            Color middleColor = new Color (middlePixel);
            reds.add(upColor.getRed());
            greens.add(upColor.getGreen());
            blues.add(upColor.getBlue());
            reds.add(downColor.getRed());
            greens.add(downColor.getGreen());
            blues.add(downColor.getBlue());
            reds.add(middleColor.getRed());
            greens.add(middleColor.getGreen());
            blues.add(middleColor.getBlue());
        }

        Collections.sort(reds);
        Collections.sort(greens);
        Collections.sort(blues);

        return new Color(reds.get(4),greens.get(4),blues.get(4));





    }
}
