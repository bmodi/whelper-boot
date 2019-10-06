package ca.svarb.whelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.svarb.whelper.rest.Grid;

@RestController
class GridService {

	private Dictionary dictionary=null;
	
	@Autowired
	private WordSearcher wordSearcher;

	public GridService() {
		try {
			loadDefaultDictionary();
		} catch (WhelperException e) {
			System.out.println("Could not load dictionary: "+e.getMessage());
		}
	}

  @PostMapping("/grid")
	public SortedSet<String> getWords(@RequestBody Grid grid) {
		System.out.println("grid="+grid);
		IGameBoard board=null;
		if ( grid.getGridType()==Grid.GridType.GRID ) {
			board=new ca.svarb.whelper.Grid(grid.getCells());
		} else {
			board=new ca.svarb.whelper.OffsetGrid(grid.getCells());
		}
		SortedSet<String> words = wordSearcher.findWords(dictionary, board);
		return words;
	}

	private void loadDefaultDictionary() throws WhelperException {
		String dictionaryName = "TWL06.txt";
		
		try {
			InputStream dictionaryStream = GridService.class.getClassLoader().getResourceAsStream(dictionaryName);
			if ( dictionaryStream==null ) throw new WhelperException("Could not find dictionary file: "+dictionaryName);
			dictionary=DictionaryLoader.getInstance().loadFromReader(new InputStreamReader(dictionaryStream));
		} catch (IOException e) {
			throw new WhelperException("Error reading from dictionary file: "+e.getMessage());
		}
	}

}

