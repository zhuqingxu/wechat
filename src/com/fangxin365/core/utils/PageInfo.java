package com.fangxin365.core.utils;

import java.io.Serializable;

public class PageInfo implements Serializable {

	private static final long serialVersionUID = -4073671450629468024L;

	protected Integer pageNo = 0;
	protected Integer total = 0;
	protected Integer pageSize = 0;

	public PageInfo() {}

	public PageInfo(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public PageInfo(Integer pageNo, Integer pageSize, Integer total) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
	}

	/**
	 * 判断是否还有后续页
	 * @return 是否还有后续页
	 */
	public boolean hasNextPage() {
		return pageNo < total;
	}

    /**
     * 设置当前页号
     * @param page 当前页号
     */
	public void setPageNo(Integer pageNo){
		this.pageNo = pageNo;
	}

    /**
     * 设置当前页号为下一页
     */
	public void nextPage(){
		if(pageNo == null) {
			pageNo = 0;
		}
		pageNo++;
	}

    /**
     * 设置当前页号为上一页
	 * @return 是否存在上一页
	 */
	public boolean previewPage(){
		if(pageNo == null || pageNo == 0) {
			return false;
		}
		pageNo--;
		return true;
	}

    /**
     * 设置每页包含的纪录数
     * @param pageSize 每页包含的纪录数
     */
	public void setPageSize(Integer size){
		this.pageSize = size;
	}

    /**
     * 设置总记录数
     * @param total 总记录数
     */
	public void setTotal(Integer total){
		this.total = total;
	}

    /**
     * 获取当前页号
     * @return 当前页号
     */
	public Integer getPageNo(){
		return this.pageNo;
	}

    /**
     * 获取每页包含的纪录数
     * @return 每页包含的纪录数
     */
	public Integer getPageSize(){
		return this.pageSize;
	}

    /**
     * 获取总记录数
     * @return 总记录数
     */
	public Integer getTotal(){
		return this.total;
	}

    /**
     * 获取总页数
     * @return 总页数
     */
	public int getTotalPage(){
		int tpage = total % pageSize;
		int totalpage = total/pageSize;
    	if(tpage != 0) {
    		totalpage += 1;
    	}
		return totalpage;
	}

}