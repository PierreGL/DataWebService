package org.pgl.ws.service;

import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(DataService.class.getName());

    private DatasourcesManager datasourceManager = DatasourcesManagerBinaryTreeImpl.getInstance();

    /**
     * Create a new data source.
     * If datasource already exists, return ERROR 412.
     * */
    @Path("/create")
    @POST
    public Response create(@FormParam("source") String source){
        LOGGER.info("source = "+source);

        Response response;

        if(datasourceManager.datasourceExist(source)){
            LOGGER.warning(source+" already exist");
            response = Response.status(Status.PRECONDITION_FAILED).build();
        }else{
            datasourceManager.createDatasource(source);
            response = Response.status(Status.CREATED).build();
            LOGGER.info("source = "+source+" created");
        }

        return response;
    }

    /**
     * Retrieve a data by key in defined source.
     * If datasource or key do not exist exists, return ERROR 412.
     * */
    @Path("/retrieve")
    @POST
    public Response retrieve(@FormParam("source") String source, @FormParam("key") String key){
        LOGGER.info("source = "+source+" - key = "+key);

        Response response;

        String result = datasourceManager.get(source, key);
        if(result != null){
            response = Response.ok(result).build();
        }else{
            response = Response.status(Status.PRECONDITION_FAILED).build();
        }
        return response;
    }

    /**
     * Insert a data with key in defined source.
     * If datasource does not exist, or key already exists return ERROR 412.
     * */
    @Path("/insert")
    @POST
    public Response insert(@FormParam("source") String source, @FormParam("key") String key, @FormParam("value") String value){		
        LOGGER.info("source = "+source+" - key = "+key+" - value = "+value);

        Response response;

        if(datasourceManager.add(source, key, value)){
            response = Response.status(Status.CREATED).build();
        }else{
            response = Response.status(Status.PRECONDITION_FAILED).build();
        }
        return response;
    }

    /**
     * Update the data by key with value in defined source.
     * If datasource or key do not exist return ERROR 412.
     * */
    @Path("/update")
    @PUT
    public Response update(@FormParam("source") String source, @FormParam("key") String key, @FormParam("value") String value){
        LOGGER.info("source = "+source+" - key = "+key+" - value = "+value);

        Response response;

        if(datasourceManager.update(source, key, value)){
            response = Response.ok().build();
        }else{
            response = Response.status(Status.PRECONDITION_FAILED).build();
        }

        return response;
    }

    /**
     * Remove a data by key in defined source.
     * If datasource or key do not exist return ERROR 412.
     * */
    @Path("/remove")
    @DELETE
    public Response remove(@FormParam("source") String source, @FormParam("key") String key){
        LOGGER.info("source = "+source+" - key = "+key);

        Response response;

        if(datasourceManager.remove(source, key)){
            response = Response.ok().build();
        }else{
            response = Response.status(Status.PRECONDITION_FAILED).build();
        }

        return response;
    }

}
