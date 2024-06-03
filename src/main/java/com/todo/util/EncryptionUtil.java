package com.todo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
    public static String generateCipherTextBySHA256(String plainText) {
        try {
            // 获取一个 MessageDigest 实例并指定使用 SHA-256 算法
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // 字符串转换为字节数组并更新 MessageDigest
            md.update(plainText.getBytes());
            // 计算哈希值并返回字节数组
            byte[] digest = md.digest();
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for(byte b:digest) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }
            // 返回最终的十六进制字符串
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}