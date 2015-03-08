package org.pgl.node.processing;

/**
 * This interface represents a Node.
 * */
public interface Node<K extends Comparable<K>, E> {
    
    /**
     * Get the key of this node.
     * */
    K getKey();
    
    /**
     * Get the entity of this node.
     * */
    E getEntity();
    
    /**
     * Get the parent of this node.
     * 
     * @param Provides parent, if this node is a root node return null.
     * */
    Node<K, E> getParent();
    
    /**
     * Set and replace if exist the parent of this node.
     * 
     * @param parent New parent oF this node.
     * */
    void setParent(Node<K, E> parent);
    
    
    /**
     * Get the right node of this node.
     * 
     * @return The right node or null if not exist.
     * */
    Node<K, E> getRightChild();
    
    /**
     * Set and replace if exist the right node of this node.
     * 
     * @param node The node to set.
     * */
    void setRightChild(Node<K, E> node);
    
    /**
     * Get the left node of this node.
     * 
     * @return The left node or null if not exist.
     * */
    Node<K, E> getLeftChild();
    
    /**
     * Set and replace if exist the left node of this node.
     * 
     * @param node The node to set.
     * */
    void setLeftChild(Node<K, E> node);
    
    /**
     * Add and place the defined node in tree of this node.
     * If a child with same key exist, it is not add.
     * 
     * @param node The node to add.
     * 
     * @return true if the child has been added, false if not.
     * */
    boolean addChild(Node<K, E> node);    
}
