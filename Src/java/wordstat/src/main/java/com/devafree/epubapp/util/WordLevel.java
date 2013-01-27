package com.devafree.epubapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.devafree.epubapp.tracetool.TraceTool;


public class WordLevel 
{
	private static WordLevel uniqueInstance = null;
	private static String MODULENAME = "WordLevel";
	
	public static final String wordLevelName[] = {"CET4", "CET6", "GRE"};
	private static List<String> wordsCET4 = new ArrayList<String>();
	private static List<String> wordsCET6 = new ArrayList<String>();
	private static List<String> wordsGRE  = new ArrayList<String>();
	private static List<List<String>> wordsList = new ArrayList<List<String>>();
	
	private WordLevel()
	{
		wordsList.add(wordsCET4);
		wordsList.add(wordsCET6);
		wordsList.add(wordsGRE);
		for (int i = 0; i < wordLevelName.length; i++)
		{
			readWords(wordLevelName[i], wordsList.get(i));
		}
	}
	
	private boolean readWords(String strLevelName, List<String> words)
	{
		String fileName = ".\\bin\\" + strLevelName + ".txt";
		File file = new File(fileName);
		if (!file.exists())
		{
			TraceTool.writeTrace(MODULENAME, "Error: file isn't exist!" + fileName);
			return false;
		}
		
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String word = null;
            while ((word = reader.readLine()) != null)
            {
            	words.add(word);
            }
            reader.close();
        }
        catch (IOException e) 
        {
        	TraceTool.writeTrace(MODULENAME, e.toString());
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {}
            }
        }
        
        return true;
	}
	
    public static WordLevel getInstance() {
        if (uniqueInstance == null)
        {
            uniqueInstance = new WordLevel();
        }
        return uniqueInstance;
     }
    
//	public static String firstLetterToUpper(String string) 
//	{
//		char[] buffer = string.toCharArray();
//		buffer[0] = Character.toUpperCase(string.charAt(0));
//		return new String(buffer);
//	}
//	
    public static String setWordLevels(Word word)
    {
    	String wordLevels = new String();
    	for (int i = 0; i < wordLevelName.length; i++)
    	{
    		if (wordsList.get(i).contains(word.getWordName()))
    		{
    			word.setWordLevel(i);
        		wordLevels = wordLevels + wordLevelName[i] + ",";
    		}
    	}
    	return wordLevels;
    }
	
}