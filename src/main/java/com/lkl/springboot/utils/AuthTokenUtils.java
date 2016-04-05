package com.lkl.springboot.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 授权token生成
 * 
 * @author lkl
 * @version $Id: AuthTokenUtils.java, v 0.1 2015年7月29日 下午11:57:46 lkl Exp $
 */
public class AuthTokenUtils {

    public static final String SECURE_CODE = "6bda3fb5bc552d5694c9ab96c22d3734";

    public static String geneToken(String key) {
        return Base64.encodeBase64String(DigestUtils.sha1(key + SECURE_CODE));
    }

    public static String obatinParamsKey(Set<String> elements, Map<String, String[]> map) {

        String[] keys = elements.toArray(new String[elements.size()]);
        Arrays.sort(keys);
        StringBuffer buf = new StringBuffer(StringUtils.EMPTY);
        for (int i = 0, length = keys.length; i < length; i++) {
            buf.append(keys[i]);
            String[] array = map.get(keys[i]);
            if (array != null && array.length > 0) {
                for (int j = 0; j < array.length; j++) {
                    buf.append(array[j]);
                }
            }
        }
        return DigestUtils.md5Hex(buf.toString().getBytes()); //md5
    }

}
