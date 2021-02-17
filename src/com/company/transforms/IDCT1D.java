package com.company.transforms;

import com.company.utilities.Utility;

public class IDCT1D implements Transform {
    double[] source;
    double[] result;

    @Override
    public void setSourceData(Object src) {
        source = (double[]) src;
        result = new double[source.length];
    }

    @Override
    public void calculate() {
        double a = 0;

        for(int x = 0; x < source.length; x++)
        {
            double sum = 0;
            for(int u = 0; u < source.length; u++)
            {
                a = Utility.alpha(u, source.length);
                double C = source[u];
                double numCos = (((double)2 * (double)x) + (double)1) * (double)u * Math.PI;
                double denCos = (double)2 * source.length;
                double cosPortion = Math.cos( numCos / denCos);

                sum += (a * C * cosPortion);
            }
            result[x] = sum;
        }
    }

    @Override
    public Object getResult() {
        return (Object) result;
    }
}
