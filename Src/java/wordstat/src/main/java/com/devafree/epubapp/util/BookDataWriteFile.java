package com.devafree.epubapp.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import com.devafree.epubapp.tracetool.TraceTool;

public class BookDataWriteFile implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6962099052044119451L;
	private static final String MODULENAME = "BookDataWriteFile";
	
	private static String bookDataDirName = ".\\bookData";
	private static String bookDataFileName;

	public BookDataWriteFile(String fileName)
	{
		bookDataFileName = bookDataDirName + "\\wordstat_" + fileName + ".txt";
		
		File dir= new File(bookDataDirName);
		if(!dir.exists())
		{
			dir.mkdir();
		}

		File file=new File(bookDataFileName);
		if(file.exists())
		{
			//delete old wordstat file
			file.delete();
		}
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				TraceTool.writeTrace(MODULENAME, e.toString());
			}
		}
	}
	
	//写数据文件
	public static void writeData(String msg)
	{
		File file=new File(bookDataFileName);
		if(!file.exists())
		{
			TraceTool.writeTrace("WordStat", "write file isn't exist!");
		}
		
		try
		{
			FileWriter writeFile = new FileWriter(file,true);
			BufferedWriter dataWrite = new BufferedWriter(writeFile);
			dataWrite.write(msg);
			dataWrite.flush();
			dataWrite.close();
		}
		catch(IOException e)
		{
			TraceTool.writeTrace(MODULENAME, e.toString());
		}
	}
	
	public static void freshLine()
	{
		writeData("\r\n");
	}
}
     