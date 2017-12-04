package uk.ac.ebi.scilite.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import uk.ac.ebi.literature.mongodb.dao.ICrudDAO;
import uk.ac.ebi.literature.mongodb.dao.ICrudDAO.MONGODB_URL;

@SpringBootApplication
@Configuration
@PropertySource(value="classpath:/annotations_ws_default.properties")
@PropertySource(value="classpath:/annotations_ws_${tomcat.hostname:localhost}.properties", ignoreResourceNotFound=true)
public class AnnotationsAPIWebApplication extends SpringBootServletInitializer{

		private final static Logger logger = LogManager.getLogger(AnnotationsAPIWebApplication.class);
	
	    @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(AnnotationsAPIWebApplication.class);
	    }

	    public static void main(String[] args) throws Exception {
	        SpringApplication.run(AnnotationsAPIWebApplication.class, args);
	    }
	    
	    @Bean
	    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	       return new PropertySourcesPlaceholderConfigurer();
	    }
	    
	    @Autowired
		Environment env;
	    
	    @Bean
	    public ICrudDAO dao() throws IOException{
	    	MONGODB_URL mongoDbUrl = MONGODB_URL.valueOf(env.getProperty("mongoDBUrl"));
	    	ICrudDAO daoMongo =  new AnnotationsAPICruDao(mongoDbUrl);
	    	logger.info("Connected to MongoDB "+daoMongo.getConnectionInfo());
	    	return daoMongo;
	    }
	    
	    @Bean
	    public int maxPageSize(){
	    	return Integer.parseInt(env.getProperty("maxPageSize"));
	    }
	    
	    @Bean
	    public String collectionName(){
	    	return env.getProperty("collectionName"); 
	    }
	    
	    @Bean
	    public String versionNumber(){
	    	String versionNumber="";
	    	try(InputStream inputStream = AnnotationsAPIController.class.getClassLoader().getResourceAsStream("build.properties");){
				Properties prop = new Properties();
				prop.load(inputStream);
				versionNumber = prop.getProperty("build.version");
	    	}catch(Exception e){
	    		logger.error("Error reading POM version number", e);
	    		versionNumber="";
	    	}
	    	
	    	return  versionNumber;
	    }
}
