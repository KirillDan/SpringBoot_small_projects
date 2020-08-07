package ru.sbrf.sb.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.service.ProjectService;

@RestController
@RequestMapping("sb/project")
@AllArgsConstructor
public class ProjectController {
	private ProjectService projectService;
	
	@GetMapping("/{id}")
	public Project get(@PathVariable UUID id) {
		return projectService.getProject(id);
	}
	
	@GetMapping()
	public List<Project> get() {
		return projectService.getProjects();
	}
	
	@PostMapping()
	public Project create(@RequestBody Project project) {
		return projectService.createProject(project);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		projectService.deleteProject(id);
	}
	
	@PutMapping()
	public void edit(@RequestBody Project project) throws NotFoundException {
		projectService.editProject(project);
	}
	
	@GetMapping("/{id}/employees")
	public List<Employee> getEmployees(@PathVariable UUID id) {
		return projectService.getProjectEmployees(id);
	}
}
