package com.company.transforms;

import com.company.utilities.ComplexNumber;

public class DFT implements Transform {
    double []source;
    ComplexNumber[]result;
    //ComplexNumber r = new ComplexNumber();

    @Override
    public void setSourceData(Object src) {
        source = (double[]) src;
        result = new ComplexNumber[source.length];
    }

    @Override
    public void calculate() {
        double prod = (double) 1 / (double) source.length;

        for(int k=0; k < source.length; k++)
        {
            double sum = 0.0;
            ComplexNumber r = new ComplexNumber();

            for(int n = 0; n < source.length; n++)
                sum += source[n] * Math.cos( ((2 * Math.PI) / source.length) * k * n);

            result[k] = new ComplexNumber();
            result[k].setReal(prod * sum);

            sum = 0.0;

            for(int n =0; n < source.length; n++)
                sum += source[n]*Math.sin( (2 * Math.PI) / source.length * k * n);

            result[k].setImaginary( (-prod) * sum);
        }

    }

    @Override
    public Object getResult() {
        return (Object) result;
    }
}
