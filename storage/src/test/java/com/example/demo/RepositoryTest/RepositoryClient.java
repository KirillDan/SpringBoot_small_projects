package com.example.demo.RepositoryTest;

import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.configuration.StorageConfiguration;

@SpringBootTest
public class RepositoryClient {
	StorageConfiguration storageConfiguration;
	
	Logger log = Logger.getLogger(RepositoryClient.class);
	
	RepositoryClient(StorageConfiguration storageConfiguration) {
		this.storageConfiguration = storageConfiguration;
		log.error(storageConfiguration.toString());
	}
}
