package org.pgl.node.processing;

/**
 * @author pguilletlhomat
 * */
public class NodeProcessImpl<K extends Comparable<K>, E> implements NodeProcess<K, E> {

    //TODO
//    @Override
//    public E findMax(Node<K, E> root) {
//
//        K rootValue = root.getKey();
//        K rightValueMax;
//        K leftValueMax;
//        Node<K, E> rightChild = root.getRightChild();
//        Node<K, E> leftChild = root.getLeftChild();
//
//        if(rightChild != null){
//            rightValueMax = findMax(rightChild);
//        }else{
//            rightValueMax = Integer.MIN_VALUE;
//        }
//
//        if(leftChild != null){
//            leftValueMax = findMax(leftChild);
//        }else{
//            leftValueMax = Integer.MIN_VALUE;
//        }
//
//        int valueMax = getMaxValue(rootValue, leftValueMax, rightValueMax);
//
//        return valueMax;
//    }

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
    public boolean removeNodeByKey(Node<K, E> current, K key) {
        boolean result = false;
        
        Node<K, E> leftChild = current.getLeftChild();
        Node<K, E> rightChild = current.getRightChild();
        K currentKey = current.getKey();

        //TODO treat case : root node to remove
        
        if(key.compareTo(currentKey) < 0){
            if(leftChild != null){
                if(leftChild.getKey() == key){
                    removingLeft(current, leftChild);
                    result = true;
                }else{
                    result = removeNodeByKey(leftChild, key);
                }
            }//else inexisting child the key has not been found

        }else if(key.compareTo(currentKey) > 0){
            if(rightChild != null){
                if(rightChild.getKey() == key){
                    removingRight(current, rightChild);
                    result = true;
                }else{
                    result = removeNodeByKey(rightChild, key);
                }
            }//else inexisting child the key has not been found
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

    //TODO
//    /**
//     * Compare 3 int values and return the max.
//     * */
//    private int getMaxValue(int a, int b, int c){
//        int result;
//        if(a > b){
//            if(a > c){
//                result = a;
//            }else{
//                result = c;
//            }
//        }else{
//            if(b > c){
//                result = b;
//            }else{
//                result = c;
//            }
//        }
//
//        return result;
//    }

}
