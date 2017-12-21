package cn.temobi.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author salim
 * @created 2013-2-1
 * @package com.plaminfo.core.common
 * 对List进行分页处理
 */
public class PageWithList {

	/**
	 * 每页显示的记录数
	 */
	private int pageRecords = 20;

	/**
	 * 总记录数
	 */
	private int totalRecord;

	/**
	 * 分页切割的启始点
	 */
	private int startIndex;

	/**
	 * 分页切割的结束点
	 */
	private int endIndex;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 当前页数
	 */
	private int currentPage = 1;

	/**
	 * 总记录集合
	 */
	@SuppressWarnings("unchecked")
	private List totalList;

	@SuppressWarnings("unchecked")
	public PageWithList(List totalList,int pageSize) {

		this.totalList = totalList;
        this.pageRecords = pageSize;
		innit();
	}

	/**
	 * 初始化该分页对象
	 */
	private void innit() {
		if (null != totalList) {
			totalRecord = totalList.size();

			if (totalRecord % this.pageRecords == 0) {
				this.totalPage = totalRecord / this.pageRecords;
			} else {
				this.totalPage = totalRecord / this.pageRecords + 1;
			}
		}
	}

	/**
	 * 得到分页后的数据
	 * 
	 * @return 分页数据
	 */
	@SuppressWarnings("unchecked")
	public List getPage(int currentPage) {
		this.currentPage = currentPage;

		if (currentPage <= 0) {
			this.currentPage = 1;
		}
		if (currentPage >= this.totalPage) {
			this.currentPage = this.totalPage;
		}

		List subList = new ArrayList();

		if (null != this.totalList) {
			subList.addAll(this.totalList.subList(getStartIndex(),getEndIndex()));
		}

		return subList;
	}

	/**
	 * 设置每页显示的记录条数,如果不设置则默认为每页显示30条记录
	 * 
	 * @param pageRecords
	 *            每页显示的记录条数(值必需介于10~100之间)
	 */
	public void setPageRecords(int pageRecords) {
		if (pageRecords >= 10 && pageRecords <= 100) {
			this.pageRecords = pageRecords;

			innit();
		}
	}

	public int getStartIndex() {
		if (null == this.totalList) {
			return 0;
		}

		this.startIndex = (getCurrentPage() - 1) * this.pageRecords;

		if (startIndex > totalRecord) {
			startIndex = totalRecord;
		}

		if (startIndex < 0) {
			startIndex = 0;
		}

		return startIndex;
	}

	public int getEndIndex() {
		if (null == this.totalList) {
			return 0;
		}

		endIndex = getStartIndex() + this.pageRecords;

		if (endIndex < 0) {
			endIndex = 0;
		}

		if (endIndex < getStartIndex()) {
			endIndex = getStartIndex();
		}

		if (endIndex > this.totalRecord) {
			endIndex = this.totalRecord;
		}

		return endIndex;
	}

	/***
	 * 获取总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 获取List集合中的总条数
	 * 
	 * @return
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	public boolean isEndPage() {
		return this.currentPage == this.totalPage;
	}

	/**
	 * 获取下一页的页数
	 * 
	 * @return 下一页的页数
	 */
	public int getNextPage() {
		int nextPage = this.currentPage + 1;

		if (nextPage > this.totalPage) {
			nextPage = this.totalPage;
		}
		if (nextPage <= 0) {
			nextPage = 1;
		}

		return nextPage;
	}

	/**
	 * 获取上一页的页数
	 * 
	 * @return 上一页的页数
	 */
	public int getPrivyPage() {
		int privyPage = this.currentPage - 1;

		if (privyPage > this.totalPage) {
			privyPage = this.totalPage;
		}

		if (privyPage <= 0) {
			privyPage = 1;
		}

		return privyPage;
	}

	/**
	 * 获取当前页页数
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
}
