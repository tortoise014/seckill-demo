package com.seckilldemo.utils;

import org.springframework.stereotype.Component;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:16
 * @PackageName:com.seckilldemo.utils
 * @ClassName: MD5Util
 * @Description:
 * @Version 1.0
 */
@Component
public class MD5Util {
    public static String md5(String src) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(src);
    }
    private static final String salt = "1a2b3c4d";
    public static String inputPassToFormPass(String inputPass) {
        String str =""+salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));

        System.out.println(formPassToDBPass(
                "d3b1294a61a07da9b49b6e22b2cbd7f9",
                "1a2b3c4d"
        ));

        System.out.println(inputPassToDBPass(
                "123456",
                "1a2b3c4d"
        ));
    }
}
