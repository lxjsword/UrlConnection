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
	public static final String[] mostwords= {"�����о�; ",
		"��������; ",
		"����Ӣ; ",
		"�����ѧ; ",
		"�������; ",
		"��ѧ����; ",
		"����; ",
		"Ӣ��; ",
		"����ѧ; ",
		"����; ",
		"����ʵ��; ",
		"�Ƽ�����; ",
		"�������; ",
		"Ӣ��; ",
		"����; ",
		"����; ",
		"ԭ��; ",
		"�����; ",
		"�Ƽ�Ӣ��; ",
		"�컯; ",
		"����; ",
		"�Ļ�; ",
		"�ﾳ; ",
		"�黯; ",
		"�й�����; ",
		"����ԭ��; ",
		"ͬ������; ",
		"�����׼; ",
		"����; ",
		"��������; ",
		"����; ",
		"��ѧ�о�; ",
		"��ʶ��̬; ",
		"���Ͽ�; ",
		"Ӣ�뺺; "
	};
	private static int[][] matrix = new int[mostwords.length][mostwords.length];
	
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException
	{
		List<String> keywordList = ExcelWrite.readExcel("excel/�й�����131.xls");
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
		FileOutputStream os = new FileOutputStream("���ʾ���.xls");
		//����������
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //�����µ�һҳ
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //����Ҫ��ʾ������,����һ����Ԫ�񣬵�һ������Ϊ�����꣬�ڶ�������Ϊ�����꣬����������Ϊ����
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
