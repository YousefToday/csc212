package dataStructures.queues;

public class LinkedQueue<T> implements Queue<T> {
    private Node head , tail;
    private int size;
    private int max;

    public LinkedQueue(int n){
        head = tail = null;
        size = 0;
        max = n;
    }
    public void enqueue(T data){
        //LQnode<T> node = new LQnode<T>(data);
        if(tail == null){
            head = tail = new Node<T>(data);
        }else{
            tail.next = new Node<T>(data);
            tail = tail.next;

        }
        size++;
    }
    public T serve(){
        T x = (T) head.data;
        head = head.next;
        size--;
        if(size == 0) tail = null;
        return x;
    }

    public int length(){
        return size;
    }

    public boolean full(){
        return size == max;
    }

    public T enquiry(){
        return (T) head.data;
    }
}

