package com.reactiveStorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactiveStorage.entity.IdAddressDto;
import com.reactiveStorage.entity.IdNameDto;
import com.reactiveStorage.entity.NameAddressDto;
import com.reactiveStorage.entity.User;
import com.reactiveStorage.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class StorageService {
	private UserRepository userRepository;
	
	@Autowired
	public StorageService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Mono<IdNameDto> name(Long id){
		Mono<User> userMono = userRepository.findById(id);
		return userMono.map(user -> new IdNameDto(user.getId(), user.getName()));
	}
	
	public Mono<IdAddressDto> address(Long id){
		Mono<User> userMono = userRepository.findById(id);
		return userMono.map(user -> new IdAddressDto(user.getId(), user.getName()));
	}
	
	public Mono<User> user(Long id){
		return userRepository.findById(id);
	}
	
	public Mono<User> create(Mono<NameAddressDto> nameAddressMono){
		Mono<NameAddressDto> nameAddressMono2 = nameAddressMono.cache();
		Mono<User> userMono = nameAddressMono2.map(nameAddress -> new User(nameAddress.getName(), nameAddress.getAddress())).cache();
		userRepository.create(userMono);
		return userMono;
	}
}
