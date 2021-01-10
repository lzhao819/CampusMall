package com.usc.o2o.service;

import com.usc.o2o.dto.ShopExecution;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopInputStreamInputStream, String fileName) throws ShopOperationException;
}
