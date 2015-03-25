package org.pgl.node.processing;

/**
 * Defined process which can be apply on tree node.
 * */
public interface NodeProcess<K extends Comparable<K>, E> {

    /**
     * Provides the Node in a tree matching with key.
     * 
     * @param root The node root of tree.
     * @param key The key to find.
     * 
     * @return return the Node if exist, else return null.
     * */
    Node<K, E> getNodeByKey(Node<K, E> root, K key);

    /**
     * Removes the Node defined by key in a tree.
     * 
     * @param root The root of tree.
     * @param keyToRemove The key of node to remove.    
     * 
     * @return Return the root node updated by removing, if none node removes return null. If all tree removed returns Node with children and key null.
     **/
    Node<K, E> removeNodeByKey(Node<K, E> root, K keyToRemove);

    /**
     * Update the defined node in the tree of the root node.
     * 
     * @param root Root node while the tree must be updated.
     * @param nodeToUpdate Node need to update.
     * 
     * @return True if update made, false if no update.
     * 
     * */
    boolean updateNode(Node<K, E> root, Node<K, E> nodeToUpdate);
}
