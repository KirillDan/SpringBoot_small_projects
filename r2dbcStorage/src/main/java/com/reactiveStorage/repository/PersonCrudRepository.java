package com.reactiveStorage.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.reactiveStorage.Entity.Person;

import reactor.core.publisher.Flux;

public interface PersonCrudRepository extends ReactiveCrudRepository<Person, Long> {
	
//	@Query("SELECT * FROM person_entity WHERE address = :address")
//	Flux<PersonEntity> findByAddress(String address);
	@Query("SELECT * FROM person WHERE name = :name")
	Flux<Person> findByName(String name);
}
