package eg.edu.alexu.csd.filestructure.btree.cs59_07;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

public class BTreeNode <K extends Comparable<K>, V> implements IBTreeNode<K, V>{
	private ArrayList<K> keys = new ArrayList<K>();
	private ArrayList<V> values = new ArrayList<V>();
	List<IBTreeNode<K, V>> children = new ArrayList<>();
	private int numOfKeys = 0;
	private boolean leaf = true;
	@Override
	public int getNumOfKeys() {
		// TODO Auto-generated method stub
		return numOfKeys;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		// TODO Auto-generated method stub
		this.numOfKeys = numOfKeys;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return leaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		// TODO Auto-generated method stub
		leaf = isLeaf;
	}

	@Override
	public List<K> getKeys() {
		// TODO Auto-generated method stub
		return keys;
	}

	@Override
	public void setKeys(List<K> keys) {
		// TODO Auto-generated method stub
		this.keys = (ArrayList<K>) keys;
	}

	@Override
	public List<V> getValues() {
		// TODO Auto-generated method stub
		return values;
	}

	@Override
	public void setValues(List<V> values) {
		// TODO Auto-generated method stub
		this.values = (ArrayList<V>) values;
	}

	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
		// TODO Auto-generated method stub
		this.children = children;
	}

}
