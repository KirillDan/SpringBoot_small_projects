package com.reactiveStorage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Service;

import com.reactiveStorage.entity.User;

import reactor.core.publisher.Mono;

@Service
public class PersonRepositoryImpl implements PersonRepository{
	
    @Autowired
    DatabaseClient databaseClient;
    
    public void create(User person) {
    	databaseClient.insert().into(User.class).using(person).fetch().one().subscribe();
    }

	public void create(Mono<User> person) {
		databaseClient.insert().into(User.class).using(person).fetch().one().subscribe();	
	}
	
	public void delete(Long id){
		databaseClient.delete().from(User.class).matching(Criteria.where("id").is(id)).fetch().rowsUpdated().subscribe();
	}
		
}
