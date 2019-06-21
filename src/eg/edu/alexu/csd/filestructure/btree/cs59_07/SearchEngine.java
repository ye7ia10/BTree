package eg.edu.alexu.csd.filestructure.btree.cs59_07;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import eg.edu.alexu.csd.filestructure.btree.ISearchEngine;
import eg.edu.alexu.csd.filestructure.btree.ISearchResult;

public class SearchEngine implements ISearchEngine{
	private BTree<String, DataOfKey> btree;
	private ArrayList<String> FilesNames = new ArrayList<>();
	public SearchEngine(int minimumDegree) {
		// TODO Auto-generated constructor stub
		btree = new BTree<String, DataOfKey>(minimumDegree);
	}
	@Override
	public void indexWebPage(String filePath) {
		if(filePath== null || filePath == "") {
			throw new RuntimeErrorException(null);
		}
		filePath = filePath.trim();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File( filePath ));
			NodeList nList = document.getElementsByTagName("doc");
			 
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				 Node node = nList.item(temp);
				 
				 if (node.getNodeType() == Node.ELEMENT_NODE)
				 {
				    //Print each employee's detail
				    Element eElement = (Element) node;
				    String id = eElement.getAttribute("id");
				    String text = eElement.getTextContent();
				    text = text.toLowerCase();
				    addText(id, text);
				    //String title = eElement.getAttribute("title");
				    //title = title.toLowerCase();
				    //addText(id, title);
				 }
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void addText(String id, String text) {
		String[] splited = text.split("\\W+");
		
		for(String str : splited) {
			DataOfKey data;
			str = str.trim();
			if(str.length() == 0)continue;
			if(btree.search(str) == null) {
				data = new DataOfKey();
				btree.insert(str, data);
			}else {
				data = btree.search(str);
			}
			data.addID(id);
		}
		
	}
	@Override
	public void indexDirectory(String directoryPath) {
		if(directoryPath== null || directoryPath == "") {
			throw new RuntimeErrorException(null);
		}
		directoryPath = directoryPath.trim();
		File f = new File(directoryPath);
		if(!f.exists()) {
			throw new RuntimeErrorException(null);
		}
		// TODO Auto-generated method stub
		File [] files = null;
		getFilesNames(directoryPath, files);
		for(String s : FilesNames) {
			indexWebPage(s);
		}
	}
	
	private void getFilesNames(String direc, File[] files){
		File folder = new File(direc);
		files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			  if (files[i].isFile()) {
				  FilesNames.add(direc + "//" + files[i].getName());
			  } else if (files[i].isDirectory()) {
			    String fDirec = direc +"//"+ files[i].getName();
			    getFilesNames(fDirec, files);
			  }
		}
	}

	@Override
	public void deleteWebPage(String filePath) {
		// TODO Auto-generated method stub
		if(filePath == null || filePath == "") {
			throw new RuntimeErrorException(null);
		}
		filePath = filePath.trim();
		File f = new File(filePath);
		if(!f.exists()) {
			throw new RuntimeErrorException(null);
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File( filePath ));
			
			NodeList nList = document.getElementsByTagName("doc");
			 
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				 Node node = nList.item(temp);
				 
				 if (node.getNodeType() == Node.ELEMENT_NODE)
				 {
				    //Print each employee's detail
				    Element eElement = (Element) node;
				    String id = eElement.getAttribute("id");
				    String text = eElement.getTextContent();
				    text = text.trim();
				    text = text.toLowerCase();
				    deleteText(id, text);
				   // String title = eElement.getAttribute("title");
				   // title = title.trim();
				   // title = title.toLowerCase();
				   // deleteText(id, title);
				 }
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void deleteText(String id, String text) {
		text = text.toLowerCase();
		text = text.trim();
		String[] splited = text.split("\\W+");
		
		for(String str : splited) {
			
			DataOfKey data;
			str = str.trim();
			if(str.length() == 0)continue;
			
			if(btree.search(str) == null) {
				continue;
			}else {
				data = btree.search(str);
			}
			data.deleteID(id);
			if(data.getDataOfKey().size() == 0) {
				btree.delete(str);
			}
		}
	}
	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
		// TODO Auto-generated method stub
		
		if(word == null) {
			
			throw new RuntimeErrorException(null);
		}
		word = word.trim();
		word = word.toLowerCase();
		DataOfKey data = btree.search(word);
		if(data == null) {
			data = new DataOfKey();
		}
		return data.getDataOfKey();
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
		
		// TODO Auto-generated method stub
		if(sentence == null) {
			throw new RuntimeErrorException(null);
		}
		sentence = sentence.trim();
		List<ISearchResult> ans = new ArrayList<>();
		sentence = sentence.toLowerCase();
		String[] splited = sentence.split("\\W+");
		if(splited.length == 0) {
			return ans;
		}
		ans = searchByWordWithRanking(splited[0]);
		for(int i = 1; i < splited.length; i++) {
			String str = splited[i];
			List<ISearchResult> list = searchByWordWithRanking(str);
			
			List<ISearchResult> tempAns = new ArrayList<>();
			for(ISearchResult list1 : ans) {
				for(ISearchResult list2 : list) {
					if(list1.getId().equals(list2.getId())) {
						tempAns.add(new SearchResult(list1.getId(), Math.min(list1.getRank(), list2.getRank())));
					}
				}
			}
			ans = tempAns;
		}
		return ans;
	}

}
