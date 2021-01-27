package com.usc.o2o.exceptions;

public class ProductOperationException extends RuntimeException{
    private static final long serialVersionUID = 6845666044369328449L;
    public ProductOperationException(String msg){
        super(msg);
    }
}
