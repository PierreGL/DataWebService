package org.pgl.client.service.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientDataServiceTestUnit {
    private List<String> lstScientistName = new ArrayList<String>();
    
	private String sourceName = "scientist-list";
    
	private String rootScientist;
	private String firstScientist;
	private String oneScientist;
	private String otherScientist;
	private String lastScientist;
    
	private ClientDataServiceExample client = new ClientDataServiceExample();
	
	@Before
    public void initTestList(){
    	rootScientist = "Seguin";
    	lstScientistName.add(rootScientist);
    	firstScientist = "Lalande";
    	lstScientistName.add(firstScientist);
    	lstScientistName.add("Tresca");
    	lstScientistName.add("Poncelet");
    	oneScientist = "Bresse";
    	lstScientistName.add(oneScientist);
    	lstScientistName.add("Lagrange");
    	lstScientistName.add("Bélanger");
    	lstScientistName.add("Bolton-cuvier");
    	lstScientistName.add("Laplace");
    	lstScientistName.add("Dulong");
    	lstScientistName.add("Chasles");
    	otherScientist = "Lavoisier";
    	lstScientistName.add(otherScientist);
    	lstScientistName.add("Ampere");
    	lstScientistName.add("Seguin");
    	lstScientistName.add("Chevreul");
    	lstScientistName.add("Flachat");
    	lstScientistName.add("Navier");
    	lstScientistName.add("Legendre");
    	lastScientist = "Chaptal";
    	lstScientistName.add(lastScientist);
    }
    
    /**
     * Create new datasource.
     * Insert set of values.
     * Get some representatives values.
     * Remove some values.
     * 
     * */
    @Test
    public void scenario1(){
        Random random = new Random ();
    	String newSource = sourceName+random.nextInt(100);;
    	if(checkOk(client.createDatasource(newSource))){
        	for (String name : lstScientistName) {
        		System.out.println(client.insert(newSource, name, name+"-value"));
    		}
        	
        	System.out.println(client.retrieve(newSource, oneScientist));
        	System.out.println(client.retrieve(newSource, otherScientist));
        	System.out.println(client.retrieve(newSource, firstScientist));
        	System.out.println(client.retrieve(newSource, rootScientist));
        	System.out.println(client.retrieve(newSource, lastScientist));
        	
        	System.out.println(client.remove(newSource, oneScientist));
        	System.out.println(client.retrieve(newSource, oneScientist));
    	}

    }
    
    /**
     * Check that HTTP response is between 200 and 299.
     * */
    private boolean checkOk(int code){
    	if(200 <= code && code < 300){
    		return true;
    	}
    	return false;
    }
}
