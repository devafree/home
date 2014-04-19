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
		
		Pattern p1 = Pattern.compile("\\w*[aeioub]y");    //����ƥ����Ԫ��+y��β�ĵ���.
		Pattern p2 = Pattern.compile("(\\w*[^aeioub])y");	//ƥ���Է�Ԫ��+y��β�ĵ���.
		Pattern p3 = Pattern.compile("\\w*[|[sx]|ch|sh]");  //ƥ����s,x,ch,sh��β�ĵ���
		Pattern p4 = Pattern.compile("\\w*[f|fe]");			//ƥ����f,fe��β�ĵ���
		Matcher m1 = p1.matcher(target);
		Matcher m2 = p2.matcher(target);
		Matcher m3 = p3.matcher(target);
		Matcher m4 = p4.matcher(target);
		
		if (target.equals("potato")||target.equals("tomato")) {  //���кܶ�����potato�����Ĵ�����o��β���Ǽ�es�ľͲ����о�
			return target + "ES";
		} else if (target.equals("belief")||target.equals("roof")) {    //���кܶ���f��β���Ǽ�s�Ĳ����о�
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
     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
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
