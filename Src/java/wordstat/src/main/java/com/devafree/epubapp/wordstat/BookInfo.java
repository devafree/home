package com.devafree.epubapp.wordstat;

import com.devafree.epubapp.tracetool.TraceTool;
import com.devafree.epubapp.util.BookDataWriteFile;
import com.devafree.epubapp.util.Words;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

public class BookInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 100L;
	private static final String MODULENAME = "BookInfo";
	
	private Book book;
	private String bookTitle = new String();
	private Words words = new Words();
	
	public BookInfo(String pathName)
	{
		try
		{
			EpubReader epubReader = new EpubReader();
			this.book = epubReader.readEpub(new FileInputStream(pathName));
		}
		catch(Exception e)
		{
			System.out.println("read book Error!");
			TraceTool.writeTrace(MODULENAME, "Read book(" + pathName + ") Error!");
			TraceTool.writeTrace(MODULENAME, e.toString());
		}
		
		Init();

		System.out.println("read book OK!");
		TraceTool.writeTrace(MODULENAME, "Read book OK, Book title is \"" + this.bookTitle + "\"!");
		
	}
	
	private void Init()
	{		
		// print the first title
		List<String> bookTitles = this.book.getMetadata().getTitles();
		this.bookTitle = bookTitles.isEmpty() ? "book has no title" : bookTitles.get(0);
		
		//create wordstat+bookTitle.txt file 
		new BookDataWriteFile(this.bookTitle);
	}
	
	public boolean isEnglishBook()
	{
		String langue = this.book.getMetadata().getLanguage();
		return langue.equalsIgnoreCase("en-US") || langue.equalsIgnoreCase("en") ;
	}
	
	public String getBookTitle()
	{
		return this.bookTitle;
	}
	
	public void setBookTitle(String title)
	{
		this.bookTitle = title;
	}
	
	/***
	 * 
	 */
	public void transformEpubToWords()
	{
		for (Resource resource: this.book.getResources().getAll())
		{
			TraceTool.writeTrace(MODULENAME, resource.toString());
			try 
			{
				if (resource.getMediaType().equals(MediatypeService.XHTML))
				{
					byte[] strBytes = resource.getData();
					this.words.htmlToWords(strBytes);
					this.words.setWordsLevels();
				}
			} 
			catch (IOException e)
			{
				TraceTool.writeTrace(MODULENAME, e.toString());
			}
		}
	}
	
	public Words getWords()
	{
		return this.words;
	}
	
	public void writeBookHead()
	{
		StringBuffer authors = new StringBuffer();
		for (Author author: this.book.getMetadata().getAuthors())
		{
			authors.append(author.getFirstname()).append(" ")
					.append(author.getLastname()).append(", ");
		}
		BookDataWriteFile.writeData(this.bookTitle);
		BookDataWriteFile.freshLine();
		BookDataWriteFile.writeData(authors.toString());
		BookDataWriteFile.freshLine();
		BookDataWriteFile.writeData("Total words: " + String.valueOf(this.words.getWordsTotalTimes()));
		BookDataWriteFile.freshLine();
	}
	
	public void writeDocWords()
	{
		BookDataWriteFile.writeData(this.words.printAllWords());
	}
}