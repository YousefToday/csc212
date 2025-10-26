package dataStructures.stacks;

public interface Stack<T>{
    public T pop();
    public void push(T e);
    public boolean full();
    public boolean empty();
}

