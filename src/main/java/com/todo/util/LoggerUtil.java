package com.todo.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerUtil {
    public static void printStartCallMethodInfo(String methodName, Object... params) {
        for(Object param : params) {
            log.info(String.format("param: %s", param.toString()));
        }
        log.info(String.format("%s method start to execute", methodName));
    }

    public static void printMethodExecuteDuration(String methodName, Long beginTime, Long endTime) {
        log.info(String.format("%s method execution time: %sms", methodName, endTime - beginTime));
    }
}