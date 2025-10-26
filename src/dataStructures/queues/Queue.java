package dataStructures.queues;

public interface Queue<T> {

    void enqueue(T data);
    T serve();
    boolean full();
    int length();
}

