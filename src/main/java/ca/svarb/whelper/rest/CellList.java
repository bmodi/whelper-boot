package ca.svarb.whelper.rest;

import java.util.ArrayList;

public class CellList extends ArrayList<Cell> {

	private static final long serialVersionUID = 6692361651670414630L;

	public CellList(int size) {
		super(size);
	}
	
	public ArrayList<Cell> getCellList() {
		return this;
	}
}
