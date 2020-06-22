package com.reactiveStorage.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactiveStorage.entity.IdAddressDto;
import com.reactiveStorage.entity.IdNameDto;
import com.reactiveStorage.entity.NameAddressDto;
import com.reactiveStorage.entity.User;
import com.reactiveStorage.repository.PersonCrudRepository;
import com.reactiveStorage.repository.PersonRepository;

import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/api/users")
public class PersonController {
	
	private PersonCrudRepository personCrudRepository;
	private PersonRepository personRepository;
	
	@Autowired
	public PersonController(PersonCrudRepository personCrudRepository, PersonRepository personRepository) {
		this.personCrudRepository = personCrudRepository;
		this.personRepository = personRepository;
	}
	
	@GetMapping(value = "/{id}/name")
	public Mono<IdNameDto> name(@PathVariable("id") Long id){
		Mono<User> userMono = personCrudRepository.findById(id);
		Mono<IdNameDto> IdNameDtoMono = userMono.map(user -> new IdNameDto(user.getId(), user.getName()));
		return IdNameDtoMono;
	}
	@GetMapping(value = "/{id}/address")
	public Mono<IdAddressDto> address(@PathVariable("id") Long id){
		Mono<User> userMono = personCrudRepository.findById(id);
		Mono<IdAddressDto> IdAddressDtoMono = userMono.map(user -> new IdAddressDto(user.getId(), user.getName()));
		return IdAddressDtoMono;
	}
	@PostMapping
	public Mono<User> create(@RequestBody Mono<NameAddressDto> nameAddressMono) {
		Mono<NameAddressDto> nameAddressMono2 = nameAddressMono.cache();
		Mono<User> userMono = nameAddressMono2.map(nameAddress -> new User(nameAddress.getName(), nameAddress.getAddress())).cache();
		personRepository.create(userMono);
		return userMono;
	}
}
