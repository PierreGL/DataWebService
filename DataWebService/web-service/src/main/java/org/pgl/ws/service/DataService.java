package org.pgl.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.pgl.datasources.DatasourcesManager;
import org.pgl.datasources.DatasourcesManagerBinaryTreeImpl;

@Path("/dataservice")
public class DataService {
	
	DatasourcesManager datasourceManager = new DatasourcesManagerBinaryTreeImpl();
	
	Logger logger = Logger.getLogger(DataService.class.getSimpleName());
	
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@FormParam("name") String name){
		logger.log(Level.INFO, "Service create : "+name);
		
		Response result;
				
		if(datasourceManager.datasourceExist(name)){
			logger.log(Level.INFO, name+" already exist");
			result = Response.status(Status.UNAUTHORIZED).build();
		}else{
			datasourceManager.createDatasource(name);
			result = Response.status(Status.CREATED).build();
			logger.log(Level.INFO, name+" created");
		}
				
		return result;
	}
	
	@Path("/retrieve")
	@POST
	public String retrieve(@FormParam("name") String name, @FormParam("key") String key){
		String result = datasourceManager.get(name, key);
		return result;
	}
	
	@Path("/insert")
	@POST
	public Response insert(@FormParam("name") String name, @FormParam("key") String key, @FormParam("value") String value){
		Response result;

		if(datasourceManager.add(name, key, value)){
			result = Response.ok().build();
		}else{
			result = Response.status(Status.UNAUTHORIZED).build();
		}
		return result;
	}
	
	@Path("/update/{key}/{value}")
	@PUT
	public String update(@FormParam("name") String name, @FormParam("key") String key, @FormParam("value") String value){
		
		
		return null;
	}
	
	@Path("/delete/{key}")
	@DELETE
	public String delete(@FormParam("name") String name, @PathParam("key") String key){
		
		
		return null;
	}
	
	
	//TODO test method :
	@GET
	@Path("/getstring")
	public Response getString(){
		return Response.ok("une reponse").build();
	}

	@GET
	@Path("/hello")
	public String getHello(){
		return "hello jersey";
	}
	
	@GET
	@Path("{id}")
	public String display(@PathParam("id") String id) {
		System.out.println("******** "+id);
		return "*** "+id+" ***";
	}
	
	@GET
	@Path("/string/{key}")
	public String getStringByKey(@PathParam("key") String key){
		String result;
		if(key == "a"){
			result = "aaaaa";
		}else{
			result = "bbbbb";
		}
		
		return result;
	}
	
	@GET
	@Path("/number/{key}")
	public String getNumberByKey(@PathParam("key") String key){
		
		return "100.0";
	}
	
	@GET
	@Path("/number-list/{key}")
	public List<Number> getNumberListByKey(@PathParam("key") String key){
		
		List<Number> lst = new ArrayList<Number>();
		lst.add(5);
		lst.add(10);
		lst.add(12.5);
		
		return lst;
	}
	
	@GET
	@Path("/string-list/{key}")
	public List<String> getStringListByKey(@PathParam("key") String key){
		
		List<String> lst = new ArrayList<String>();
		lst.add(key+" toto");
		lst.add(key+" tata");
		lst.add(key+" tutu");
		
		return lst;
	}
	
//	@GET
//	@Path("/entity/{key}/{entity}")
//	public <T> T getGenericByKey(@PathParam("key") String key, @PathParam("entity") T t){
//		String result;
//		if(key == "a"){
//			result = "aaaaa";
//		}else{
//			result = "bbbbb";
//		}
//		
//		return t;
//	}

	//	@GET
	//	@Path("/getString")
	//	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	//	public Response getString(){
	//		return Response.status(200).entity("salu salut").build();
	//		
	////		return Response.ok("Salut tt le monde").build();
	//	}

	//	@GE
	//	@GET
	//	@HttpResourc (location="/customers/{first}/{last}")
	//	void findCutomer(@WebParam(name="first") String firstName, @WebParam(name="last") String lastName){
	//		
	//	}

	//    @Pu
	//    @Path("/getbook/{name}")
	//    @Produces({"application/xml","application/json"})
	//    @Consumes({"application/xml","application/json","application/x-www-form-urlencoded"})
	//    public Response getString() {




	//        log.debug("name : " + name);
	//        BookVO bookVO = null;u
	//        try {
	//            bookVO = HashDB.getBook(URLDecoder.decode(name, "UTF-8"));
	//        } catch (UnsupportedEncodingException e) {
	//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	//        }
	// 
	//        if(bookVO == null){
	//            return Response.status(Response.Status.BAD_REQUEST).build();
	//        }else{
	//            return Response.ok(bookVO).build();
	//        }
	//    }

}
