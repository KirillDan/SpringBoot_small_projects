package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.demo.Entity.PersonEntity;

@RepositoryRestResource(path = "person")
public interface PersonRepository extends JpaRepository<PersonEntity, Long>{
	@RestResource(path = "address")
	List<PersonEntity> findByAddress(@Param("address") String address);
	Iterable<PersonEntity> findByName(@Param("name") String name);
	List<PersonEntity> findBySecondaryName(@Param("secondaryName") String secondaryName);
	List<PersonEntity> findByNameAndSecondaryName(@Param("name") String name, @Param("secondaryName") String secondaryName);
	List<PersonEntity> findAll();
}
