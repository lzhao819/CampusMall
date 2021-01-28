package com.usc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usc.o2o.dto.ImageHolder;
import com.usc.o2o.dto.ProductExecution;
import com.usc.o2o.entity.Product;
import com.usc.o2o.entity.Shop;
import com.usc.o2o.enums.ProductStateEnum;
import com.usc.o2o.exceptions.ProductOperationException;
import com.usc.o2o.service.ProductService;
import com.usc.o2o.util.CodeUtil;
import com.usc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT=6;
    @RequestMapping(value="/addproduct",method= RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request){
        Map<String, Object> modelMap  = new HashMap<>();
        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //接收前段参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product=null;
        String  productStr = HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            //若请求中存在文件流，则取出相关的文件（包括缩略图详情图;
            if(multipartResolver.isMultipart(request)){
                multipartRequest = (MultipartHttpServletRequest) request;
                //取出缩略图并构建imageHolder对象
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                    //取出详情图列表并构建List<ImageHolder>列表对象,最多支持6张
                    for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                        CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                        if (productImgFile != null) {
                            //取出的第i个详情图问字流不为空，则将其将加入详情图列表
                            ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                            productImgList.add(productImg);
                        }else{
                        //若取出的第i个详情文件图片流为空，则终止循环
                            break;
                        }
                    }
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        try{
            //尝试获取前端传来的 表单string流并将其转化成Product实体类
            product = mapper.readValue(productStr, Product.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        //若product信息，缩略图以及详情图列表非空，则开始进行商品添加操作
        if(product!=null && thumbnail!=null && productImgList.size()>0){
            try{
                //从session中获取当前店铺ID并赋值给product，减少对前端数据的依赖
                Shop currentShop =  (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if(pe.getState()== ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }
}

