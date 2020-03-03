
package testbstset;
import static java.lang.Integer.max;
import java.util.Arrays;
import java.util.ArrayList; 



public class BSTSet 
{
    private TNode root;
    
    /* Constructors */
    public BSTSet() {root = null;}
    
    // recurse to build left and right trees from sorted array
    // pick the middle point of each subarray to be the root of the subtree 
    // to minimize tree height
    public BSTSet(int[] input)
    {
        int[] uniqueinput = Arrays.stream(input).distinct().toArray();
        Arrays.sort(uniqueinput);
        root = sortedArray2Tree(uniqueinput, 0, uniqueinput.length-1);
    }
    private TNode sortedArray2Tree(int[] sortedinput, int start, int end)
    {
        //System.out.println(Arrays.toString(sortedinput));
        if (start > end)
            return null;
        int mid = (start + end) / 2;
        //System.out.println(mid);
        //System.out.println(end);
        TNode node = new TNode(sortedinput[mid], null, null);
        node.left = sortedArray2Tree(sortedinput, start, mid-1);
        node.right = sortedArray2Tree(sortedinput, mid+1, end);
        return node;
    }
    
    
    /* Returns true if integer v is an element of this BSTSet. It returns false otherwise. */
    public boolean isIn(int v)
    {
        return isIn(v, root);
    }
    private boolean isIn(int v, TNode n)
    {
        if (n == null)
            return false;
        if (v == n.element)
            return true;
        if (v < n.element)
            return isIn(v, n.left);
        return isIn(v, n.right);
    }
    
    
    /* Adds v to this BSTSet if v was not already an element of this BSTSet. It does nothing otherwise. */
    public void add(int v)
    {
        if (!isIn(v))
            root = add(v, root);
    }
    private TNode add(int v, TNode curr)
    {
        if (curr == null)
            curr = new TNode(v, null, null);
        else 
        {
            if (v < curr.element)
                curr.left = add(v, curr.left);
            else
                curr.right = add(v, curr.right);
        }
        return curr;
    }
    
    
    /* Removes v from this BSTSet if v was an element of this BSTSet and returns true. Returns false if v was not an element of this BSTSet. */
    public boolean remove(int v)
    {
        if (!isIn(v))
            return false;
        else {
            root = remove(v, root);
            return true;
        }
    }
    // helper function: find the smallest element (leftmost leaf) in a tree
    private TNode minNode(TNode node)
    {
        while(node.left != null) 
            node = node.left;
        return node;
    }
    
