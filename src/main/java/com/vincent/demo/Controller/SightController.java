package com.vincent.demo.Controller;
import com.vincent.demo.service.Sight;
import com.vincent.demo.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sights")
public class SightController {
    private final SightService sightService;

    @Autowired
    public SightController(SightService sightService) {
        this.sightService = sightService;
    }
    @RequestMapping
    public List<Sight> getSightsByZone(@RequestParam("zone") String zone) {
        return sightService.getSightsByZone(zone);

    }
}