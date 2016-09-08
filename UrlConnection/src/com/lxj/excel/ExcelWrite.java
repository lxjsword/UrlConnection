package com.lxj.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.lxj.dao.PaperInfo;

public class ExcelWrite
{
	public static void createExcel(OutputStream os, List<PaperInfo> infos) throws RowsExceededException, WriteException, IOException
	{
		//����������
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //�����µ�һҳ
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //����Ҫ��ʾ������,����һ����Ԫ�񣬵�һ������Ϊ�����꣬�ڶ�������Ϊ�����꣬����������Ϊ����
        Label num = new Label(0,0,"���");
        sheet.addCell(num);
        Label pubnum = new Label(1,0,"�ڿ���");
        sheet.addCell(pubnum);
        Label classifynum = new Label(2,0,"�����");
        sheet.addCell(classifynum);
        Label title = new Label(3,0,"����������Ŀ");
        sheet.addCell(title);
        Label keyword = new Label(4,0,"���Ĺؼ���");
        sheet.addCell(keyword);
        Label count = new Label(5,0,"���Ĺؼ��ʸ���");
        sheet.addCell(count);
        
        for (int i = 0; i < infos.size(); ++i)
        {
        	PaperInfo tmp = infos.get(i);
        	Number lnum = new Number(0, i+1, i+1);
            sheet.addCell(lnum);
            Label lpubnum = new Label(1,i+1,tmp.getPubNum());
            sheet.addCell(lpubnum);
            Label lclassifynum = new Label(2,i+1,tmp.getClassifyNum());
            sheet.addCell(lclassifynum);
            Label ltitle = new Label(3,i+1,tmp.getPaperTitle());
            sheet.addCell(ltitle);
            Label lkeyword = new Label(4,i+1,tmp.getKeywords());
            sheet.addCell(lkeyword);
            Number lcount = new Number(5,i+1,tmp.getKeywordCount());
            sheet.addCell(lcount);
        }
        workbook.write();
        workbook.close();
        os.close();
	}
	
	public static List<String> readExcel(String url)
	{
		List<String> keywordList = new ArrayList<String>();
		try 
		{
            Workbook book = Workbook.getWorkbook(new File(url));
            // ��õ�һ�����������
            Sheet sheet = book.getSheet(0);
            // �õ���Ԫ��
            for (int j = 1; j < sheet.getRows(); j++) 
            {
                    Cell cell = sheet.getCell(4, j);
                    //System.out.print(cell.getContents() + "  ");
                    keywordList.add(cell.getContents());
            }
            book.close();
        } 
		catch (Exception e) 
		{
            System.out.println(e);
        }
		return keywordList;
	}
	
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException
	{
//		List<PaperInfo> infos = new ArrayList<PaperInfo>();
//		infos.add(new PaperInfo("2010(3)","32","�ط���","ds;dfs;erw;", 3));
//		infos.add(new PaperInfo("2010(2)","32h","��da����","ds;dfs;erw;", 3));
//		infos.add(new PaperInfo("2010(1)","33","�ط�hfd��","ds;dfs;erw;", 3));
//		infos.add(new PaperInfo("2010(5)","32h","�ط�fsfad��","ds;dfs;erw;", 3));
//		OutputStream os = new FileOutputStream("tj.xls");
//		ExcelWrite.createExcel(os, infos);
		readExcel("excel/�й�����131.xls");
		
	}
}
