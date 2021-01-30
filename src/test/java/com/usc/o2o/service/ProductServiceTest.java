package com.usc.o2o.service;

import com.usc.o2o.BaseTest;
import com.usc.o2o.dto.ImageHolder;
import com.usc.o2o.dto.ProductExecution;
import com.usc.o2o.entity.Product;
import com.usc.o2o.entity.ProductCategory;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.enums.ProductCategoryStateEnum;
import com.usc.o2o.enums.ProductStateEnum;
import com.usc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    @Ignore
    public void testAddProduct() throws ShopOperationException, FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(34L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(19L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductCategoryStateEnum.SUCCESS.getState());
        // 创建缩略图文件流
        File thumbnailFile = new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1=new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2=new File("/Users/lu/Documents/project/CampusMall/src/assets/testimg.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));
        //添加商品并验证
        ProductExecution pe =  productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }
    @Test
    public void testModifyProduct() throws ShopOperationException, FileNotFoundException{
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(33L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(20L);
        product.setProductId(17L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("正式的商品");
        product.setProductDesc("正式的商品");
        //创建缩略图文件流
        File thumbnailFile = new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
        //创建两个商品详情图文件流并将他们添加详情图列表中
        File productImg1 = new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        //添加商品并验证
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }
}
