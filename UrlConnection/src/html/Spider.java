package html;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider
{
	 public static void main(String[] args) throws URISyntaxException
	 {
	        
	        String filepath = "124.html";
	        
	        String url_str = "http://epub.cnki.net/kns/brief/brief.aspx?pagename="
				+ "ASP.brief_default_result_aspx&dbPrefix=SCDB&dbCatalog="
				+ "中国学术文献网络出版总库&ConfigFile=SCDBINDEX.xml&research=off&t="
				+ "1429169091112&keyValue=中国翻译&S=1&DisplayMode=listmode";

	        URL url = null;
	        try 
	        {
	            url = new URL(url_str);
	        } catch (MalformedURLException e) 
	        {
	            e.printStackTrace();
	        }
	        
	        String charset = "utf-8";
	        int sec_cont = 1000;
	        try 
	        {
	        	CookieManager manager = new CookieManager();
	        	CookieHandler.setDefault(manager);
	        	CookieStore store = manager.getCookieStore();
	        	List<HttpCookie> cookies = new ArrayList<HttpCookie>();
	        	cookies.add(new HttpCookie("RsPerPage", "50"));
	        	cookies.add(new HttpCookie("KNS_DisplayModel", "listmode"));
	        	cookies.add(new HttpCookie("ASP.NET_SessionId", "se03hwvmcuiqrn55ddxt4qa3"));
	        	cookies.add(new HttpCookie("LID", "WEEvREcwSlJHSldRa1FhdXNXR"
	        			+ "3EvRjdvdkc2QVFwQWI5Q3RRNGh4UG9OV1BDcUQ"
	        			+ "xUFJHZkorTy9yS1FGRWRLRjhRPT0="
	        			+ "$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4IQ"
	        			+ "MovwHtwkF4VYPoHbKxJw!!"));
	        	cookies.add(new HttpCookie("c_m_LinID", "LinID=WEEvREcwSlJHSldR"
	        			+ "a1FhdXNXR3EvRjdvdkc2QVFwQWI5Q3RRNGh4UG9OV1BDcUQxUFJHZ"
	        			+ "korTy9yS1FGRWRLRjhRPT0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKens"
	        			+ "W4IQMovwHtwkF4VYPoHbKxJw!!&ot=04/16/2015 15:51:13"));
	        	for (int i = 0; i <cookies.size(); ++i)
	        	{
	        		store.add(url.toURI(), cookies.get(i));
	        	}
	            URLConnection url_con = url.openConnection();
	            url_con.setDoOutput(true);
	            url_con.setReadTimeout(10 * sec_cont);
	            //url_con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
	            InputStream htm_in = url_con.getInputStream();
	            
	            String htm_str = InputStream2String(htm_in,charset);
	            saveHtml(filepath,htm_str);
//	            System.out.println(getTitle(htm_str));
//	            System.out.println(getKeyword(htm_str));
//	            System.out.println(getClassifyNum(htm_str));
//	            System.out.println(getPubNum(htm_str));
	            htm_in.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }

	    }

	    public static void saveHtml(String filepath, String str)
	    {
	        
	        try 
	        {
	            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), "utf-8");
	            outs.write(str);
	            System.out.print(str);
	            outs.close();
	        } 
	        catch (IOException e) 
	        {
	            System.out.println("Error at save html...");
	            e.printStackTrace();
	        }
	    }

	    public static String InputStream2String(InputStream in_st,String charset) throws IOException
	    {
	        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
	        StringBuffer res = new StringBuffer();
	        String line = "";
	        while((line = buff.readLine()) != null)
	        {
	            res.append(line);
	        }
	        return res.toString();
	    }
	    
	    public static String getTitle(String s) {  
	        String regex;  
	        String title = "";  
	        List<String> list = new ArrayList<String>();  
	        regex = "<span id=\"chTitle\">.*?</span>";
	        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);  
	        Matcher ma = pa.matcher(s);  
	        while (ma.find()) 
	        {  
	            list.add(ma.group());  
	        }  
	        for (int i = 0; i < list.size(); i++) 
	        {  
	            title = title + list.get(i);  
	        }  
	        return title;  
	    }  

	    public static String getKeyword(String s)
	    {
	    	String regex;  
	        String keyword = "";  
	        List<String> list = new ArrayList<String>();  
	        regex = "<a class=\"KnowledgeNetLink\" target=\"_blank\""
	        + ".*?" + "\">.*?</a>";
	        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);  
	        Matcher ma = pa.matcher(s);  
	        while (ma.find()) {  
	            list.add(ma.group());  
	        }  
	        for (int i = 3; i < list.size(); i++)
	        {  
	        	keyword = keyword + list.get(i) + "\n";  
	        }  
	        return keyword;  
	    }
	    
	    public static String getClassifyNum(String s)
	    {
	    	String regex;  
	        String clsNum = "";  
	        List<String> list = new ArrayList<String>();  
	        regex = "<ul class=\"break\">.*?</ul>";
	        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);  
	        Matcher ma = pa.matcher(s);  
	        while (ma.find()) 
	        {  
	            list.add(ma.group());  
	        }  	         
	        for (int i = 0; i < list.size(); i++) 
	        {  
	        	clsNum = clsNum + list.get(i);  
	        }    	        
	        return clsNum;  
	    }
	    
	    public static String getPubNum(String s)
	    {
	    	String regex;  
	        String pubNum = "";  
	        List<String> list = new ArrayList<String>();  
	        regex = "<a onclick=.*>.*?</a>";
	        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);  
	        Matcher ma = pa.matcher(s);  
	        while (ma.find()) 
	        {  
	            list.add(ma.group());  
	        }  	         
	        for (int i = 0; i < list.size(); i++) 
	        {  
	        	pubNum = pubNum + list.get(i);  
	        }    	        
	        return pubNum;  
	    }
}
