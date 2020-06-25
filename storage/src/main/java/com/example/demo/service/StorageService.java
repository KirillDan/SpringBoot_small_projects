package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.IdAddressDto;
import com.example.demo.entity.IdNameDto;
import com.example.demo.entity.NameAddressDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class StorageService {
	
	private UserRepository userRepository;
	
	@Autowired
	public StorageService(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	public User createUser(NameAddressDto nameAddress) {
		return userRepository.save(new User(nameAddress.getName(), nameAddress.getAddress()));
	}
	
	public IdNameDto findName(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.get();
		return new IdNameDto(user.getId(), user.getName());
	}
	
	public IdAddressDto findAddress(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.get();
		return new IdAddressDto(user.getId(), user.getAddress());
	}
	
	public User findUser(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.get();
	}
}
