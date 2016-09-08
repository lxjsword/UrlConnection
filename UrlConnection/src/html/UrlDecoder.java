package html;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecoder
{
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		String originalStr = "http://epub.cnki.net/kns/brief/brief.aspx?"
				+ "pagename=ASP.brief_default_result_aspx&dbPrefix="
				+ "SCDB&dbCatalog=%E4%B8%AD%E5%9B%BD%E5%AD%A6%E6%9C%AF"
				+ "%E6%96%87%E7%8C%AE%E7%BD%91%E7%BB%9C%E5%87%BA%E7"
				+ "%89%88%E6%80%BB%E5%BA%93&ConfigFile=SCDBINDEX.xml&research="
				+ "off&t=1429169091112&keyValue=%E4%B8%AD%E5%9B%BD%E7%BF%BB%E8"
				+ "%AF%91&S=1&DisplayMode=listmode";
		
		String output = URLDecoder.decode(originalStr, "UTF-8");
		System.out.println(output);
	}
}
