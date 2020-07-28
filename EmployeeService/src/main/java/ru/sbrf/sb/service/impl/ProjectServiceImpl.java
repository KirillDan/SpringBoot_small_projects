package ru.sbrf.sb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.repository.ProjectRepository;
import ru.sbrf.sb.service.ProjectService;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
	private ProjectRepository projectRepository;
	
	@Override
	public Project createProject(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public void deleteProject(Long projectId) {
		projectRepository.deleteById(projectId);
	}

	@Override
	public void editProject(Project project) throws NotFoundException {
		if(projectRepository.existsById(project.getProjectId())) {
			projectRepository.save(project);
		} else {
			throw new NotFoundException("Project with Id = " + project.getProjectId() + " does not exist");
		}
	}

	@Override
	public Project getProject(Long projectId) {
		return projectRepository.findById(projectId).get();
	}

	@Override
	public List<Employee> getProjectEmployees(Long projectId) {
		return projectRepository.findById(projectId).get().getEmployees();
	}

	@Override
	public List<Project> getProjects() {
		return projectRepository.findAll();
	}

}
