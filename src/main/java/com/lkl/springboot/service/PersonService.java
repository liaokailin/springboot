package com.lkl.springboot.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import jersey.repackaged.com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lkl.springboot.domain.Person;
import com.lkl.springboot.mongodb.PersonRepository;

@Service
public class PersonService {

    /**
     * @Cacheable 注解中的参数
     *  value : 必填 可以理解为namespace之类
     *  key: 缓存中对应的key值，可以使用spel表达式  
     *  condition: 表示生成缓存的条件
     * @param name
     * @return
     */
    @Cacheable(value = { "person" }, key = "#name", condition = "#name !='zhangsan'")
    public String getPersonInfo(String name) {
        return getPersonInfoFromDB(name);
    }

    private String getPersonInfoFromDB(String name) {
        System.out.println("模拟从数据库取值");
        return "spring-boot-" + name;
    }

    /**
     * redis 相关操作
     */
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public String redisOps() {
        try {
            ValueOperations<String, String> stringOps = redisTemplate.opsForValue();
            HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
            ListOperations<String, String> listOps = redisTemplate.opsForList();
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();

            //字符串操作
            System.out.println("--------------String Ops Start----------------");
            stringOps.set(RedisKey.strKey, "hello world springboot str", 100, TimeUnit.MINUTES);
            stringOps.append(RedisKey.strKey, "-suffix");
            System.out.println("after append: " + stringOps.get(RedisKey.strKey));
            String getAndSet = stringOps.getAndSet(RedisKey.strKey, " reset hello");
            System.out.println("do getAndSet: " + getAndSet);
            System.out.println("after getAndSet: " + stringOps.get(RedisKey.strKey));
            System.out.println("--------------String Ops End----------------");
            System.out.println("--------------HashOps Ops Start----------------");
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", "zhangsan");
            map.put("age", "20");
            hashOps.putAll(RedisKey.hashKey, map);
            Map<Object, Object> result = hashOps.entries(RedisKey.hashKey);
            System.out.println(result);
            if (result != null) {
                Set<Entry<Object, Object>> setObj = result.entrySet();
                Iterator<Map.Entry<Object, Object>> iter = setObj.iterator();
                while (iter.hasNext()) {
                    Entry<Object, Object> entry = iter.next();
                    System.out.println(entry.getKey() + "," + entry.getValue());
                }

            }

            System.out.println("--------------HashOps Ops End----------------");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            redisTemplate.discard();
        }
        return "";
    }

    private interface RedisKey {
        String strKey  = "spring:boot:str:key";
        String hashKey = "spring:boot:hash:key";
    }

    @Autowired
    PersonRepository personRepository;

    /**
     * 保存到mongo
     */
    public void saveInMongdo() {
        Person p = new Person();
        p.setId(UUID.randomUUID().toString());
        p.setName("liaokailin");
        p.setAge(25);
        Person result = personRepository.save(p);
        System.out.println(result);
    }

    public void findAll() {
        List<Person> list = personRepository.findAll();
        if (!CollectionUtils.isEmpty(list))
            for (Person p : list) {
                System.out.println(p);
            }

    }

    public String aopMethod(String name) {
        System.out.println("---aopMethod----" + name);
        return "hello" + name;
    }

}
