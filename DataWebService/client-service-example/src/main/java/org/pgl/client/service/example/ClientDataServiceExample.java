package org.pgl.client.service.example;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

/**
 * Few examples to use the data service from client.
 * */
public class ClientDataServiceExample {
	
	/**
	 * Create new datasource.
	 * */
    public int createDatasource(String name){
		System.out.println("create : source = "+name);

    	Client client = Client.create();
    	WebResource resource = client.resource("http://localhost:8080/web-service/dataservice/create");
    	
    	Form form = new Form();
    	form.add("name", name);
    	
    	ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, form);
    	return response.getStatus();
    }
    
    /**
     * Insert entity with key and value.
     * */
    public int insert(String source, String key, String value){
		System.out.println("insert : source = "+source+" - key = "+key+" - value = "+value);

       	Client client = Client.create();
    	WebResource resource = client.resource("http://localhost:8080/web-service/dataservice/insert");
    	
    	Form form = new Form();
    	form.add("name", source);
    	form.add("key", key);
    	form.add("value", value);
    	
    	ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, form);
    	return response.getStatus();
	
    }
    
    /**
     * Retrieve entity by key.
     * 
     * @return Value of entity, if not exist return null.
     * */
    public String retrieve(String source, String key){
		System.out.println("retrieve source = "+source+" - key = "+key);

		String result = null;
		
       	Client client = Client.create();
    	WebResource resource = client.resource("http://localhost:8080/web-service/dataservice/retrieve");
    	
    	Form form = new Form();
    	form.add("name", source);
    	form.add("key", key);
    	
    	ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, form);
    	int status = response.getStatus();
    	System.out.println("+++"+status);
    	
    	if(Status.OK.getStatusCode() == status){
    		result = response.getEntity(String.class);
    		System.out.println(result);
    	}
    	
    	return result;
    }
    
    /**
     * Remove entity by key.
     * */
    public int remove(String source, String key){
		System.out.println("remove source = "+source+" - key = "+key);

       	Client client = Client.create();
    	WebResource resource = client.resource("http://localhost:8080/web-service/dataservice/remove");
    	
    	Form form = new Form();
    	form.add("name", source);
    	form.add("key", key);
    	
    	ClientResponse response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, form);
    	return response.getStatus();
    }
}
