package com.devafree.wordlevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordLevelPrase
{
	
	private static List<String> words = new ArrayList<String>();
	
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
		if (!file.exists())
		{
			System.out.println("Error:file doesn't exisit!" + fileName);
		}
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null)
            {
                System.out.println("line " + line + ": " + tempString);
                String[] tmps = tempString.split("\\s+");
                if (!tmps[0].equals(""))
                {
                	System.out.println(tmps[0]);
                	words.add(tmps[0]);
                }
                else if(!tmps[1].equals(""))
                {
                	System.out.println(tmps[1]);
                	words.add(tmps[1]);
                }
                line++;
            }
            reader.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
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
                {
                }
            }
        }
    }

	public static void main( String[] args )
	{
		String wlOrgFileName = "GRE_org.txt";
		String wlDstFileName = "GRE.txt";
		readFileByLines(wlOrgFileName);
		
    	File file=new File(wlDstFileName);
		if(file.exists())
		{
			try
			{
				FileWriter writeFile = new FileWriter(file,false);
				for (String word: words)
				{
					System.out.println(word);
					writeFile.write(word);
					writeFile.write("\r\n");
					writeFile.flush();
				}
				writeFile.flush();
				writeFile.close();
			}
			catch(Exception e)
			{
				e.fillInStackTrace();
			}
		}
		

		System.out.println("end!");
	}
}