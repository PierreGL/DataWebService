package org.pgl.datasources.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pgl.datasources.DatasourcesManager;
import org.pgl.datasources.DatasourcesManagerBinaryTreeImpl;

/**
 * Unit test to datasourceManager.
 * */
public class DatasourcesManagerTest {

    private List<String> lstAuthorName;

    private String sourceName = "author-list";
    private String rootAuthor;
    private String firstAuthor;
    private String oneAuthor;
    private String otherAuthor;
    private String lastAuthor;

    private final String SUFFIX_VALUE = "-value";

    DatasourcesManager datasourcesManager;

    @Before
    public void initDataTests(){
        datasourcesManager = DatasourcesManagerBinaryTreeImpl.getInstance();

        lstAuthorName = new ArrayList<String>();

        rootAuthor = "Hugo";
        lstAuthorName.add(rootAuthor);
        firstAuthor = "Tillier";
        lstAuthorName.add(firstAuthor);
        lstAuthorName.add("Dumas");
        lstAuthorName.add("Mérimée");
        oneAuthor = "Quinet";
        lstAuthorName.add(oneAuthor);
        lstAuthorName.add("Sainte-Beuve");
        lstAuthorName.add("Dupin");
        lstAuthorName.add("Sue");
        lstAuthorName.add("Tocqueville");
        lstAuthorName.add("Arvers");
        lstAuthorName.add("Lassailly");
        otherAuthor = "Nisard";
        lstAuthorName.add(otherAuthor);
        lstAuthorName.add("Souvestre");
        lstAuthorName.add("Bertrand");
        lstAuthorName.add("Nerval");
        lstAuthorName.add("Proudhon");
        lstAuthorName.add("Borel");
        lastAuthor = "Forneret";
        lstAuthorName.add(lastAuthor);    
    }

    @After
    public void reinitData(){
        datasourcesManager = null;
    }

    /**
     * Test creation and existence checking of sources.
     * */
    @Test
    public void testCreateNewSource(){
        //Test none created source does not exist
        Assert.assertFalse("The none created source ["+sourceName+"] is declared existing.", datasourcesManager.datasourceExist(sourceName)); 

        //create new source
        Assert.assertTrue("The source ["+sourceName+"] has not been created.", datasourcesManager.createDatasource(sourceName)); 

        //test created source exists
        Assert.assertTrue("The created source ["+sourceName+"] is declared not existing.", datasourcesManager.datasourceExist(sourceName)); 
    }

    /**
     * Test different cases to add data.
     * */
    @Test
    public void testAddData(){

        String newSourceName = this.sourceName+"-2";

        //test add data in none existing source
        Assert.assertFalse("A data is indicated added in none existing source", datasourcesManager.add(newSourceName, oneAuthor, oneAuthor+SUFFIX_VALUE));

        //test add data in existing source
        datasourcesManager.createDatasource(newSourceName);
        for (String authorName : lstAuthorName) {
            Assert.assertTrue ("A data has not been inserted in existing source.", datasourcesManager.add(newSourceName, authorName, authorName+SUFFIX_VALUE));
        }

        //test add data already existing in existing source
        for (String authorName : lstAuthorName) {
            Assert.assertFalse("A data already inserted has been inserted again.", datasourcesManager.add(newSourceName, authorName, authorName+SUFFIX_VALUE));
        }
    }

    /**
     * Test different cases to get data.
     * */
    @Test
    public void testGetData(){
        String newSourceName = this.sourceName+"-3";

        //get data in none existing source
        Assert.assertFalse("A data is indicated get in none existing source", datasourcesManager.get(newSourceName, oneAuthor) != null);

        //get data in existing but empty source
        datasourcesManager.createDatasource(newSourceName);
        Assert.assertFalse("A data is indicated get in empty source", datasourcesManager.get(newSourceName, oneAuthor) != null);

        //get inexisting data in existing not empty source
        loadSource(newSourceName, lstAuthorName);
        Assert.assertFalse("An data not inserted is indicated get in source", datasourcesManager.get(newSourceName, "InexistingAuthor") != null);

        //get existing data in good source
        for (String authorName : lstAuthorName) {
            Assert.assertTrue("An inserted data has not been found in source.", datasourcesManager.get(newSourceName, authorName) != null);
        }

        //get existing data in bad empty source
        String newEmptySourceName = this.sourceName+"-4";
        datasourcesManager.createDatasource(newEmptySourceName);
        for (String authorName : lstAuthorName) {
            Assert.assertFalse("An inserted data has not been found in source.", datasourcesManager.get(newEmptySourceName, authorName) != null);
        }
    }

