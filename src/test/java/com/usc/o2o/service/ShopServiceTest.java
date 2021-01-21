package com.usc.o2o.service;

import com.usc.o2o.BaseTest;
import com.usc.o2o.dto.ShopExecution;
import com.usc.o2o.entity.Area;
import com.usc.o2o.entity.PersonInfo;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.entity.ShopCategory;
import com.usc.o2o.enums.ShopStateEnum;
import com.usc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition,0,2);
        //count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表数：" + se.getShopList().size());
        System.out.println("店铺总数：" + se.getCount());
    }
    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException, FileNotFoundException{
        Shop shop  = new Shop();
        shop.setShopId(34L);
        shop.setShopName("修改过后的店铺名称");
        File shopImg = new File( "/Users/lu/Documents/project/CampusMall/src/assets/testimg.jpg");
        InputStream is =  new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.modifyShop(shop, is, "testimg.jpg");
        System.out.println("新的图片地址为"+ shopExecution.getShop().getShopImg());
    }
    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2L);
        shopCategory.setShopCategoryId(1L);
        shop.setOwnerId(1L);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File shopImg = new File( "/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop, is, shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }
}
