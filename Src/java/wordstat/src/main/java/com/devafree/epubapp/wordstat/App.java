package com.devafree.epubapp.wordstat;

import com.devafree.epubapp.wordstat.BookInfo;
import com.devafree.epubapp.tracetool.TraceTool;
/**
 * Hello world!
 *
 */
public class App 
{
	private static String MODULENAME = "WordStat";
    public static void main( String[] args )
    {
        System.out.println( "begin!" );
        TraceTool.getInstance();
        TraceTool.freshLine();
        TraceTool.writeTrace(MODULENAME, "wordstat begin!");
        
        String pathName = "D:\\devafree\\workspace\\java\\wordstat\\bin\\edward-macdowell-critical-and-historical-essays.epub";
        
        BookInfo bookInfo = new BookInfo(pathName);
        if(!bookInfo.isEnglishBook())
        {
        	//System.out.println("It's not English book!");
        	TraceTool.writeTrace(MODULENAME, "It's not English book!");
        }
        
        bookInfo.transformEpubToWords();
        
        bookInfo.writeBookHead();
        bookInfo.writeDocWords();        
        
        TraceTool.writeTrace(MODULENAME, "wordstat end!");
        System.out.println("end");
        System.out.println(bookInfo.getWords().getWordsTotalTimes());
    }
}
