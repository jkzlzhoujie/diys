package cn.temobi.complex.action.def;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class WaterMarkUtils {

	/** 
     * 图片添加水印 
     * @param srcImgPath 需要添加水印的图片的路径 
     * @param outImgPath 添加水印后图片输出路径 
     * @param markContentColor 水印文字的颜色 
     * @param waterMarkContent 水印的文字 
     */  
    public void mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {  
        try {  
            // 读取原图片信息  
            File srcImgFile = new File(srcImgPath);  
            Image srcImg = ImageIO.read(srcImgFile);  
            int srcImgWidth = srcImg.getWidth(null);  
            int srcImgHeight = srcImg.getHeight(null);  
            // 加水印  
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g = bufImg.createGraphics();  
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);  
           
            //Font font = new Font("Courier New", Font.PLAIN, 12);  
            Font font = new Font("宋体", Font.PLAIN, 50);    
            g.setColor(markContentColor); //根据图片的背景设置水印颜色  
            g.setFont(font);  
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;  
            int y = srcImgHeight - 3;  
           
            //int x = (srcImgWidth - getWatermarkLength(watermarkStr, g)) / 2;  
            //int y = srcImgHeight / 2;  
            
            g.drawString(waterMarkContent, x, y);  
            g.dispose();  
            // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);  
            ImageIO.write(bufImg, "PNG", outImgStream);  
            outImgStream.flush();  
            outImgStream.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 获取水印文字总长度 
     * @param waterMarkContent 水印的文字 
     * @param g 
     * @return 水印文字总长度 
     */  
    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    }  
    
    
    
    /**  
     * 给图片添加水印  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     */  
    public static void markImageByIcon(String iconPath, String srcImgPath,   
            String targerPath) {   
        markImageByIcon(iconPath, srcImgPath, targerPath, null);   
    }   
  
    /**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印图片旋转角度  
     */  
    public static void markImageByIcon(String iconPath, String srcImgPath,   
            String targerPath, Integer degree) {   
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath));   
  
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
            // 得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree),   
                        (double) buffImg.getWidth() / 2, (double) buffImg   
                                .getHeight() / 2);   
            }   
  
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
            ImageIcon imgIcon = new ImageIcon(iconPath);   
  
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
  
            float alpha = 0.5f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));   
  
            // 表示水印图片的位置   
            g.drawImage(img, 600, 850, null);   
  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
  
            g.dispose();   
  
            os = new FileOutputStream(targerPath);   
  
            // 生成图片   
            ImageIO.write(buffImg, "PNG", os);   
  
            System.out.println("图片完成添加Icon印章。。。。。。");   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   

      
    public static void main(String[] args) {  
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字  
        new WaterMarkUtils().mark("f:/postWatermark.png", "f:/afterWatermark.png", Color.red, "水印效果测试");  
        
        String iconPath = "f:/waterIcon.png";   
        String srcImgPath = "f:/postWatermark.png";   
//        String targerPath = "f:/afterWatermark2.jpg";   
//        String targerPath2 = "f:/afterWatermark45.jpg";   
//        
//        // 给图片添加水印   
//        markImageByIcon(iconPath, srcImgPath, targerPath);   
//      
//        // 给图片添加水印,水印旋转-45   
//        markImageByIcon(iconPath, srcImgPath, targerPath2,-45);   
        
        
        
   }  




}
