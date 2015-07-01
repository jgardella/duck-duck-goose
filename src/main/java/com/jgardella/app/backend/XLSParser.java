package com.jgardella.app.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class XLSParser
{

	static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd  h:mm a");

	public static Event parseXLS(File xlsFile) throws IOException, InvalidFormatException
	{
		Event event = null;

		InputStream fis = new FileInputStream(xlsFile);

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next(); // skip first row (headers)
		while(rowIterator.hasNext())
		{
			Row row = rowIterator.next();
			if(row.getRowNum() == 1) // create event from first row with data
			{
				String str_eventDate = row.getCell(0).getRichStringCellValue().getString();
				LocalDateTime eventDate = LocalDateTime.parse(str_eventDate, TIME_FORMAT);
				event = new Event(xlsFile.getName().substring(0, xlsFile.getName().indexOf('.')), xlsFile.getParentFile().getName(), eventDate);
			}

			if(row.getCell(1) != null)
			{
				// create member and add to event attendance
				int id = (int) row.getCell(1).getNumericCellValue();
				String lastName = row.getCell(2).getRichStringCellValue().getString();
				String firstName = row.getCell(3).getRichStringCellValue().getString();

				event.addMemberToAttendance(new Member(firstName, lastName, id));
			}
		}
		
		workbook.close();
		
		return event;
	}
}
