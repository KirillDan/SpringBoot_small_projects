package com.reactiveStorage.repository;

import com.reactiveStorage.entity.User;

import reactor.core.publisher.Mono;

public interface PersonRepository {
	
	public void create(User person);
	
	public void create(Mono<User> person);
	
	public void delete(Long id);
}
