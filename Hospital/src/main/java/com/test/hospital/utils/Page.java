package com.test.hospital.utils;

import java.util.List;

public class Page<T> {

	private int page;
	private int size;
	private long totalCount;
	private List<T> dtoList;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getDtoList() {
		return dtoList;
	}

	public void setDtoList(List<T> dtoList) {
		this.dtoList = dtoList;
	}

}
