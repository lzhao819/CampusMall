package com.usc.o2o.service;

import com.usc.o2o.entity.Area;
import com.usc.o2o.entity.PersonInfo;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.entity.ShopCategory;
import com.usc.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop(){
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
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        FileItem fi = factory.createItem("formFieldName", "application/zip", false,
                "/Users/lu/Documents/project/CampusMall/src/assets/picUpdate1.jpeg");

        CommonsMultipartFile shopImg = new CommonsMultipartFile(fi);
        shopService.addShop(shop, shopImg);
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);

    }
}
