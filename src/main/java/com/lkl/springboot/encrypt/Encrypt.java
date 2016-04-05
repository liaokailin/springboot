package com.lkl.springboot.encrypt;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

public class Encrypt {

    public static void main(String[] args) {
        String userUUID = "123456";
        byte[] randomBytes = DigestUtils.sha1(userUUID + RandomStringUtils.random(10) + System.currentTimeMillis());
        String token = Base64.encodeBase64String(randomBytes);

        System.out.println(token);
        System.out.println(new String(Base64.decodeBase64(Base64.encodeBase64String("123".getBytes()))));
    }

    /**
     * 加密：
     * 1.首先将需要传递的参数按照格式(key1=value1&key2=values2...) 拼揍成字符串
     *   eg: appId=1&userId=123&activityCode=hello
     * 2.对步骤1生成的字符串进行base64加密 获取实际传递的参数param对应值(假设为paramBase64Value)
     * 3.获得当前时间戳(格式yyyyMMddHHmmss)timestamp，转换为数字加上51，再加上安全码，对结果进行MD5加密，对得到的结果先进行sha1加密后再base64加密 得到实际传递的参数token对应值(假设为tokenValue) ,去掉token中的所有加号(+)
     * 4.在实际传递参数时，需要将步骤3得到的时间戳传递过来
     * 5.最终传递的参数格式为：
     * http://ip:host/projectName/methdoName?param=paramBase64Value&token=tokenValue&timestamp=timestamp
     * 案例如下：
     */
    public static final String SECURE_CODE = "3e013d2ce6eeea909facde405547f1e6";

    public static void encrypt() {
        String url = "http://localhost:8080/creditcenter-web/noLoginController/scoreWebService/checkObtainedPrice.do?appId=1&userId=123&activityCode=hello";
        /* 1.首先将需要传递的参数按照格式(key1=value1&key2=values2...) 拼揍成字符串
         */
        url = url.substring(url.lastIndexOf("?") + 1, url.length()); //appId=1&userId=123&activityCode=hello
        /*
         * 2.对步骤1生成的字符串进行base64加密 获取实际传递的参数param对应值(假设为paramBase64Value)
         */
        String param = Base64.encodeBase64String(url.getBytes()); //YXBwSWQ9MSZ1c2VySWQ9MTIzJmFjdGl2aXR5Q29kZT1oZWxsbw==
        /*
         * 3.1获取时间戳
         */
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); //20150908130742
        /*
         * 3.2时间戳转换为数字后加上51，再加上安全码，对结果进行MD5加密
         */
        String key = DigestUtils.md5Hex((Long.parseLong(timestamp) + 51) + SECURE_CODE); //17bb094645baa0a3cbc0b9cfdedf8b43
        /*
         * 3.3对得到的结果先进行sha1加密后再base64加密 得到实际传递的参数token对应值
         */
        String token = geneToken(key); // +86QM/sZnnn3CD4Og5ZVqsnk720=

        // 4.最终传递参数:
        String result = "http://localhost:8080/creditcenter-web/noLoginController/scoreWebService/checkObtainedPrice.do?"
                        + "param=" + param + "&timestamp=" + timestamp + "&token=" + token;
        System.out.println(result);
    }

    public static String geneToken(String key) {
        return Base64.encodeBase64String(DigestUtils.sha1(key)).replaceAll("\\+", "");
    }
}
