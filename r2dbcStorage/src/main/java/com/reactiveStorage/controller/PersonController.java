package com.reactiveStorage.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactiveStorage.Entity.Person;
import com.reactiveStorage.repository.PersonCrudRepository;
import com.reactiveStorage.repository.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	private PersonCrudRepository personCrudRepository;
	private PersonRepository personRepository;
	
	@Autowired
	public PersonController(PersonCrudRepository personCrudRepository, PersonRepository personRepository) {
		this.personCrudRepository = personCrudRepository;
		this.personRepository = personRepository;
	}
		
	@GetMapping("")
	public Flux<Person> findAll(){
		return personCrudRepository.findAll();
	}
	@GetMapping("/id/{id}")
	public Mono<Person> findById(@PathVariable String id){
		return personCrudRepository.findById(Long.parseLong(id));
	}
	@GetMapping("/name/{name}")
	public Flux<Person> findByName(@PathVariable String name){
		return personCrudRepository.findByName(name);
	}
	
	
	@PostMapping(consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<Person> savePerson(@RequestBody Mono<Person> person){
		Mono<Person> person2 = person.cache();
		personRepository.create(person2);
			return person2;
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<Void> delete(@PathVariable Long id) {
		personRepository.delete(id);
		return null;
	}
	
	
	
	
		
//	@PostMapping("/block")
//	public Mono<Person> savePerson(@RequestBody Person person){
//			personRepository.create(Mono.just(person));
//			return null;//personCrudRepository.findById(personEntity.map(t -> t.getId()));
//	}
	
//	Long l = 10L;
//	@GetMapping("/test")
//	public Mono<Person> test(){
//		l = l + 1L;
//		Person p = new Person(l,"test", "test", "test", "test");
//		return Mono.fromSupplier(() -> {
//			personRepository.create(p);
//			return p;
//		});	
//
//	}
	
	
//	    @GetMapping("/test2")
//	    public Mono<Person> test2(){
//	    	l = l + 1L;
//	    	WebClient webClient = WebClient.create();
//	    	Person p = new Person(l,"test", "test", "test", "test");
//	    		return webClient.post().uri("http://localhost:9095/person")
//	    		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
//	    		.body(Mono.just(p), Person.class).retrieve().bodyToMono(Person.class);
//
//	    	}
	
//	@GetMapping("/test2")
//	public Mono<Void> deleteGET() {
//		String uri = "http://localhost:9095/person/delete/30";
//		WebClient webClient = WebClient.create();
//		return webClient.delete().uri(uri)
//		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
//		.retrieve().bodyToMono(Void.class);
//		
//	}
}
