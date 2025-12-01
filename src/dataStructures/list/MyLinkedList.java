package dataStructures.list;

public class MyLinkedList<T> implements list<T> {
    private Node<T> head , current;
    private int size;


    public MyLinkedList(){
        head = current = null;
        size = 0;
    }
    public void findFirst(){
        if(!empty())
            current = head;
    }
    public void findNext(){
        if(!empty() && !last())
            current = current.next;
    }
    public void update(T val){
        if(!empty())
            current.data = val;
    }
    public T retrieve(){
        return !empty() ? (T) current.data : null;
    }
    public void insert(T val){
        if(full()) return;
        Node<T> n = new Node<>(val);
        if(empty()) {
            current = head = n;
        }else {
            n.next = current.next;
            current.next = n;
            current = n;
        }
        size++;

    }
    public void remove(){
        if(!empty()) {
            if(current == head){
                head = head.next;
            }else {
                Node<T> n = head;
                while(n.next != current){
                    n = n.next;
                }
                n.next = current.next;
            }
            current = (head == null) ? null : (current != null && current.next != null ? current.next : head);
            size--;
        }
    }
    public boolean full(){
        return false;
    }
    public boolean empty(){
        return head == null;
    }
    public boolean last(){
        return !empty() && current.next == null;
    }

    @Override
    public void display() {
        if(head == null)
            return;
        Node n = head;
        while(n != null) {
            System.out.println(n.data);
            n = n.next;
        }
    }
    public void wipe(){
        current = head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }
}
