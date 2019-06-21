package eg.edu.alexu.csd.filestructure.btree.cs59_07;

import eg.edu.alexu.csd.filestructure.btree.ISearchResult;

public class SearchResult implements ISearchResult{
	String ID;
	int rank;
	
	public SearchResult(String ID, int rank) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.rank = rank;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setId(String ID) {
		// TODO Auto-generated method stub
		this.ID = ID;
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	@Override
	public void setRank(int rank) {
		// TODO Auto-generated method stub
		this.rank = rank;
	}

}
