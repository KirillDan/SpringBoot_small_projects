package ru.sbrf.sb.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.sbrf.sb.entity.Project;

@EnableJpaRepositories
public interface ProjectRepository extends JpaRepository<Project, UUID> {
	public Optional<Project> findByProjectId(UUID id);
	public Boolean existsByProjectId(UUID id);
	public void deleteByProjectId(UUID id);
}
