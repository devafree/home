package com.devafree.epubapp.util;

import java.util.BitSet;


public class Word
{

	private String name = null;
	private int times = 0;
	private BitSet bitWordLevel = new BitSet(WordLevel.wordLevelName.length);

	public Word(String word)
	{
		this.name = word;
		bitWordLevel.clear();
	}
	
	public String getWordName()
	{
		return this.name;
	}

//	public void setWordName(String word)
//	{
//		this.name = word;
//	}
	
	public int getWordTimes()
	{
		return this.times;
	}
	
	public void addWordTimes()
	{
		this.times = this.times + 1;
	}
	
	public String getWordLevel()
	{
		StringBuffer strLevel = new StringBuffer();
		
		for (int i = 0; i < WordLevel.wordLevelName.length; i++)
		{
			if (bitWordLevel.get(i))
			{
				strLevel.append(WordLevel.wordLevelName[i]).append(",");
			}
		}
		if (strLevel.length() == 0)
		{
			//"all bit is false" means: this word is "Other" level.
			strLevel.append("Other");
		}
		else
		{
			strLevel.deleteCharAt(strLevel.length()-1);
		}
		return strLevel.toString();
	}
	
	public void setWordLevel(String levels)
	{
		String[] strlevels = levels.split("(,|\\s)+");
		for (int i = 0; i < strlevels.length; i++)
		{
			for (int j = 0; j < WordLevel.wordLevelName.length; j++)
			{
				if (strlevels[i].equals(WordLevel.wordLevelName[j]))
				{
					this.bitWordLevel.set(j);
				}
			}
		}
	}

	public void setWordLevel(int index)
	{
		this.bitWordLevel.set(index);
	}
	
	public String printWord()
	{
		StringBuffer strWord = new StringBuffer();
		strWord.append("<")
				.append(name).append(", ")
				.append(times).append(", ")
				.append(this.getWordLevel())
				.append(">");
		return strWord.toString();
	}
	
}