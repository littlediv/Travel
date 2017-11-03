package com.example;

/**
 * Created by mac on 2017/6/15.
 */

public class TokenTest {

    public static void main(String[] args) {
//        String token = "QRAPToken:/OROx5HCoWbgXI6VIbiXPU1Ya1gmPsLkMMlLBnoSHkmJsVT7jRhksErFjtgDxYoLkUuk1POiqj7S\nNrr6lBF4MmPiaHM7HYPyjmS7UzyUm4UaC9QEaSMgr8j26wQ8+iMWvJ4jVXJJWYYiS31MmUbiMQ==";
//
//        TokenUtil util = new TokenUtil();
//
//        String[] mySecrets = util.AuthToken(token, "mySecret");

        int temp = 8;

        for (int i=-8; i<24;i++) {
            int resutl = (i +6 - 2) % 6;
            System.out.println(i +" ---- "+resutl);
        }



    }
}
