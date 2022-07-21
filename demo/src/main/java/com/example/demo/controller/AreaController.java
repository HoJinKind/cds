package com.example.demo.controller;


import com.example.demo.dto.AreaDTO;
import com.example.demo.dto.AreaResponseDTO;
import com.example.demo.service.AreaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/area")
@Validated
@Slf4j
public class AreaController {

    public static HashMap<List<Integer>, Integer> cacheLayer = new HashMap<>();
    @Autowired
    AreaServiceImpl areaService;

    @PostMapping(value = "/getarea")
    @ResponseBody
    public ResponseEntity<AreaResponseDTO> getArea(@RequestBody @Validated AreaDTO areaDTO) {
        List<Integer> heights = areaDTO.getHeights();
        int area = 0;
        if (cacheLayer.get(heights) != null) {
            log.info("hit cache");
            area = cacheLayer.get(heights);
        } else {
            area = areaService.maxArea(heights);
            cacheLayer.put(heights, area);
        }
        return new ResponseEntity(new AreaResponseDTO(area), HttpStatus.OK);
    }
}
