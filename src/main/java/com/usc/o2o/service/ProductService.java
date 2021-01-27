package com.usc.o2o.service;

import com.usc.o2o.dto.ImageHolder;
import com.usc.o2o.dto.ProductExecution;
import com.usc.o2o.entity.Product;
import com.usc.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    /**
     * 添加 商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
}
