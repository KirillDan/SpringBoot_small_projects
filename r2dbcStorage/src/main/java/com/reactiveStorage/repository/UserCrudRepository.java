package com.reactiveStorage.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.reactiveStorage.entity.User;

public interface UserCrudRepository extends ReactiveCrudRepository<User, Long> {}
