package ru.sbrf.sb.service.impl;

import java.util.List;
import java.util.UUID;

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
		project.setProjectId(UUID.randomUUID());
		return projectRepository.save(project);
	}

	@Override
	public void deleteProject(UUID projectId) {
		projectRepository.deleteByProjectId(projectId);
	}

	@Override
	public void editProject(Project project) throws NotFoundException {
		if(projectRepository.existsByProjectId(project.getProjectId())) {
			Project projectDB = projectRepository.findByProjectId(project.getProjectId()).get();
			projectRepository.save(projectDB);
		} else {
			throw new NotFoundException("Project with Id = " + project.getProjectId() + " does not exist");
		}
	}

	@Override
	public Project getProject(UUID projectId) {
		return projectRepository.findByProjectId(projectId).get();
	}

	@Override
	public List<Employee> getProjectEmployees(UUID projectId) {
		return projectRepository.findByProjectId(projectId).get().getEmployees();
	}

	@Override
	public List<Project> getProjects() {
		return projectRepository.findAll();
	}

}
