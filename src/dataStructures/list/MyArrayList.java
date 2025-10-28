package dataStructures.list;

public class MyArrayList<T> implements list<T> {
    private T[] nodes;
    private int current , size , maxsize;

    public MyArrayList(int n){
        size = 0;
        maxsize = n;
        current = -1;
        nodes = (T[]) new Object[n];
    }

    public void findFirst(){
        if(!empty())
            current = 0;
    }
    public void findNext(){
        if(!last())
            current++;
    }
    public void update(T val){
        if(!empty())
            nodes[current] = val;
    }
    public T retrieve(){
        return !empty() ? nodes[current] : null;
    }
    public void insert(T val){
        if(!full()){
            for(int i = size - 1 ; i > current ; i--)
                nodes[i+1] = nodes[i];
        }
        current++;
        nodes[current] = val;
        size++;
    }
    public void remove(){
        if(!empty()){
            for(int i = current + 1 ; i < size ; i++)
                nodes[i-1] = nodes[i];
        }
        size--;
        if(empty())     //handling deleting last, having an empty list
            current = -1;
        else if(current == size)
            current = 0;
    }
    public boolean full(){
        return size == maxsize;
    }
    public boolean empty(){
        return size == 0;
    }
    public boolean last(){
        return !empty() && current == size - 1;
    }

    public void display(){
        if(size == 0)
            return;
        T t;
        for(int i = 0 ; i < size ; i++) {
            t = nodes[i];
            System.out.println(t);
        }
    }
}

