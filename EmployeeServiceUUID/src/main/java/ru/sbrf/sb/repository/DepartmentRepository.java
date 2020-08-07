package ru.sbrf.sb.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.sbrf.sb.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	public Optional<Department> findByDepartmentId(UUID id);
	public Boolean existsByDepartmentId(UUID id);
	public void deleteByDepartmentId(UUID id);
}
