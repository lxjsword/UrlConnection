package com.lxj.util;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class LinksUtil
{
	// 得到链接列表
	public static List<String> getLinks(String htm_str) throws ParserException
	{
		Parser parser = new Parser(htm_str);
		NodeFilter tagFilter = new TagNameFilter("a");
		NodeFilter attrfilter = new HasAttributeFilter( "class", "fz14" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		List<String> list = new ArrayList<String>();
		if (nodes != null)
		{
			for (int i = 0; i < nodes.size(); i++)
			{
				Node textnode = (Node) nodes.elementAt(i);
				list.add(replaceLinks(textnode.getText()));
			}
		}
		return list;
	}
	// 将原始链接地址转换为正确地址
	public static String replaceLinks(String originalLink)
	{
		String newLink = "http://www.cnki.net/KCMS/" + 
				originalLink.substring(originalLink.indexOf("detail"), originalLink.indexOf("pr=")) +
				originalLink.substring(originalLink.indexOf("pr=")+20, originalLink.length());
		return newLink;	
	}
	
}
