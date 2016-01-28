package com.fangxin365.wechat.entity;

import java.util.List;

public class TreeGridNode {

	/*
	 * tree
	 */
	private String id;
	private String text;
	private String state; // 节点状态,“开放”或“关闭”,默认为“打开”

	/*
	 * index grid
	 */
	private String indexPid;
	private String indexPname;
	private String indexName;
	private Integer status;

	private List<TreeGridNode> children; // 一个数组节点有一些子节点

	public TreeGridNode() {
		super();
	}

	public TreeGridNode(String id, String text, String state, String indexPid,
			String indexPname, String indexName, List<TreeGridNode> children) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.indexPid = indexPid;
		this.indexPname = indexPname;
		this.indexName = indexName;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIndexPid() {
		return indexPid;
	}

	public void setIndexPid(String indexPid) {
		this.indexPid = indexPid;
	}

	public String getIndexPname() {
		return indexPname;
	}

	public void setIndexPname(String indexPname) {
		this.indexPname = indexPname;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<TreeGridNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeGridNode> children) {
		this.children = children;
	}

}
