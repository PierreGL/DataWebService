package org.node.processing.test;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pgl.node.processing.Node;
import org.pgl.node.processing.NodeImpl;
import org.pgl.node.processing.NodeProcess;
import org.pgl.node.processing.NodeProcessImpl;

/**
 * Test unit to nodeProcess operations.
 * */
public class NodeProcessTest {

	private NodeProcess<Integer, Integer> nodeProcess;

	private Node<Integer, Integer> nodeRoot;
	private int[] arrayInt;

	private int rootKey;
	private int firstKey;
	private int oneKey;
	private int lastKey;

	@Before
	public void prepareAnyTest(){
		this.nodeProcess = new NodeProcessImpl<Integer, Integer>();

		createTree();

		this.rootKey = this.arrayInt[0];
		this.firstKey = this.arrayInt[1];
		this.oneKey = this.arrayInt[this.arrayInt.length/2];
		this.lastKey = this.arrayInt[this.arrayInt.length-1];
	}

	/**
	 * Create a tree with 50 random int, and store these values in a separated array, to use as node key in different tests.
	 * */
	public void createTree(){        
		Random random = new Random ();
		int rootKey = random.nextInt(100);
		this.nodeRoot = new NodeImpl<Integer, Integer>(rootKey);
		this.arrayInt = new int[50];

		this.arrayInt[0] = rootKey;
		
		for(int i = 1; i<50;i++){
			int newKey = random.nextInt(100);
			this.arrayInt[i] = newKey;

			Node<Integer, Integer> newNode = new NodeImpl<Integer, Integer>(newKey);
			this.nodeRoot.addChild(newNode);
		}
	}
	
	/**
	 * Get different representative nodes.
	 * */
	@Test
	public void testGetNode(){
		Node<Integer, Integer> nodeFirst = this.nodeProcess.getNodeByKey(this.nodeRoot, this.firstKey);
		Node<Integer, Integer> nodeAny = this.nodeProcess.getNodeByKey(this.nodeRoot, this.oneKey);
		Node<Integer, Integer> nodeLast = this.nodeProcess.getNodeByKey(this.nodeRoot, this.lastKey);
		Node<Integer, Integer> nodeRoot = this.nodeProcess.getNodeByKey(this.nodeRoot, this.rootKey);

		Assert.assertTrue("The node ["+this.firstKey+"] has not been found", nodeFirst != null && nodeFirst.getKey() == this.firstKey); 
		Assert.assertTrue("The node ["+this.oneKey+"] has not been found", nodeAny != null && nodeAny.getKey() == this.oneKey); 
		Assert.assertTrue("The node ["+this.lastKey+"] has not been found", nodeLast != null && nodeLast.getKey() == this.lastKey);
		Assert.assertTrue("The node ["+this.rootKey+"] has not been found", nodeRoot != null && nodeRoot.getKey() == this.rootKey);
	}

	/**
	 * Updates different representatives node and check that update has really been performed.
	 * */
	@Test
	public void testUpdateNode(){		
		//Update different node by with one new value.
		int newOneValue = 1000;
		Node<Integer, Integer> oneNode = new NodeImpl<Integer, Integer>(this.oneKey, newOneValue);
		int newFirstValue = 2000;
		Node<Integer, Integer> firstNode = new NodeImpl<Integer, Integer>(this.firstKey, newFirstValue);
		int newLastValue = 3000;
		Node<Integer, Integer> lastNode = new NodeImpl<Integer, Integer>(this.lastKey, newLastValue);
		int newRootValue = 4000;
		Node<Integer, Integer> rootNode = new NodeImpl<Integer, Integer>(this.rootKey, newRootValue);
		
		//Assert that any update succeed
		Assert.assertTrue("The node ["+this.oneKey+"] has not been updated", this.nodeProcess.updateNode(this.nodeRoot, oneNode)); 
		Assert.assertTrue("The node ["+this.firstKey+"] has not been updated", this.nodeProcess.updateNode(this.nodeRoot, firstNode)); 
		Assert.assertTrue("The node ["+this.lastKey+"] has not been updated", this.nodeProcess.updateNode(this.nodeRoot, lastNode)); 
		Assert.assertTrue("The node ["+this.rootKey+"] has not been updated", this.nodeProcess.updateNode(this.nodeRoot, rootNode)); 
		
		Node<Integer, Integer> oneNodeUpdated = this.nodeProcess.getNodeByKey(this.nodeRoot, this.oneKey);
		Node<Integer, Integer> firstNodeUpdated = this.nodeProcess.getNodeByKey(this.nodeRoot, this.firstKey);
		Node<Integer, Integer> lastNodeUpdated = this.nodeProcess.getNodeByKey(this.nodeRoot, this.lastKey);
		Node<Integer, Integer> rootNodeUpdated = this.nodeProcess.getNodeByKey(this.nodeRoot, this.rootKey);

		//Assert that any node has really the new value
		Assert.assertTrue("The node ["+this.oneKey+"] has not the new value.", oneNodeUpdated.getEntity() == newOneValue); 
		Assert.assertTrue("The node ["+this.firstKey+"] has not the new value.", firstNodeUpdated.getEntity() == newFirstValue); 
		Assert.assertTrue("The node ["+this.lastKey+"] has not the new value.", lastNodeUpdated.getEntity() == newLastValue); 
		Assert.assertTrue("The node ["+this.rootKey+"] has not the new value.", rootNodeUpdated.getEntity() == newRootValue); 
	}

	/**
	 * Removes different nodes in a tree. Then try to get the matching nodes to assure that they are removed.
	 * */
	@Test
	public void testRemoveGetNode(){

		boolean firstIsRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.firstKey);
		boolean anyIsRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.oneKey);
		boolean lastIsRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.lastKey);
		boolean rootIsRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.rootKey);

		//If the nodes has been removed the return value is true
		Assert.assertTrue("The first node ["+this.firstKey+"] has not been removed", firstIsRemoved); 
		Assert.assertTrue("The any node ["+this.oneKey+"] has not been removed", anyIsRemoved); 
		Assert.assertTrue("The last node ["+this.lastKey+"] has not been removed", lastIsRemoved); 
		Assert.assertTrue("The root node ["+this.rootKey+"] has not been removed", rootIsRemoved); 

		//If a nodes has been removed no node returned, then the value is null.
		Node<Integer, Integer> nodeFirst = this.nodeProcess.getNodeByKey(this.nodeRoot, this.firstKey);
		Node<Integer, Integer> nodeAny = this.nodeProcess.getNodeByKey(this.nodeRoot, this.oneKey);
		Node<Integer, Integer> nodeLast = this.nodeProcess.getNodeByKey(this.nodeRoot, this.lastKey);
		Node<Integer, Integer> nodeRoot = this.nodeProcess.getNodeByKey(this.nodeRoot, this.rootKey);

		Assert.assertTrue("The first node ["+this.firstKey+"] exists again", nodeFirst == null); 
		Assert.assertTrue("The any node ["+this.oneKey+"] exists again", nodeAny == null); 
		Assert.assertTrue("The last node ["+this.lastKey+"] exists again", nodeLast == null); 
		Assert.assertTrue("The root node ["+this.rootKey+"] exists again", nodeRoot == null); 
	}

}
