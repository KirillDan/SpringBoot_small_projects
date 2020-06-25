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
import com.example.demo.service.StorageService;

@RestController()
@RequestMapping(value = "/api/users")
public class StorageController {	
	private StorageService storageService;
	
	@Autowired
	public StorageController(StorageService storageService){
		this.storageService = storageService;
	}
	@GetMapping(value = "/{id}/name")
	public ResponseEntity<IdNameDto> name(@PathVariable("id") Long id){
		IdNameDto idName = storageService.findName(id);
		return ResponseEntity.ok(idName);
	}
	@GetMapping(value = "/{id}/address")
	public ResponseEntity<IdAddressDto> address(@PathVariable("id") Long id){
		IdAddressDto idAddress = storageService.findAddress(id);
		return ResponseEntity.ok(idAddress);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> user(@PathVariable("id") Long id){
		User user = storageService.findUser(id);
		return ResponseEntity.ok(user);
	}
	@PostMapping
	public ResponseEntity<User> create(@RequestBody NameAddressDto nameAddress) {
		User user = storageService.createUser(nameAddress);
		return ResponseEntity.ok(user);
	}
}
