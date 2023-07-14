package com.example.board.util;

import com.example.board.exception.CustomException;

public class NullChecker {
    private NullChecker(){};

    public static <T> T check(T obj, CustomException e) throws CustomException {
        if(obj == null){
            throw e;
        }
        return obj;
    }
}
