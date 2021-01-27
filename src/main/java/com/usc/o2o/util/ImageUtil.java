//package com.usc.o2o.util;
//
//import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.geometry.Positions;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//
//public class ImageUtil {
//    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//    private static final SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//    private static final Random r = new Random();
//    public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
//        String realFileName = getRandomFileName();//随机名
//        String extension = getFileExtension(fileName);//扩展名
//        makeDirPath(targetAddr);
//        String relativeAddr = targetAddr + realFileName + extension;
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        try {
//            Thumbnails.of(thumbnailInputStream).size(200, 200)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath +"/watermark.png")),0.25f).outputQuality(0.8f).toFile(dest);
//        } catch (IOException e) {
//            throw new RuntimeException("创建缩略图失败：" + e.toString());
//        }
//        return relativeAddr;
//    }
//
//    /**
//     * 生成随机文件名，当前年月日小时分秒+五位随机数
//     * @return
//     */
//    public static String getRandomFileName(){
//        //获取随机五位数
//        int ranNum = r.nextInt(89999)+10000;
//        String nowTimeStr = sDataFormat.format(new Date());
//        return nowTimeStr + ranNum;
//    }
//
//    /**
//     * 获取输入文件名的扩展名
//     * @return
//     */
//    private static String getFileExtension(String fileName){
//        return fileName.substring(fileName.lastIndexOf("."));
//    }
//
//    /**
//     * 创建目标路径所涉及到的目录，即/home/work/lu/xxx.jpg
//     * 那么home work lu这三个都要被创建
//     * @param targetAddr
//     */
//    private static void makeDirPath(String targetAddr) {
//        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
//        File dirPath = new File(realFileParentPath);
//        if(!dirPath.exists()){
//            dirPath.mkdir();
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        Thumbnails.of(new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg")).size(200, 200)
//                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath +"/watermark.png")),0.25f).outputQuality(0.8f)
//                .toFile("/Users/lu/Documents/project/CampusMall/src/assets/picUpdate1.jpeg");
//    }
//}
package com.usc.o2o.util;

import com.usc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        //获取不重复的随机名
        String realFileName = getRandomFileName();
        //获取文件的拓展名
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在则自动创建
        //System.out.println("target Addr is:"+ targetAddr);
        makeDirPath(targetAddr);
        //获取相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        //System.out.println("current relative Addr is:"+ relativeAddr);//logger.debug();
        //获取要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        //System.out.println("current complete Addr is:"+ PathUtil.getImgBasePath() + relativeAddr);
        //调用thumbnail生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            //System.out.println(e.toString());
            //logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败："+e.toString());
        }
        //返回相对路径
        return relativeAddr;
    }

    /**
     * 创建目标呢路径所涉及到的目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        // Auto-generated method stub
        // get random 5-digits
        int random = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + random;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("/Users/lu/Documents/project/CampusMall/src/assets/pic.jpeg"))
                .size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
                .outputQuality(0.8f).toFile("/Users/lu/Documents/project/CampusMall/src/assets/picUpdata1.jpeg");
    }

    /**
     * 如果storePath是文件路径还是目录路径
     * 如果是文件则删除该文件
     * 如果是目录则删除目录下的所以文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File files[] =  fileOrPath.listFiles();
                for(int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }

    }
}

