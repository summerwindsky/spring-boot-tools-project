package com.company.project.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

public class EncodeUtils {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static String md5(InputStream input) throws IOException {
        return DigestUtils.md5Hex(input);
    }

    public static String md5(byte[] buffer) {
        return DigestUtils.md5Hex(buffer);
    }

    public static String md5(String content) {
        return DigestUtils.md5Hex(content);
    }

    public static String string2Base64(String message) {
        return Base64.encodeBase64String(message.getBytes());
    }

    public static String base642String(String base64) {
        return new String(Base64.decodeBase64(base64.getBytes()));
    }

    public static String dealContent(String content) {
    	if(StringUtils.isEmpty(content)){
    		return content;
    	}
    	String string = content.replaceAll("[\u0001-\u0009\u000B\u000C\u000E-\u001F]+", "");
    	return string;
	}
    
    public static void main(String[] args) {
        System.out.println(EncodeUtils.md5("zheshiiswode"));
        System.out.println(EncodeUtils.random(9));
    }

    public static int random(int rang) {
        Random random = new Random();
        return random.nextInt(rang);
    }
}
