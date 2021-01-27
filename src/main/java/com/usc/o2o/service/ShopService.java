package com.usc.o2o.service;

import com.usc.o2o.dto.ImageHolder;
import com.usc.o2o.dto.ShopExecution;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    /**
     * 根据shopCondition分页返回相应列表数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)throws ShopOperationException;

    /**
     * 注册店铺
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
