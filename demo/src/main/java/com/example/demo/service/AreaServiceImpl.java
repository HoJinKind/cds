package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl {
    public int maxArea(List<Integer> height) {
        int max = 0;
        int startPointer = 0;
        int endPointer = height.size() - 1;

        while (endPointer > startPointer) {
            int currentArea = (endPointer - startPointer) * Math.min(height.get(endPointer), height.get(startPointer));
            if (currentArea > max) {
                max = currentArea;
            }
            if (height.get(endPointer) >= height.get(startPointer)) {
                startPointer++;
            } else {
                endPointer--;
            }
        }

        return max;
    }
}
