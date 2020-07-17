package com.reactiveStorage.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Repository;

import com.reactiveStorage.entity.User;

import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    DatabaseClient databaseClient;
    
    public void create(User person) {
    	databaseClient.insert().into(User.class).using(person).fetch().one().subscribe();
    }
	public void create(Mono<User> person) {
		databaseClient.insert().into(User.class).using(person).fetch().one().subscribe();	
	}
	public Mono<User> findById(Long id) {
		return databaseClient.select().from(User.class).matching(Criteria.where("id").is(id)).fetch().one()
		.switchIfEmpty(Mono.error(new RuntimeException("User not found")));
	}
		
}
