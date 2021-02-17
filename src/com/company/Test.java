package com.company;

import com.company.transforms.*;
import com.company.utilities.ComplexNumber;

import java.util.Scanner;


public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choice a test to run\nTest DCT1D [0]\nTest DCT2D [1]\nTest DFT [2]\nTest JPEGPipeline [3]");
        int choice = scanner.nextInt();
        switch (choice)
        {
            case 0:
                testDCT1D();
                break;
            case 1:
                testDCT2D();
                break;
            case 2:
                testDFT();
                break;
            case 3:
                testJPEGPipeline();
                break;
            default:
                System.out.println("Choice not available, closing the test program.");
                System.exit(1);
        }
    }

    public static void testJPEGPipeline()
    {
        JPEGPipeline jpegPipeline = new JPEGPipeline();
        jpegPipeline.setFileName("SampleCol.jpg");
        jpegPipeline.compute();
    }


    public static void testDFT()
    {
        Transform myDFT = new DFT();
        Transform myIDFT = new IDFT();

        System.out.println("DFT");
        System.out.println("INITIAL VALUES");

        double[] src = new double[8];

        src[0] = 20.0;
        src[1] = 12.0;
        src[2] = 18.0;
        src[3] = 56.0;
        src[4] = 83.0;
        src[5] = 110.0;
        src[6] = 104.0;
        src[7] = 114.0;

        for(int i = 0; i < src.length; i++)
            System.out.println(src[i]);

        myDFT.setSourceData(src);
        myDFT.calculate();
        ComplexNumber[]res = (ComplexNumber[]) myDFT.getResult();

        System.out.println("\nAFTER DFT VALUES");
        for(int i = 0; i < res.length; i++)
            System.out.println(res[i].getReal() + " + " + res[i].getImaginary() );

        myIDFT.setSourceData(res);
        myIDFT.calculate();
        double [] inverse = (double[]) myIDFT.getResult();

        System.out.println("\nINVERSE DFT VALUES");
        for(int i = 0; i < inverse.length; i++)
            System.out.println(inverse[i]);

    }

    public static void testDCT2D()
    {
        Transform myDCT2D = new DCT2D();
        Transform myIDCT2D = new IDCT2D();

        System.out.println("DCT 2D");
        System.out.println("INITIAL VALUES");


        double src[][] = new double[8][8];
        double result[][];
        loadRandomMatrix(src);
        printMatrix(src);

        System.out.println("\nAFTER DCT2D");
        myDCT2D.setSourceData(src);
        myDCT2D.calculate();
        result = (double[][]) myDCT2D.getResult();
        printMatrix(result);

        System.out.println("\nINVERSE DCT2D");
        double inverse[][];
        myIDCT2D.setSourceData(result);
        myIDCT2D.calculate();
        inverse = (double[][]) myIDCT2D.getResult();
        printMatrix(inverse);
    }

    public static void loadRandomMatrix(double [][]matrix)
    {
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix.length; j++)
                matrix [i][j] = Math.random();
    }

    public static void printMatrix(double [][]matrix)
    {
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++)
                System.out.print(matrix[i][j] + " | ");
            System.out.println();
        }
    }


    public static void testDCT1D()
    {
        Transform myDCT1D = new DCT1D();
        Transform myIDCT1D = new IDCT1D();

        System.out.println("DCT 1D RESULT");

        double[] src = new double[8];

        src[0] = 20.0;
        src[1] = 12.0;
        src[2] = 18.0;
        src[3] = 56.0;
        src[4] = 83.0;
        src[5] = 110.0;
        src[6] = 104.0;
        src[7] = 114.0;

        myDCT1D.setSourceData(src);
        myDCT1D.calculate();
        double res [] = (double[]) myDCT1D.getResult();

        for(int i = 0; i < res.length; i++)
            System.out.println(res[i]);

        System.out.println("\nDCT 1D INVERSA");

        myIDCT1D.setSourceData(res);
        myIDCT1D.calculate();
        double ires[] = (double[]) myIDCT1D.getResult();

        for(int i = 0; i < res.length; i++)
            System.out.println(ires[i]);
    }

}
