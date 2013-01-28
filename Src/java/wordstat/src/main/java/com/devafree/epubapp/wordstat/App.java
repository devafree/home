package com.devafree.epubapp.wordstat;

import javax.swing.JOptionPane;
import com.devafree.epubapp.wordstat.BookInfo;
import com.devafree.epubapp.tracetool.TraceTool;
import com.devafree.epubapp.util.FileOpenDlg;

/**
 * Hello world!
 *
 */
public class App 
{
	private static String MODULENAME = "WordStat";
	
    private static boolean wordStat(String filePath)
    {
        if(filePath.length() == 0)
        {
        	TraceTool.writeTrace(MODULENAME, "Error: the filePath is null!");
        	return false;
        }
        
        BookInfo bookInfo = new BookInfo(filePath);
        if(!bookInfo.isEnglishBook())
        {
        	//System.out.println("It's not English book!");
        	TraceTool.writeTrace(MODULENAME, "It's not original English book!");
        }
        
        bookInfo.transformEpubToWords();
        
        bookInfo.writeBookHead();
        bookInfo.writeDocWords();
        return true;
    }
    
    public static void main( String[] args )
    {
        System.out.println( "begin!" );
        TraceTool.getInstance();
        TraceTool.freshLine();
        TraceTool.writeTrace(MODULENAME, "wordstat begin!");
   
        String filePath = FileOpenDlg.getEpubFilePath();
        TraceTool.writeTrace(MODULENAME, "epub file = "+filePath);
        
        wordStat(filePath);
        
        JOptionPane.showMessageDialog(null, "WordStat success!", "Word Stat", JOptionPane.INFORMATION_MESSAGE);
        TraceTool.writeTrace(MODULENAME, "wordstat end!");
        System.out.println("end!");
    }
}
