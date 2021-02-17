package com.company.transforms;

import com.company.utilities.Utility;

public class IDCT2D implements Transform {
    double [][] source;
    double [][] result;

    @Override
    public void setSourceData(Object src) {
        source = (double [][]) src;
        result = new double [source.length][source.length];

    }

    @Override
    public void calculate() {

        for(int x = 0; x < source.length; x++)
        {
            for (int y = 0; y < source.length; y++)
            {
                double outerSum = 0;
                for(int u=0; u < source.length; u++)
                {
                    double innerSum = 0;
                    for(int v =0; v < source.length; v++)
                    {
                        double alphaU = Utility.alpha(u, source.length);
                        double alphaV = Utility.alpha(v, source.length);

                        double firstCos = Math.cos( ((2 * x + 1) * u * Math.PI) / (2 * source.length) );
                        double secondCos = Math.cos( ((2 * y + 1) * v * Math.PI) / (2 * source.length) );

                        innerSum += alphaU * alphaV * source[u][v] * firstCos * secondCos;
                    }
                    outerSum += innerSum;
                }
                result[x][y] = outerSum;
            }
        }
    }

    @Override
    public Object getResult() {
        return (Object)result;
    }
}
