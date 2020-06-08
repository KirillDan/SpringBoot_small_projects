package com.front.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.front.classes.Person;
import com.front.configuration.FrontConfiguration;

@Controller
@RequestMapping(value = "/front")
public class FrontController {
	
	@Autowired
	RestTemplate restTemplate;
	FrontConfiguration conf;
	
	@Autowired
	FrontController(FrontConfiguration frontConfiguration){
		this.conf = frontConfiguration;
	}
	
	@GetMapping("/main")
	public String main(Model model) {
		
		return "main";
	}
	
	@GetMapping("/all")
	public String all(Model model) {
		String url = conf.getUrl() + conf.getBasePath() + "/all";
		ResponseEntity<Person[]> responseEntity = restTemplate.getForEntity(url, Person[].class);
		List<Person> persons = Arrays.asList(responseEntity.getBody());
		model.addAttribute("persons", persons);
		model.addAttribute("person", new Person());
		return "main";
	}
	@PostMapping("/new")
	public String add(Person person){
		String url = conf.getUrl() + conf.getBasePath() + "/insert";
		restTemplate.postForEntity(url, person, Person.class);
		return "redirect:/front/all";
	}
	@GetMapping("/del/{id}")
	public String delete(@PathVariable("id") String id) {
		String url = conf.getUrl() + conf.getBasePath() + "/delete/{id}";
		Map<String,String> params = new HashMap<String, String>();
		params.put("id", id);
		restTemplate.delete(url, params);	
		return "redirect:/front/all";
	}
	
}
