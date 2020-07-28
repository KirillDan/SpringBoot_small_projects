package com.webclient;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.dto.EmployeeDto;
import com.dto.EmployeeResponseDto;
import com.dto.PostDto;
import com.dto.ResponseDto;
import com.dto.TokenDto;

import lombok.AllArgsConstructor;

@Controller
@SessionAttributes(value="tokenDto")
public class ClientKeycloak {

	private static final String grant_type = "password";
	private static final String client_id = "springboot-microservice";
	private static final String client_secret = "415d1d1b-d467-4f39-936b-c4380753b948";
	
	private RestTemplate restTemplate;
	
	@ModelAttribute("tokenDto") 
	TokenDto tokenDto() {
		return new TokenDto();
	}
	
	@Autowired
	public ClientKeycloak(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

//	@GetMapping("/test")
//	public String test(Model model) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
//		map.add("grant_type", "password");
//		map.add("client_id", "springboot-microservice");
//		map.add("client_secret", "415d1d1b-d467-4f39-936b-c4380753b948");
//		map.add("username", "employee1");
//		map.add("password", "mypassword");
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//		String token = restTemplate.postForEntity(
//				"http://localhost:8080/auth/realms/Demo-Realm/protocol/openid-connect/token",
//				request,
//				ResponseDto.class).getBody().getAccess_token();
//		model.addAttribute("token", token);
//		return "token";
//	}
	
	@GetMapping("/login")
	public String test2(Model model) {
		model.addAttribute("postDto", new PostDto());
		return "index";
	}
	
	@PostMapping("/getToken")
	public String test2(PostDto postDto, Model model, @ModelAttribute("tokenDto") TokenDto tokenDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("grant_type", this.grant_type);
		map.add("client_id", this.client_id);
		map.add("client_secret", this.client_secret);
		map.add("username", postDto.getUsername());//employee1
		map.add("password", postDto.getPassword());//mypassword
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		String token = restTemplate.postForEntity(
				"http://localhost:8080/auth/realms/Demo-Realm/protocol/openid-connect/token",
				request,
				ResponseDto.class).getBody().getAccess_token();
		model.addAttribute("token", token);
		tokenDto.setToken(token);
		return "token";
	}
	
	@GetMapping("/getToken")
	public String getToken(@ModelAttribute("tokenDto") TokenDto tokenDto, Model model) {
		model.addAttribute("token", tokenDto.getToken());
		return "token";
	}	
	
//-----------------employee
	@GetMapping("/employeeCreate")
	public String createEmployee(Model model) {
		model.addAttribute("employee", new EmployeeDto());
		return "employeeCreate";
	}
	
	@PostMapping("/employeeCreate")
	public String createEmployee(EmployeeDto employeeDto, 
			@ModelAttribute("tokenDto") TokenDto tokenDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "bearer " + tokenDto.getToken());
		HttpEntity<EmployeeDto> request = new HttpEntity<>(employeeDto, headers);
		EmployeeDto createdEmployeeDto = restTemplate.postForEntity("http://localhost:8085/sb/employee",
				request, 
				EmployeeDto.class).getBody();
		return "redirect:/employeeAll";
	}
	
	@GetMapping("/employeeDelete/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		restTemplate.delete("http://localhost:8085/sb/employee/" + id);
		return "redirect:/employeeAll";
	}
	
	@GetMapping("/employeeAll")
	public String allEmp(Model model,
			@ModelAttribute("tokenDto") TokenDto tokenDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "bearer " + tokenDto.getToken());
		HttpEntity request = new HttpEntity(headers);	
		EmployeeResponseDto[] employees = restTemplate.exchange(
				"http://localhost:8085/sb/employee",
				HttpMethod.GET, request, EmployeeResponseDto[].class).getBody();
		model.addAttribute("employees", employees);
		return "employeeAll";
	}
}
