package com.lxj.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GrabUtil
{
	// 将输入流转化为字符串
	private static String InputStream2String(InputStream in_st, String charset)
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
	
	// 从url中得到html字符串
	@SuppressWarnings("finally")
	public static String getFromUrlStr(String urlstr)
	{
		String htm_str = null;
		try
		{
			URL url = new URL(urlstr);
			URLConnection url_con = url.openConnection();
			url_con.setDoOutput(true);
			url_con.setReadTimeout(10 * 1000);
			InputStream htm_in = url_con.getInputStream();

			htm_str = InputStream2String(htm_in, "utf-8");
			htm_in.close();
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return htm_str;
		}
	}
	
	// 从本地文件中得到html字符串
	public static String getFromFileStr(String filestr) throws IOException
	{
		InputStream in = new FileInputStream(filestr);
		String htm_str = InputStream2String(in, "utf-8");
		return htm_str;
	}
}
