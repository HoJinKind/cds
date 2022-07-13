package com.example.demo.service;

import com.example.demo.mapper.User;
import com.example.demo.mapper.UserMapper;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Spy
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserMapper userMapper;


    public List<User> genUsers() {
        List<User> users = Lists.newArrayList();
        User user = new User("Tom", "123");
        users.add(user);
        return users;
    }

    @Test
    public void testGetUsers() throws Exception {

        Mockito.doReturn(genUsers()).when(userMapper).getUsers(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        List<User> result = userServiceImpl.getUsers(new BigDecimal(1), new BigDecimal(124), 0, 10, "name");
        Assert.assertEquals(result, genUsers());
    }

    @Test
    public void testUpsertUsers() throws Exception {
        Mockito.doReturn(1).when(userMapper).batchUpsert(Mockito.anyList());
        List<String> lineList = Lists.newArrayList();
        lineList.add("abc,123");
        userServiceImpl.upsertUsers(lineList);
    }

    @Test
    public void testUpsertUsersFailFormat() {
        List<String> lineList = Lists.newArrayList();
        lineList.add("abc,aaa");
        Exception exception = assertThrows(Exception.class, () -> {
            userServiceImpl.upsertUsers(lineList);
        });
        String expectedMessage = "Character a is neither a decimal digit number, decimal point, nor \"e\" notation exponential mark.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpsertUsersFailExtraColumns() {
        List<String> lineList = Lists.newArrayList();
        lineList.add("abc,aaa,aaa");
        Exception exception = assertThrows(Exception.class, () -> {
            userServiceImpl.upsertUsers(lineList);
        });
        String expectedMessage = "invalid number of columns";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}