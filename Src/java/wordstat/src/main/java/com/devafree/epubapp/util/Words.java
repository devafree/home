package com.devafree.epubapp.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import nl.siegmann.epublib.Constants;

import com.devafree.epubapp.tracetool.TraceTool;

public class Words
{
	private final String MODULENAME = "Words";
	private static final Map<String, Word> words = new TreeMap<String, Word>();
	
	public Words()
	{}
	
	public void addWord(String wordName)
	{
		String wordLowerCase = wordName.toLowerCase();
		if (!wordLowerCase.equals(""))
		{
			Word word = words.get(wordLowerCase);
			if (word == null)
			{
				word = new Word(wordLowerCase);
				words.put(wordLowerCase, word);
			}
			
			word.addWordTimes();
		}
	}
	
	public int getWordsTotalTimes()
	{
		int sum = 0;
		for (Map.Entry<String, Word> wordpair: words.entrySet())
		{
			sum = sum + wordpair.getValue().getWordTimes();
		}
		return sum;
	}
	
	public void htmlToWords(byte[] bytes)
	{
		String strBytes = new String();
		try
		{
			strBytes = new String(bytes, Constants.CHARACTER_ENCODING);
		}
		catch(UnsupportedEncodingException e)
		{
			TraceTool.writeTrace(MODULENAME, e.toString());
		}
		
		TraceTool.writeTrace(MODULENAME, strBytes);
		String txtBytes = HtmlToTxt.html2text(strBytes);
		String[] strWords = txtBytes.split("([.,!?:;/\"-]|\\s)+");
		for (String word : strWords)
		{
			//System.out.println("word="+word);
			addWord(word);
		}
		TraceTool.writeTrace(MODULENAME, "prase html to words success!");
	}
	
	public String printAllWords()
	{
		StringBuffer strWords = new StringBuffer();
		for (Map.Entry<String, Word> wordpair: words.entrySet())
		{
			strWords.append(wordpair.getValue().printWord()).append("\n");
		}
		return strWords.toString();
	}
	
	
	public void setWordsLevels()
	{
		WordLevel.getInstance();
		for (Map.Entry<String, Word> wordpair: words.entrySet())
		{
			WordLevel.setWordLevels(wordpair.getValue());
		}
	}
	
}