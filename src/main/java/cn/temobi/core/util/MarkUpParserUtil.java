package cn.temobi.core.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MarkUpParserUtil {
	
	public static void main(String[] args) throws IOException {
		//Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		String xmlStr ="<all><ab><a>a111</a><b>b111</b></ab><bc><b>b1112</b><c>c111</c></bc></all>";
		Document doc = Jsoup.parse(xmlStr);
		Elements newsHeadlines = doc.select("all bc b");
		for(Element element :newsHeadlines){
			String elem = element.text();
			System.out.println(elem);
		}
		 
	}

}
