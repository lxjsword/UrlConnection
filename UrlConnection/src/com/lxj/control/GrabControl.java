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
		//"�����ѧ���о�.html", "�����.html", "�����.html", "�ִ�����.html", "�����������ѧ.html","����ѧ��.html","�����ѧ.html",
		//"��ž������ѧԺѧ��.html",
		//"�����ѧ���о�.xls", "�����.xls", "�����.xls","�ִ�����.xls", "�����������ѧ.xls","����ѧ��.xls","�����ѧ.xls",
		//"��ž������ѧԺѧ��.xls",
//		String[] s1 = {"�����о�.html","����绯��ѧ.html","�й�����.html",
//				"�������.html","�����ѧ������ʵ��.html","ɽ�������ѧ.html"}; 
//		String[] s2 = {"�����о�.xls","����绯��ѧ.xls","�й�����.xls",
//				"�������.xls","�����ѧ������ʵ��.xls","ɽ�������ѧ.xls"}; 
//		
//		for (int i = 1; i <=3; ++i)
//		{
//			GrabControl controller = new GrabControl(String.format("�Ĵ�����ѧԺѧ��%d.html", i), String.format("�Ĵ�����ѧԺѧ��%d.xls", i));
//			controller.startGrab();
//		}
		GrabControl controller = new GrabControl("���������ѧ.html", "���������ѧ.xls");
		controller.startGrab();
		
	}
}
