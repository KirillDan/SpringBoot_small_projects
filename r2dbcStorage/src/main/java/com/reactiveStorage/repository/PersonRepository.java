package com.reactiveStorage.repository;

import com.reactiveStorage.Entity.Person;

import reactor.core.publisher.Mono;

public interface PersonRepository {
	
	public void create(Person person);
	
	public void create(Mono<Person> person);
	
	public void delete(Long id);
}
