package com.orkestr.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.orkestr.classes.Person;
import com.orkestr.configuration.StorageConfiguration;

@RestController
@RequestMapping(value = "/orkestr")
public class RestPersonClient {
	@Autowired
	private RestTemplate restTemplate;
	private StorageConfiguration conf;
	@Autowired
	public RestPersonClient(StorageConfiguration storageConfiguration) {
		this.conf = storageConfiguration;

	}
	
	@GetMapping(value = "/id/{id}")
	public Person findById(@PathVariable("id") String id) {
		Map<String,String> params = new HashMap<String, String>();
		params.put("id", id);
		return restTemplate.getForObject(conf.getUrl() + conf.getBasePath() + "/{id}", Person.class, params);	
	}
	@GetMapping(value = "/all")
	public Iterable<Person> findAll() throws URISyntaxException {
		String url = conf.getUrl() + "/all";
		RequestEntity<Iterable<Person>> requestEntity = new RequestEntity<Iterable<Person>>(
				HttpMethod.GET,
				new URI(url));
		ResponseEntity<Iterable<Person>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Iterable<Person>>() {});
		return responseEntity.getBody();
	}
	@GetMapping(value = "/person")
	public Iterable<Person> findByName(@RequestParam("name") String name) throws URISyntaxException {
		String url = conf.getUrl() + "/name?name=" + name;
		RequestEntity<Iterable<Person>> requestEntity = new RequestEntity<Iterable<Person>>(
				HttpMethod.GET,
				new URI(url));
		ResponseEntity<Iterable<Person>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Iterable<Person>>() {});
		return responseEntity.getBody();
	}
	@GetMapping(value = "/personS")
	public Iterable<Person> findBySecondaryName(@RequestParam("secondaryName") String secondaryName) throws URISyntaxException {
		String url = conf.getUrl() + "/secondaryName?secondaryName=" + secondaryName;
		RequestEntity<Iterable<Person>> requestEntity = new RequestEntity<Iterable<Person>>(
				HttpMethod.GET,
				new URI(url));
		ResponseEntity<Iterable<Person>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Iterable<Person>>() {});
		return responseEntity.getBody();
	}
	@GetMapping(value = "/personFS")
	public Iterable<Person> indByNameAndSecondaryName(@RequestParam("name") String name, @RequestParam("secondaryName") String secondaryName) throws URISyntaxException {
		String url = conf.getUrl() + "/names?name=" + name + "&secondaryName=" + secondaryName;
		RequestEntity<Iterable<Person>> requestEntity = new RequestEntity<Iterable<Person>>(
				HttpMethod.GET,
				new URI(url));
		ResponseEntity<Iterable<Person>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Iterable<Person>>() {});
		return responseEntity.getBody();
	}
	@PostMapping(value = "/insert")
	public void insert(@RequestBody Person person) throws URISyntaxException {
		String uri = conf.getUrl() + conf.getBasePath();
		RequestEntity<?> requestEntity = new RequestEntity<>(person, HttpMethod.POST, new URI(uri));
		ResponseEntity<?> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Person>() {});
	}
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") String id) {
		String uri = conf.getUrl() + conf.getBasePath()  + "/{id}";
		Map<String,String> params = new HashMap<String, String>();
		params.put("id", id);
		restTemplate.delete(uri, params);
	}
	
}
