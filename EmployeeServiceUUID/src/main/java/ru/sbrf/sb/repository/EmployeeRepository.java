package ru.sbrf.sb.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.sbrf.sb.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	public List<Employee> findByPosition(String name);
	public Optional<Employee> findByEmployeeId(UUID id);
	public Boolean existsByEmployeeId(UUID id);
	public void deleteByEmployeeId(UUID id);
}
