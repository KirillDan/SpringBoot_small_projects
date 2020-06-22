package com.reactiveStorage.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.reactiveStorage.entity.User;

public interface PersonCrudRepository extends ReactiveCrudRepository<User, Long> {}
