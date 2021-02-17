package com.company.utilities;

public class ComplexNumber {

    private double m_realPart;
    private double m_imaginaryPart;

    public ComplexNumber()
    {
        m_realPart = 0.0;
        m_imaginaryPart = 0.0;
    }

    ComplexNumber(double t_realPart, double t_imaginaryPart) {
        m_realPart = t_realPart;
        m_imaginaryPart = t_imaginaryPart;
    }

    public double getReal()
    {
        return m_realPart;
    }

    public double getImaginary()
    {
        return m_imaginaryPart;
    }

    public void setReal(double t_realPart)
    {
        m_realPart = t_realPart;
    }

    public void setImaginary(double t_imaginaryPart)
    {
        m_imaginaryPart = t_imaginaryPart;
    }

    public double getModule()
    {
        return Math.sqrt(m_realPart*m_realPart + m_imaginaryPart*m_imaginaryPart);
    }

    public double getPhase()
    {
        return Math.atan(m_imaginaryPart / m_realPart);
    }

}
