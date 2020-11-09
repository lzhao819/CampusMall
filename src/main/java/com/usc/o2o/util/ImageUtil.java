package com.usc.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();//随机名
        String extension = getFileExtension(thumbnail);//扩展名
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath +"/watermark.png")),0.25f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    /**
     * 生成随机文件名，当前年月日小时分秒+五位随机数
     * @return
     */
    private static String getRandomFileName(){
        //获取随机五位数
        int ranNum = r.nextInt(89999)+10000;
        String nowTimeStr = sDataFormat.format(new Date());
        return nowTimeStr + ranNum;
    }

    /**
     * 获取输入文件名的扩展名
     * @return
     */
    private static String getFileExtension(CommonsMultipartFile cFile){
        String originalFileName = cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/lu/xxx.jpg
     * 那么home work lu这三个都要被创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdir();
        }
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg")).size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath +"/watermark.png")),0.25f).outputQuality(0.8f)
                .toFile("/Users/lu/Documents/project/CampusMall/src/assets/picUpdate1.jpeg");
    }
}
