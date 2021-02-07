package com.usc.o2o.dao;

import com.usc.o2o.BaseTest;
import com.usc.o2o.entity.Product;
import com.usc.o2o.entity.ProductCategory;
import com.usc.o2o.entity.ProductImg;
import com.usc.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    @Ignore
    public void testAInsertProduct() throws Exception{
        Shop shop1 = new Shop();
        shop1.setShopId(33L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(19L);
        //初始化三个商品实例并添加进shopId为1的店铺
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试1Desc");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试2Desc");
        product2.setImgAddr("test2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);
        Product product3 = new Product();
        product3.setProductName("测试3");
        product3.setProductDesc("测试3Desc");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(pc1);
        //判断添加是否成功
        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1,effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1,effectedNum);
        effectedNum = productDao.insertProduct(product3);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void testBQueryProductList() throws Exception{
        Product productCondition = new Product();
        //分页查询,预期返回三条结果
        List<Product> productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());
        //查询商品总数
        int count = productDao.queryProductCount(productCondition);
        assertEquals(6,count);
        //使用商品模糊查询, 名称为测试的，预期返回两条结果
        productCondition.setProductName("测试");
        productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());
        count=productDao.queryProductCount(productCondition);
        assertEquals(5,count);
    }
    @Test
    @Ignore
    public void testCQueryProductById() throws Exception{
        long productId = 3;
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
        Product product = productDao.queryProductByProductId(productId);
        assertEquals(2, product.getProductImgList().size());
        effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectedNum);
    }

    @Test
    @Ignore
    public void testDUpdateProduct() throws Exception{
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(33L);
        pc.setProductCategoryId(20L);
        product.setProductId(17L);
        product.setShop(shop);
        product.setProductName("第二个产品");
        product.setProductCategory(pc);
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testEUpdateProductCategoryToNull(){
        int effectedNum = productDao.updateProductCategoryToNull(20L);
        assertEquals(2,effectedNum);

    }



}
