package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class SuccessResult<String> {

    private String success;

    public static <T> SuccessResult<T> successValue(T t) {
        return SuccessResult.<T>builder()
                .success(t)
                .build();
    }


}
