package com.lkl.springboot.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lkl.springboot.domain.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
