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
public class Result<T> {

    private T results;

    public static <T> Result<T> success(T t) {
        return Result.<T>builder()
                .results(t)
                .build();
    }


}
