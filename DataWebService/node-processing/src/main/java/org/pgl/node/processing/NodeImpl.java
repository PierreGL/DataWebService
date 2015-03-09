package org.pgl.node.processing;

import java.io.Serializable;

/**
 * The default implementation of Node class.
 * */
public class NodeImpl<K extends Comparable<K> & Serializable, E extends Serializable> implements Node<K, E> {

    private K key;
    
    private E entity;
    
    private Node<K, E> parent;

    private Node<K, E> leftChild;
    private Node<K, E> rightChild;
    
    public NodeImpl(K key){
        this.key = key;
    }
    
    public NodeImpl(K key, E entity){
        this.key = key;
        this.entity = entity;
    }
    
    @Override
    public boolean addChild(Node<K, E> node){
        
        boolean result = false;
        
        if(node != null){
            K nodeKey = node.getKey();
            
            if(nodeKey != null && this.key != null){
                if(nodeKey.compareTo(this.key) > 0){
                    if(this.rightChild == null){
                        node.setParent(this);
                        this.rightChild = node;
                        result = true;
                    }else{
                        result = this.rightChild.addChild(node);
                    }
                }else if(nodeKey.compareTo(this.key) < 0){
                    if(this.leftChild == null){
                        node.setParent(this);
                        this.leftChild = node;
                        result = true;
                    }else{
                        result = this.leftChild.addChild(node);
                    }
                }else{
                    result = false;
                }
            }
        }
        
        return result;
    }
    
    @Override
    public void destroyKey() {
        if(rightChild == null && leftChild == null){
            this.key = null;
        }
    }
    
    @Override
    public K getKey() {
        return key;
    }
    
    @Override
    public E getEntity() {
        return entity;
    }
    
    @Override
    public void setEntity(E entity) {
        this.entity = entity;
    }

    @Override
    public Node<K, E> getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Node<K, E> parent) {
        this.parent = parent;        
    }
    
    @Override
    public Node<K, E> getRightChild() {
        return rightChild;
    }
    
    @Override
    public void setRightChild(Node<K, E> node) {
        this.rightChild = node;        
    }
    
    @Override
    public void setLeftChild(Node<K, E> node) {
        this.leftChild = node;        
    }

    @Override
    public Node<K, E> getLeftChild() {
        return leftChild;
    }

}
