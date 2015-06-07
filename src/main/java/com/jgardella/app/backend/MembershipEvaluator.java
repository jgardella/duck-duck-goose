package com.jgardella.app.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class MembershipEvaluator
{

	private ArrayList<Member> memberList;
	private HashMap<String, ArrayList<Event>> eventMap;
	private ArrayList<Requirement> reqList;

	/**
	   Parses the given event type directories, generating an internal member list and event map.
	   @param File... eventDirs The event type directories containing the attendance sheets.
	  **/
	public void parseEvents(File... eventDirs) throws IOException, InvalidFormatException
	{
		for(File eventDir : eventDirs)
		{
			parseEvent(eventDir);
		}
	}

	private void parseEvent(File eventDir) throws IOException, InvalidFormatException
	{
		ArrayList<Event> events = new ArrayList<Event>();
		File[] attendanceSheets = eventDir.listFiles();
		// iterate through every attendance sheet for this event type
		for(File file : attendanceSheets)
		{
			Event parsedEvent = XLSParser.parseXLS(file);
			events.add(parsedEvent); // add event to event list for this type

			// add new members to member list
			for(Member member : parsedEvent.getAttendance())
			{
				if(!memberList.contains(member))
				{
					memberList.add(member);
				}
			}
		}
		Collections.sort(events); // sort event list by date (oldest to most recent)
		eventMap.put(eventDir.getName(), events); // put event list in map
	}

	/**
	  Adds the given membership requirement to the requirement list.
	  @param Requirement requirement The requirement for active membership to add
	  **/
	public void addRequirement(Requirement requirement)
	{
		reqList.add(requirement);
	}

	public void evaluateMembership()
	{

	}

	public ArrayList<Member> getActiveMembers()
	{

	}

	public ArrayList<Member> getNonactiveMembers()
	{

	}

	public ArrayList<Member> getAllMembers()
	{

	}

}
