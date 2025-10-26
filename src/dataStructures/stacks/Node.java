package dataStructures.stacks;

public class Node<T> {
    public Node next;
    public T data;

    public Node(){
        data = null;
        next = null;
    }
    public Node(T e){
        data = e;
        next = null;
    }
}

