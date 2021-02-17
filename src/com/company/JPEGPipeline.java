package com.company;

import com.company.transforms.DCT2D;
import com.company.transforms.IDCT2D;
import com.company.transforms.Transform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPEGPipeline {

    private static String m_path = "samples\\";
    public static BufferedImage originalImage;
    public static BufferedImage processedImage;

    public static JLabel label;
    public static JLabel label2;
    public static JFrame f;
    public static JFrame f2;

    final static int[][] quantizationTable = {
        {16, 11, 10, 16, 24, 40, 51, 61},
        {12, 12, 14, 19, 26, 58, 60, 55},
        {14, 13, 16, 24, 40, 57, 69, 56},
        {14, 17, 22, 29, 51, 87, 80, 62},
        {18, 22, 37, 56, 68, 109, 103, 77},
        {24, 35, 55, 64, 81, 104, 113, 92},
        {49, 64, 78, 87, 103, 121, 120, 101},
        {72, 92, 95, 98, 112, 100, 103, 99}
    };

    JPEGPipeline()  { }
    JPEGPipeline(String fileName)
    {
        m_path += fileName;
    }

    public void setFileName(String fileName)
    {
        m_path += fileName;
    }

    public void  loadImage()
    {
        try {
            originalImage = ImageIO.read(new File(m_path));
            processedImage = ImageIO.read(new File(m_path));
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image resolution: " + originalImage.getWidth() + "x"+ originalImage.getHeight());
    }

    public void compute()
    {
        //  Load image
        loadImage();

        //  Initialize variables
        int start_x = 0, start_y = 0;
        int bands = originalImage.getRaster().getNumBands();

        while(start_y != originalImage.getHeight()) {
            double[][] DCTMatrix = new double[8][8];
            double[][] outMatrix;
            Transform myDCT = new DCT2D();
            Transform myIDCT = new IDCT2D();

            //  Load 8x8 matrices
            for (int channel = 0; channel < bands; channel++) {
                // read pixel on given channel
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++)
                        DCTMatrix[i][j] = originalImage.getRaster().getSample(start_x + i, start_y + j, channel) - 128;

                //  Perform DCT
                myDCT.setSourceData(DCTMatrix);
                myDCT.calculate();
                DCTMatrix = (double[][]) myDCT.getResult();

                //  Quantize
                int[][] quantizedMatrix = quantize(DCTMatrix);

                //  De-quantize
                DCTMatrix = deQuantize(quantizedMatrix);

                //  Perform Inverse DCT
                myIDCT.setSourceData(DCTMatrix);
                myIDCT.calculate();
                outMatrix = (double[][]) myIDCT.getResult();

                //  Store results using the same channel
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        //  apply inverse 0-centering and clamping the values in [0, 255]
                        outMatrix[i][j] += 128;
                        outMatrix[i][j] = (outMatrix[i][j] < 0) ? 0 : (outMatrix[i][j] > 255) ? 255 : outMatrix[i][j];

                        //  set samples
                        processedImage.getRaster().setSample(start_x + i, start_y + j, channel, outMatrix[i][j]);
                    }
                }
            }

            //  Increment counters
            start_x += 8;
            if (start_x >= originalImage.getWidth()) {
                start_y += 8;
                start_x = 0;
            }
        }
        display();
    }

    public void display() {
        label = new JLabel(new ImageIcon(originalImage));
        f = new JFrame("Original Picture");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(20,20);
        f.setVisible(true);

        label2 = new JLabel(new ImageIcon(processedImage));
        f2 = new JFrame("Quantized Picture");
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.getContentPane().add(label2);
        f2.pack();
        f2.setLocation(100,100);
        f2.setVisible(true);

        try {
            File outputFile = new File("Sample output.png");
            ImageIO.write(processedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] quantize (double[][] source) {
        int[][] output = new int[8][8];

        for(int i = 0; i < source.length; i++)
            for(int j = 0; j < source.length; j++)
                output[i][j] = (int) Math.round(source[i][j]/ quantizationTable[i][j]);

        return output;
    }

    public double[][] deQuantize (int[][] source) {
        double[][] output = new double[8][8];

        for(int i = 0; i < source.length; i++)
            for(int j = 0; j < source.length; j++)
                output[i][j]= source[i][j] * quantizationTable[i][j];

        return output;
    }

}