    /**
     * Test different cases to update data.
     * */
    @Test
    public void testUpdateData(){
        String newSourceName = this.sourceName+"-5";
        //update data in none existing source
        Assert.assertFalse("A data is indicated updated in none existing source", datasourcesManager.update(newSourceName, oneAuthor, "NEW VALUE"));

        //update data in existing but empty source
        datasourcesManager.createDatasource(newSourceName);
        Assert.assertFalse("A data is indicated updated in empty source", datasourcesManager.update(newSourceName, oneAuthor, "NEW VALUE"));

        //update inexisting data in existing not empty source
        loadSource(newSourceName, lstAuthorName);
        Assert.assertFalse("An inexisting data is indicated updated in source", datasourcesManager.update(newSourceName, "InexistingAuthor", "NEW VALUE"));


        //update existing data in good existing source
        final String NEW_SUFFIX_VALUE = "-new-value";
        for (String authorName : lstAuthorName) {
            String expectedValue = authorName+NEW_SUFFIX_VALUE;
            Assert.assertTrue("A data has not been updated in source.", datasourcesManager.update(newSourceName, authorName, expectedValue));

            String newValue = datasourcesManager.get(newSourceName, authorName);
            String failMsg = "An updated data ["+authorName+"] has not the expected value. Expected =["+expectedValue+"]. Found value = ["+newValue+"]";
            Assert.assertTrue(failMsg, expectedValue.equals(newValue));
        }

        //update existing data in not matching existing source
        String newEmptySourceName = this.sourceName+"-6";
        datasourcesManager.createDatasource(newEmptySourceName);
        Assert.assertFalse("A data has been updated in not matching source.", datasourcesManager.update(newEmptySourceName, this.oneAuthor, "NEW VALUE"));
    }

    /**
     * Test different cases to remove data.
     * */
    @Test
    public void testRemoveData(){
        String newSourceName = this.sourceName+"-7";

        //remove data in none existing source
        Assert.assertFalse("A data is indicated removed in none existing source", datasourcesManager.remove(newSourceName, oneAuthor));


        //remove data in empty source
        datasourcesManager.createDatasource(newSourceName);
        Assert.assertFalse("A data is indicated removed in empty source", datasourcesManager.remove(newSourceName, oneAuthor));

        //remove inexisting data in existing source
        loadSource(newSourceName, lstAuthorName);
        Assert.assertFalse("An inexisting data is indicated removed in source", datasourcesManager.remove(newSourceName, "InexistingAuthor"));

        //remove existing data in good existing source
        for (String authorName : lstAuthorName) {
            Assert.assertTrue("A data has not been removed in source.", datasourcesManager.remove(newSourceName, authorName));
            Assert.assertTrue("A removed data ["+authorName+"] is indicated found.", datasourcesManager.get(newSourceName, authorName) == null);
        }

        //remove existing data in bad existing source
        String newEmptySourceName = this.sourceName+"-8";
        loadSource(newSourceName, lstAuthorName);//Load again the first source and attempt removing in other source
        datasourcesManager.createDatasource(newEmptySourceName);
        Assert.assertFalse("A data has been removed in not matching source.", datasourcesManager.remove(newEmptySourceName, this.oneAuthor));
    }

    /**
     * Fill a defined created source, with list of key.
     * */
    private void loadSource(String sourceName, List<String> listKey){
        for (String authorName : listKey) {
            datasourcesManager.add(sourceName, authorName, authorName+SUFFIX_VALUE);
        }
    }
}
