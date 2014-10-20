package Model;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
 
public class Getimage {
    // 自定义的web服务器的资源
	static String imgurl;
    private static String URL_PATH = "http://i.uestc.edu.cn/iGEM2014/RNAFold/";
 
    public Getimage() {
        // TODO Auto-generated constructor stub
    	
    }
    public Getimage(String url) {
        // TODO Auto-generated constructor stub
    	this.imgurl=url;
    }
 
//    public static void saveImageToDisk() throws IOException {
//        InputStream inputStream = getInputStream();
//        byte[] data = new byte[1024];
//        int len = 0;
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream("D:\\p.jpg");
//            while ((len = inputStream.read(data)) != -1) {
//                fileOutputStream.write(data, 0, len);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (fileOutputStream != null) {
//                try {
//                    fileOutputStream.close();
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
// 
//    }
    public BufferedImage image() throws IOException{
	   InputStream inputStream = getInputStream();
	   BufferedImage image = ImageIO.read(inputStream);
	   return image;
   	}
    /**
     * 获得服务器端数据，以InputStream形式返回
     * 
     * @return
     * @throws IOException
     */
    public static InputStream getInputStream() throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(URL_PATH+imgurl);
            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置连接网络的超时时间
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                // 设置本次http请求使用get方式请求
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    inputStream = httpURLConnection.getInputStream();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        return inputStream;
    }
 
    public static void main(String[] args) throws IOException {
        // 从服务器获得图片保存到本地
//        saveImageToDisk();
        System.out.println("传输步骤完毕");
    }
}
