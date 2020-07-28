package ru.sbrf.sb.service;

import java.util.List;

import javassist.NotFoundException;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;

public interface ProjectService {
//CRUD интерфейсы
	public Project createProject(Project project);
	public void deleteProject(Long projectId);
	public void editProject(Project project) throws NotFoundException;
	public Project getProject(Long projectId);
	public List<Project> getProjects();
//Дополнительные данные
	public List<Employee> getProjectEmployees(Long projectId);
}
