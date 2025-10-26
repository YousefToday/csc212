package dataStructures.stacks;

public class LinkedStack<T> implements Stack<T> {
    private Node<T> top;


    public LinkedStack(){
        top = null;
    }
    public void push(T e){
        Node<T> n = new Node<T>(e);
        n.next = top;
        top = n;
    }
    public T pop(){
        T x = top.data;
        top = top.next;
        return x;
    }

    public boolean full(){
        return false;
    }
    public boolean empty(){
        return top == null;
    }
}
