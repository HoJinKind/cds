package com.example.demo.service;

import com.example.demo.mapper.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl {

    private final static List<String> SORTABLES =  new ArrayList<>(Arrays.asList("name", "salary"));
    public static final String DECIMAL_SALARY = "*1";


    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers(BigDecimal min,
                               BigDecimal max,
                               Integer offset,
                               Integer limit,
                               String sort
    ) {
        if (!SORTABLES.contains(sort)) {
            sort = null;
        }else {
            sort = sort.toLowerCase();
            if(sort.equals("salary")) {
                sort+= DECIMAL_SALARY;
            }
        }

        String minString = null;

        String maxString = null;
        if (Objects.nonNull(min)) {
            minString = min.toPlainString();
        }

        if (Objects.nonNull(max)) {
            maxString = max.toPlainString();
        }
        List<User> users = userMapper.getUsers(minString, maxString, sort, offset, limit);
        System.out.println(users);
        return users;
    }

    @Transactional(rollbackFor = Exception.class)
    public void upsertUsers(List<String> lineList) throws Exception {
        List<User> userList = Lists.newArrayList();
        for (String line : lineList) {

            String[] lines = line.split(",");
            if (lines.length>2) {
                throw new Exception("invalid number of columns");
            }
            String name = lines[0].trim();
            String salary = lines[1].trim();
            BigDecimal salaryDecimal = new BigDecimal(salary);
            if (salaryDecimal.compareTo(BigDecimal.ZERO) >= 0) {
                userList.add(new User(name, salaryDecimal.toPlainString()));
            }
        }
        userMapper.batchUpsert(userList);
    }
}
