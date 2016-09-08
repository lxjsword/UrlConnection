package com.lxj.dao;

public class PaperInfo
{
	private String pubNum;
	private String classifyNum;
	private String paperTitle;
	private String keywords;
	private int keywordCount = 0;
	
	public PaperInfo(String pubNum, String classifyNum, String paperTitle,
			String keywords, int keywordCount)
	{
		this.pubNum = pubNum;
		this.classifyNum = classifyNum;
		this.paperTitle = paperTitle;
		this.keywords = keywords;
		this.keywordCount = keywordCount;
	}
	public String getPubNum()
	{
		return pubNum;
	}
	public void setPubNum(String pubNum)
	{
		this.pubNum = pubNum;
	}
	public String getClassifyNum()
	{
		return classifyNum;
	}
	public void setClassifyNum(String classifyNum)
	{
		this.classifyNum = classifyNum;
	}
	public String getPaperTitle()
	{
		return paperTitle;
	}
	public void setPaperTitle(String paperTitle)
	{
		this.paperTitle = paperTitle;
	}
	public String getKeywords()
	{
		return keywords;
	}
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}
	public int getKeywordCount()
	{
		return keywordCount;
	}
	public void setKeywordCount(int keywordCount)
	{
		this.keywordCount = keywordCount;
	}
	
	
}
