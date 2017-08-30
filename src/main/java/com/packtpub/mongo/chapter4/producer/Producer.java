package com.packtpub.mongo.chapter4.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@ApplicationScoped
public class Producer {
	private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());

	@Produces
	public MongoClient mongoClient() {
		try {

			List<ServerAddress> seeds = new ArrayList<ServerAddress>();
			seeds.add(new ServerAddress("localhost"));
			ServerAddress server = new ServerAddress(System.getProperty("DB_HOST"), 27017);
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();

			credentials.add(MongoCredential.createMongoCRCredential(System.getProperty("DB_USERNAME"), System.getProperty("DB_DATABASE"), System.getProperty("DB_PASSWORD").toCharArray()));

			return new MongoClient(seeds, credentials);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}
