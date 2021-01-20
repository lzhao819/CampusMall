package com.usc.o2o.dao;

import com.usc.o2o.entity.Shop;

public interface ShopDao {
    /**
     * 查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
    /**
     * 新增店铺
     * @param shop
     * @return
     */
   int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
   int updateShop(Shop shop);

}
