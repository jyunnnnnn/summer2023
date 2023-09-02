package com.vincent.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;


@Component
public class KeelungSightsCrawler {
    private String url = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity";
    private String[] area = {"七堵", "中山", "中正", "暖暖", "信義", "安樂", "仁愛"};
    private Map<String, Sight[]> areaData = new HashMap<>();

    public KeelungSightsCrawler() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements top = doc.select("h4");

        try {
            for (Element a : top) {
                List<Sight> sights = new ArrayList<>(); // 各區list
                String areaname=Contains(a.text());
                if (areaname!="0") {
                    System.out.println(areaname);
                    Element ulelement = a.nextElementSibling();
                    Elements lielements = ulelement.select("li");
                    for (Element li : lielements) {
                        Elements aElements = li.select("a");
                        String href = aElements.attr("href");
                        String newUrl = "https://www.travelking.com.tw" + href;
                        Document docTwo = Jsoup.connect(newUrl).get();
                        Elements divElement = docTwo.select("#point_area");
                        Elements sightName = divElement.select("h1");
                        Elements typeElement = divElement.select(".point_type");
                        Elements zoneElement = typeElement.select("strong");
                        Elements urlElement = divElement.select(".gpic");
                        Elements descriptionElement = divElement.select("meta[itemprop=description]");
                        Elements addressElement = divElement.select("meta[itemprop=address]");
                        Elements srcElement = urlElement.select("img");
                        Sight temp = new Sight();
                        if (srcElement.isEmpty()) {
                            temp.setPhotoURL("https://upload.wikimedia.org/wikipedia/zh/thumb/3/3e/National_Taiwan_Ocean_University_Logo.svg/1200px-National_Taiwan_Ocean_University_Logo.svg.png");
                        } else {
                            temp.setPhotoURL(srcElement.attr("data-src"));
                        }

                        temp.setSightName(sightName.text());
                        temp.setZone(a.text());
                        temp.setCategory(zoneElement.text());
                        temp.setDescription(descriptionElement.attr("content"));
                        temp.setAddress(addressElement.attr("content"));
                        sights.add(temp);
                        //System.out.println(temp);
                    }
                    areaData.put(areaname, sights.toArray(new Sight[sights.size()])); // 區域名稱為鍵
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  String Contains(String target){
        for(String str:area){
            if(target.contains(str)){
                return str;
            }
        }
        return "0";
    }
    public Sight[] getItems(String area) {
        return areaData.get(area);
    }
}

