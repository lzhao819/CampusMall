package com.usc.o2o.util;

public class PathUtil {
    private static String seperator = System.getProperty("file.separator");
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/projectdev/image/";
        } else {
            basePath = "/Users/lu/Documents/Pictures/o2o";
        }
        basePath = basePath.replace("/", seperator);
        return basePath;
    }
    public static String getShopImagePath(long shopId) {
        StringBuilder shopImagePathBuilder = new StringBuilder();
        shopImagePathBuilder.append("/upload/images/item/shop/");
        shopImagePathBuilder.append(shopId);
        shopImagePathBuilder.append("/");
        String shopImagePath = shopImagePathBuilder.toString().replace("/",
                seperator);
        return shopImagePath;
    }
}
