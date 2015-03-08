package org.pgl.ws.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.pgl.datasources.DatasourcesManager;
import org.pgl.datasources.DatasourcesManagerBinaryTreeImpl;

/**
 * This service presents basic functionalities to store and manipulate data by key value. 
 * */
@Path("/dataservice")
public class DataService {
	
	private DatasourcesManager datasourceManager = DatasourcesManagerBinaryTreeImpl.getInstance();
	
	/**
	 * Create a new data source.
	 * */
	@Path("/create")
	@POST
	public Response create(@FormParam("source") String source){
		System.out.println("Service create : source = "+source);

		Response response;
				
		if(datasourceManager.datasourceExist(source)){
			System.out.println("Service create : "+source+" already exist");
			response = Response.serverError().build();
		}else{
			datasourceManager.createDatasource(source);
			response = Response.status(Status.CREATED).build();
			System.out.println("Service create : "+source+" created");
		}
				
		return response;
	}
	
	/**
	 * Retrieve a data by key in defined source.
	 * */
	@Path("/retrieve")
	@POST
	public Response retrieve(@FormParam("source") String source, @FormParam("key") String key){
		System.out.println("Service retrieve : source = "+source+" - key = "+key);
		
		Response response;

		String result = datasourceManager.get(source, key);
		if(result != null){
			response = Response.ok(result).build();
		}else{
			response = Response.status(Status.NO_CONTENT).build();
		}
		return response;
	}
	
	/**
	 * Insert a data with key in defined source.
	 * */
	@Path("/insert")
	@POST
	public Response insert(@FormParam("source") String source, @FormParam("key") String key, @FormParam("value") String value){		
		System.out.println("Service insert : source = "+source+" - key = "+key+" - value = "+value);

		Response response;

		if(datasourceManager.add(source, key, value)){
			response = Response.status(Status.CREATED).build();
			System.out.println("Service insert : value inserted");
		}else{
			response = Response.serverError().build();
			System.out.println("Service insert : insert failed");
		}
		return response;
	}
	
	/**
	 * Update the data by key with value in defined source.
	 * */
	@Path("/update")
	@PUT
	public Response update(@FormParam("source") String source, @FormParam("key") String key, @FormParam("value") String value){
		System.out.println("Service update : source = "+source+" - key = "+key+" - value = "+value);
		
		Response response;
		
		if(datasourceManager.update(source, key, value)){
			response = Response.ok().build();
		}else{
			response = Response.serverError().build();
		}
		
		return response;
	}
	
	/**
	 * Remove a data by key in defined source.
	 * */
	@Path("/remove")
	@DELETE
	public Response remove(@FormParam("source") String source, @FormParam("key") String key){
		System.out.println("Service remove : source = "+source+" - key = "+key);
		
		Response response;
		
		if(datasourceManager.remove(source, key)){
			response = Response.ok().build();
		}else{
			response = Response.serverError().build();
		}
		
		return response;
	}

}
