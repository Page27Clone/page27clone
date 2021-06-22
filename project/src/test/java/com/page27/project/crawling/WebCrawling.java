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
import java.util.Locale;

@SpringBootTest
public class WebCrawling {

    @Autowired
    ItemRepository itemRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @Transactional
    @Rollback(false)
    public void webCrawlingTest() {
//        webCrawlingMethod("http://page27.co.kr/product/%EC%85%80%EB%A6%B0-%EB%82%98%EC%9D%BC%EB%A1%A0-%EB%B0%94%EB%9E%8C%EB%A7%89%EC%9D%B4-%ED%9B%84%EB%93%9C%EC%9E%90%EC%BC%933color/2989/category/45/display/1/", 1L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%ED%95%98-%EB%A6%B0%EB%84%A8-%EB%B8%94%EB%A0%88%EC%9D%B4%EC%A0%B8-%EC%9E%90%EC%BC%935color/2593/category/45/display/1/", 2L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%98%88-%EB%82%98%EC%9D%BC%EB%A1%A0-%EB%B0%94%EB%9E%8C%EB%A7%89%EC%9D%B4-%EC%A7%91%EC%97%8510color/2954/category/45/display/1/",3L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EB%82%98-%EB%B8%94%EB%A0%88%EC%9D%B4%EC%A0%80-%EC%9E%90%EC%BC%932color/2945/category/45/display/1/",4L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%9D%BC%EC%9D%BC%EB%9D%BD-%ED%81%AC%EB%A1%AD-%ED%9B%84%EB%93%9C%EC%9E%90%EC%BC%933color/2942/category/45/display/1/",5L);
//        ebCrawlingMethod("http://page27.co.kr/product/%ED%8F%AC%ED%85%90-%EB%82%98%EC%9D%BC%EB%A1%A0-%EC%88%8F%EC%9E%90%EC%BC%932color/2940/category/45/display/1/",6L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%B8%94%EB%9E%91-%ED%94%BC%EA%B7%B8%EB%A8%BC%ED%8A%B8-%ED%9B%84%EB%93%9C%EC%A7%91%EC%97%854color/2937/category/45/display/1/",7L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A5%B4%EB%B0%98-%EB%A0%88%EB%8D%94-%EC%9E%90%EC%BC%932color/2936/category/45/display/1/",8L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%AC%B4%EB%B8%8C-%EC%98%A4%EB%B2%84%ED%95%8F-%EC%B2%AD%EC%9E%90%EC%BC%932color/2928/category/45/display/1/",9L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%94%84%EB%9D%BC%ED%95%98-%ED%88%AC%EC%9B%A8%EC%9D%B4-%ED%9B%84%EB%93%9C%EC%9E%90%EC%BC%934color/2924/category/45/display/1/",10L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A7%88%EC%9D%BC%EB%93%9C-2%EC%98%A8%EC%8A%A4-%EC%95%BC%EC%83%812olor/2895/category/45/display/1/",11L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A9%9C%EB%A1%9C%EC%9D%B4-%EC%9A%B8-%EB%8B%88%ED%8A%B8%EC%9E%90%EC%BC%932color/2880/category/45/display/1/",12L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%B2%84%EB%8B%9D-%EC%88%8F-%ED%8C%A8%EB%94%A9%EC%A0%90%ED%8D%BC5color/2877/category/45/display/1/",13L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%BC%80%EC%9D%B4-3m-%EC%94%AC%EC%97%90%EC%96%B4-%ED%8C%A8%EB%94%A94color/2873/category/45/display/1/",14L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%97%98-%ED%97%A4%EB%B9%84-%ED%9B%84%EB%A6%AC%EC%8A%A4-%ED%9B%84%EB%93%9C%EC%A7%91%EC%97%852color/2869/category/45/display/1/",15L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%83%80%EC%9D%B4%EC%B9%B8-%EC%9B%B0%EB%A1%A0-%EB%A1%B1%ED%8C%A8%EB%94%A92color/2839/category/45/display/1/",16L);

//        여기까지 자켓 크롤링

//        webCrawlingMethod("http://page27.co.kr/product/%EC%97%98%EB%B9%84%EC%8A%A4-%EB%A6%B0%EB%84%A8-%EA%B0%80%EB%94%94%EA%B1%B46color/2981/category/77/display/1/",17L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%A0%9C%EB%83%90-%EB%AF%B9%EC%8A%A4-%EC%A7%91%EC%97%85%EA%B0%80%EB%94%94%EA%B1%B45color/2941/category/77/display/1/",18L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%84%88%EB%93%9C-%EA%B3%A0%EB%B0%80%EB%8F%84-%EA%B0%80%EB%94%94%EA%B1%B410color/2922/category/77/display/1/",19L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%98%88-%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%98%EC%B0%8C-%EA%B0%80%EB%94%94%EA%B1%B48color/2908/category/77/display/1/",20L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%8B%A4%EC%9A%B4%ED%83%80%EC%9A%B4-%EB%8B%88%ED%8A%B8-%EA%B0%80%EB%94%94%EA%B1%B411color/2902/category/77/display/1/",21L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A3%A8%EC%9D%B4%EB%A5%B4-%EB%B8%8C%EC%9D%B4%EB%84%A5-%EA%B0%80%EB%94%94%EA%B1%B45color/2900/category/77/display/1/",22L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%94%84%EB%9E%AD%ED%81%AC-%EB%9D%BC%EC%9D%B8-%EA%B0%80%EB%94%94%EA%B1%B45color/2897/category/77/display/1/",23L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%97%A4%EB%B9%84-%EB%9E%A8%EC%8A%A4%EC%9A%B8-%ED%95%98%EC%B0%8C-%EB%8B%88%ED%8A%B8%EC%A7%91%EC%97%855color/2885/category/77/display/1/",24L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A5%B4%EB%A7%9D-%EC%BA%90%EC%8B%9C-%EC%A7%91%EC%97%85-%EA%B0%80%EB%94%94%EA%B1%B45color/2857/category/77/display/1/",25L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%8A%A4%ED%83%A0-%EC%B2%B4%ED%81%AC-%EC%98%A4%EB%B2%84%ED%95%8F-%EA%B0%80%EB%94%94%EA%B1%B43color/2769/category/77/display/1/",26L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%9B%90%EC%8A%A4-%EB%B0%B0%EC%83%89-%EC%BA%90%EC%8B%9C%EB%AF%B8%EC%96%B4-%EA%B0%80%EB%94%94%EA%B1%B43color/2767/category/77/display/1/",27L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%95%98%EB%B2%84-%EC%8A%A4%ED%8B%B0%EC%B9%98-%EC%98%A4%EB%B2%84%ED%95%8F-%EA%B0%80%EB%94%94%EA%B1%B42color/2754/category/77/display/1/",28L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%95%84-%EB%AF%B9%EC%8A%A4-%EB%8B%88%ED%8A%B8-%EC%A7%91%EC%97%85-%EA%B0%80%EB%94%94%EA%B1%B45color/2752/category/77/display/1/",29L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84-%EC%86%8C%ED%94%84%ED%8A%B8-%EA%B0%80%EB%94%94%EA%B1%B411color/2734/category/77/display/1/",30L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A7%88%EB%A1%A0-%EC%BD%94%ED%8A%BC-%EA%B0%80%EB%94%94%EA%B1%B45color/2729/category/77/display/1/",31L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%82%AC%EC%9A%B4%EC%A6%88-%EB%A6%B0%EB%84%A8-%EA%B0%80%EB%94%94%EA%B1%B43color/2709/category/77/display/1/",32L);

//        여기가지 가디건 크롤링

//        webCrawlingMethod("http://page27.co.kr/product/%EC%83%8C%EB%93%9C-%EB%B9%84%EC%A1%B0-%EC%BA%90%EC%8B%9C-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B82color-40sale/2825/category/51/display/1/", 33L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A7%88%EA%B0%80%EB%A0%9B-%EC%98%A8%EC%8A%A4-%ED%8C%A8%EB%94%A9%EC%BD%94%ED%8A%B83color/2822/category/51/display/1/", 34L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%9D%BC%EC%9D%B4%ED%94%84-%EB%A1%9C%EB%B8%8C-%ED%95%B8%EB%93%9C%EB%A9%94%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8A%B83color/2806/category/51/display/1/", 35L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A7%88%EB%A5%B4%EC%BD%94-%ED%95%B8%EB%93%9C%EB%A9%94%EC%9D%B4%EB%93%9C-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B84color/2785/category/51/display/1/", 36L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%9D%B4%EB%B8%90-%EC%9B%8C%EC%8B%B1-%EB%A7%A5-%ED%8A%B8%EB%A0%8C%EC%B9%98%EC%BD%94%ED%8A%B83color/2452/category/51/display/1/", 37L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%81%B4%EB%9E%98%EC%8B%9D-%ED%97%A4%EB%A7%81%EB%B3%B8-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B82color/2327/category/51/display/1/", 38L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A5%B4%EB%A9%94%EB%A5%B4-%EB%A1%9C%EB%B8%8C-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B83color/2308/category/51/display/1/", 39L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%B0%9C%EB%A7%88%EC%B9%B8-%EC%9A%B8-%EC%BD%94%ED%8A%B84color/2301/category/51/display/1/", 40L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%8E%98%EC%9D%B4%EC%A7%8027-%EC%BA%90%EC%8B%9C%EB%AF%B8%EC%96%B4-%EC%98%A4%EB%B2%84%EC%BD%94%ED%8A%B82color-50sale/2276/category/51/display/1/", 41L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%AF%B8%EB%8B%88%EB%A9%80-%EB%A7%81-%EB%A1%9C%EB%B8%8C-%EC%9A%B8%EC%BD%94%ED%8A%B82color/2298/category/51/display/1/", 42L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%B0%9C%EB%A0%8C-%EC%9A%B8-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B82color/2284/category/51/display/1/", 43L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%97%98%ED%94%84-%EC%98%A4%EB%B2%84-%EC%9A%B8-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B84color/2281/category/51/display/1/", 44L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%95%84%EB%A5%B4%EB%8B%88-%EB%A1%9C%EB%B8%8C-%EC%9A%B8%EC%BD%94%ED%8A%B83color/2250/category/51/display/1/", 45L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%8D%BC%ED%8E%99%ED%8A%B8-%EC%9A%B8-%EB%8D%94%EB%B8%94%EC%BD%94%ED%8A%B83color/2239/category/51/display/1/", 46L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A3%A8%EC%9D%B4%EC%8A%A4-%EC%9A%B8-%EC%8B%B1%EA%B8%80%EC%BD%94%ED%8A%B83color/2236/category/51/display/1/", 47L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A9%9C%EB%A1%A0-%EC%98%A4%EB%B2%84-%EC%9A%B8-%EC%8B%B1%EA%B8%80%EC%BD%94%ED%8A%B84color/2216/category/51/display/1/", 48L);

//        여기까지 코트 크롤링

//        webCrawlingMethod("https://page27.co.kr/product/%ED%8F%B4%EB%A7%81-%EC%8B%A4%EC%BC%93-%EC%9C%A0%EB%84%A5-%EB%B0%98%ED%8C%94%ED%8B%B04color/3035/category/25/display/1/", 49L);
//        webCrawlingMethod("https://page27.co.kr/product/11%EB%A1%9C%EB%A7%9D-%EB%B6%84%EB%98%90-%EB%B0%98%ED%8C%94%ED%8B%B0%EC%85%94%EC%B8%A012color/3037/category/25/display/1/", 50L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%9F%AC%EC%89%AC-%EC%B9%B4%EB%9D%BC-%EB%B0%98%ED%8C%94%EB%8B%88%ED%8A%B84color/3034/category/25/display/1/", 51L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%95%8C%EB%A6%AC-%EB%82%98%EC%97%BC-%ED%80%84%EB%A6%AC%ED%8B%B0-%EB%B0%98%ED%8C%94%ED%8B%B03color/3032/category/25/display/1/", 52L);
//        webCrawlingMethod("https://page27.co.kr/product/11%EB%A1%9C%ED%82%A4-%EB%A8%B8%EC%8A%AC-%EC%B9%B4%EB%B8%8C%EB%9D%BC-%EB%B0%98%ED%8C%94%ED%8B%B06color/3030/category/25/display/1/", 53L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%93%9C%EB%9D%BC%EC%9D%B4%EB%B9%99-%EC%98%A4%EB%B2%84%ED%95%8F-%EB%B0%98%ED%8C%94%ED%8B%B03color/3028/category/25/display/1/", 54L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%AA%A8%EB%8D%98-%EC%B9%B4%EB%9D%BC-%EB%B0%98%ED%8C%94%EB%8B%88%ED%8A%B810color/2645/category/25/display/1/", 55L);
//        webCrawlingMethod("https://page27.co.kr/product/%ED%8F%B4%EB%93%9C-%EB%A6%B0%EB%84%A8-%EB%B0%98%ED%8C%94-%EC%B9%B4%EB%9D%BC%EB%8B%88%ED%8A%B86color/2554/category/25/display/1/", 56L);
//        webCrawlingMethod("https://page27.co.kr/product/11%EB%8D%B0%EC%98%A4%EB%93%9C%EB%9E%80%ED%8A%B8-%EB%A6%B0%EB%84%A8-%EB%B0%98%ED%8C%94%EB%8B%88%ED%8A%B810color/3020/category/25/display/1/", 57L);

//        여기까지가 TOP 크롤링

//        webCrawlingMethod("https://page27.co.kr/product/%EB%AC%B4%EB%B8%8C-%EC%8B%9C%EC%96%B4%EC%84%9C%EC%BB%A4-%EB%B0%98%ED%8C%94%EC%85%94%EC%B8%A05color/3026/category/78/display/1/", 58L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%8A%88%EA%B0%80-%EB%A6%B0%EB%84%A8-%EB%B0%98%ED%8C%94%EC%85%94%EC%B8%A06color/3023/category/78/display/1/", 59L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%A1%A4%EB%A6%AC-%ED%8C%A8%ED%84%B4-%EC%BF%A8-%EB%B0%98%ED%8C%94%EC%85%94%EC%B8%A08color/3021/category/78/display/1/", 60L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%8A%A4%ED%86%A4-%EC%95%84%EC%9D%B4%EC%8A%A4-%EC%98%A4%EB%B2%84-%EC%85%94%EC%B8%A08color/3015/category/78/display/1/", 61L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%95%84%EB%8D%B4-%EC%BF%A8-%EB%A6%B0%EB%84%A8-%EC%85%94%EC%B8%A013color/2992/category/78/display/1/", 62L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%8B%9C%EA%B7%B8%EB%8B%88%EC%B2%98-%EB%82%98%EC%9D%BC%EB%A1%A0-%EC%98%A4%EB%B2%84%EC%85%94%EC%B8%A06color/2984/category/78/display/1/", 63L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%98%A4%EC%85%98-%EC%BF%A8-%ED%97%A8%EB%A6%AC%EB%84%A5-%EC%85%94%EC%B8%A03color/1983/category/78/display/1/", 64L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%A1%9C%EC%95%84-%EB%B2%A0%EC%9D%B4%EC%A7%81-%EC%86%94%EB%A6%AC%EB%93%9C-%EC%85%94%EC%B8%A07color/2971/category/78/display/1/", 65L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%B8%8C%EB%A1%A0%EC%B8%A0-%ED%8C%A8%ED%84%B4-%EB%A6%B0%EB%84%A8-%EC%85%94%EC%B8%A03color/2689/category/78/display/1/", 66L);

//        여기까지가 SHIRTS 크롤링

//        webCrawlingMethod("http://page27.co.kr/product/%EC%95%8C%EC%A0%9C%EB%A6%AC-%EB%B8%8C%EC%9D%B4%EB%84%A5-%EC%8A%A4%ED%8A%B8%EB%9D%BC%EC%9D%B4%ED%94%84-%EB%8B%88%ED%8A%B82color/2952/category/44/display/1/", 67L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%97%98%EB%9D%BC-%EB%B8%8C%EC%9D%B4%EB%84%A5-%EB%8B%88%ED%8A%B87color/2948/category/44/display/1/", 68L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%A0%9C-%ED%95%98%EC%B0%8C-%EB%B8%8C%EC%9D%B4%EB%84%A5-%EB%8B%88%ED%8A%B8%EC%A1%B0%EB%81%BC/2934/category/44/display/1/", 69L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%A0%9C%EB%83%90-%EB%AF%B9%EC%8A%A4-%EB%B0%98%EC%A7%91%EC%97%85-%EB%8B%88%ED%8A%B85color/2932/category/44/display/1/", 70L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%9D%BC%EC%9D%B4%ED%86%A4-%EB%A6%B0%EB%84%A8-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%B0%98%ED%8C%94%EB%8B%88%ED%8A%B814color/2519/category/44/display/1/", 71L);
//        webCrawlingMethod("http://page27.co.kr/product/%ED%8A%B8%EC%9C%84%EC%8A%A4%ED%8A%B8-%EB%8B%A8%EA%B0%80%EB%9D%BC-%EB%8B%88%ED%8A%B87color/2918/category/44/display/1/", 72L);
//        webCrawlingMethod("http://page27.co.kr/product/%EC%97%90%EC%9D%B4%EC%8A%A4-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%8B%88%ED%8A%B88color/2911/category/44/display/1/", 73L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%AF%B9%EC%8A%A4-%ED%95%98%ED%94%84-%EB%B0%98%EC%A7%91%EC%97%85-%EB%8B%88%ED%8A%B86color/2901/category/44/display/1/", 74L);
//        webCrawlingMethod("http://page27.co.kr/product/%EB%A1%9C%EC%A7%80%EC%8A%A4-%ED%95%98%EC%B0%8C-%EB%8B%88%ED%8A%B812color/2896/category/44/display/1/", 75L);

//        여기까지가 KNIT 크롤링

//        webCrawlingMethod("https://page27.co.kr/product/%EC%8D%A8%EB%A8%B8-%EC%99%80%EC%9D%B4%EB%93%9C-%EB%B0%B4%EB%94%A9%ED%8C%AC%EC%B8%A04color/3038/category/26/display/1/", 76L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%86%8C%ED%94%84%ED%8A%B8-%ED%88%AC%ED%84%B1-%EB%A6%B0%EB%84%A8-%EB%B0%B4%EB%94%A9%ED%8C%AC%EC%B8%A06color/3036/category/26/display/1/", 77L);
//        webCrawlingMethod("https://page27.co.kr/product/11%EB%A7%88%EC%95%BD-%EC%95%84%EC%9D%B4%EC%8A%A4-%EC%B9%B4%EA%B3%A0-%EB%B0%B4%EB%94%A9%EB%B0%98%EB%B0%94%EC%A7%804color/3033/category/26/display/1/", 78L);
//        webCrawlingMethod("https://page27.co.kr/product/%ED%8F%B4%EB%A7%81-%EC%BF%A8-%ED%95%80%ED%84%B1-%EC%99%80%EC%9D%B4%EB%93%9C-%EB%B0%B4%EB%94%A9%ED%8C%AC%EC%B8%A02color/3029/category/26/display/1/", 79L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%A7%88%EB%A1%A0-%EC%9B%8C%EC%8B%B1-%EC%B9%B4%EA%B3%A0%EB%B0%98%EB%B0%94%EC%A7%805color/3027/category/26/display/1/", 80L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%9D%BC%EC%9D%B4%ED%8A%B8-%EC%BF%A8-%EC%99%80%EC%9D%B4%EB%93%9C-%EC%8A%AC%EB%9E%99%EC%8A%A43color/3025/category/26/display/1/", 81L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%AA%A8%EB%85%B8-%EB%A0%88%EA%B7%A4%EB%9F%AC-%ED%95%98%ED%94%84%ED%8C%AC%EC%B8%A03color/3024/category/26/display/1/", 82L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%9D%B4%EB%8D%94-%EB%A6%B0%EB%84%A8-%EC%99%80%EC%9D%B4%EB%93%9C-%EB%B0%B4%EB%94%A9%ED%8C%AC%EC%B8%A04color/3022/category/26/display/1/", 83L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%A1%9C%ED%8D%BC-%EB%A6%B0%EB%84%A8-%ED%88%AC%ED%84%B1-%EB%B0%98%EB%B0%B4%EB%94%A9-%ED%8C%AC%EC%B8%A03color/3018/category/26/display/1/", 84L);

//        여기까지가 BOTTOM 크롤링

//        webCrawlingMethod("https://page27.co.kr/product/%EC%97%90%EB%B8%8C%EB%A6%AC%EB%8D%B0%EC%9D%B4-%EC%AA%BC%EB%A6%AC2color/3019/category/28/display/1/", 85L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%95%A4%EB%8D%94%EC%8A%A8-%EC%8A%A4%EB%8B%88%EC%BB%A4%EC%A6%882color/2721/category/28/display/1/", 86L);
//        webCrawlingMethod("https://page27.co.kr/product/%ED%81%AC%EB%A1%9C%EC%8A%A4-%EB%A0%88%EB%8D%94-%EC%AA%BC%EB%A6%AC-%EC%8A%AC%EB%A6%AC%ED%8D%BC3color/2634/category/28/display/1/", 87L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%9F%AC%EC%89%AC-%EC%8A%A4%EB%8B%88%EC%BB%A4%EC%A6%88-%EB%8B%A8%ED%99%945color/1298/category/28/display/1/", 88L);
//        webCrawlingMethod("https://page27.co.kr/product/%ED%83%80%EB%B9%84-%EC%8A%A4%EB%8B%88%EC%BB%A4%EC%A6%882color/1923/category/28/display/1/", 89L);
//        webCrawlingMethod("https://page27.co.kr/product/%EB%A6%AC%EC%96%BC-%EC%8A%A4%EC%9B%A8%EC%9D%B4%EB%93%9C-%EB%A1%9C%ED%8D%BC3color/1753/category/28/display/1/", 90L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%85%80%EB%9F%BD-%EC%AA%BC%EB%A6%AC-%EC%8A%AC%EB%A6%AC%ED%8D%BC3color/2062/category/28/display/1/", 91L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%88%98%EC%A0%9C%ED%99%94-%EC%86%8C%EA%B0%80%EC%A3%BD-%EC%AA%BC%EB%A6%AC-%EC%8A%AC%EB%A6%AC%ED%8D%BC4color/2039/category/28/display/1/", 92L);
//        webCrawlingMethod("https://page27.co.kr/product/%EC%84%9C%ED%94%84-%EC%BF%A0%EC%85%98-%EC%8A%AC%EB%A6%AC%ED%8D%BC2color/2010/category/28/display/1/", 93L);

//        여기까지가 SHOES 크롤링

//        webCrawlingMethod("https://page27.co.kr/product/11%EB%8D%B0%EC%98%A4%EB%93%9C%EB%9E%80%ED%8A%B8-%EB%A6%B0%EB%84%A8-%EB%B0%98%ED%8C%94%EB%8B%88%ED%8A%B810color/3020/category/25/display/2/", 94L);
//        위에거 1차 카테고리 : TOP 2차 카테고리 : shortsleeve 위와 중복 url 아님

//        webCrawlingMethod("https://page27.co.kr/product/11%EB%A7%88%EC%95%BD-%EC%95%84%EC%9D%B4%EC%8A%A4-%EC%B9%B4%EA%B3%A0-%EB%B0%B4%EB%94%A9%EB%B0%98%EB%B0%94%EC%A7%804color/3033/category/26/display/2/", 95L);
//        위에거 1차 카테고리 : BOTTOM 2차 카테고리 : shorts 위와 중복 url 아님

//        webCrawlingMethod("https://page27.co.kr/product/%EC%98%A4%EC%85%98-%EC%BF%A8-%ED%97%A8%EB%A6%AC%EB%84%A5-%EC%85%94%EC%B8%A03color/1983/category/1/display/3/", 96L);
//        위에거 1차 카테고리 : SHIRTS 2차 카테고리 : basic 위와 중복 url 아님

//        webCrawlingMethod("https://page27.co.kr/product/%EB%9D%BC%EC%9D%B4%ED%8A%B8-%EC%9C%88%EB%93%9C%EB%B8%8C%EB%A0%88%EC%9D%B4%EC%BB%A4%EC%A1%B0%EA%B1%B0%ED%8C%AC%EC%B8%A0-%EC%85%8B%EC%97%852color/2977/category/1/display/3/", 97L);
//        위에거 1차 카테고리 : TOP 2차 카테고리 : longsleeve 위와 중복 url 아님

//        webCrawlingMethod("https://page27.co.kr/product/%ED%8E%98%EC%9D%B4%EC%A7%8027%EC%98%A4%EB%94%94%EB%84%88%EB%A6%AC-%EC%9B%8C%EC%8B%B1-%ED%9B%84%EB%93%9C-%EC%9E%90%EC%BC%93-%ED%95%9C%EC%A0%95%EC%88%98%EB%9F%8930sale/2701/category/1/display/7/", 98L);
//        위에거 1차 카테고리 : OUTER 2차 카테고리 : jacket 위와 중복 url 아님

//        여기까지가 메인 CAROUSEL 크롤링

    }

