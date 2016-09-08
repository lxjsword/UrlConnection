package com.lxj.httphelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpHelperTest
{
	public static void main(String[] args)
	{
		try 
		{  
			String url = "http://dingdong.ganji.com/3g/dialogue.html?userId=429447064&puid=1391139577&ifid=gj3g_detail_zxgt&qq-pf-to=pcqq.c2c";
			HttpRequester request = new HttpRequester();
			Map<String, String> params = new HashMap<String, String>();
			
			Map<String, String> propertys = new HashMap<String, String>();
			
			
			HttpRespons hr = request.sendGet(url, params, propertys); 
			HttpHelperTest.saveHtml("hahaha.html", hr.getContent());
          
//			String url = "http://epub.cnki.net/kns/brief/brief.aspx";
//			Map<String, String> params = new HashMap<String, String>();
//			
//			params.put("pagename", "ASP.brief_default_result_aspx");
//			params.put("dbPrefix", "SCDB");
//			params.put("dbCatalog", "中国学术文献网络出版总库");
//			params.put("ConfigFile", "SCDBINDEX.xml");
//			params.put("research", "off");
//			
//			params.put("t", "1429080121420");
//			params.put("keyValue", "中国翻译");
//			params.put("S", "1");
//			params.put("DisplayMode", "listmode");
//			
//			Map<String, String> propertys = new HashMap<String, String>();
//			propertys.put("Connection", "keep-alive");
//			propertys.put("Cookie", "RsPerPage=50; KNS_DisplayModel=listmode; ASP.NET_SessionId=4ihijm55gezojg45bq1nhk45; LID=WEEvREcwSlJHSldRa1FhdkJPcUVLWm5mdUE2YWlRYVVpdjcvWE95R2ZzdDducjlIZlI4WEZlN2I4NnRQOWtaZCtBPT0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4IQMovwHtwkF4VYPoHbKxJw!!; c_m_LinID=LinID=WEEvREcwSlJHSldRa1FhdkJPcUVLWm5mdUE2YWlRYVVpdjcvWE95R2ZzdDducjlIZlI4WEZlN2I4NnRQOWtaZCtBPT0=$9A4hF_YAuvQ5obgVAqNKPCYcEjKensW4IQMovwHtwkF4VYPoHbKxJw!!&ot=04/19/2015 16:44:46");
//
//            HttpRequester request = new HttpRequester();  
//            HttpRespons hr = request.sendGet(url, params, propertys);  
            
//            System.out.println(hr.getUrlString());  
//            System.out.println(hr.getProtocol());  
//            System.out.println(hr.getHost());  
//            System.out.println(hr.getPort());  
//            System.out.println(hr.getContentEncoding());  
//            System.out.println(hr.getMethod());  
//            System.out.println(hr.getContent());  
//            HttpHelperTest.saveHtml("haha.html", hr.getContent());
        } 
		catch (Exception e) 
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
}
