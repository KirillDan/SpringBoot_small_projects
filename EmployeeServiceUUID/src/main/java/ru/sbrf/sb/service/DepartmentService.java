package ru.sbrf.sb.service;

import java.util.List;
import java.util.UUID;

import javassist.NotFoundException;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;

public interface DepartmentService {
//CRUD интерфейсы
	public Department createDepartment(Department department);
	public void deleteDepartment(UUID departmentId);
	public void editDepartment(Department department) throws NotFoundException;
	public Department getDepartment(UUID departmentId);
	public List<Department> getDepartments();
//Дополнительные данные
	public List<Employee> getDepartmentEmployees(UUID departmentId);
}
