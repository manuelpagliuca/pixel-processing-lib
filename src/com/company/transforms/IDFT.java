package com.company.transforms;

import com.company.utilities.ComplexNumber;

public class IDFT implements Transform {
    ComplexNumber[] source;
    double result[];

    @Override
    public void setSourceData(Object src) {
        source = (ComplexNumber[]) src;
        result = new double[source.length];
    }

    @Override
    public void calculate() {
        for(int k =0; k < source.length; k++)
        {
            double sum = 0.0;
            for(int n = 0; n < source.length ; n++)
            {
                 double firstCos = Math.cos( (2 * Math.PI * k * n) / (double) source.length);
                 double firstSin = Math.sin( (2 * Math.PI * k *n) / (double) source.length);
                 sum += (source[n].getReal() * firstCos) - (source[n].getImaginary() * firstSin);
            }

            result[k] = sum;
        }
    }

    @Override
    public Object getResult() {
        return (Object) result;
    }
}
