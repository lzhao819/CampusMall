package com.usc.o2o.dao;

import com.usc.o2o.BaseTest;
import com.usc.o2o.entity.Area;
import com.usc.o2o.entity.PersonInfo;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
    @Test
    public void testQueryByShopId(){
        long shopId=33;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId:"+shop.getArea().getAreaId());
        System.out.println("areaName:"+shop.getArea().getAreaName());
    }
    @Test
    @Ignore
    public void testInsertShop() {
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
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }
    @Test
    @Ignore
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(33L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
}