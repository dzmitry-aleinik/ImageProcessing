
/*  use image with paper and salt noise to te noiseReducing*/

import marvin.image.MarvinImage;
import org.marvinproject.image.histogram.colorHistogram.ColorHistogram;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MainFrame extends JFrame {

    private String imagePath;
    private BufferedImage image;

    public MainFrame() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1500,900));
        this.setLocationRelativeTo(null);
        JMenuBar jMenuBar =new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu tools = new JMenu("Tools");
        JMenuItem medianFilter = new JMenuItem("MedianFilter");
        JMenuItem averageFilter = new JMenuItem("AverageFilter");
        JMenuItem imageHistogram = new JMenuItem("Image Histogram");
        JMenuItem imageGray = new JMenuItem("Grayscale");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem imageBinary = new JMenuItem("Binary");
        file.add(open);
        tools.add(imageHistogram);
        tools.add(imageGray);
        tools.add(imageBinary);
        tools.add(averageFilter);
        tools.add(medianFilter);
        jMenuBar.add(file);
        jMenuBar.add(tools);
        this.setJMenuBar(jMenuBar);
        JLabel labelForImage = new JLabel();
        this.add(labelForImage);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.showOpenDialog(null);
                    imagePath = jFileChooser.getSelectedFile().getPath();
                    image = ImageIO.read(new File(imagePath));
                    labelForImage.setIcon(new ImageIcon(imagePath));
                }catch (IOException i){
                }
            }
        });
        imageHistogram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ColorHistogram histogram = new ColorHistogram();
                    histogram.process(new MarvinImage(image), new MarvinImage(image));

                } catch (Exception i) {
                    JOptionPane.showMessageDialog(null, i.getMessage());
                }

            }
        });
        imageGray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image = ImageProcessor.getGrayscale(image);
                labelForImage.setIcon(new ImageIcon(image));
            }
        });
        medianFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                image = ImageProcessor.removeNoiseMedianFilter(image);
                labelForImage.setIcon(new ImageIcon(image));
            }
        });
        imageBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image = ImageProcessor.performBinary(image);
                labelForImage.setIcon(new ImageIcon(image));
            }
        });
        averageFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                image = ImageProcessor.removeNoiseAverageFilter(image);
                labelForImage.setIcon(new ImageIcon(image));
            }
        });
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
class WrongImageException extends  Exception{
    public WrongImageException(String message){
        super(message);
    }
}
