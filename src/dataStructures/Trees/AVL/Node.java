package dataStructures.Trees.AVL;

public class Node<T> {
        public Key key;
        public T val;
        public Node<T> left , right;
        public int height;

        Node(Key k, T v) {
            this.key = k;
            this.val = v;
            this.height = 0;
            left = right = null;
        }
    }

