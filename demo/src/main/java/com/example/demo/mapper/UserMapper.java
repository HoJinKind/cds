package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
@Component
public interface UserMapper extends Mapper<User> {

    @Select({
            "<script>",
            "select * from users ",
            " <trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">",
            " 1 = 1",
            " <if test=\"min != null\">",
            " and salary >= #{min}*1",
            " </if>",
            " <if test=\"max != null\">",
            " and #{max}*1 >= salary ",
            " </if>",
            "</trim>",

            " <if test=\"sortBy != null\">",
            " order by ${sortBy} ",
            " </if>",

            " <if test=\"limit != null\">",
            " LIMIT #{limit} ",
            " </if>",

            " <if test=\"offset != null\">",
            " OFFSET #{offset} ",
            " </if>",
            "</script>",
    })
    List<User> getUsers(@Param("min") String min, @Param("max") String max, @Param("sortBy") String sortBy, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Insert("<script>Insert into users" +
            " ( name,salary  ) VALUE" +
            "<foreach collection='userList' item='item'  index='index' separator=','>" +
            "(#{item.name},#{item.salary})" +
            "</foreach>" +
            " AS new" +
            " ON DUPLICATE KEY UPDATE " +
            "salary = new.salary " +
            "</script>")
    int batchUpsert(@Param("userList") List<User> userList);
}
