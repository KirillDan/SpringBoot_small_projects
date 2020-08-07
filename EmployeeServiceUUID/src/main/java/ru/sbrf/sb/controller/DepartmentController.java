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
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.service.DepartmentService;
import ru.sbrf.sb.service.ProjectService;

@RestController
@RequestMapping("sb/department")
@AllArgsConstructor
public class DepartmentController {
	private DepartmentService departmentService;
	
	@GetMapping("/{id}")
	public Department get(@PathVariable UUID id) {
		return departmentService.getDepartment(id);
	}
	
	@GetMapping()
	public List<Department> get() {
		return departmentService.getDepartments();
	}
	
	@PostMapping()
	public Department create(@RequestBody Department department) {
		return departmentService.createDepartment(department);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		departmentService.deleteDepartment(id);
	}
	
	@PutMapping()
	public void edit(@RequestBody Department department) throws NotFoundException {
		departmentService.editDepartment(department);
	}
	
	@GetMapping("/{id}/employees")
	public List<Employee> getEmployees(@PathVariable UUID id) {
		return departmentService.getDepartmentEmployees(id);
	}
}
