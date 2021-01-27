package com.castio.test;

import static java.sql.DriverManager.println;

public class JavaTest {
    public static void main(String[] args) {
        String password = "请输入一个英文字符串或解密字符串";//获取用户输入
        char[] array = password.toCharArray();  //获取字符数组
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] ^ 2);
        }
        println("加密结果如下");
        System.err.println(new String(array));
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] ^ 2);
        }
        println("解密结果如下");
        System.err.println(new String(array));
    }
}
