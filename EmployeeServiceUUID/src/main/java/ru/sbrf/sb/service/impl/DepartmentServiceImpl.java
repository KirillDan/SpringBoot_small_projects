package ru.sbrf.sb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
		department.setDepartmentId(UUID.randomUUID());
		return departmentRepository.save(department);	
	}

	@Override
	public void deleteDepartment(UUID departmentId) {
		departmentRepository.deleteByDepartmentId(departmentId);
	}

	@Override
	public void editDepartment(Department department) throws NotFoundException {
		if(departmentRepository.existsByDepartmentId(department.getDepartmentId())) {
			Department departmentDB = departmentRepository.findByDepartmentId(department.getDepartmentId()).get();
			departmentRepository.save(departmentDB);
		} else {
			throw new NotFoundException("Department with Id = " + department.getDepartmentId() + " does not exist\n");
		}
	}

	@Override
	public Department getDepartment(UUID departmentId) {
		Optional<Department> optional = departmentRepository.findByDepartmentId(departmentId);
		return optional.get();
	}

	@Override
	public List<Employee> getDepartmentEmployees(UUID departmentId) {
		Department department = departmentRepository.findByDepartmentId(departmentId).get();
		return department.getEmployees();
	}

	@Override
	public List<Department> getDepartments() {
		return departmentRepository.findAll();
	}

}
