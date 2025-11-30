package dataStructures.Trees.AVL;

public class AVLTree<T> {
    private Node<T> root;



    public T find(Key k) {
        Node<T> p = root;
        while (p != null) {
            int c = k.compare(p.key);
            if (c < 0)
                p = p.left;
            else if (c > 0)
                p = p.right;
            else return p.val;
        }
        return null;
    }
    private int height(Node<T> n) {
        return n == null ? -1 : n.height;
    }
    private void pull(Node<T> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }
    private int bf(Node<T> n) {
        return height(n.right) - height(n.left);
    }
    public boolean empty(){
        return root == null;
    }

    private Node<T> RR(Node<T> A) {
        Node<T> B = A.right;
        A.right = B.left;
        B.left = A;
        pull(A); //update the child before the parent
        pull(B);
        return B;
    }
    private Node<T> LL(Node<T> A) {
        Node<T> B = A.left;
        A.left = B.right;
        B.right = A;
        pull(A); //update the child before the parent
        pull(B);
        return B;
    }
    private Node<T> RL(Node<T> A){
        A.right = LL(A.right);
        return RR(A);
    }
    private Node<T> LR(Node<T> A){
        A.left = RR(A.left);
        return LL(A);
    }

    private Node<T> rebalance(Node<T> A) {
        pull(A);
        int bf = bf(A);
        if (bf == 2) {
            if(bf(A.right) < 0)
                A = RL(A);
            else
                A = RR(A);
        }else if (bf == -2) {
            if(bf(A.left) > 0)
                A = LR(A);
            else
                A = LL(A);
        }
        return A;
    }
    public boolean insert(Key k, T val) {
        boolean[] flag = {false};
        root = insert_aux(k , root , val , flag);
        return flag[0];
    }
    private Node<T> insert_aux(Key k, Node<T> rt,T val, boolean[] changed) {
        if (rt == null){
            rt = new Node<>(k, val);
            changed[0] = true;
        }else if(k.compare(rt.key) > 0)
            rt.right = insert_aux(k , rt.right ,val , changed);
        else if(k.compare(rt.key) < 0)
            rt.left = insert_aux(k , rt.left ,val , changed);
        else //duplicated
            return rt;
        return rebalance(rt); //only the pivot node triggers a rotation
    }

    public boolean remove(Key k) {
        boolean[] flag = {false};
        root = remove_aux(k , root , flag);
        return flag[0];
    }
    private Node<T> remove_aux(Key k, Node<T> rt, boolean[] removed) {
        if(rt == null)
            return null;
        else if(k.compare(rt.key) > 0)
            rt.right = remove_aux(k , rt.right , removed);
        else if(k.compare(rt.key) < 0)
            rt.left = remove_aux(k , rt.left , removed);
        else { //found
            if(rt.left != null && rt.right != null) {
                Node<T> minRight;
                minRight = min(rt.right);
                rt.key = minRight.key;
                rt.val = minRight.val;
                rt.right = remove_aux(minRight.key ,rt.right , removed);
            }else{
                removed[0] = true;
                if(rt.left == null)
                    return rt.right;
                return rt.left;
            }
        }
        return rebalance(rt);
    }
    public Node<T> min(Node<T> rt){
        if (rt == null) return null;
        while(rt.left != null)
            rt = rt.left;
        return rt;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void traverse(Order ord , Node<T> rt){
        if(rt == null)
            return;
        switch (ord){
            case preOrder:
                System.out.println(rt.val);
                traverse(ord , rt.left);
                traverse(ord, rt.right);
                break;
            case inOrder:
                traverse(ord , rt.left);
                System.out.println(rt.val);
                traverse(ord, rt.right);
                break;
            case postOrder:
                traverse(ord , rt.left);
                traverse(ord, rt.right);
                System.out.println(rt.val);
                break;
        }
    }
    public void traverse(String s){
        if(s.toLowerCase().equals("preorder"))
            traverse(Order.preOrder , root);
        else if(s.toLowerCase().equals("inorder"))
            traverse(Order.inOrder , root);
        else if(s.toLowerCase().equals("postorder"))
            traverse(Order.postOrder , root);
    }
    public void Select(Key lo, Key hi, dataStructures.list.MyLinkedList<T> out) {
        collectRangeRec(root, lo, hi, out);
    }
    private void collectRangeRec(Node<T> n, Key lo, Key hi, dataStructures.list.MyLinkedList<T> out) { //inclusive
        if (n == null) return;
        if (lo.compare(n.key) < 0)  collectRangeRec(n.left,  lo, hi, out);
        if (lo.compare(n.key) <= 0 && hi.compare(n.key) >= 0) out.insert(n.val);
        if (hi.compare(n.key) > 0)  collectRangeRec(n.right, lo, hi, out);
    }
}
