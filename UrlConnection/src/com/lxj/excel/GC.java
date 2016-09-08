package com.lxj.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class GC
{
	public static final String[] mostwords= {"翻译研究; ",
		"翻译理论; ",
		"汉译英; ",
		"翻译教学; ",
		"翻译策略; ",
		"文学翻译; ",
		"译文; ",
		"英译; ",
		"翻译学; ",
		"译者; ",
		"翻译实践; ",
		"科技翻译; ",
		"翻译过程; ",
		"英语; ",
		"口译; ",
		"汉语; ",
		"原文; ",
		"翻译家; ",
		"科技英语; ",
		"异化; ",
		"术语; ",
		"文化; ",
		"语境; ",
		"归化; ",
		"中国翻译; ",
		"翻译原则; ",
		"同声传译; ",
		"翻译标准; ",
		"汉译; ",
		"翻译能力; ",
		"翻译活动; ",
		"译学研究; ",
		"意识形态; ",
		"语料库; ",
		"英译汉; "
	};
	private static int[][] matrix = new int[mostwords.length][mostwords.length];
	
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException
	{
		List<String> keywordList = ExcelWrite.readExcel("excel/中国翻译131.xls");
		Iterator iterator = keywordList.iterator();
		String s = null;
		while (iterator.hasNext())
		{
			s = iterator.next().toString();
			
			System.out.println(s);
			for (int i = 0; i < mostwords.length; ++i)
			{
				for (int j = 0; j < mostwords.length; ++j)
				{
					if (s.contains(mostwords[i]) && s.contains(mostwords[j]) && i != j)
					{
						matrix[i][j]++;
					}
				}
			}
		}
		
		for (int i = 0; i < mostwords.length; ++i)
		{
			for (int j = 0; j < mostwords.length; ++j)
			{
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		FileOutputStream os = new FileOutputStream("共词矩阵.xls");
		//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        for (int i = 1; i <= mostwords.length; ++i)
        {
	        Label cnum = new Label(i,0,mostwords[i-1]);
	        sheet.addCell(cnum);
	        Label rnum = new Label(0,i,mostwords[i-1]);
	        sheet.addCell(rnum);
        }

        
        for (int i = 1; i <= mostwords.length; ++i)
        {
        	for (int j = 1; j <= mostwords.length; ++j)
			{
	            Number num = new Number(j,i,matrix[i-1][j-1]);
	            sheet.addCell(num);
			}
        }
        workbook.write();
        workbook.close();
        os.close();
	}
}