    private TNode remove(int v, TNode curr)
    {
        if (v < curr.element) 
            curr.left = remove(v, curr.left);
            
        else if (v > curr.element) 
            curr.right = remove(v, curr.right);
       
        else 
        {
            if (curr.left == null) 
                return curr.right;
            else if (curr.right == null)
                return curr.left;
            
            curr.element = minNode(curr.right).element;    
            curr.right = remove(curr.element, curr.right);
        }
        return curr;
    }
    
    
    /* Returns a new BSTSet which represents the union of this BSTSet and s. This method should not modify the input sets. */
    public BSTSet union(BSTSet s) //throws EmptyStackException
    {
        ArrayList<Integer> mergelist = new ArrayList<>();
        int[] list1 = this.inorderTraversal();
        int[] list2 = s.inorderTraversal();
        
        int m = list1.length, n = list2.length;       
        int i = 0, j = 0;
        
        // merge two lists
        while (i < m && j < n) 
        {
            if(list1[i] < list2[j]) 
                mergelist.add(list1[i++]);
            else if(list2[j] < list1[i])
                mergelist.add(list2[j++]);
            else 
            {
                mergelist.add(list1[i++]);
                j++;
            }               
        }
        
        while(i<m) 
            mergelist.add(list1[i++]);
        while(j<n)
            mergelist.add(list2[j++]);
        
        // turn ArrayList to array
        int[] mergearray = new int[mergelist.size()];
        for (int k=0; k<mergelist.size(); k++) 
            mergearray[k] = mergelist.get(k);
        
        // build new tree
        BSTSet uniontree = new BSTSet();
        uniontree.root = sortedArray2Tree(mergearray, 0, mergearray.length-1);
        
        return uniontree;
    }
    
    
    /* Returns a new BSTSet which represents the intersection of this BSTSet and s. This method should not modify the input sets. */
    public BSTSet intersection(BSTSet s) //throws EmptyStackException
    {
        int m = this.size();
        int n = s.size();       
        BSTSet treetree;
        BSTSet arraytree;
        
        if (m<n) 
        {
            arraytree = this;
            treetree = s;
        }
        else 
        {
            arraytree = s;
            treetree = this;
        }
       
        ArrayList<Integer> intersect = new ArrayList<>();
        int[] listarraytree = arraytree.inorderTraversal();
        for (int i=0; i<listarraytree.length; i++) {
            if (treetree.isIn(listarraytree[i]))
                intersect.add(listarraytree[i]);
        }
        
        // turn ArrayList to array
        int[] intersect_arr = new int[intersect.size()];
        for (int k=0; k<intersect.size(); k++) 
            intersect_arr[k] = intersect.get(k);
        
        // build new tree
        BSTSet intersect_tree = new BSTSet();
        intersect_tree.root = sortedArray2Tree(intersect_arr, 0, intersect_arr.length-1);
        
        return intersect_tree;              
    }
    
    
    /* Returns a new BSTSet which represents the difference of this BSTSet and s. This method should not modify the input sets. */
    public BSTSet difference(BSTSet s) //throws EmptyStackException
    {
        // make a deep copy of this tree
        BSTSet difftree = new BSTSet();
        int[] this_arr = this.inorderTraversal();
        for (int i=0; i<this_arr.length; i++)
            difftree.add(this_arr[i]);
        
        // delete those existing in s tree
        int[] s_arr = s.inorderTraversal();        
        for (int i=0; i<s_arr.length; i++)
            difftree.remove(s_arr[i]);
        
        return difftree;
    }
    
    
    /*  Returns the number of elements in this set. */
    public int size()
    {
        return size(root);
    }
    private int size(TNode node)
    {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }
    
    
    /* Returns the height of this BSTSet.  Height of empty set is -1. */
    public int height()
    {
        if (root == null)
            return -1;
        return height(root);
    }
    private int height(TNode curr)
    {
        if (curr == null)
            return 0;
        return 1 + max(height(curr.left), height(curr.right));
    }
    
    
    /* helper function: in-order traverse to create an ordered (increasing) array that represents the tree. */
    private int[] inorderTraversal() //throws EmptyStackException
    {
        int treesize = this.size();
        int[] ret = new int[treesize];
        int idx = 0;
        if (root == null)
            return ret;
        
        Stack<TNode> s = new ArrayStack<>();
        TNode curr = root;
        
        while (curr!=null || !s.isEmpty()) 
        {
            while (curr != null) 
            {
                s.push(curr);
                curr = curr.left;
            }
            curr = s.pop();
            ret[idx++] = curr.element;
            curr = curr.right;
        }
        
        return ret;
    }
    
    
    /*  Outputs the elements of this set to the console, in increasing order. */
    public void printBSTSet()
    {
        if (root == null)
            System.out.println("The set is empty");
        else {
            System.out.print("The set elements are: ");
            printBSTSet(root);
            System.out.print("\n");
        }
    }
    /* Outputs to the console the elements stored in the subtree rooted in t, in increasing order. */
    private void printBSTSet(TNode t)
    {
        if (t!=null) {
            printBSTSet(t.left);
            System.out.print(" " + t.element + ", ");
            printBSTSet(t.right);
        }
    }
    
    
    /* Prints the integers in this BSTSet in increasing order. 
    This method is nonrecursive and uses a stack to implement the inorder traversal. */
    public void printNonRec() //throws EmptyStackException
    {
        if (root == null) 
            System.out.println("The set is empty");
        else {
            int[] toprint = inorderTraversal();
            System.out.print("The set elements are: ");
            for (int i=0; i < toprint.length-1; i++)
                System.out.print(toprint[i] + ", ");
            System.out.print(toprint[toprint.length-1] + "\n");
        }        
    }
}
