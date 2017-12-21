package cn.temobi.core.util.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author salim
 * @created 2012-11-26
 * @package com.plaminfo.core.util.xml
 */
public class XMLNode
{
    /**
     * 标签名字
     */
    private String tagName = null;

    /**
     * 文本值
     */
    private String textValue = null;

    /**
     * 属性列表
     */
    private List<XMLNode> attribute;

    /**
     * 子节点列表
     */
    private List<XMLNode> children;

    public XMLNode() {
    }

    /**
     * 构造函数
     *
     * @param tagName
     *            标签名字
     * @param textValue
     *            标签值
     */
    public XMLNode(String tagName, String textValue) {
        super();
        this.tagName = tagName;
        this.textValue = textValue;
    }

    /**
     * 构造函数
     *
     * @param tagName
     *            标签名字
     * @param textValue
     *            标签值
     * @param attributeName
     *            属性名字
     * @param attributeValue
     *            属性值
     */
    public XMLNode(String tagName, String textValue, String attributeName, String attributeValue) {
        super();
        this.tagName = tagName;
        this.textValue = textValue;
        if (attributeName != null && !attributeName.equals("")) {
            getAttribute().add(new XMLNode(attributeName, attributeValue));
        }
    }

    /**
     * 添加属性
     *
     * @param attribute
     *            属性节点对象
     */
    public void addAttribute(XMLNode attribute) {
        getAttribute().add(attribute);
    }

    /**
     * 添加子节点
     *
     * @param childNode
     *            子节点对象
     */
    public void addChildNode(XMLNode childNode) {
        getChildren().add(childNode);
    }

    /**
     * 添加子节点(返回添加的子节点对象)
     *
     * @param tagName
     *            标签名字
     * @param textValue
     *            标签值
     * @return XMLNode
     */
    public XMLNode addChildNode(String tagName, String textValue) {
        XMLNode node = new XMLNode(tagName, textValue);
        getChildren().add(node);
        return node;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public List<XMLNode> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<XMLNode>();
        }
        return attribute;
    }

    public void setAttribute(List<XMLNode> attribute) {
        this.attribute = attribute;
    }

    public List<XMLNode> getChildren() {
        if (children == null) {
            children = new ArrayList<XMLNode>();
        }
        return children;
    }

    public void setChildren(List<XMLNode> children) {
        this.children = children;
    }

    /**
     * 是否叶子节点
     *
     * @return boolean
     */
    public boolean isLeaf() {
        return getChildren().size() == 0 ? true : false;
    }

}