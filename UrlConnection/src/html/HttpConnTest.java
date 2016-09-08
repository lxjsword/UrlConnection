package html;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

public class HttpConnTest
{
	private List<URL> urlList = new ArrayList<URL>();
	private int count = 0;

	private void doHttpConn() throws Exception
	{
		count++;
		URL url = new URL("http://epub.cnki.net/kns/brief/brief.aspx?pagename="
				+ "ASP.brief_default_result_aspx&dbPrefix=SCDB&dbCatalog="
				+ "�й�ѧ��������������ܿ�&ConfigFile=SCDBINDEX.xml&research=off&t="
				+ "1429169091112&keyValue=�й�����&S=1&DisplayMode=listmode");
		if (!urlList.isEmpty())
		{
			url = urlList.get(0);
		}
		String urlRegx = "(http|www|ftp)(://)?(//w+(-//w+)*)"
				+ "(//.(//w+(-//w+)*))*((://d+)?)(/(//w+(-//w+)*))"
				+ "*(//.?(//w)*)(//?)?(((//w*%)*(//w*//?)*(//w*:)"
				+ "*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*"
				+ "(//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*"
				+ "(//w*&)*(//w*-)*(//w*=)*)*(//w*)*)";
		Pattern p = Pattern.compile(urlRegx, Pattern.CASE_INSENSITIVE);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		// ��ӡ������Ӧ��ͷ���ļ�
		Map<String, List<String>> header = conn.getHeaderFields();
		for (String key : header.keySet())
		{
			System.out.println(key + ":" + header.get(key));
		}
		// ��ӡ��Ӧ����
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String str = null;
		while ((str = br.readLine()) != null)
		{
			System.out.println(str);
			Matcher m = p.matcher(str);
			while (m.find())
			{
				urlList.add(new URL(m.group(0)));
			}
		}
		conn.disconnect();
		System.out.println("-----------------------");
		System.out.println(urlList.size());
		for (URL aurl : urlList)
		{
			System.out.println(aurl.toString());
		}
	}

	public static void main(String[] args) throws Exception
	{
		HttpConnTest hct = new HttpConnTest();
		while (hct.count <= 3)
		{
			hct.doHttpConn();
		}
		System.out.println("---DONE---" + hct.count);
	}
}