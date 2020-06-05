package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.PersonEntity;
import com.example.demo.repository.PersonRepository;

@RestController
public class StorageController {
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping(value = "/name")
	public ResponseEntity<Iterable<PersonEntity>> name(@RequestParam("name") String name){
		return ResponseEntity.ok(personRepository.findByName(name));
	}
	
	@GetMapping(value = "/secondaryName")
	public ResponseEntity<Iterable<PersonEntity>> secondaryName(@RequestParam("secondaryName") String secondaryName){
		return ResponseEntity.ok(personRepository.findBySecondaryName(secondaryName));
	}
	@GetMapping(value = "/names")
	public ResponseEntity<Iterable<PersonEntity>> names(@RequestParam("name") String name, @RequestParam("secondaryName") String secondaryName){
		return ResponseEntity.ok(personRepository.findByNameAndSecondaryName(name, secondaryName));
	}
	@GetMapping("/all")
	public ResponseEntity<Iterable<PersonEntity>> all(){
		return ResponseEntity.ok(personRepository.findAll());
	}
}
