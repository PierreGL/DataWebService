package org.pgl.datasources;

/**
 * Object to manage set of dataSources based on key value principles.
 * */
public interface DatasourcesManager {
	
	/**
	 * Check if a datasource with defined name already exist.
	 * 
	 * @param name Datasource name.
	 * @return True if exist, false if not.
	 * */
	boolean datasourceExist(String name);

	/**
	 * Create a new datasource with defined name. If the name already exist, no datasource is created.
	 * 
	 * @param name Datasource name.
	 * @return True if data source created, false if not created.
	 * */
	boolean createDatasource(String name);
	
	/**
	 * Get a value in defined datasource.
	 * 
	 * @param name Name of datasource.
	 * @param key Key of value to get.
	 * @return The value searched.If datasource does not exist or if the key does not exist, return null.
	 * */
	String get(String name, String key);
	
	/**
	 * Add a value in defined datasource. If already exists, does not add.
	 * 
	 * @param name Name of datasource.
	 * @param key Key of value to add.
	 * @param value Value to add.
	 * @return True if value added, false if datasource does not exist or if value already exist.
	 * */
	boolean add(String name, String key, String value);
	
	/**
	 * Remove a value in defined datasource. If the datasource or the key does not exist, nothing is removed.
	 * 
	 * @param name Name of datasource.
	 * @param key Key of value to remove.
	 * @return True if value removed, false if not.
	 * */
	boolean remove(String name, String key);
	
	/**
	 * Update value in defined source. If the datasource or the key does not exist, nothing is updated.
	 * 
	 * @param name Name of datasource.
	 * @param key Key of value to update.
	 * @param value New value.
	 * @return True if value updated, false if not.
	 * 
	 * */
	boolean update(String name, String key, String value);

}
