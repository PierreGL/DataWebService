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
        Node<Integer, Integer> firstNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.firstKey);
        Node<Integer, Integer> oneNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.oneKey);
        Node<Integer, Integer> lastNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.lastKey);
        Node<Integer, Integer> rootNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.rootKey);

        Assert.assertTrue("The node ["+this.firstKey+"] has not been found", firstNode != null && firstNode.getKey() == this.firstKey); 
        Assert.assertTrue("The node ["+this.oneKey+"] has not been found", oneNode != null && oneNode.getKey() == this.oneKey); 
        Assert.assertTrue("The node ["+this.lastKey+"] has not been found", lastNode != null && lastNode.getKey() == this.lastKey);
        Assert.assertTrue("The node ["+this.rootKey+"] has not been found", rootNode != null && rootNode.getKey() == this.rootKey);
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
        Assert.assertTrue("The node ["+this.oneKey+"] has not the new value.", oneNodeUpdated.getEntity().equals(newOneValue)); 
        Assert.assertTrue("The node ["+this.firstKey+"] has not the new value.", firstNodeUpdated.getEntity().equals(newFirstValue)); 
        Assert.assertTrue("The node ["+this.lastKey+"] has not the new value.", lastNodeUpdated.getEntity().equals(newLastValue)); 
        Assert.assertTrue("The node ["+this.rootKey+"] has not the new value.", rootNodeUpdated.getEntity().equals(newRootValue)); 
    }

    /**
     * Removes different nodes in a tree. Then try to get the matching nodes to assure that they are removed.
     * */
    @Test
    public void testRemoveGetNode(){

        Node<Integer, Integer> rootFirstRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.firstKey);
        Node<Integer, Integer> rootOneRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.oneKey);
        Node<Integer, Integer> rootLastRemoved = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.lastKey);

        //If the nodes has been removed the return node is not null
        Assert.assertTrue("The first node ["+this.firstKey+"] has not been removed", rootFirstRemoved != null); 
        Assert.assertTrue("The node ["+this.oneKey+"] has not been removed", rootOneRemoved != null); 
        Assert.assertTrue("The last node ["+this.lastKey+"] has not been removed", rootLastRemoved != null); 

        //If a nodes has been removed no node returned, then the value is null.
        Node<Integer, Integer> firstNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.firstKey);
        Node<Integer, Integer> oneNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.oneKey);
        Node<Integer, Integer> lastNode = this.nodeProcess.getNodeByKey(this.nodeRoot, this.lastKey);

        Assert.assertTrue("The first node ["+this.firstKey+"] exists again", firstNode == null); 
        Assert.assertTrue("The node ["+this.oneKey+"] exists again", oneNode == null); 
        Assert.assertTrue("The last node ["+this.lastKey+"] exists again", lastNode == null); 
    }

    /**
     * Test case removing root node.
     * */
    @Test
    public void testRemoveRootNode(){

        //Test removing on complete nodeRoot with right child and left child :
        Node<Integer, Integer> rootReplaced = this.nodeProcess.removeNodeByKey(this.nodeRoot, this.rootKey);
        assertRootNodeRemoved(rootReplaced, this.rootKey);
        Node<Integer, Integer> rootNode = this.nodeProcess.getNodeByKey(rootReplaced, this.rootKey);
        Assert.assertTrue("The removed root node ["+this.rootKey+"] exists again", rootNode == null); 

        //Test removing root node without child / the root node returned must have a null key
        Integer newRootKey = 100;
        Node<Integer, Integer> nodeRootWithoutChild = new NodeImpl<Integer, Integer>(newRootKey, 1000);
        Node<Integer, Integer> rootDeleted = this.nodeProcess.removeNodeByKey(nodeRootWithoutChild, newRootKey);
        assertRootNodeRemoved(rootDeleted, newRootKey);
        Assert.assertTrue("The removed root node is not completely destroyed ["+newRootKey+"] exists again", rootDeleted.getKey() == null);

        //Test removing root node with on right child
        Integer newRootWithChildKey = 100;
        Node<Integer, Integer> nodeRootWithChild = new NodeImpl<Integer, Integer>(newRootWithChildKey, 1000);
        Integer newChildAloneKey = 200;
        Node<Integer, Integer> nodeChildAlone = new NodeImpl<Integer, Integer>(newChildAloneKey, 2000);
        nodeRootWithChild.addChild(nodeChildAlone);
        Node<Integer, Integer> rootWithChildReplaced = this.nodeProcess.removeNodeByKey(nodeRootWithChild, newRootWithChildKey);
        assertRootNodeRemoved(rootWithChildReplaced, newRootWithChildKey);
        Assert.assertTrue("The removed root node ["+newRootWithChildKey+"] has not been replaced by its only child ["+newChildAloneKey+"]", newChildAloneKey.equals(rootWithChildReplaced.getKey()));  
    }

    /**
     * After removing root node, check and assert that the node has been found and remove, check that the new root is not the same taht removed.
     * */
    private void assertRootNodeRemoved(Node<Integer, Integer> rootReplaced, Integer oldKey){
        Assert.assertTrue("The root node ["+oldKey+"] has not been removed", rootReplaced != null); 
        Assert.assertTrue("The root node ["+oldKey+"] has not been removed", rootReplaced.getKey() != oldKey); 
    }
}
