package com.example.demo.controller;

import com.example.demo.mapper.User;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.Result;
import com.example.demo.utils.SuccessResult;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Value("${testConfig}")
    private String testConfig;

    @GetMapping(value = "/users")
    public Result<List<User>> getUsersByPage(@RequestParam(required = false) BigDecimal min,
                                             @RequestParam(required = false) BigDecimal max,
                                             @RequestParam(required = false) Integer offset,
                                             @RequestParam(required = false) Integer limit,
                                             @RequestParam(required = false) String sort
    ) throws Exception {
//        throw  new Exception("111");
        System.out.println(sort);
        List<User> users = userService.getUsers(min, max, offset, limit,sort);
        return Result.success(users);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<SuccessResult> storecsv(MultipartFile file) {
        List<String> lineList = Lists.newArrayList();
        FileUtil.readFile(file,lineList);
        System.out.println(lineList);
        SuccessResult<Integer> result = SuccessResult.successValue(1);
        try {
            userService.upsertUsers(lineList);
        } catch (Exception e ) {
            result.setSuccess(0);
            System.out.println(e);
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }
}

