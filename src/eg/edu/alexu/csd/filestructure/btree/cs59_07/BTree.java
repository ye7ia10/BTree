package eg.edu.alexu.csd.filestructure.btree.cs59_07;


import javax.management.RuntimeErrorException;
import eg.edu.alexu.csd.filestructure.btree.IBTree;
import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

public class BTree <K extends Comparable<K>, V> implements IBTree<K, V>{
	private int minimumDegree;
	private IBTreeNode<K,V> root;
	private boolean flg = true;
	public BTree(int minimumDegree) {
		// TODO Auto-generated constructor stub
		this.minimumDegree = minimumDegree;
	}
	@Override
	public int getMinimumDegree() {
		// TODO Auto-generated method stub
		return minimumDegree;
	}

	@Override
	public IBTreeNode<K, V> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public void insert(K key, V value) {
		// TODO Auto-generated method stub
		if(key == null || value == null) {
			throw new RuntimeErrorException(null);
		}
		
		if(root == null) {
			root = new BTreeNode<K, V>();
			root.getKeys().add(key);
			root.getValues().add(value);
			root.setNumOfKeys(root.getNumOfKeys() + 1);
			return;
		}
		// may be overflow in the root so we should split here and increase the height by one
		if(root.getNumOfKeys() == 2 * minimumDegree - 1) {
			
			IBTreeNode<K, V> newNode = root;
			root = new BTreeNode<>();
			root.getChildren().add(newNode);
			split(root,newNode);
			root.setLeaf(false);
		}
		searchAndInsert(key, value, root);
	}
	
