package com.jgardella.app;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import com.jgardella.app.backend.Event;
import com.jgardella.app.backend.Member;
import com.jgardella.app.backend.XLSParser;

public class XLSParserTest
{

	private Event parsedEvent;

	@Before
	public void parseEvent()
	{
		try
		{
			File sampleEventFile = new File(this.getClass().getResource("/sampleEventType/sampleEvent.xlsx").toURI());
			parsedEvent = XLSParser.parseXLS(sampleEventFile);
		} catch (IOException | URISyntaxException | InvalidFormatException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void parsedEventShouldContainEventTime()
	{

	}

	@Test
	public void parsedEventShouldContainEventName()
	{
		assertTrue("Parsed event should contain event name.", parsedEvent.getName().equals("sampleEvent"));
	}

	@Test
	public void parsedEventShouldContainEventType()
	{
		assertTrue("Parsed event should contain event type.", parsedEvent.getType().equals("sampleEventType"));
	}

	@Test
	public void parsedEventShouldContainAttendanceList()
	{
		ArrayList<Member> attendance = parsedEvent.getAttendance();

		assertTrue("Member should be Kevin Quigley.", attendance.get(0).getFullName().equals("Kevin Quigley"));
		assertTrue("Member should be Jason Gardella.", attendance.get(1).getFullName().equals("Jason Gardella"));
		assertTrue("Member should be Jeffrey Meli.", attendance.get(2).getFullName().equals("Jeffrey Meli"));
		assertTrue("Member should be Adam Gincel.", attendance.get(3).getFullName().equals("Adam Gincel"));
		assertTrue("Member should be James Romph.", attendance.get(4).getFullName().equals("James Romph"));
		assertTrue("Member should be Matthew Lagarenne.", attendance.get(5).getFullName().equals("Matthew Lagarenne"));
		assertTrue("Member should be Alex Massenzio.", attendance.get(6).getFullName().equals("Alex Massenzio"));
		assertTrue("Member should be Joshua Phillips.", attendance.get(7).getFullName().equals("Joshua Phillips"));
		assertTrue("Member should be William Mosca.", attendance.get(8).getFullName().equals("William Mosca"));
		assertTrue("Member should be Brian Intile.", attendance.get(9).getFullName().equals("Brian Intile"));
		assertTrue("Member should be Robyn Verrill.", attendance.get(10).getFullName().equals("Robyn Verrill"));
		assertTrue("Member should be Christian Harrypersad.", attendance.get(11).getFullName().equals("Christian Harrypersad"));
	}
}
