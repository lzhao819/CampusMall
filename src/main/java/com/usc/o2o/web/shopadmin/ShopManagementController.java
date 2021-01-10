package com.usc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usc.o2o.dto.ShopExecution;
import com.usc.o2o.entity.PersonInfo;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.enums.ShopStateEnum;
import com.usc.o2o.service.ShopService;
import com.usc.o2o.util.HttpServletRequestUtil;
import com.usc.o2o.util.ImageUtil;
import com.usc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //1接受并转化相应的参数，包括店铺信息和图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr, Shop.class);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2注册店铺
        if(shop!=null && shopImg!=null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwnerId(owner.getUserId());
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            ShopExecution se = shopService.addShop(shop, shopImg);
            if(se.getState()== ShopStateEnum.CHECK.getState()){
                modelMap.put("success", true);
            }else{
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }
    private static void inputStreamToFile(InputStream ins, File file){
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = ins.read(buffer))!=-1){
                os.write(buffer, 0, bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常"+e.getMessage());
        }finally {
            try{
                if(os!=null){
                    os.close();
                }
                if(ins!=null){
                    ins.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭IO产生异常"+e.getMessage());
            }
        }
    }
}