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
		//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        Label num = new Label(0,0,"序号");
        sheet.addCell(num);
        Label pubnum = new Label(1,0,"期刊号");
        sheet.addCell(pubnum);
        Label classifynum = new Label(2,0,"分类号");
        sheet.addCell(classifynum);
        Label title = new Label(3,0,"论文中文题目");
        sheet.addCell(title);
        Label keyword = new Label(4,0,"中文关键字");
        sheet.addCell(keyword);
        Label count = new Label(5,0,"中文关键词个数");
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
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            // 得到单元格
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
//		infos.add(new PaperInfo("2010(3)","32","地方撒","ds;dfs;erw;", 3));
//		infos.add(new PaperInfo("2010(2)","32h","地da方撒","ds;dfs;erw;", 3));
//		infos.add(new PaperInfo("2010(1)","33","地方hfd撒","ds;dfs;erw;", 3));
//		infos.add(new PaperInfo("2010(5)","32h","地方fsfad撒","ds;dfs;erw;", 3));
//		OutputStream os = new FileOutputStream("tj.xls");
//		ExcelWrite.createExcel(os, infos);
		readExcel("excel/中国翻译131.xls");
		
	}
}
