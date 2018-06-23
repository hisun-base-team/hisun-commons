package com.hisun.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageDrawStringUtil {

    public  static ImageDrawStringUtil getInstance(){
        return new ImageDrawStringUtil();
    }

    public int getX(int srcImgWidth,Graphics2D g,String waterMarkContent){
        int x = srcImgWidth-waterMarkContent.length()*g.getFontMetrics(g.getFont()).getAscent()/2;
        return x;
    }
    public int getY(int srcImgHeight,Graphics2D g){
        int y = (g.getFontMetrics(g.getFont()).getHeight()+g.getFontMetrics(g.getFont()).getFont().getSize()/5)/2;
        return y;
    }

    /**
     *
     * @param datas 文件字节流
     * @param waterMarkContent 水印文字 imgCode
     * @param x x轴坐标
     * @param y y轴坐标
     * @return
     */
    public byte[] drawStringStream(byte[] datas, String waterMarkContent, int x, int y) {
        File file = new File("test.jpg");
        Color color=new Color(0,0,0,255);
        OutputStream output = null;
        byte[] byt = null;
        try {
            output = new FileOutputStream(file);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(datas);

            Image srcImg = ImageIO.read(file);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            int size = 20;
            int s = srcImgWidth/600;
            if(s>1){
                size=size*s;
            }
            Font font = new Font("SansSerif", Font.PLAIN, size);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            if(x>(getX(srcImgWidth,g,waterMarkContent))){
                x = getX(srcImgWidth,g,waterMarkContent);
            }else if(x<0){
                x=0;
            }

            if(y>(srcImgHeight-getY(srcImgHeight,g))||y<getY(srcImgHeight,g)){
                y = getY(srcImgHeight,g);
            }
            g.drawString(waterMarkContent, x, y);  //画出水印
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(file);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");

            InputStream input = new FileInputStream(file);

            byt = new byte[input.available()];
            input.read(byt);
            input.close();

            output.flush();
            output.close();
            bufferedOutput.flush();
            bufferedOutput.close();
            outImgStream.flush();
            outImgStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byt;
    }
}
