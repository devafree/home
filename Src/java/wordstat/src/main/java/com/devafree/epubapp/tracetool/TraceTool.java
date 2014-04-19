package com.devafree.epubapp.tracetool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/*******
 * 日志模块:用于记录系统运行日志。
 * */


public class TraceTool
{
//	public enum TraceModule
//	{
//		WORDSTAT, BOOKINFO, BOOKCONTENT
//	}
//	public final String traceModuleArr[] = {"WORDSTAT", "BOOKINFO", "BOOKCONTENT"};
	
	private static TraceTool uniqueInstance = null;
	private static String traceDirName = ".\\trace";
	private static String traceFileName = null;

	private TraceTool()
	{
		StringBuffer timebuf = new StringBuffer(50);
		Calendar now = Calendar.getInstance();
		timebuf.append( now.get(Calendar.YEAR) ).append("-")
				.append( now.get(Calendar.MONTH-2) ).append("-")
				.append( now.get(Calendar.DAY_OF_MONTH) ).append(" ")
				.append( now.get(Calendar.HOUR_OF_DAY) ).append("-")
				.append( now.get(Calendar.MINUTE) ).append("-")
				.append( now.get(Calendar.SECOND) );
		File dir= new File(traceDirName);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		
		traceFileName = traceDirName + "\\debugTrace" + timebuf.toString() + ".txt";
		File file=new File(traceFileName);
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
    public static TraceTool getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TraceTool();
        }
        return uniqueInstance;
     }
	
	//记录消息的方法
	public static void writeTrace(String moduleName, String msg)
	{
		//将消息写入一个文件中
		//得到当前系统的时间
		Calendar now = Calendar.getInstance();
		StringBuffer traceStrBuf = new StringBuffer(1000);
		traceStrBuf.append( now.get(Calendar.YEAR) ).append("-")
				.append( now.get(Calendar.MONTH-2) ).append("-")
				.append( now.get(Calendar.DAY_OF_MONTH) ).append(" ")
				.append( now.get(Calendar.HOUR_OF_DAY) ).append(":")
				.append( now.get(Calendar.MINUTE) ).append(":")
				.append( now.get(Calendar.SECOND) ).append(".")
				.append( now.get(Calendar.MILLISECOND) ).append(" ")
				.append( "(").append(moduleName).append(")")
				.append(msg).append("\r\n");;


		File file=new File(traceFileName);
		if(file.exists())
		{
			try
			{
				FileWriter writeFile = new FileWriter(file,true);
				BufferedWriter traceWrite = new BufferedWriter(writeFile);
				traceWrite.write(traceStrBuf.toString());
				traceWrite.flush();
				traceWrite.close();
			}
			catch(Exception e)
			{
				e.fillInStackTrace();
			}
		}
		else if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void freshLine()
	{
		File file=new File(traceFileName);
		if(file.exists())
		{
			try
			{
				FileWriter writeFile = new FileWriter(file,true);
				BufferedWriter traceWrite = new BufferedWriter(writeFile);
				traceWrite.write("\r\n");
				traceWrite.flush();
				traceWrite.close();
			}
			catch(Exception e)
			{
				e.fillInStackTrace();
			}
		}
	}
}
     