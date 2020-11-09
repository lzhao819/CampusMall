package com.usc.o2o.exceptions;

public class ShopOperationException extends RuntimeException{
    private static final long serialVersionUID = 6813725044369328449L;
    public ShopOperationException(String msg){
        super(msg);
    }
}
