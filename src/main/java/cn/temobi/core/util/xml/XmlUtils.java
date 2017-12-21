package cn.temobi.core.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 * @author salim
 * @created 2012-11-26
 * @package com.plaminfo.core.util
 */
public class XmlUtils {

	/**
	 * 判断依据1：节点文本内容不为空
	 */
	public static final String JUDGEMENT_BASIS_TYPE1 = "textContentNotNull";

	/**
	 * 判断依据2：节点文本内容为空
	 */
	public static final String JUDGEMENT_BASIS_TYPE2 = "textContentNull";

	/**
	 * 判断依据3：属性不为空
	 */
	public static final String JUDGEMENT_BASIS_TYPE3 = "attributeNotNull";

	/**
	 * 判断依据4：属性为空
	 */
	public static final String JUDGEMENT_BASIS_TYPE4 = "attributeNull";

	/**
	 * 默认的判断依据是节点文本内容不为空
	 */
	private String judgementBasis = JUDGEMENT_BASIS_TYPE1;

	/**
	 * 是否仅获取一个唯一的节点
	 */
	private boolean isOnly = false;

	/**
	 * 自定义xml解析接口
	 */
	private XmlParserHandler xmlParserHandler = null;

	private boolean refresh = false;

	private String filePath = null;

	public XmlUtils() {
	}

	public XmlUtils(XmlParserHandler xmlParserHandler) {
		this.xmlParserHandler = xmlParserHandler;
	}

	public XmlUtils(String judgementBasis) {
		this.judgementBasis = judgementBasis;
	}

