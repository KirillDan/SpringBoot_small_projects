package ru.sbrf.sb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.repository.DepartmentRepository;
import ru.sbrf.sb.service.DepartmentService;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {
	private DepartmentRepository departmentRepository;
	
	@Override
	public Department createDepartment(Department department) {
		return departmentRepository.save(department);	
	}

	@Override
	public void deleteDepartment(Long departmentId) {
		departmentRepository.deleteById(departmentId);
	}

	@Override
	public void editDepartment(Department department) throws NotFoundException {
		if(departmentRepository.existsById(department.getDepartmentId())) {
			departmentRepository.save(department);
		} else {
			throw new NotFoundException("Department with Id = " + department.getDepartmentId() + " does not exist\n");
		}
	}

	@Override
	public Department getDepartment(Long departmentId) {
		Optional<Department> optional = departmentRepository.findById(departmentId);
		return optional.get();
	}

	@Override
	public List<Employee> getDepartmentEmployees(Long departmentId) {
		Department department = departmentRepository.findById(departmentId).get();
		return department.getEmployees();
	}

	@Override
	public List<Department> getDepartments() {
		return departmentRepository.findAll();
	}

}
