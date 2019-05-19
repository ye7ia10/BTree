package eg.edu.alexu.csd.filestructure.btree.cs59_07;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.btree.IBTree;
import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

public class BTree <K extends Comparable<K>, V> implements IBTree<K, V>{
	private int minimumDegree;
	private IBTreeNode<K,V> root;
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
	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		return false;
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
	
}
