package com.util.chagetype;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class SingleToDouble {
	
	public static String toDouble(String target){
		
		Pattern p1 = Pattern.compile("\\w*[aeioub]y");    //用于匹配以元音+y结尾的单词.
		Pattern p2 = Pattern.compile("(\\w*[^aeioub])y");	//匹配以非元音+y结尾的单词.
		Pattern p3 = Pattern.compile("\\w*[|[sx]|ch|sh]");  //匹配以s,x,ch,sh结尾的单词
		Pattern p4 = Pattern.compile("\\w*[f|fe]");			//匹配以f,fe结尾的单词
		Matcher m1 = p1.matcher(target);
		Matcher m2 = p2.matcher(target);
		Matcher m3 = p3.matcher(target);
		Matcher m4 = p4.matcher(target);
		
		if (target.equals("potato")||target.equals("tomato")) {  //还有很多类似potato这样的词语以o结尾但是加es的就不再列举
			return target + "ES";
		} else if (target.equals("belief")||target.equals("roof")) {    //还有很多以f结尾但是加s的不再列举
			return target + "S";
		} else if (m4.matches()) {
			if (target.lastIndexOf("f")== target.length()-1) {
				return target.substring(0,target.length()-1)+"VES";
			}else {
				return target.substring(0,target.length()-2)+"VES";
			}
		}else if (m1.matches()) {
			return target.substring(0, target.length()-1) + "IES";
		} else if (m2.matches()) {
			return  target.replace(target, target+"S");	
		} else if (m3.matches()){
			return target.replace(target, target+"ES");
		} else {
			return target +"S";
		}
		
		
	}
	
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

            while ((tempString = reader.readLine()) != null)
            {
            	tempString = SingleToDouble.toDouble(tempString.trim());
            	
            	words.add(tempString);
            	
            	System.out.println(tempString);
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

	
	public static void main(String[] args){
		
		String filePath = "F:\\workspace\\words\\bin\\a.txt";
		String wlDstFileName = "F:\\workspace\\words\\bin\\a_output.txt";
		
		readFileByLines(filePath);
		
    	File file=new File(wlDstFileName);
		if(file.exists())
		{
			try
			{
				FileWriter writeFile = new FileWriter(file,false);
				for (String word: words)
				{
//					System.out.println(word);
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
		
	}

}
