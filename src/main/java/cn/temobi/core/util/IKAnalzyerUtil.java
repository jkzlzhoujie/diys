/**
 * IK 中文分词  版本 5.0.1
 * IK Analyzer release 5.0.1
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package cn.temobi.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.temobi.core.util.FileUtil;

/**
 * 使用IKAnalyzer进行分词的演示
 * 2012-10-22
 *
 */
public class IKAnalzyerUtil {
	
	public static String str="篮球高手";
	public static void main(String[] args){
      
		StringReader re = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(re,true);
		Lexeme lex = null;
		try {
			while((lex=ik.next())!=null){
			System.out.print(lex.getLexemeText()+"|");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		 e.printStackTrace();
		 }
	}	
	
	
	public static List<String> split(String str)
	{
		List<String> list = new ArrayList<String>();
		StringReader re = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(re,true);
		Lexeme lex = null;
		try {
			while((lex=ik.next())!=null){
				list.add(lex.getLexemeText());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		
		return list;
	}
}
