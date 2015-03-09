package org.pgl.node.processing;

/**
 * Default implementation to NodeProcess.
 * */
public class NodeProcessImpl<K extends Comparable<K>, E> implements NodeProcess<K, E> {

    @Override
    public Node<K, E> getNodeByKey(Node<K, E> root, K key) {

        Node<K, E> result = null;

        K rootKey = root.getKey();

        if(key.compareTo(rootKey) > 0){
            Node<K, E> rightNode = root.getRightChild();
            if(rightNode != null){
                result = getNodeByKey(rightNode, key);
            }
        }else if(key.compareTo(rootKey) <0){
            Node<K, E> leftNode = root.getLeftChild();
            if(leftNode != null){
                result = getNodeByKey(leftNode, key);
            }
        }else{//value == rootValue
            result = root;
        }

        return result;
    }

    @Override
    public boolean removeNodeByKey(Node<K, E> current, K keyToRemove) {
        boolean result = false;
        
        Node<K, E> leftChild = current.getLeftChild();
        Node<K, E> rightChild = current.getRightChild();
        K currentKey = current.getKey();

        //TODO treat case : root node to remove
        
        if(keyToRemove.compareTo(currentKey) < 0){
            if(leftChild != null){
                if(keyToRemove.equals(leftChild.getKey())){
                    removingLeft(current, leftChild);
                    result = true;
                }else{
                    result = removeNodeByKey(leftChild, keyToRemove);
                }
            }//else inexisting child the key has not been found

        }else if(keyToRemove.compareTo(currentKey) > 0){
            if(rightChild != null){
                if(keyToRemove.equals(rightChild.getKey())){
                    removingRight(current, rightChild);
                    result = true;
                }else{
                    result = removeNodeByKey(rightChild, keyToRemove);
                }
            }//else inexisting child the key has not been found
        }else{
        	current = null;
        }
            
        return result;
    }
    
    @Override
    public boolean updateNode(Node<K, E> root, Node<K, E> node) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Remove the node when it is left child, and if necessary, move the child of removed node.
     * */
    private void removingLeft(Node<K, E> parent, Node<K, E> nodeToRemove){
        Node<K, E> leftChildNodeToRemove = nodeToRemove.getLeftChild();
        Node<K, E> rightChildNodeToRemove = nodeToRemove.getRightChild();

        if(leftChildNodeToRemove == null && rightChildNodeToRemove == null){
            parent.setLeftChild(null);
        }else if(leftChildNodeToRemove == null){
            parent.setLeftChild(rightChildNodeToRemove);
        }else if(rightChildNodeToRemove == null){
            parent.setLeftChild(leftChildNodeToRemove);
        }else{
            parent.setLeftChild(leftChildNodeToRemove);
            parent.addChild(rightChildNodeToRemove);
        }
    }
    
    /**
     * Remove the node when it is right child, and if necessary, move the child of removed node.
     * */
    private void removingRight(Node<K, E> parent, Node<K, E> nodeToRemove){
        Node<K, E> leftChildNodeToRemove = nodeToRemove.getLeftChild();
        Node<K, E> rightChildNodeToRemove = nodeToRemove.getRightChild();

        if(leftChildNodeToRemove == null && rightChildNodeToRemove == null){
            parent.setRightChild(null);
        }else if(leftChildNodeToRemove == null){
            parent.setRightChild(rightChildNodeToRemove);
        }else if(rightChildNodeToRemove == null){
            parent.setRightChild(leftChildNodeToRemove);
        }else{
            parent.setRightChild(leftChildNodeToRemove);
            parent.addChild(rightChildNodeToRemove);
        }
    }
}
