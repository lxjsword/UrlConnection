package com.lxj.util;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParseUtil
{
	private String parserstr;
	private int keywordCount;
	
	public ParseUtil(String str) throws ParserException
	{
		parserstr = str;
		keywordCount = 0;
	}

	public void setParserstr(String parserstr)
	{
		this.parserstr = parserstr;
	}
	
	// 得到关键词个数
	public int getKeywordCount()
	{
		return this.keywordCount;
	}

	//得到期刊号
	public String getPubNum() throws ParserException
	{
		Parser parser = new Parser(parserstr);
		NodeFilter tagFilter = new TagNameFilter("div");
		NodeFilter attrfilter = new HasAttributeFilter( "class", "detailLink" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		String date = nodes.elementAt(0).getChildren().elementAt(8).getFirstChild().toPlainTextString();
		String year = date.substring(date.indexOf("年")-4, date.indexOf("年"));
		String month = date.substring(date.indexOf("期")-2, date.indexOf("期"));
		return year + "(" + month + ")";
	}
	
	//得到分类号
	public String getClassifyNum() throws ParserException
	{
		Parser parser = new Parser(parserstr);
		NodeFilter attrfilter = new HasAttributeFilter( "class", "break" );
		NodeList nodes = parser.extractAllNodesThatMatch(attrfilter);
		Node node = (Node) nodes.elementAt(0);
		NodeList linodes = node.getChildren();
		String s2 = null;
		for (int i = 0; i <linodes.size(); ++i)
		{
			Node textnode = (Node) linodes.elementAt(i);
			if (textnode.getFirstChild() != null)
			{
				String s1 = textnode.getFirstChild().toPlainTextString();
				if (s1.contains("分类号"))
					s2 = s1.substring(s1.indexOf('】')+1, s1.length());
			}
		}
		return s2;
	}
	
	// 得到论文题目
	public String getPaperTitle() throws ParserException
	{
		Parser parser = new Parser(parserstr);
		NodeFilter tagFilter = new TagNameFilter("span");
		NodeFilter attrfilter = new HasAttributeFilter( "id", "chTitle" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		
		if (nodes != null)
		{
			Node textnode = (Node) nodes.elementAt(0);
			if (textnode != null)
				return textnode.getFirstChild().toPlainTextString();
			else 
				return "";
		}
		return null;
	}
	
	// 得到关键词
	public String getKeyword() throws ParserException
	{
		Parser parser = new Parser(parserstr);
		NodeFilter tagFilter = new TagNameFilter("span");
		NodeFilter attrfilter = new HasAttributeFilter( "id", "ChDivKeyWord" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		NodeList anodes = ((Node)nodes.elementAt(0)).getChildren();
		String str = "";
		if (anodes != null)
		{
			for (int i = 0; i < anodes.size(); i=i+2)
			{
				Node textnode = (Node) anodes.elementAt(i);
				str += textnode.getFirstChild().toPlainTextString() + "; ";
				keywordCount++;
			}
		}
		return str;
	}
	
	public static void main(String[] args) throws ParserException
	{
		String str = "http://www.cnki.net/KCMS/detail/detail.aspx?QueryID=10&CurRec=42&recid=&filename=KJFY201303017&dbname=CJFD2013&dbcode=CJFQ&";
		
		ParseUtil parseutil = new ParseUtil(GrabUtil.getFromUrlStr(str));
		System.out.println(parseutil.getPubNum());
		System.out.println(parseutil.getClassifyNum());
		System.out.println(parseutil.getPaperTitle());
		System.out.println(parseutil.getKeyword());
		System.out.println(parseutil.getKeywordCount());

	}
}
