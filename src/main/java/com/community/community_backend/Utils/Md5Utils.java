package com.community.community_backend.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    public static String getPwd(String pwd) {
        try {
            //创建加密对象
            MessageDigest md5 = MessageDigest.getInstance("md5");
            //调用加密对象的方法,加密的动作已完成
            byte[] result = md5.digest(pwd.getBytes());
            //接下来,我们要对加密后的结果,进行优化,按照mysql的优化思路走
            //MySQL的优化思路:
            //1.将数据全部转成正数
            String hexString = "";
            for (byte b : result) {
                //1.全部数据转成正数
                int temp = b & 255;
                //2.将全部数据转成16进制形式
                //16进制,转换的时候注意
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
