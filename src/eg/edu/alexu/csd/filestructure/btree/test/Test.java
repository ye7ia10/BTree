package eg.edu.alexu.csd.filestructure.btree.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.omg.CORBA.portable.Delegate;

import eg.edu.alexu.csd.filestructure.btree.IBTree;
import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;
import eg.edu.alexu.csd.filestructure.btree.cs59_07.BTree;
import eg.edu.alexu.csd.filestructure.btree.cs59_07.BTreeNode;

public class Test {

	public static <K extends Comparable<K>, V> void main(String[] args) {
		// TODO Auto-generated method stub
		BTree<Integer, String> t = new BTree<>(3);
		
		    t.insert(1, "a"); 
		    t.insert(3, "a"); 
		    t.insert(7, "a"); 
		    t.insert(10, "a"); 
		    t.insert(11, "a"); 
		    t.insert(13, "a"); 
		    t.insert(14, "a"); 
		    t.insert(15, "a"); 
		    t.insert(18, "a"); 
		    t.insert(16, "a"); 
		    t.insert(19, "a"); 
		    t.insert(24, "a"); 
		    t.insert(25, "a"); 
		    t.insert(26, "a"); 
		    t.insert(21, "a"); 
		    t.insert(4, "a"); 
		    t.insert(5, "a"); 
		    t.insert(20, "a"); 
		    t.insert(22, "a"); 
		    t.insert(2, "a"); 
		    t.insert(17, "a"); 
		    t.insert(12, "a"); 
		    t.insert(6, "a"); 
		  
		    System.out.println("Traversal of tree constructed is"); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
		    t.delete(6); 
		    System.out.println("Traversal of tree constructed is 6 "); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
		    t.delete(13); 
		    System.out.println("Traversal of tree constructed is 13 "); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
		    t.delete(7); 
		    System.out.println("Traversal of tree constructed is 7 "); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
		    t.delete(4); 
		    System.out.println("Traversal of tree constructed is 4 "); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
		    t.delete(2); 
		    System.out.println("Traversal of tree constructed is 2 "); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
		    t.delete(16); 
		    System.out.println("Traversal of tree constructed is 16 "); 
		    t.traverse(t.getRoot()); 
		    System.out.println(); 
		  
			}

}
