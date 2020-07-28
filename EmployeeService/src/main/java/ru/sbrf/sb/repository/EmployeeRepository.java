package ru.sbrf.sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.sbrf.sb.entity.Employee;

@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public List<Employee> findByPosition(String name);
}
