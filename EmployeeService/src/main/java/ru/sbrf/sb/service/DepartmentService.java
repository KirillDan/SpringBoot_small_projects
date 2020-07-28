package ru.sbrf.sb.service;

import java.util.List;

import javassist.NotFoundException;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;

public interface DepartmentService {
//CRUD интерфейсы
	public Department createDepartment(Department department);
	public void deleteDepartment(Long departmentId);
	public void editDepartment(Department department) throws NotFoundException;
	public Department getDepartment(Long departmentId);
	public List<Department> getDepartments();
//Дополнительные данные
	public List<Employee> getDepartmentEmployees(Long departmentId);
}
