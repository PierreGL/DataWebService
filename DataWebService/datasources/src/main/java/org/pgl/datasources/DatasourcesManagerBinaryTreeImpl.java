package org.pgl.datasources;

import java.util.HashMap;
import java.util.Map;

import org.pgl.node.processing.Node;
import org.pgl.node.processing.NodeImpl;
import org.pgl.node.processing.NodeProcess;
import org.pgl.node.processing.NodeProcessImpl;

/**
 * This implementation of DatasourcesManager uses BinaryTree to store the key/value sets.
 * */
public class DatasourcesManagerBinaryTreeImpl implements DatasourcesManager {

	Map<String, Node<String, String>> mapBinaryTree = new HashMap<String, Node<String, String>>();
	
	NodeProcess<String, String> nodeProcess = new NodeProcessImpl<String, String>();
	
	@Override
	public boolean datasourceExist(String name) {		
		boolean result = mapBinaryTree.containsKey(name);		
		return result;
	}

	@Override
	public boolean createDatasource(String name) {
		boolean result = false;
		if(!mapBinaryTree.containsKey(name)){
			mapBinaryTree.put(name, null);//Create empty tree
			result = true;
		}//else datasource already exists
		
		return result;
	}

	@Override
	public String get(String name, String key) {
		
		String result = null;
		
		//If tree exist and have a root node it is possible to search key
		if(mapBinaryTree.containsKey(name)){
			Node<String, String> root = mapBinaryTree.get(name);
			if(root != null){
				Node<String, String> nodeResult = nodeProcess.getNodeByKey(root, key);
				if(nodeResult != null){
					result = nodeResult.getEntity();
				}
			}
		}
		
		return result;
	}

	@Override
	public boolean add(String name, String key, String value) {
		boolean result = false;
		
		if(mapBinaryTree.containsKey(name)){
			Node<String, String> root = mapBinaryTree.get(name);
			Node<String, String> newNode = new NodeImpl<String, String>(key, value);
			if(root == null){//The datasource exist but is empty. The node is root node
				mapBinaryTree.put(name, newNode);
			}else{
				result = root.addChild(newNode);//If the node already exists, addChild will return false.
			}
		}
		
		return result;
	}

	@Override
	public boolean remove(String name, String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String name, String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

}