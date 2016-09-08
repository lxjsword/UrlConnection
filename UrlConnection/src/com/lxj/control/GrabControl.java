package com.lxj.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.htmlparser.util.ParserException;

import com.lxj.dao.PaperInfo;
import com.lxj.excel.ExcelWrite;
import com.lxj.util.GrabUtil;
import com.lxj.util.LinksUtil;
import com.lxj.util.ParseUtil;

public class GrabControl
{
	private String reslovedStr;
	private String savePath;
	
	public GrabControl(String resolvedStr, String savePath)
	{
		this.reslovedStr = resolvedStr;
		this.savePath = savePath;
	}
	
	public void startGrab() throws IOException, ParserException, RowsExceededException, WriteException
	{
		String link_str = GrabUtil.getFromFileStr(reslovedStr);
		List<String> l_links = LinksUtil.getLinks(link_str);
		Iterator iter = l_links.iterator();
		int count = 0;
		List<PaperInfo> infos = new ArrayList<PaperInfo>();
		System.out.println(reslovedStr);
		while ( iter.hasNext())
		{
			String htm_str = GrabUtil.getFromUrlStr((String)iter.next());
			ParseUtil parseutil = new ParseUtil(htm_str);
			infos.add(new PaperInfo(parseutil.getPubNum(),
					parseutil.getClassifyNum(),parseutil.getPaperTitle(),
					parseutil.getKeyword(), parseutil.getKeywordCount()));
			System.out.print(parseutil.getPubNum() + "  ");
			System.out.print(parseutil.getClassifyNum() + "  ");
			System.out.print(parseutil.getPaperTitle() + "  ");
			System.out.print(parseutil.getKeyword() + "  ");
			System.out.print(parseutil.getKeywordCount() + "  ");
			System.out.println();
			System.out.println(count++);
		}
		//System.out.println(count);
		
		OutputStream os = new FileOutputStream(savePath);
		ExcelWrite.createExcel(os, infos);
		os.close();
	}
	
	public static void main(String[] args) throws ParserException, IOException, RowsExceededException, WriteException
	{
		//"外语教学与研究.html", "外国语.html", "外语界.html", "现代外语.html", "外语与外语教学.html","外语学刊.html","外语教学.html",
		//"解放军外国语学院学报.html",
		//"外语教学与研究.xls", "外国语.xls", "外语界.xls","现代外语.xls", "外语与外语教学.xls","外语学刊.xls","外语教学.xls",
		//"解放军外国语学院学报.xls",
//		String[] s1 = {"外语研究.html","外语电化教学.html","中国外语.html",
//				"外国语文.html","外语教学理论与实践.html","山东外语教学.html"}; 
//		String[] s2 = {"外语研究.xls","外语电化教学.xls","中国外语.xls",
//				"外国语文.xls","外语教学理论与实践.xls","山东外语教学.xls"}; 
//		
//		for (int i = 1; i <=3; ++i)
//		{
//			GrabControl controller = new GrabControl(String.format("四川外语学院学报%d.html", i), String.format("四川外语学院学报%d.xls", i));
//			controller.startGrab();
//		}
		GrabControl controller = new GrabControl("国外外语教学.html", "国外外语教学.xls");
		controller.startGrab();
		
	}
}
