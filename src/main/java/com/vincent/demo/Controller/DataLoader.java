package com.vincent.demo.Controller;

import com.vincent.demo.service.KeelungSightsCrawler;
import com.vincent.demo.service.Sight;
import com.vincent.demo.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;


@Component
public class DataLoader {
    private final SightRepository sightRepository;
    private final KeelungSightsCrawler keelungSightsCrawler;

    @Autowired
    public DataLoader(SightRepository sightRepository, KeelungSightsCrawler keelungSightsCrawler) {
        this.sightRepository = sightRepository;
        this.keelungSightsCrawler = keelungSightsCrawler;
    }

    @PostConstruct
    public void loadData() throws IOException {
        String[] zones = { "中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖" };

        for (String zone : zones) {
            Sight[] sights = keelungSightsCrawler.getItems(zone);
            sightRepository.saveAll(Arrays.asList(sights));
        }

        System.out.println("加載成功");
    }
}
