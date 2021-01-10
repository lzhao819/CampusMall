package com.usc.o2o.service.impl;

import com.usc.o2o.dao.ShopDao;
import com.usc.o2o.dto.ShopExecution;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.enums.ShopStateEnum;
import com.usc.o2o.exceptions.ShopOperationException;
import com.usc.o2o.service.ShopService;
import com.usc.o2o.util.ImageUtil;
import com.usc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    @Transactional
    /**
     * 使用注解控制事务方法的优点： 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg){
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息附初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
//            if (shop.getShopCategory() != null) {
//                Long shopCategoryId = shop.getShopCategory()
//                        .getShopCategoryId();
//                ShopCategory sc = new ShopCategory();
//                sc = shopCategoryDao.queryShopCategoryById(shopCategoryId);
//                ShopCategory parentCategory = new ShopCategory();
//                parentCategory.setShopCategoryId(sc.getParentId());
//                shop.setParentCategory(parentCategory);
//            }
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                try {
                    if (shopImg != null) {
                        //存储图片
                        addShopImg(shop, shopImg);
                        //更新店铺图片信息
                        effectedNum = shopDao.updateShop(shop);
                        if (effectedNum <= 0) {
                            throw new ShopOperationException("创建图片地址失败");
                        }
                    }
                } catch (Exception e) {
                    throw new ShopOperationException("addShopImg error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("insertShop error: " + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        //获取shop图片目录相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
        //
    }

}