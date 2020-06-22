package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.IdAddressDto;
import com.example.demo.entity.IdNameDto;
import com.example.demo.entity.NameAddressDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController()
@RequestMapping(value = "/api/users")
public class StorageController {	
	private UserRepository userRepository;
	
	@Autowired
	public StorageController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	@GetMapping(value = "/{id}/name")
	public ResponseEntity<IdNameDto> name(@PathVariable("id") Long id){
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.get();
		IdNameDto idName = new IdNameDto(user.getId(), user.getName());
		return ResponseEntity.ok(idName);
	}
	@GetMapping(value = "/{id}/address")
	public ResponseEntity<IdAddressDto> address(@PathVariable("id") Long id){
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.get();
		IdAddressDto idAddress = new IdAddressDto(user.getId(), user.getAddress());
		return ResponseEntity.ok(idAddress);
	}
	@PostMapping
	public ResponseEntity<User> create(@RequestBody NameAddressDto nameAddress) {
		User user = userRepository.save(new User(nameAddress.getName(), nameAddress.getAddress()));
		return ResponseEntity.ok(user);
	}
}
