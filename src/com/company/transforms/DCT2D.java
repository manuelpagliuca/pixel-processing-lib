package com.company.transforms;

import com.company.utilities.Utility;

public class DCT2D implements Transform {
    double [][] source;
    double [][] result;

    @Override
    public void setSourceData(Object src) {
        source = (double [][]) src;
        result = new double[source.length][source.length];


    }

    @Override
    public void calculate() {
        double alphaU;
        double alphaV;

        for(int u = 0; u < source.length; u++)
        {
            for(int v = 0; v < source.length; v++)
            {
                alphaU = Utility.alpha(u, source.length);
                alphaV = Utility.alpha(v, source.length);
                double outerSum = 0;

                for(int x = 0; x < source.length ; x++)
                {
                    double innerSum = 0;
                    for(int y = 0; y < source.length; y++)
                    {
                        double firstCos = Math.cos( ((2 * x + 1) * u * Math.PI)/(2 * source.length) );
                        double secondCos = Math.cos( ((2 * y + 1) * v * Math.PI) / (2 * source.length) );

                        innerSum += source[x][y] * firstCos * secondCos;
                    }
                    outerSum += innerSum;
                }
                result[u][v] = alphaU * alphaV * outerSum;
            }
        }

    }

    @Override
    public Object getResult() {
        return (Object) result;
    }
}
