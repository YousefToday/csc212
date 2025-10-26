package dataStructures.list;

public class Node<T> {
    Node next;
    T data;

    public Node(){
        next = null;
        data = null;
    }
    public Node(T val){
        next = null;
        data = val;
    }
}
