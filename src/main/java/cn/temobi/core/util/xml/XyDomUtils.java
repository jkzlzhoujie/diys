package cn.temobi.core.util.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author salim
 * @created 2012-11-26
 * @package com.plaminfo.core.util.xml
 */
public class XyDomUtils extends DomUtils {
    static DocumentBuilder builder;
    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static Document parseFile(String filePath) {
        Document document = null;
        try {
            document = builder.parse(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
 
    public static Document parseString(String xml) {
        Document document = null;
        try {
            document = builder.parse(new ByteArrayInputStream(xml
                    .getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
 
    public static Document newDocument() {
        return builder.newDocument();
    }
 
    public static void writeFile(String filename, Document document) {
        try {
            Transformer transformer = defaultTransromer();
            DOMSource source = new DOMSource(document);
 
            StreamResult result = new StreamResult(new PrintWriter(
                    new FileOutputStream(filename)));
 
            transformer.transform(source, result);
        } catch (TransformerException mye) {
            mye.printStackTrace();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
 
    public static String writeString(Document document) {
        try {
            Transformer transformer = defaultTransromer();
            DOMSource source = new DOMSource(document);
 
            Writer sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
 
            transformer.transform(source, result);
            return sw.toString();
        } catch (TransformerException mye) {
            mye.printStackTrace();
        }
        return null;
    }
 
    private static Transformer defaultTransromer()
            throws TransformerFactoryConfigurationError,
            TransformerConfigurationException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }
    
    public static void main(String[] args){
    	
    	Document document = XyDomUtils.parseString("<?xml version='1.0' encoding='UTF-8' ?><table name='people' class='com.peaa.dataaccess.datemapper.Person' />");
    	Element documentElement = document.getDocumentElement();
    	System.out.println(documentElement.getNodeName());
    	
    	document = XyDomUtils.parseString("<?xml version='1.0' encoding='UTF-8' ?><table name='people' class='com.peaa.dataaccess.datemapper.Person' />");
    	String xml = XyDomUtils.writeString(document);
    	System.out.println(xml);
    	
        document = XyDomUtils.parseString("<?xml version='1.0' encoding='UTF-8' ?><table name='people' class='com.peaa.dataaccess.datemapper.Person' />");
        XyDomUtils.writeFile(System.getProperty("user.dir")==null?"C:\\":System.getProperty("user.dir")
        		+ "/xmlt/table.xml", document);
        
        document = XyDomUtils.parseFile(System.getProperty("user.dir")==null?"C:\\":System.getProperty("user.dir")
                + "/xmlt/table.xml");
        documentElement = document.getDocumentElement();
        System.out.println(documentElement.getNodeName());
    }
}
