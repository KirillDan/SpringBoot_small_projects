package ru.sbrf.sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.sbrf.sb.entity.Project;

@EnableJpaRepositories
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
