package org.pgl.ws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.pgl.datasources.DatasourcesManager;
import org.pgl.datasources.DatasourcesManagerBinaryTreeImpl;

@Path("/dataservice")
public class DataService {
	
	DatasourcesManager datasourceManager = DatasourcesManagerBinaryTreeImpl.getInstance();
	
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@FormParam("name") String name){
		System.out.println("Service create : source = "+name);

		Response response;
				
		if(datasourceManager.datasourceExist(name)){
			System.out.println("Service create : "+name+" already exist");
			response = Response.serverError().build();
		}else{
			datasourceManager.createDatasource(name);
			response = Response.status(Status.CREATED).build();
			System.out.println("Service create : "+name+" created");
		}
				
		return response;
	}
	
	@Path("/retrieve")
	@POST
	public Response retrieve(@FormParam("name") String name, @FormParam("key") String key){
		System.out.println("Service retrieve : source = "+name+" - key = "+key);
		
		Response response;

		String result = datasourceManager.get(name, key);
		if(result != null){
			response = Response.ok(result).build();
		}else{
			response = Response.status(Status.NO_CONTENT).build();
		}
		return response;
	}
	
	@Path("/insert")
	@POST
	public Response insert(@FormParam("name") String name, @FormParam("key") String key, @FormParam("value") String value){		
		System.out.println("Service insert : source = "+name+" - key = "+key+" - value = "+value);

		Response response;

		if(datasourceManager.add(name, key, value)){
			response = Response.status(Status.CREATED).build();
			System.out.println("Service insert : value inserted");
		}else{
			response = Response.serverError().build();
			System.out.println("Service insert : insert failed");
		}
		return response;
	}
	
	@Path("/update")
	@PUT
	public Response update(@FormParam("name") String name, @FormParam("key") String key, @FormParam("value") String value){
		System.out.println("Service update : source = "+name+" - key = "+key+" - value = "+value);
		
		Response response;
		
		if(datasourceManager.update(name, key, value)){
			response = Response.ok().build();
		}else{
			response = Response.serverError().build();
		}
		
		return response;
	}
	
	@Path("/remove")
	@DELETE
	public Response remove(@FormParam("name") String name, @FormParam("key") String key){
		System.out.println("Service remove : source = "+name+" - key = "+key);
		
		Response response;
		
		if(datasourceManager.remove(name, key)){
			response = Response.ok().build();
		}else{
			response = Response.serverError().build();
		}
		
		return response;
	}

}
