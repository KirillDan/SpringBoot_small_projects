package com.reactiveStorage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Service;

import com.reactiveStorage.Entity.Person;

import reactor.core.publisher.Mono;

@Service
public class PersonRepositoryImpl implements PersonRepository{
	
    @Autowired
    DatabaseClient databaseClient;
    
    public void create(Person person) {
    	databaseClient.insert().into(Person.class).using(person).fetch().one().subscribe();
    }

	public void create(Mono<Person> person) {
		databaseClient.insert().into(Person.class).using(person).fetch().one().subscribe();	
	}
	
	public void delete(Long id){
		databaseClient.delete().from(Person.class).matching(Criteria.where("id").is(id)).fetch().rowsUpdated().subscribe();
	}
		
}
