package com.reactiveStorage.controller;

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
import com.reactiveStorage.service.StorageService;

import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/api/users")
public class PersonController {
	private StorageService storageService;
	
	@Autowired
	public PersonController(StorageService storageService) {
		this.storageService = storageService;
	}
	@GetMapping(value = "/{id}/name")
	public Mono<IdNameDto> name(@PathVariable("id") Long id){
		Mono<IdNameDto> IdNameDtoMono = storageService.name(id);
		return IdNameDtoMono;
	}
	@GetMapping(value = "/{id}/address")
	public Mono<IdAddressDto> address(@PathVariable("id") Long id){
		Mono<IdAddressDto> IdAddressDtoMono = storageService.address(id);
		return IdAddressDtoMono;
	}
	@GetMapping(value = "/{id}")
	public Mono<User> user(@PathVariable("id") Long id){
		Mono<User> userMono = storageService.user(id);
		return userMono;
	}
	@PostMapping
	public Mono<User> create(@RequestBody Mono<NameAddressDto> nameAddressMono) {
		Mono<User> userMono = storageService.create(nameAddressMono);
		return userMono;
	}
}