	/************************************* 下面18个是对外公布的接口 ***********************************************/
	/**
	 * 根据节点tag从xml文件中解析获取一个满足条件的xml节点的文本值
	 * 
	 * @param filePath
	 *            文件路径
	 * @param tagName
	 *            节点tag
	 * @return String
	 */
	public String getTextContentByTagFromFile(String filePath, String tagName) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, filePath, true), tagName, null,
				XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0).getTextValue();
		}
		return null;
	}

	/**
	 * 根据节点tag从xml文件中解析获取一个满足条件的xml节点
	 * 
	 * @param filePath
	 *            文件路径
	 * @param tagName
	 *            节点tag
	 * @return XMLNode
	 */
	public XMLNode getXMLNodeByTagFromFile(String filePath, String tagName) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, filePath, true), tagName, null,
				XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据节点文本内容从xml文件中解析获取一个满足条件的xml节点
	 * 
	 * @param filePath
	 *            文件路径
	 * @param text
	 *            节点文本内容
	 * @return XMLNode
	 */
	public XMLNode getXMLNodeByTextFromFile(String filePath, String text) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, filePath, true), null, text, XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据节点tag从xml文件中解析获取一组满足条件的xml节点
	 * 
	 * @param filePath
	 *            文件路径
	 * @param tagName
	 *            节点tag
	 * @return List<XMLNode>
	 */
	public List<XMLNode> getXMLNodeListByTagFromFile(String filePath,
			String tagName) {
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, filePath, true), tagName, null,
				XMLNodeList);
		return XMLNodeList;
	}

	/**
	 * 根据节点文本内容从xml文件中解析获取一组满足条件的xml节点
	 * 
	 * @param filePath
	 *            文件路径
	 * @param text
	 *            节点文本内容
	 * @return List<XMLNode>
	 */
	public List<XMLNode> getXMLNodeListByTextFromFile(String filePath,
			String text) {
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, filePath, true), null, text, XMLNodeList);
		return XMLNodeList;
	}

	/*-----------------------------------------------------------------------------------------------*/
	/**
	 * 根据节点tag从xml文件内容中解析获取一个满足条件的xml节点的文本值
	 * 
	 * @param content
	 *            xml文件内容
	 * @param tagName
	 *            节点tag
	 * @return String
	 */
	public String getTextContentByTagFromStr(String content, String tagName) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, content, false), tagName, null,
				XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0).getTextValue();
		}
		return null;
	}

	/**
	 * 根据节点tag从xml文件内容中解析获取一个满足条件的xml节点
	 * 
	 * @param content
	 *            xml文件内容
	 * @param tagName
	 *            节点tag
	 * @return XMLNode
	 */
	public XMLNode getXMLNodeByTagFromStr(String content, String tagName) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, content, false), tagName, null,
				XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据节点文本内容从xml文件内容中解析获取一个满足条件的xml节点
	 * 
	 * @param content
	 *            xml文件内容
	 * @param text
	 *            节点文本内容
	 * @return XMLNode
	 */
	public XMLNode getXMLNodeByTextFromStr(String content, String text) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, content, false), null, text, XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据节点tag从xml文件内容中解析获取一组满足条件的xml节点
	 * 
	 * @param content
	 *            xml文件内容
	 * @param tagName
	 *            节点tag
	 * @return List<XMLNode>
	 */
	public List<XMLNode> getXMLNodeListByTagFromStr(String content,
			String tagName) {
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, content, false), tagName, null,
				XMLNodeList);
		return XMLNodeList;
	}

	/**
	 * 根据节点文本内容从xml文件内容中解析获取一组满足条件的xml节点
	 * 
	 * @param content
	 *            xml文件内容
	 * @param text
	 *            节点文本内容
	 * @return List<XMLNode>
	 */
	public List<XMLNode> getXMLNodeListByTextFromStr(String content, String text) {
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(null, content, false), null, text, XMLNodeList);
		return XMLNodeList;
	}

	/*-----------------------------------------------------------------------------------------------*/
	/**
	 * 根据节点tag从xml文件输入流中解析获取一个满足条件的xml节点的文本值
	 * 
	 * @param ins
	 *            xml文件输入流
	 * @param tagName
	 *            节点tag
	 * @return String
	 */
	public String getTextContentByTagFromInputStream(InputStream ins,
			String tagName) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(ins, null, false), tagName, null, XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0).getTextValue();
		}
		return null;
	}

	/**
	 * 根据节点tag从xml文件输入流中解析获取一个满足条件的xml节点
	 * 
	 * @param ins
	 *            xml文件输入流
	 * @param tagName
	 *            节点tag
	 * @return XMLNode
	 */
	public XMLNode getXMLNodeByTagFromInputStream(InputStream ins,
			String tagName) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(ins, null, false), tagName, null, XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据节点文本内容从xml文件输入流中解析获取一个满足条件的xml节点
	 * 
	 * @param ins
	 *            xml文件输入流
	 * @param text
	 *            节点文本内容
	 * @return XMLNode
	 */
	public XMLNode getXMLNodeByTextFromInputStream(InputStream ins, String text) {
		this.isOnly = true;
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(ins, null, false), null, text, XMLNodeList);
		if (XMLNodeList.size() > 0) {
			return XMLNodeList.get(0);
		}
		return null;
	}

	/**
	 * 根据节点tag从xml文件输入流中解析获取一组满足条件的xml节点
	 * 
	 * @param ins
	 *            xml文件输入流
	 * @param tagName
	 *            节点tag
	 * @return List<XMLNode>
	 */
	public List<XMLNode> getXMLNodeListByTagFromInputStream(InputStream ins,
			String tagName) {
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(ins, null, false), tagName, null, XMLNodeList);
		return XMLNodeList;
	}

	/**
	 * 根据节点文本内容从xml文件输入流中解析获取一组满足条件的xml节点
	 * 
	 * @param ins
	 *            xml文件输入流
	 * @param text
	 *            节点文本内容
	 * @return List<XMLNode>
	 */
	public List<XMLNode> getXMLNodeListByTextFromInputStream(InputStream ins,
			String text) {
		List<XMLNode> XMLNodeList = new ArrayList<XMLNode>();
		getXMLNode(getDocument(ins, null, false), null, text, XMLNodeList);
		return XMLNodeList;
	}

	/*-----------------------------------------------------------------------------------------------*/
	/**
	 * 从一个xml文件中进行解析(针对于自定义解析接口的情况,实现比较复杂的xml解析逻辑)
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public void parserXmlFromFile(String filePath) {
		getXMLNode(getDocument(null, filePath, true), null, null, null);
	}

	/**
	 * 从一个xml文件内容中进行解析(针对于自定义解析接口的情况,实现比较复杂的xml解析逻辑)
	 * 
	 * @param filePath
	 *            文件内容
	 */
	public void parserXmlFromStr(String content) {
		getXMLNode(getDocument(null, content, false), null, null, null);
	}

	/**
	 * 从一个xml文件输入流中进行解析(针对于自定义解析接口的情况,实现比较复杂的xml解析逻辑)
	 * 
	 * @param ins
	 *            文件输入流
	 */
	public void parserXmlFromInputStream(InputStream ins) {
		getXMLNode(getDocument(ins, null, false), null, null, null);
	}

	/************************************* 上面18个是对外公布的接口 ***********************************************/
	/**
	 * 获取一个Document
	 * 
	 * @param ins
	 *            输入流
	 * @param pathOrContent
	 *            文件路径or文件内容
	 * @param file
	 *            是否是文件
	 * @return Document
	 */
	private static Document getDocument(InputStream ins, String pathOrContent,
			boolean file) {
		// 创建新的输入源SAX 解析器
		SAXBuilder sb = new SAXBuilder();
		// 创建一个dom对象
		Document doc = null;
		FileInputStream fis = null;
		try {
			// 如果输入流不为空,那么直接构造
			if (ins != null) {
				doc = sb.build(ins);
			} else {
				InputSource source = null;
				// 如果是文件,那么先构造一个文件
				if (file) {
					File newfile = new File(pathOrContent);
					fis = new FileInputStream(newfile);
					source = new InputSource(fis);
				}
				// 如果是内容,那么构造一个输入流
				else {
					StringReader reader = new StringReader(pathOrContent);
					source = new InputSource(reader);
				}
				// 通过输入源构造一个Document
				doc = sb.build(source);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				// 2011年3月14（关闭流）
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// Transformer transformer;
		// try {
		// transformer = TransformerFactory.newInstance().newTransformer(new
		// JDOMSource(doc));
		// transformer.getOutputProperty("encoding");
		// }
		// catch (TransformerConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// catch (TransformerFactoryConfigurationError e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// XSLTransformer transformer=new XSLTransformer(doc);

		return doc;
	}

	public void getXMLNode(Document doc, String tagName, String text,
			List<XMLNode> XMLNodeList) {
		// 取的根元素
		Element root = doc.getRootElement();
		// 递归整个dom tree
		recureDocument(root, tagName, text, XMLNodeList);
		// 如果需要更新
		if (refresh) {
			refreshXml(doc, filePath);
		}
	}

	public static void refreshXml(Document doc, String filePath) {
		Format f = Format.getPrettyFormat();
		f.setEncoding("utf-8");
		f.setLineSeparator("\r\n");
		XMLOutputter outputter = new XMLOutputter(f);
		try {
			FileWriter writer = new FileWriter(filePath);
			outputter.output(doc, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// try {
		// Transformer transformer =
		// TransformerFactory.newInstance().newTransformer();
		// transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// transformer.transform(new JDOMSource(doc), new
		// StreamResult(filePath));
		// }
		// catch (TransformerException e) {
		// e.printStackTrace();
		// }
		// catch (TransformerFactoryConfigurationError e) {
		// e.printStackTrace();
		// }

		//
		// TransformerFactory tf = TransformerFactory.newInstance();
		// // 此实例可以用于处理来自不同源的 XML，并将转换输出写入各种接收器。
		// Transformer transformer;
		// OutputStreamWriter pw = null;
		// try {
		// transformer = tf.newTransformer();
		// // 创建带有 DOM 节点的新输入源
		// JDOMSource source = new JDOMSource(doc);
		// // 设置转换中实际的输出属性
		// transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		// // 设置转换中实际的输出属性
		// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// // 编码方式
		// pw = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		// // 从字节流构造 StreamResult
		// StreamResult result = new StreamResult(pw);
		// // 充当转换结果的持有者，可以为
		// // XML、纯文本、HTML
		// // 或某些其他格式的标记
		// // 将 XML Source 转换为 Result
		// transformer.transform(source, result);
		// // 关闭流
		// }
		// catch (Exception ex) {
		// Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null,
		// ex);
		// }
		// finally {
		// if (pw != null) {
		// try {
		// pw.close();
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }

	}

	/**
	 * 递归Document树：找出满足条件的节点
	 * 
	 * @param nodeEl
	 *            当前节点
	 * @param tagName
	 *            标签名字
	 * @param text
	 *            文本内容
	 * @param XMLNodeList
	 *            存储满足条件节点的容器
	 * @return boolean 是否结束
	 */
	public boolean recureDocument(Element nodeEl, String tagName, String text,
			List<XMLNode> XMLNodeList) {
		// 判断是否到了解析终止点
		boolean breakPoint = checkBreakPoint(nodeEl, tagName, text);
		// 如果到了终止点
		if (breakPoint) {
			// 自定义接口的直接终止,否则根据解析过滤规则判断是否能够终止
			if (xmlParserHandler != null || endDocument(nodeEl, XMLNodeList)) {
				return true;
			}
		}
		// 没有的话继续递归后续子节点
		else {
			// 得到根元素所有子元素的集合
			List<Element> nodeList = nodeEl.getChildren();
			if (nodeList != null && nodeList.size() > 0) {
				for (Element ele : nodeList) {
					if (recureDocument(ele, tagName, text, XMLNodeList)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 检测是否到了解析终止点
	 * 
	 * @param nodeEl
	 *            当前节点
	 * @param tagName
	 *            标签名字
	 * @param text
	 *            文本内容
	 * @return 是否终止点
	 */
	private boolean checkBreakPoint(Element nodeEl, String tagName, String text) {
		boolean breakPoint = false;
		// 自定义xml解析接口
		if (xmlParserHandler != null) {
			breakPoint = xmlParserHandler.parserElementNode(nodeEl);
		}
		// 查询某些特殊节点
		else {
			boolean tag = tagName == null ? false : true;
			// 查询节点的方式,tag or text,根据名字匹配规则判断是否找到了节点
			if (tag && tagName.equals(nodeEl.getName()) || !tag
					&& text.equals(nodeEl.getText())) {
				breakPoint = true;
			}
		}
		return breakPoint;
	}

	/**
	 * 添加一个XMLNode,并且判断是否已经完成
	 * 
	 * @param root
	 *            Element节点
	 * @return XMLNode
	 */
	private boolean endDocument(Element root, List<XMLNode> XMLNodeList) {
		boolean end = false;
		XMLNode node = new XMLNode(root.getName(), root.getText());
		List<Attribute> list = root.getAttributes();
		if (list != null && list.size() > 0) {
			for (Attribute attribute : list) {
				node.getAttribute().add(
						new XMLNode(attribute.getName(), attribute.getValue()));
			}
		}
		// 是否要获取唯一节点
		if (isOnly) {
			if (judgementBasis.equals(JUDGEMENT_BASIS_TYPE1)) {
				if (root.getText() != null && !root.getText().equals("")) {
					XMLNodeList.add(node);
					end = true;
				}
			} else if (judgementBasis.equals(JUDGEMENT_BASIS_TYPE2)) {
				if (root.getText() == null || root.getText().equals("")) {
					XMLNodeList.add(node);
					end = true;
				}
			} else if (judgementBasis.equals(JUDGEMENT_BASIS_TYPE3)) {
				if (root.getAttributes() != null
						&& root.getAttributes().size() > 0) {
					XMLNodeList.add(node);
					end = true;
				}
			} else if (judgementBasis.equals(JUDGEMENT_BASIS_TYPE4)) {
				if (root.getAttributes() == null
						|| root.getAttributes().size() == 0) {
					XMLNodeList.add(node);
					end = true;
				}
			}

		} else {
			XMLNodeList.add(node);
		}
		return end;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static void main(String[] args) {
		String content ="<HttpResponse><App><appId>4909</appId>" +
				"<appName>高德地图（免费语音导航）</appName><appSize>8476992</appSize>" +
				"<firstTypeName>null</firstTypeName>" +
				"<imgUrl>http://image.189store.com:8080/data/app_files/2012/09/7160a2d246c6248564207372c2b3e5a0.gif</imgUrl></App>" +
				"<App><appId>5474</appId><appName>赶集生活</appName><appSize>3921563</appSize><firstTypeName>null</firstTypeName><imgUrl>http://image.189store.com:8080/data/app_files/2012/11/61eeb081ac117c31310fd9d0b6c3509d.gif</imgUrl></App></HttpResponse>";
		/**
		String content = "<xml attr=\"xmlattr\"><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"+
		"<CreateTime>1351776360</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>23.134521</Location_X><Location_Y>113.358803</Location_Y>"+
		"<Scale>20</Scale><Label><![CDATA[位置信息]]></Label></xml>";
		
		XMLNode node = new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE3).getXMLNodeByTagFromStr(content, "xml");
		System.out.println(node.getAttribute().get(0).getTextValue());
		System.out.println("ToUserName : " + new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE4).getTextContentByTagFromStr(content,"ToUserName"));
		System.out.println("FromUserName : " + new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE4).getTextContentByTagFromStr(content,"FromUserName"));
		System.out.println("Location_X : " + new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE4).getTextContentByTagFromStr(content,"Location_X"));
        **/
		List<String> appIdList = new ArrayList<String>();
		List<String> appNameList = new ArrayList<String>();
		List<String> appSizeList = new ArrayList<String>();
		List<XMLNode> nodes = new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE4).getXMLNodeListByTagFromStr(content, "appId");
		for(XMLNode node : nodes){
			//System.out.println("appId : " + node.getTextValue());
			appIdList.add(node.getTextValue());
		}
		List<XMLNode> appNames = new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE4).getXMLNodeListByTagFromStr(content, "appName");
		for(XMLNode node : appNames){
			//System.out.println("appName : " + node.getTextValue());
			appNameList.add(node.getTextValue());
		}
		List<XMLNode> appSizes = new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE4).getXMLNodeListByTagFromStr(content, "appSize");
		for(XMLNode node : appSizes){
			//System.out.println("appSize : " + node.getTextValue());
			appSizeList.add(node.getTextValue());
		}
		
		for(int i=0;i<appSizeList.size();i++){
			System.out.println(appIdList.get(i)+appNameList.get(i)+appSizeList.get(i));
		}
		System.out.println("==============split line================");
		// 创建新的输入源SAX 解析器
		SAXBuilder sb = new SAXBuilder();
		// 创建一个dom对象
		try {
					StringReader reader = new StringReader(content);
					InputSource source = new InputSource(reader);
					// 通过输入源构造一个Document
					Document doc = sb.build(source);
					// 取的根元素
					Element root = doc.getRootElement();
					List<Element> nodeList = root.getChildren();
					if (nodeList != null && nodeList.size() > 0) {
						for (Element ele : nodeList) {
							List<Content> com = ele.getContent();
							String appId = com.get(0).getValue();
							String appName = com.get(1).getValue();
							String appSize = com.get(2).getValue();
							System.out.println(appId+appName+appSize);
						}
					}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		System.out.println(new XmlUtils().getTextContentByTagFromStr("D:\\test.xml", "第二层节点"));
//		XMLNode node = new XmlUtils(XmlUtils.JUDGEMENT_BASIS_TYPE3).getXMLNodeByTagFromStr("D:\\test.xml","第一层节点");
//		System.out.println(node.getAttribute().get(0).getTagName());
//		System.out.println(node.getAttribute().get(0).getTextValue());
//	
//		List<XMLNode> list = new XmlUtils().getXMLNodeListByTagFromStr("D:\\test.xml", "第二层节点");
//		if (list != null && list.size() > 0) {
//			for (XMLNode nodeItem : list) {
//				System.out.println("节点名字：" + nodeItem.getTagName());
//				System.out.println("节点值：" + nodeItem.getTextValue());
//				List<XMLNode> attribute = nodeItem.getAttribute();
//				for (XMLNode item : attribute) {
//				System.out.println("属性名字：" + item.getTagName());
//				System.out.println("属性值：" + item.getTextValue());
//				}
//			}
//		}
	}

}
