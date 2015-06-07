package com.jgardella.app.backend;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.apache.poi.*;

public class XLSParser
{

	final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static Event parseXLS(File xlsFile)
	{
		Event event = null;

		FileInputStream fis = new FileInputStream(xlsFile);

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next(); // skip first row (headers)
		while(rowIterator.hasNext())
		{
			Row row = rowIterator.next();
			if(row.getRowNum() == 0)
			{
				String str_eventDate = row.getCell(0).getRichStringCellValue().getString();
				LocalDateTime eventDate = LocalDateTime.parse(str_eventDate, TIME_FORMAT);
				event = new Event(xlsFile.getName(), xlsFile.getParentFile().getName(), eventDate);
			}

			event.addMemberToAttendance(new Member(row.getCell(3).getRichStringCellValue().getString(),
												   row.getCell(2).getRichStringCellValue().getString(),
												   (int) row.getCell(1).getNumericCellValue()));
		}

		return event;
	}
}
