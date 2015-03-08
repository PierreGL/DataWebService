package org.pgl.client.service.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pgl.client.service.example.ClientDataServiceExample;

/**
 * Few scenarios to test the client service example. The server needs to be started.
 * */
public class ClientDataServiceTestUnit {

	private List<String> lstScientistName;

	private String sourceBaseName = "scientist-list";
	private String newSource;
	private String rootScientist;
	private String firstScientist;
	private String oneScientist;
	private String otherScientist;
	private String lastScientist;

	private final String SUFFIX_VALUE = "-value";

	private ClientDataServiceExample client = new ClientDataServiceExample();

	@Before
	public void initDataTests(){
		lstScientistName = new ArrayList<String>();
		
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
		lstScientistName.add("Chevreul");
		lstScientistName.add("Flachat");
		lstScientistName.add("Navier");
		lstScientistName.add("Legendre");
		lastScientist = "Chaptal";
		lstScientistName.add(lastScientist);
		
		//If the test unit is played several times, use a random different name to avoid parasites other tests.
		Random random = new Random ();
		newSource = sourceBaseName+"-"+random.nextInt(1000);;
	}
	
	/**
	 * Create new datasource.
	 * Insert set of values.
	 * Get some representatives values.
	 * */
	@Test
	public void scenarioInsertRetrieve(){

		boolean datasourceCreated = checkHttpCodeSucceed(client.createDatasource(newSource));

		if(datasourceCreated){
			for (String name : lstScientistName) {
				assertInsertName(newSource, name);
			}

			assertRetrieveName(newSource, oneScientist);
			assertRetrieveName(newSource, otherScientist);
			assertRetrieveName(newSource, firstScientist);
			assertRetrieveName(newSource, lastScientist);
			assertRetrieveName(newSource, rootScientist);
		}
	}

	/**
	 * Create new datasource.
	 * Insert set of values.
	 * Remove some representatives values.
	 * */
	@Test
	public void scenarioInsertRemove(){
		boolean datasourceCreated = checkHttpCodeSucceed(client.createDatasource(newSource));

		if(datasourceCreated){
			for (String name : lstScientistName) {
				assertInsertName(newSource, name);
			}
			
			assertRemoveName(newSource, oneScientist);
			assertRemoveName(newSource, otherScientist);
			assertRemoveName(newSource, firstScientist);
			assertRemoveName(newSource, lastScientist);
		}
	}

	private void assertInsertName(String source, String name){
		String failMsg = "Insert "+name+" failed";
		Assert.assertTrue(failMsg, checkHttpCodeSucceed(client.insert(source, name, name+SUFFIX_VALUE)));
	}

	private void assertRetrieveName(String source, String name){
		String failMsg = "Retrieve "+name+" failed";
		String expectedValue = name+SUFFIX_VALUE;
		Assert.assertTrue(failMsg, expectedValue.equals(client.retrieve(source, name)));
	}

	private void assertRemoveName(String source, String name){
		String failMsg = "Remove "+name+" failed";
		Assert.assertTrue(failMsg, checkHttpCodeSucceed(client.remove(source, name)));

		String failMsg2 = "The previoulsy removed "+name+" has been retrieved";
		Assert.assertTrue(failMsg2, client.retrieve(source, name) == null);
	}

	/**
	 * Check that HTTP response has succeed.
	 * 
	 * @return return true if the code is between 200 and 299.
	 * */
	private boolean checkHttpCodeSucceed(int code){
		if(200 <= code && code < 300){
			return true;
		}
		return false;
	}
}
