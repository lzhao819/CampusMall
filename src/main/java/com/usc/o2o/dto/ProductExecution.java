package com.usc.o2o.dto;

import com.usc.o2o.entity.Product;
import com.usc.o2o.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    private int state;//结果状态
    private String stateInfo;//状态标示
    private int count;//商品数量
    private Product product;//操作的product
    private List<Product> productList;//获取的product列表
    public ProductExecution(){

    }
    public ProductExecution(ProductStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    public ProductExecution(ProductStateEnum stateEnum, Product product){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }
    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
