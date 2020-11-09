package com.usc.o2o.service;

import com.usc.o2o.dto.ShopExecution;
import com.usc.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface ShopService {
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException;

}
