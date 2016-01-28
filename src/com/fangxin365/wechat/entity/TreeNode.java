package com.fangxin365.wechat.entity;

import java.util.List;
import java.util.Map;

public class TreeNode {

	private Long id; // 节点ID，加载远程数据

	private String text; // 文字显示节点

	private String state; // 节点的状态，“开放”或“关闭”，默认为“打开”。 当设置为“关闭”，该节点有子节点，并将它们远程加载

	private String checked; // 指示节点是否被选中

	private Map<String, String> attributes; // 自定义属性可以被添加到一个节点

	private List<TreeNode> children; // 一个数组节点有一些子节点

	public TreeNode() {
		super();
	}

	public TreeNode(Long id, String text, String state, String checked,
			Map<String, String> attributes, List<TreeNode> children) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
		this.attributes = attributes;
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