    public Long webCrawlingMethod(String itemUrl, Long idx) {
        try {
            Document doc = Jsoup.connect(itemUrl).get();

            Elements color = doc.select("#product_option_id1 option");

            for (int j = 2; j < color.size(); j++) {

                String tempItemName = doc.select(".name").get(0).text();
                String itemName = tempItemName.replaceAll("\\%","-").replaceAll("\\[","").replaceAll("\\]","");


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
//                String model = doc.text().substring(doc.text().indexOf("MODEL") + 5, doc.text().indexOf("수량") - 1).trim();
                String model = "174/64 FREE Size";
                int quantity = 10;

                Elements Category = doc.select(".xans-product-headcategory:last-child");
//                String firstCategory = Category.select("li:nth-child(2)").text().toLowerCase(Locale.ROOT);
//                String secondCategory = Category.select("li:nth-child(3)").text();
                String firstCategory = "top";
                String secondCategory = "knit";

                String direction = "";

                for (int i = 0; i < url.size(); i++) {
                    Item item = new Item();
                    if (i == 0) {
                        String repUrl = url.get(i);
//                        디렉토리 생성
                        File newFile = new File("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\TOP\\knit\\" + itemName);
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
                        FileOutputStream repOut = new FileOutputStream("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\TOP\\knit\\" + itemName + "\\" + itemName + "0" + ".jpg");
//                FileOutputStream out = new FileOutputStream("C:\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName + "\\" + itemName  + i + ".jpg");

                        String totalUrl = "/image/Item/TOP/knit/" + itemName + "/" + itemName + i + ".jpg";


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
                        FileOutputStream out = new FileOutputStream("C:\\Users\\skyey\\Desktop\\페이지27 프로젝트\\프로젝트\\project\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\TOP\\knit\\" + itemName + "\\" + itemName + i + ".jpg");
//                FileOutputStream out = new FileOutputStream("C:\\page27clone\\project\\src\\main\\resources\\static\\image\\Item\\OUTER\\자켓\\" + itemName + "\\" + itemName  + i + ".jpg");

                        String totalUrl = "/image/Item/TOP/knit/" + itemName + "/" + itemName + i + ".jpg";
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