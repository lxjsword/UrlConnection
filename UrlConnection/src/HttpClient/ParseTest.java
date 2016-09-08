package HttpClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParseTest
{
	public static final String URL_STR = "http://www.cnki.net/KCMS/detail/detail.aspx?QueryID=1&CurRec=103&recid=&filename=ZGFY200901036&dbname=CJFD2009&dbcode=CJFQ&pr=&urlid=&yx=&uid=WEEvREcwSlJHSldRa1FhaXZ3RTRwTytXWUdtYjV3eEtReE5GSkJmaVlXS2Z6TkpoTGl5UVNkUks3RXA5M0dIbEFRPT0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4IQMovwHtwkF4VYPoHbKxJw!!&v=MDM0MzBqTXJvOUdZb1I4ZVgxTHV4WVM3RGgxVDNxVHJXTTFGckNVUkwrZlkrZHFGeS9uV3J2QlB5ck5kN0c0SHQ=";

	public static void main(String[] args)
	{
		try
		{
//			URL url = new URL("URL_STR");
//			URLConnection url_con = url.openConnection();
//			url_con.setDoOutput(true);
//			url_con.setReadTimeout(10 * 1000);
//			InputStream htm_in = url_con.getInputStream();
//
//			String htm_str = InputStream2String(htm_in, "utf-8");
			
//			getPubNum(htm_str);
//			showClassfyNum(htm_str);
//			showTitle(htm_str);
//			showKeyword(htm_str);
			
			InputStream in = new FileInputStream("093.html");
			String htm_str = InputStream2String(in, "utf-8");
			showLinks(htm_str);
	
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String InputStream2String(InputStream in_st, String charset)
			throws IOException
	{
		BufferedReader buff = new BufferedReader(new InputStreamReader(in_st,
				charset));
		StringBuffer res = new StringBuffer();
		String line = "";
		while ((line = buff.readLine()) != null)
		{
			res.append(line);
		}
		return res.toString();
	}
	
	public static void showTitle(String htm_str) throws ParserException
	{
		Parser parser = new Parser(htm_str);
		NodeFilter tagFilter = new TagNameFilter("span");
		NodeFilter attrfilter = new HasAttributeFilter( "id", "chTitle" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		
		if (nodes != null)
		{
			for (int i = 0; i < nodes.size(); i++)
			{
				Node textnode = (Node) nodes.elementAt(i);
				System.out.println("getTitle: " + textnode.getFirstChild().toPlainTextString());
			}
		}
		
	}
	
	public static void showKeyword(String htm_str) throws ParserException
	{
		Parser parser = new Parser(htm_str);
		NodeFilter tagFilter = new TagNameFilter("span");
		NodeFilter attrfilter = new HasAttributeFilter( "id", "ChDivKeyWord" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		NodeList anodes = ((Node)nodes.elementAt(0)).getChildren();
		System.out.print("getKeyword: ");
		int count = 0;
		if (anodes != null)
		{
			for (int i = 0; i < anodes.size(); i=i+2)
			{
				Node textnode = (Node) anodes.elementAt(i);
				System.out.print(textnode.getFirstChild().toPlainTextString() + "; ");
				count++;
			}
		}
		System.out.println("\nKeyword num is: " + count);
	}
	
	public static void showClassfyNum(String htm_str) throws ParserException
	{
		Parser parser = new Parser(htm_str);
		NodeFilter attrfilter = new HasAttributeFilter( "class", "break" );
		NodeList nodes = parser.extractAllNodesThatMatch(attrfilter);
		Node node = (Node) nodes.elementAt(0);
		NodeList linodes = node.getChildren();
		Node textnode = (Node) linodes.elementAt(1);
		String s1 = textnode.getFirstChild().toPlainTextString();
		String s2 = s1.substring(s1.indexOf('】')+1, s1.length());
		System.out.println("getClassfyNum: " + s2);
	}
	
	public static void getPubNum(String htm_str) throws ParserException
    {
		Parser parser = new Parser(htm_str);
		NodeFilter tagFilter = new TagNameFilter("div");
		NodeFilter attrfilter = new HasAttributeFilter( "class", "detailLink" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		String date = nodes.elementAt(0).getChildren().elementAt(8).getFirstChild().toPlainTextString();
		String year = date.substring(date.indexOf("年")-4, date.indexOf("年"));
		String month = date.substring(date.indexOf("期")-2, date.indexOf("期"));
		System.out.println("getPubNum: " + year + "(" + month + ")");
    }
	
	public static void showLinks(String htm_str) throws ParserException
	{
		Parser parser = new Parser(htm_str);
		NodeFilter tagFilter = new TagNameFilter("a");
		NodeFilter attrfilter = new HasAttributeFilter( "class", "fz14" );
		NodeFilter andfilter = new AndFilter(tagFilter, attrfilter);
		NodeList nodes = parser.extractAllNodesThatMatch(andfilter);
		if (nodes != null)
		{
			for (int i = 0; i < nodes.size(); i++)
			{
				Node textnode = (Node) nodes.elementAt(i);
				System.out.println(replaceLinks(textnode.getText()));
			}
		}
	}
	
	public static String replaceLinks(String originalLink)
	{
		String newLink = "http://www.cnki.net/KCMS/" + 
				originalLink.substring(originalLink.indexOf("detail"), originalLink.indexOf("pr=")) +
				originalLink.substring(originalLink.indexOf("pr=")+20, originalLink.length());
		return newLink;	
	}
}
