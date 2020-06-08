package com.reactiveStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import com.reactiveStorage.Entity.Person;
import com.reactiveStorage.repository.PersonRepositoryImpl;


import java.util.Arrays;
import java.util.List;

@Component
public class SampleDataPopulator implements CommandLineRunner
{

    @Autowired
    DatabaseClient databaseClient;
    @Autowired
    PersonRepositoryImpl personService;
    
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS PERSON;";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE PERSON (id INT PRIMARY KEY, name VARCHAR(255), secondary_name VARCHAR(255), patronymic VARCHAR(255), address VARCHAR(255));";
    private static final String SQL_INSERT_INTO_TABLE = "INSERT INTO PERSON VALUES" +
    "(1,'test', 'test', 'test', 'test')," +
    "(2,'test', 'test', 'test', 'test');";

    
    @Override
    public void run(String... strings) throws Exception {
    	
    	List<String> statements = Arrays.asList(SQL_CREATE_TABLE);
    	statements.forEach(statement -> databaseClient.execute(statement).fetch().rowsUpdated().subscribe());
    	
    	List<String> statements2 = Arrays.asList(SQL_INSERT_INTO_TABLE);
    	statements2.forEach(statement -> databaseClient.execute(statement).fetch().rowsUpdated().subscribe());
    	
    	personService.create(new Person(30L, "test", "test", "test", "test"));
    	
    	
//    	personRepository.deleteAll();
//    	personRepository.saveAll(sampleUsers())
//                .doOnComplete(() -> System.out.println("Count:"+personRepository.count().block()))
//                .subscribe();

    }

    
//    private List<PersonEntity> sampleUsers()
//    {
//        return Arrays.asList(
//                new PersonEntity(20l,"test", "test", "test", "test"),
//                new PersonEntity(21l,"test", "test", "test", "test"),
//                new PersonEntity(22l,"test", "test", "test", "test"),
//                new PersonEntity(23l,"test", "test", "test", "test"),
//                new PersonEntity(24l,"test", "test", "test", "test")
//               );
//    }
    
    
}
