package com.cncounter.demo.compile;

public interface Formatter {
    <T> String format(T object) throws Exception;
}