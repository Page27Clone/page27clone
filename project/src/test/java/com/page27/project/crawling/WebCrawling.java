package com.page27.project.crawling;

import com.page27.project.domain.Item;
import com.page27.project.repository.ItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


@SpringBootTest
public class WebCrawling {

    @Autowired
    ItemRepository itemRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @Transactional
    @Rollback(false)
    public void webCrawlingTest() {
        webCrawlingMethod("http://page27.co.kr/product/%EC%85%80%EB%A6%B0-%EB%82%98%EC%9D%BC%EB%A1%A0-%EB%B0%94%EB%9E%8C%EB%A7%89%EC%9D%B4-%ED%9B%84%EB%93%9C%EC%9E%90%EC%BC%933color/2989/category/45/display/1/", 1L);
        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%ED%95%98-%EB%A6%B0%EB%84%A8-%EB%B8%94%EB%A0%88%EC%9D%B4%EC%A0%B8-%EC%9E%90%EC%BC%935color/2593/category/45/display/1/", 2L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%9D%BC%EC%9D%B4%ED%8A%B8-%EC%9C%88%EB%93%9C%EB%B8%8C%EB%A0%88%EC%9D%B4%EC%BB%A4%EC%A1%B0%EA%B1%B0%ED%8C%AC%EC%B8%A0-%EC%85%8B%EC%97%852color/2977/category/45/display/1/",3L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%B0%94%EC%8A%A4%EB%9D%BD-%ED%80%84%EB%A6%AC%ED%8B%B0-%EC%95%84%EB%85%B8%EB%9D%BD-%EC%85%8B%EC%97%852color-%EB%8B%B9%EC%9D%BC%EB%B0%B0%EC%86%A1/2972/category/45/display/1/",4L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%9D%BC%EC%9D%B4%EC%A7%95-%EC%95%84%EB%85%B8%EB%9D%BD-%EB%B0%98%EB%B0%94%EC%A7%80-%EC%85%8B%EC%97%854color/2964/category/45/display/1/",5L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%98%88-%EB%82%98%EC%9D%BC%EB%A1%A0-%EB%B0%94%EB%9E%8C%EB%A7%89%EC%9D%B4-%EC%A7%91%EC%97%8510color/2954/category/45/display/1/",6L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A0%88%EC%98%A4-%ED%88%AC%EC%9B%A8%EC%9D%B4-%ED%9B%84%EB%93%9C%EC%A7%91%EC%97%85%EC%A1%B0%EA%B1%B0%ED%8C%AC%EC%B8%A04color/2953/category/45/display/1/",7L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EB%82%98-%EB%B8%94%EB%A0%88%EC%9D%B4%EC%A0%80-%EC%9E%90%EC%BC%932color/2945/category/45/display/1/",8L);

    }

    public void getInfoFromWebCrawling() {

    }

    public Long webCrawlingMethod(String itemUrl, Long idx) {
        try {
            Document doc = Jsoup.connect(itemUrl).get();

            Elements color = doc.select("#product_option_id1 option");

            for (int j = 2; j < color.size(); j++) {

                String itemName = doc.select(".name").get(0).text();

                Elements img = doc.select(".cont img");
                ArrayList<String> url = new ArrayList<String>();

                for (int i = 1; i < img.size(); i += 2) {
//                System.out.println(img.get(i).attr("ec-data-src"));
                    url.add("http://page27.co.kr" + img.get(i).attr("ec-data-src"));
                }

                String stringPrice = doc.select(".sale_rate").attr("item_price");
                int price = Integer.parseInt(stringPrice);

                String info = doc.text().substring(doc.text().indexOf("INFO") + 4, doc.text().indexOf("Color") - 4).trim();
                String size = "FREE";
                String fabric = doc.text().substring(doc.text().indexOf("FABRIC") + 6, doc.text().indexOf("SIZE") - 4).trim();
                String model = doc.text().substring(doc.text().indexOf("MODEL") + 5, doc.text().indexOf("수량") - 1).trim();

                int quantity = 10;

                Elements Category = doc.select(".xans-product-headcategory:last-child");
                String firstCategory = Category.select("li:nth-child(2)").text();
                String secondCategory = Category.select("li:nth-child(3)").text();

                String direction = "";

                for (int i = 0; i < url.size(); i++) {
                    Item item = new Item();
                    if (i == 0) {
                        String repUrl = url.get(i);
//                        디렉토리 생성
                        File newFile = new File("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName);
//            File newFile = new File("C:\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName);
                        if (newFile.mkdir()) {
                            logger.info("directory make ok");
                        } else {
                            logger.warn("directory can't make");
                        }
                        if (repUrl.substring(repUrl.length() - 3, repUrl.length()).equals("png")) {
                            continue;
                        }
                        URL repImgUrl = new URL(repUrl);
                        BufferedImage repImage = ImageIO.read(repImgUrl);
//                        대표사진 넣기
                        FileOutputStream repOut = new FileOutputStream("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName + "\\" + itemName + "0" + ".jpg");
//                FileOutputStream out = new FileOutputStream("C:\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName + "\\" + itemName  + i + ".jpg");

                        String totalUrl = "/image/Item/OUTER/자켓/" + itemName + "/" + itemName + i + ".jpg";

                        ImageIO.write(repImage, "jpg", repOut);
                        boolean rep = true;

                        String itemColor = color.get(j).attr("value");

                        item.setFirstCategory(firstCategory);
                        item.setSecondCategory(secondCategory);
                        item.setThirdCategory("");
                        item.setItemName(itemName);
                        item.setPrice(price);
                        item.setItemInfo(info);
                        item.setColor(itemColor);
                        item.setFabric(fabric);
                        item.setModel(model);
                        item.setSize(size);
                        item.setStockQuantity(quantity);
                        item.setSaleStatus("onsale");
                        item.setItemIdx(idx);
//                item.setImgUrl(direction);
                        item.setImgUrl(totalUrl);
                        item.setRep(rep);

                        itemRepository.save(item);
                    } else {
                        String tempUrl = url.get(i);
                        if (tempUrl.substring(tempUrl.length() - 3, tempUrl.length()).equals("png")) {
                            continue;
                        }
                        URL imgUrl = new URL(tempUrl);
                        BufferedImage image = ImageIO.read(imgUrl);
//                        두번째 사진부터 넣기
                        FileOutputStream out = new FileOutputStream("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName + "\\" + itemName + i + ".jpg");
//                FileOutputStream out = new FileOutputStream("C:\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName + "\\" + itemName  + i + ".jpg");

                        String totalUrl = "/image/Item/OUTER/자켓/" + itemName + "/" + itemName + i + ".jpg";
//                    direction += totalUrl + ',';

                        ImageIO.write(image, "jpg", out);
                        boolean rep = false;

                        String itemColor = color.get(j).attr("value");

                        item.setFirstCategory(firstCategory);
                        item.setSecondCategory(secondCategory);
                        item.setThirdCategory("");
                        item.setItemName(itemName);
                        item.setPrice(price);
                        item.setItemInfo(info);
                        item.setColor(itemColor);
                        item.setFabric(fabric);
                        item.setModel(model);
                        item.setSize(size);
                        item.setStockQuantity(quantity);
                        item.setSaleStatus("onsale");
                        item.setItemIdx(idx);
//                item.setImgUrl(direction);
                        item.setImgUrl(totalUrl);
                        item.setRep(rep);

                        itemRepository.save(item);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idx;
    }
}