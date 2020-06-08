package com.orkestr.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.orkestr.classes.Person;
import com.orkestr.configuration.StorageConfiguration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/orkestr")
public class RestPersonClient {

	private WebClient webClient;
	
	private StorageConfiguration conf;
	@Autowired
	public RestPersonClient(StorageConfiguration storageConfiguration, WebClient webClient) {
		this.conf = storageConfiguration;
		this.webClient = webClient;

	}
	
	@GetMapping(value = "/id/{id}")
	public Mono<Object> findById(@PathVariable("id") String id) {
		String url = conf.getUrl() + conf.getBasePath() + "/id/" + id;	
		return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange()
				.flatMap(response -> response.bodyToMono(Person.class));
	}
	
	@GetMapping(value = "/name/{id}")
	public Flux<Person> findByName(@PathVariable("name") String name) {
		String url = conf.getUrl() + conf.getBasePath() + "/name/" + name;	
		return webClient.get().uri(url).accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(Person.class);
	}
	
	@GetMapping(value = "/all")
	public Flux<Person> findAll() throws URISyntaxException {
		String url = conf.getUrl() + conf.getBasePath();

		/*Getting Mono
		Mono<ClientResponse> clientResponse = webClient
				.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange();
			Mono<Object> listPerson = clientResponse.flatMap(response -> response.bodyToFlux(Person.class).collectList());
		*/
		
		/* Getting Flux  */
		Flux<Person> listPerson = webClient.get().uri(url).accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(Person.class);
		
		return listPerson;
	}

	@PostMapping(value = "/insert")
	public Mono<Person> insert(@RequestBody Person person) throws URISyntaxException {
		String uri = conf.getUrl() + conf.getBasePath();
		return webClient.post().uri("http://localhost:9095/person")
	    		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
	    		.body(Mono.just(person), Person.class).retrieve().bodyToMono(Person.class);
	
	}
		
	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Mono<Void> delete(@PathVariable("id") String id) {
		String uri = conf.getUrl() + conf.getBasePath()  + "/delete/" + id;
		return webClient.delete().uri(uri)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
		.retrieve().bodyToMono(Void.class);
	}
	
	
	
	
//	@GetMapping(value = "/test2/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//	public Mono<Void> deleteGET(@PathVariable("id") String id) {
//		String uri = conf.getUrl() + conf.getBasePath()  + "/delete/" + id;
//		return webClient.delete().uri(uri)
//		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
//		.retrieve().bodyToMono(Void.class);
//		
//	}
//	
	
	
/*	For Testing POST connection
	Long l = 50L;
	@GetMapping("/test")
	public Mono<Person> test(){
		l = l + 1L;
		String uri = "http://localhost:9095/person";
		Person p = new Person(l,"test", "test", "test", "test");
			return webClient.post().uri("http://localhost:9095/person")
		    		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
		    		.body(Mono.just(p), Person.class).retrieve().bodyToMono(Person.class);

	}
	*/
	
}
