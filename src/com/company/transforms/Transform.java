package com.company.transforms;

public interface Transform {
    void setSourceData(Object src);
    void calculate();
    Object getResult();
}
