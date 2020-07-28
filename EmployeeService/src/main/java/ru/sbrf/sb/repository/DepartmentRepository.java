package ru.sbrf.sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.sbrf.sb.entity.Department;

@EnableJpaRepositories
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
