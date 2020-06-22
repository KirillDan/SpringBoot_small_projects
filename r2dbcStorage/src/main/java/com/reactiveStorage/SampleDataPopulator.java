package com.reactiveStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
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
    
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS USERS;";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE PERSON (id INT PRIMARY KEY, name VARCHAR(255), address VARCHAR(255));";
    
    @Override
    public void run(String... strings) throws Exception {
    	
    	List<String> statements = Arrays.asList(SQL_DROP_TABLE);
    	statements.forEach(statement -> databaseClient.execute(statement).fetch().rowsUpdated().subscribe());
    	
    	List<String> statements2 = Arrays.asList(SQL_CREATE_TABLE);
    	statements.forEach(statement2 -> databaseClient.execute(statement2).fetch().rowsUpdated().subscribe()); 
    }
}
