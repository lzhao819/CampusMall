package com.usc.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException{
    private static final long serialVersionUID = 4773036550412750789L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }

}
