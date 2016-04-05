package com.lkl.springboot.retrofit;

import retrofit.RestAdapter;

import com.lkl.springboot.domain.Person;

/**
 * 官方api ： http://square.github.io/retrofit/
 * 
 * @author lkl
 * @version $Id: RetrofitClient.java, v 0.1 2015年8月4日 下午7:12:25 lkl Exp $
 */
public class RetrofitClient {
    public static void main(String[] args) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://localhost:8080").build();
        RetrofitService retrofitService = restAdapter.create(RetrofitService.class);
        Person p = retrofitService.getPersonInfo("liaokailin");
        System.out.println(p);
    }

}
