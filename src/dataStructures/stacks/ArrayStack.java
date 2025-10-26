package dataStructures.stacks;

public class ArrayStack<T> implements Stack<T> {
    private int top;
    private T[] nodes;
    private int maxsize;

    public ArrayStack(int n){
        top = -1;
        maxsize = n;
        nodes = (T[]) new Object[n];
    }

    public void push(T e){
        if(!full())
            nodes[++top] = e;
        else{
            System.out.println("Stack is full");
        }

    }
    public T pop(){
        if(!empty())
            return nodes[top--];
        else{
            System.out.println("Stack is empty");
        }
        return null;
    }
    public boolean full(){
        return top == maxsize -1;
    }
    public boolean empty(){
        return top == -1;
    }
}
