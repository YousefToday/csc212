package dataStructures.list;

public interface list<T> {
    void findFirst();
    void findNext();
    void insert(T val);
    void remove();
    void update(T val);
    T retrieve();
    boolean last();
    boolean empty();
    boolean full();
    void display();
}

