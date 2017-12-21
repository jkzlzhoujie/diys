package cn.temobi.core.util.xml;

import org.jdom2.Element;

/**
 * @author salim
 * @created 2012-11-26
 * @package com.plaminfo.core.util.xml
 */
public interface XmlParserHandler
{
    /**
     * 解析xml dom tree 节点
     *
     * @param nodeEl
     *            某个节点
     * @return 是否结束解析
     */
    public boolean parserElementNode(Element nodeEl);
}
