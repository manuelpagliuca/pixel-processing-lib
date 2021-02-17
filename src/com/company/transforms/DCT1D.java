package com.company.transforms;

import com.company.utilities.Utility;

public class DCT1D implements Transform {
    double[] source;
    double[] result;

    @Override
    public void setSourceData(Object src) {
        source = (double[]) src;
        result = new double[source.length];
    }

    @Override
    public void calculate() {
        for (int u = 0; u < source.length; u++)
        {
            double a = Utility.alpha(u, source.length);
            double sum = 0;

            for (int x = 0; x < source.length; x++)
            {
                double fx = source[x];
                double numCos = ( ((double)2 * (double)x) + (double)1) * (double)u * Math.PI;
                double denCos = (double) 2 * source.length;
                double cosPortion = Math.cos(numCos / denCos);

                sum += (fx * cosPortion);
            }

            result[u] = a * sum;
        }
    }

    @Override
    public Object getResult() {
        return (double []) result;
    }


}
