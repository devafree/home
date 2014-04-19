package com.devafree.epubapp.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileOpenDlg
{
//	private static String MODULENAME = "FileOpenDlg";
	
	public FileOpenDlg()
	{}

	public static String getEpubFilePath()
	{
		String filePath = new String();
		JFileChooser fileChooser = new JFileChooser(new File("./"));
		//添加过滤的两种方法：
		FileNameExtensionFilter ff = new FileNameExtensionFilter( null, "epub");
		fileChooser.setFileFilter(ff);
		//法二：
//		fileChooser.addChoosableFileFilter(new FileFilter()
//		{							
//			@Override
//			public String getDescription()
//			{
//				return "*.epub";
//			}
//							
//			@Override
//			public boolean accept(File file)
//			{				
//				return file.getName().endsWith(".epub");
//				}	
//			}
//		);
		
		int option = fileChooser.showOpenDialog(null);  
		if(option == JFileChooser.APPROVE_OPTION)
		{
			filePath = fileChooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
	}
}