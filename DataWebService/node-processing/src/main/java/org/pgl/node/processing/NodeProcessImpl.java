package org.pgl.node.processing;

/**
 * Default implementation to NodeProcess.
 * */
public class NodeProcessImpl<K extends Comparable<K>, E> implements NodeProcess<K, E> {

    @Override
    public Node<K, E> getNodeByKey(Node<K, E> root, K key) {

        Node<K, E> result = null;

        K rootKey = root.getKey();

        if(key != null && rootKey != null){
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
        }

        return result;
    }

    @Override
    public Node<K, E> removeNodeByKey(Node<K, E> current, K keyToRemove) {
        Node<K, E> result = null;
        
        Node<K, E> leftChild = current.getLeftChild();
        Node<K, E> rightChild = current.getRightChild();
        K currentKey = current.getKey();
        
        if(keyToRemove.compareTo(currentKey) == 0){//Case : the root node has to be removed. The root is replaced.
            result = removingRoot(current);
        }else{
            if(keyToRemove.compareTo(currentKey) < 0){//If node to remove exists : it is in left branch
                if(leftChild != null){
                    if(keyToRemove.equals(leftChild.getKey())){
                        removingLeft(current, leftChild);
                        result = current;
                    }else{
                        result = removeNodeByKey(leftChild, keyToRemove);
                    }
                }//else inexisting child the key has not been found

            }else if(keyToRemove.compareTo(currentKey) > 0){//If node to remove exists : it is in right branch
                if(rightChild != null){
                    if(keyToRemove.equals(rightChild.getKey())){
                        removingRight(current, rightChild);
                        result = current;
                    }else{
                        result = removeNodeByKey(rightChild, keyToRemove);
                    }
                }//else inexisting child the key has not been found        }
            }
            
            //If null node to remove not found : return null recursively to indicate the impossible removing.
            //If not null return the current node to get the root.
            if(result != null){
                result = current;
            }
        }

        return result;
    }
    
    @Override
    public boolean updateNode(Node<K, E> root, Node<K, E> nodeToUpdate) {
        boolean result = false;

        K rootKey = root.getKey();
        K keyToUpdate = nodeToUpdate.getKey();

        if(keyToUpdate.compareTo(rootKey) > 0){
            Node<K, E> rightNode = root.getRightChild();
            if(rightNode != null){
                result = updateNode(rightNode, nodeToUpdate);
            }
        }else if(keyToUpdate.compareTo(rootKey) <0){
            Node<K, E> leftNode = root.getLeftChild();
            if(leftNode != null){
                result = updateNode(leftNode, nodeToUpdate);
            }
        }else{//The node to update is root node.
            root.setEntity(nodeToUpdate.getEntity());
            result = true;
        }

        return result;        
    }

    /**
     * Specific method to remove the root node. If the node has one or several children it is replaced by it 
     * else, the root node is returned with null key to indicate its deletion.
     * */
    private Node<K, E> removingRoot(Node<K, E> root){
        
        Node<K, E> newRoot;// = null;
        
        Node<K, E> leftChildRoot = root.getLeftChild();
        Node<K, E> rightChildRoot = root.getRightChild();

        if(leftChildRoot == null && rightChildRoot == null){//If the root has no children the tree is destroyed, a root without child or key is returned.
            root.destroyKey();
            newRoot = root;
        }else if(leftChildRoot == null){//If the root has only a right child it is the new root
            newRoot = rightChildRoot;
        }else if(rightChildRoot == null){//If the root has only a left child it is the new root
            newRoot = leftChildRoot;
        }else{//If the root has two child, one is arbitrary choiced and other is included in the new root.
            newRoot = leftChildRoot;
            newRoot.addChild(rightChildRoot);
        }

        return newRoot;
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
