package com.page27.project.crawling;

import com.page27.project.domain.Item;
import com.page27.project.repository.ItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

@SpringBootTest
public class WebCrawling {

    @Autowired ItemRepository itemRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void webCrawlingTest() {

        try {

            Document doc = Jsoup.connect("http://page27.co.kr/product/%EC%85%80%EB%A6%B0-%EB%82%98%EC%9D%BC%EB%A1%A0-%EB%B0%94%EB%9E%8C%EB%A7%89%EC%9D%B4-%ED%9B%84%EB%93%9C%EC%9E%90%EC%BC%933color/2989/category/45/display/1/").get();

            //String url = "http:" + doc.select(".BigImage").attr("src").trim();

            String itemName = doc.select(".name").text();
            //System.out.println(itemName);
            String totalUrl;
            Elements img = doc.select(".cont img");
            ArrayList<String> url = new ArrayList<String>();
            for(int i = 1; i<img.size();i++){
                //System.out.println(img.get(i).attr("ec-data-src"));
                url.add("http://page27.co.kr" + img.get(i).attr("ec-data-src"));
            }
            for(int i = 0;i<url.size();i++){
                String tempUrl = url.get(i);
                if(tempUrl.substring(tempUrl.length()-3,tempUrl.length()).equals("png")){
                    continue;
                }
                URL imgUrl = new URL(tempUrl);

                //System.out.println(imgUrl);

                BufferedImage image = ImageIO.read(imgUrl);
                FileOutputStream out = new FileOutputStream("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName  + i + ".jpg");
                ImageIO.write(image,"jpg",out);
            }

            String stringPrice = doc.select(".sale_rate").attr("item_price");
            int price = Integer.parseInt(stringPrice);
            System.out.println(price);

            String temp = doc.text();
            String info = temp.substring(temp.indexOf("INFO") + 4,temp.indexOf("Color") - 4).trim();
            System.out.println(info);

            Elements color = doc.select("#product_option_id1 option");
            for(int i = 0 ; i<color.size(); i++){
                if(i < 2) continue;
                System.out.println(color.get(i).attr("value"));
            }

            String size = "FREE";

            String fabric = temp.substring(temp.indexOf("FABRIC") + 6,temp.indexOf("SIZE") - 4).trim();
            System.out.println(fabric);

            String model = temp.substring(temp.indexOf("MODEL") + 5 , temp.indexOf("수량") - 1).trim();
            System.out.println(model);

            int quantity = 10;

            Elements Category = doc.select(".xans-product-headcategory:last-child");
            String firstCategory = Category.select("li:nth-child(2)").text();
            String secondCategory = Category.select("li:nth-child(3)").text();
//            System.out.println(Category.select("li:nth-child(2)").text());
//            System.out.println(Category.select("li:nth-child(3)").text());

//            Item item = new Item(firstCategory,secondCategory,"",itemName,price,info,)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
