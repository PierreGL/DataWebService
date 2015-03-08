package org.pgl.client.service.example;

import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

/**
 * Few examples to use the data service from client.
 * */
public class ClientDataServiceExample {
	
	private static final Logger LOGGER = Logger.getLogger(ClientDataServiceExample.class.getSimpleName());
	
	private final String SERVICE_URL = "http://localhost:8080/web-service/dataservice";
	
	/**
	 * Create new datasource.
	 * */
    public int createDatasource(String name){
		LOGGER.info("source = "+name);

    	Client client = Client.create();
    	WebResource resource = client.resource(SERVICE_URL+"/create");
    	
    	Form form = new Form();
    	form.add("source", name);
    	
    	ClientResponse response = resource.post(ClientResponse.class, form);
		int status = response.getStatus();
		LOGGER.fine("response status = "+status);

    	return status;
    }
    
    /**
     * Insert entity with key and value.
     * */
    public int insert(String source, String key, String value){
		LOGGER.info("source = "+source+" - key = "+key+" - value = "+value);

       	Client client = Client.create();
    	WebResource resource = client.resource(SERVICE_URL+"/insert");
    	
    	Form form = new Form();
    	form.add("source", source);
    	form.add("key", key);
    	form.add("value", value);
    	
    	ClientResponse response = resource.post(ClientResponse.class, form);		
    	int status = response.getStatus();
		LOGGER.fine("response status = "+status);
    	
    	return status;
    }
    
    /**
     * Retrieve entity by key.
     * 
     * @return Value of entity, if not exist return null.
     * */
    public String retrieve(String source, String key){
		LOGGER.info("source = "+source+" - key = "+key);
		
		String result = null;
		
       	Client client = Client.create();
    	WebResource resource = client.resource(SERVICE_URL+"/retrieve");
    	
    	Form form = new Form();
    	form.add("source", source);
    	form.add("key", key);
    	
    	ClientResponse response = resource.post(ClientResponse.class, form);

    	int status = response.getStatus();
    	if(Status.OK.getStatusCode() == status){
    		result = response.getEntity(String.class);
    	}
		LOGGER.fine("response status = "+status+" - restrieve "+result);
    	return result;
    }
    
    /**
     * Remove entity by key.
     * */
    public int remove(String source, String key){
		LOGGER.info("source = "+source+" - key = "+key);

       	Client client = Client.create();
    	WebResource resource = client.resource(SERVICE_URL+"/remove");
    	
    	Form form = new Form();
    	form.add("source", source);
    	form.add("key", key);
    	
    	ClientResponse response = resource.delete(ClientResponse.class, form);
    	int status = response.getStatus();
		LOGGER.fine("response status = "+status);
    	
    	return status;
    }
}