	/*
	 * used to insert key in the tree and split when find full node
	 */
	private void searchAndInsert(K key, V value, IBTreeNode<K, V> node) {
		// TODO Auto-generated method stub
		
		int i = 0;
		// loop to get the key if exist or pointer to child if not
		while(i < node.getNumOfKeys() && node.getKeys().get(i).compareTo(key) < 0)
			i++;
		
		// if the key was found
		if(i < node.getNumOfKeys() && node.getKeys().get(i).compareTo(key) == 0) {
			
			return;
		}
		//if the key not found then it may be a leaf or not
		if(node.isLeaf()) {
			
			node.getKeys().add(i, key);
			node.getValues().add(i, value);
			node.setNumOfKeys(node.getNumOfKeys() + 1);
			return;
		} else {
			
			if(node.getChildren().get(i).getNumOfKeys() == 2 * minimumDegree - 1) {
				split(node, node.getChildren().get(i));
				searchAndInsert(key, value, node);
			}else {
				searchAndInsert(key, value, node.getChildren().get(i));
			}
		}
		
		
	}
	
	
	/*
	 * split a node into two nodes to avoid overflow (used in insert)
	 */
	private void split(IBTreeNode<K, V> parent, IBTreeNode<K, V> willSplit) {
		
		IBTreeNode<K, V> newNode = new BTreeNode<>();
		K key = willSplit.getKeys().get(minimumDegree - 1);
		V value = willSplit.getValues().get(minimumDegree - 1);
		newNode.setLeaf(willSplit.isLeaf());
		for(int i = minimumDegree; i < willSplit.getNumOfKeys(); i++) {
			newNode.getKeys().add(willSplit.getKeys().get(i));
			newNode.getValues().add(willSplit.getValues().get(i));
		}
		if(!willSplit.isLeaf()) {
			for(int i = minimumDegree; i <= willSplit.getNumOfKeys(); i++) {
				newNode.getChildren().add(willSplit.getChildren().get(i));
			}
		}
		while(willSplit.getKeys().size() > minimumDegree - 1) {
			willSplit.getKeys().remove(willSplit.getKeys().size()-1);
			willSplit.getValues().remove(willSplit.getValues().size()-1);
		}
		while(willSplit.getChildren().size() > willSplit.getKeys().size() + 1) {
			willSplit.getChildren().remove(willSplit.getChildren().size() - 1);
		}
		newNode.setNumOfKeys(newNode.getKeys().size());
		willSplit.setNumOfKeys(minimumDegree - 1);
		
		int i = 0;
		while(i < parent.getNumOfKeys() && parent.getKeys().get(i).compareTo(key) < 0) {
			i++;
		}
		parent.getKeys().add(i, key);
		parent.getValues().add(i, value);
		parent.setNumOfKeys(parent.getNumOfKeys() + 1);
		if(parent.getChildren().size() >= 1) {
			parent.getChildren().add(i + 1, newNode);
		}else {
			parent.getChildren().add(newNode);
		}
	}
	
	
	
	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
		return searchHelper(key, root);
	}
	/*
	 * used to recurse search for a key and return the value if found and nll otherwise
	 */
	private V searchHelper(K key, IBTreeNode<K, V> node) {
		// TODO Auto-generated method stub
		
		int i = 0;
		while(i < node.getNumOfKeys() && node.getKeys().get(i).compareTo(key) < 0) {
			i++;
		}
		if(i < node.getNumOfKeys() && node.getKeys().get(i).compareTo(key) == 0) {
			return node.getValues().get(i);
		}
		if(node.isLeaf()) {
			return null;
		}
		return searchHelper(key, node.getChildren().get(i));
	}
	
	public void check(IBTreeNode<K, V> node, int level) {
		System.out.println("level  " + level);
		for(int i = 0; i < node.getNumOfKeys(); i++) {
			System.out.print(node.getKeys().get(i) + "  ");
		}
		System.out.println("");
		if(!node.isLeaf()) {
			for(int i = 0; i < node.getChildren().size(); i++) {
				check(node.getChildren().get(i), level+1);
			}
		}
	}
	
	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		
		if (key == null) {
			throw new RuntimeErrorException(null);
		} 
		
		if (getRoot() == null) {
			return false;
		}
		 remove(root, key);
		 if (root.getNumOfKeys() == 0) 
		 {  
		        if (root.isLeaf()) 
		            root = null; 
		        else
		            root = root.getChildren().get(0); 
		} 
		if (flg)
		 return true;
		else
		return false;
	}

	private void remove(IBTreeNode<K, V> root, K key) {
		// TODO Auto-generated method stub
		int idx = findKey(root, key); 
	    // The key to be removed is present in this node 
	    if (idx < root.getNumOfKeys() && root.getKeys().get(idx).compareTo(key) == 0) 
	    {  
	        if (root.isLeaf()) 
	            removeFromLeaf(root, idx); 
	        else
	            removeFromNonLeaf(root, idx); 
	    }  
	    else
	    {  
	        if (root.isLeaf()) 
	        { 
	            flg = false; 
	            return; 
	        } 
	        boolean flag = ( (idx== root.getNumOfKeys())? true : false ); 
	        if (   root.getChildren().get(idx).getNumOfKeys() < getMinimumDegree()) 
	            fill(root, idx); 
	        if (flag && idx > root.getNumOfKeys()) 
	            remove(root.getChildren().get(idx - 1), key); 
	        else
	        	remove(root.getChildren().get(idx), key); 
	    } 
	    return;
	}
	
	private void fill(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		// If the previous child(C[idx-1]) has more than t-1 keys, borrow a key 
	    // from that child 
	    if (idx != 0 && root2.getChildren().get(idx -1)
	    	.getNumOfKeys() >= getMinimumDegree()) 
	        borrowFromPrev(root2, idx); 
	  
	    // If the next child(C[idx+1]) has more than t-1 keys, borrow a key 
	    // from that child 
	    else if (idx!= root2.getNumOfKeys() && root2.getChildren().get(idx+1)
		    	.getNumOfKeys() >= getMinimumDegree()) 
	        borrowFromNext(root2, idx); 
	  
	    // Merge C[idx] with its sibling 
	    // If C[idx] is the last child, merge it with with its previous sibling 
	    // Otherwise merge it with its next sibling 
	    else
	    { 
	        if (idx != root2.getNumOfKeys()) 
	            merge(root2, idx); 
	        else
	            merge(root2, idx-1); 
	    } 
	    return;
	}

	private void borrowFromNext(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		System.out.println("neeeeeeeeeeext");
		IBTreeNode<K,V> child = root2.getChildren().get(idx); 
	    IBTreeNode<K,V> sibling = root2.getChildren().get(idx+1); 
		    // Setting child's first key equal to keys[idx-1] from the current node 
		child.getKeys().add(root2.getKeys().get(idx));   
		child.getValues().add(root2.getValues().get(idx)); 
		root2.getKeys().remove(idx);
		root2.getValues().remove(idx);
		    // Moving sibling's last child as C[idx]'s first child 
		if(!child.isLeaf()) {
		   child.getChildren().add(sibling.getChildren().get(0));
		   sibling.getChildren().remove(0);
		}
		  
		    // Moving the key from the sibling to the parent 
		    // This reduces the number of keys in the sibling 
		
		root2.getKeys().add(idx, sibling.getKeys().get(0));
		root2.getValues().add(idx, sibling.getValues().get(0));
		
	    sibling.getKeys().remove(0);
	    sibling.getValues().remove(0);
	    sibling.setNumOfKeys(sibling.getKeys().size());
	    child.setNumOfKeys(child.getKeys().size());
		return;
		
	}

	private void borrowFromPrev(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		System.out.println("preeeeeeeeev");
		IBTreeNode<K,V> child = root2.getChildren().get(idx); 
	    IBTreeNode<K,V> sibling = root2.getChildren().get(idx-1); 
		    // Setting child's first key equal to keys[idx-1] from the current node 
		child.getKeys().add(0, root2.getKeys().get(idx - 1));   
		child.getValues().add(0, root2.getValues().get(idx - 1)); 
		root2.getKeys().remove(idx-1);
		root2.getValues().remove(idx-1);
		    // Moving sibling's last child as C[idx]'s first child 
		if(!child.isLeaf()) {
		   child.getChildren().add(0, sibling.getChildren().get(sibling.getKeys().size()));
	       sibling.getChildren().remove(sibling.getKeys().size());
		}
		  
		    // Moving the key from the sibling to the parent 
		    // This reduces the number of keys in the sibling 
		
		root2.getKeys().add(idx-1, sibling.getKeys().get(sibling.getKeys().size() - 1));
		root2.getValues().add(idx-1, sibling.getValues().get(sibling.getKeys().size() - 1));
		
	    sibling.getKeys().remove(sibling.getKeys().size() - 1);
	    sibling.getValues().remove(sibling.getKeys().size() - 1);
	    sibling.setNumOfKeys(sibling.getKeys().size());
	    child.setNumOfKeys(child.getKeys().size());
		    return; 
	}

	private void removeFromNonLeaf(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		System.out.println("non-leeeeeeeeaf");
	    K k = root2.getKeys().get(idx); 
	    
	    if (root2.getChildren().get(idx).getNumOfKeys() >= getMinimumDegree()) 
	    { 
	        IBTreeNode<K, V> pred = getPred(root2, idx); 
	        root2.getKeys().set(idx, pred.getKeys().get(pred.getNumOfKeys()-1)); 
	        root2.getValues().set(idx, pred.getValues().get(pred.getNumOfKeys()-1)); 
	        remove(root2.getChildren().get(idx), pred.getKeys().get(pred.getNumOfKeys()-1)); 
	    } 
	    
	    else if  (root2.getChildren().get(idx+1).getNumOfKeys() >= getMinimumDegree()) 
	    { 
	    	IBTreeNode<K, V> succ = getSucc(root2, idx); 
	    	root2.getKeys().set(idx, succ.getKeys().get(0)); 
	    	root2.getValues().set(idx, succ.getValues().get(0)); 
	    	remove(root2.getChildren().get(idx+1), succ.getKeys().get(0));  
	    } 
	    
	
	   	else
	    { 
	   			System.out.println("heeeeeeeeereeeeeeee");
	            merge(root2, idx); 
	            remove(root2.getChildren().get(idx), k); 
	    } 
	    return; 
	    
	}

	private void merge(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		IBTreeNode<K,V> child = root2.getChildren().get(idx);
	    IBTreeNode<K,V> sibling = root2.getChildren().get(idx+1); 
	  
	    // Pulling a key from the current node and inserting it into (t-1)th 
	    // position of C[idx]
	    child.getKeys().add(root2.getKeys().get(idx));
	    child.getValues().add(root2.getValues().get(idx));
	    child.getKeys().addAll(sibling.getKeys());
	    child.getValues().addAll(sibling.getValues());
	    
	    if (!child.isLeaf()) {
	    	child.getChildren().addAll(sibling.getChildren());	
	    }
	 
	  
	  root2.getKeys().remove(idx);
	  root2.getValues().remove(idx);
	  root2.getChildren().remove(idx+1);
	   // Updating the key count of child and the current node 
	  root2.setNumOfKeys(root2.getKeys().size());
	  child.setNumOfKeys(child.getKeys().size());
	  
	    return;
	}

	private IBTreeNode<K, V> getSucc(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		System.out.println("succcccccccccccccc");
		// Keep moving to the right most node until we reach a leaf 
		IBTreeNode<K, V> cur = root2.getChildren().get(idx+1);
	    while (!cur.isLeaf()) 
	        cur = cur.getChildren().get(0); 
	    // Return the last key of the leaf 
	    return  cur;
	}

	private IBTreeNode<K, V> getPred(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		System.out.println("preeeeeeeeed");
		// Keep moving to the right most node until we reach a leaf 
		IBTreeNode<K, V> cur = root2.getChildren().get(idx);
	    while (!cur.isLeaf()) 
	        cur = cur.getChildren().get(cur.getNumOfKeys()); 
	  
	    // Return the last key of the leaf 
	    return  cur;
	}

	private void removeFromLeaf(IBTreeNode<K, V> root2, int idx) {
		// TODO Auto-generated method stub
		System.out.println("leaaaaaaaaf");
		root2.getKeys().remove(idx);
		root2.getValues().remove(idx);
		root2.setNumOfKeys(root2.getKeys().size());
	}

	private int findKey(IBTreeNode<K, V> node, K k) 
	{ 
	    int idx=0; 
	    while (idx< node.getNumOfKeys() && node.getKeys().get(idx).compareTo(k) < 0) 
	        ++idx; 
	    return idx; 
	} 
	
	public void traverse(IBTreeNode<K, V> root) {
	    // There are n keys and n+1 children, travers through n keys 
	    // and first n children 
	    int i; 
	   // System.out.println(root.getKeys().size()  + "keys ***" + root.getChildren().size() + "childs  " + root.isLeaf());
	    for (i = 0; i < root.getKeys().size(); i++) 
	    { 
	        // If this is not leaf, then before printing key[i], 
	        // traverse the subtree rooted with child C[i]. 
	        if (!root.isLeaf()) 
	            traverse(root.getChildren().get(i)); 
	        System.out.print("** " + root.getKeys().get(i) + " "); 
	    }
	    
	  
	    // Print the subtree rooted with last child 
	    if (!root.isLeaf()) 
	        traverse(root.getChildren().get(i)); 
	}
	
	

}
