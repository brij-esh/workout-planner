package com.workout.planner.utils;

import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;


@Service
public class MapperUtils {
    private MapperUtils() {
    }
    public static <T, U> U map(T source, Class<U> targetClass) {
        try {
            U target = targetClass.getDeclaredConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new MyCustomException("Error while mapping objects", e);
        }
    }
}

class MyCustomException extends RuntimeException {
    public MyCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
