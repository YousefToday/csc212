package dataStructures.queues;

public class ArrayQueue<T> implements Queue<T>{ //no node class

    private int head , tail;
    private T[] nodes;
    private int size;
    private int max;

    public ArrayQueue(int n){
        max = n;
        size = 0;
        head = tail = 0;
        nodes = (T[]) new Object[max];
    }

    public void enqueue(Object e){
        nodes[tail] = (T) e;
        tail = (tail + 1) % max;
        size++;
    }
    public T serve(){
        T data = nodes[head];
        head = (head + 1 )%max;
        size--;
        return data;
    }


    public boolean full(){
        return size == max;
    }
    public int length(){
        return size;
    }
    public T enquiry(){
        return nodes[head];
    }
}

