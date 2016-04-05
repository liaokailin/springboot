package com.lkl.springboot.retrofit;

import retrofit.http.GET;
import retrofit.http.Path;

import com.lkl.springboot.domain.Person;
import com.lkl.springboot.swagger.SwaggerConfig;

/**
 * 通过retrofit 调用rest接口
 * 
 * @author lkl
 * @version $Id: RetrofitService.java, v 0.1 2015年8月4日 下午7:09:56 lkl Exp $
 */
interface RetrofitService {

    public static final String PROJECT_NAME    = "springboot";
    public static final String PROJECT_VERSION = "v1";
    public static final String BASE_URL        = "/" + SwaggerConfig.PROJECT_NAME + "/api/"
                                                 + SwaggerConfig.PROJECT_VERSION;

    /**
     * 获取人员信息
     * 
     * @param name
     * @return
     */
    @GET(BASE_URL + "/person/{name}")
    Person getPersonInfo(@Path("name") String name);

}
