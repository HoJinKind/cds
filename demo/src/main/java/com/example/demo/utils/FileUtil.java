package com.example.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileUtil {
    public static SuccessResult<Integer> readFile(MultipartFile file, List<String> lineList){
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith("csv")) {
            return SuccessResult.successValue(0);
        }
        //try-with-resources
        try (InputStream is = file.getInputStream()) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            br.readLine();
            while ((line = br.readLine()) != null ) {
                if (!line.isEmpty()) {
                    lineList.add(line);
                }
            }
        } catch (IOException e) {
            return SuccessResult.successValue(0);
        }
        return null;
    }
}
