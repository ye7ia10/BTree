package eg.edu.alexu.csd.filestructure.btree.cs59_07;

import java.io.Serializable;
import java.util.ArrayList;

import eg.edu.alexu.csd.filestructure.btree.ISearchResult;

public class DataOfKey implements Serializable{
	private ArrayList<ISearchResult> dataOfKey = new ArrayList<ISearchResult>();
	
	public void addID(String id) {
		boolean found = false;
		for(ISearchResult res : dataOfKey) {
			if(res.getId().equals(id)) {
				res.setRank(res.getRank() + 1);
				found = true;
				break;
			}
		}
		if(!found) {
			dataOfKey.add(new SearchResult(id,1));
		}
	}
	public ArrayList<ISearchResult> getDataOfKey(){
		return dataOfKey;
	}
}
