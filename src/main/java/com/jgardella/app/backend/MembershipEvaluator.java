package com.jgardella.app.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import com.jgardella.app.backend.Requirement;
import com.jgardella.app.backend.Requirement.ReqType;
import com.jgardella.app.backend.Member.Status;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class MembershipEvaluator
{

	private ArrayList<Member> memberList;
	private HashMap<String, ArrayList<Event>> eventMap;
	private ArrayList<Requirement> reqList;

	private ArrayList<Member> activeMembers;
	private ArrayList<Member> inactiveMembers;

	/**
	   Parses the given event type directories, generating an internal member list and event map.
	   @param File... eventDirs The event type directories containing the attendance sheets.
	  **/
	public void parseEvents(File... eventDirs) throws IOException, InvalidFormatException
	{
		memberList = new ArrayList<Member>();
		eventMap = new HashMap<String, ArrayList<Event>>();
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
		activeMembers = new ArrayList<Member>(memberList); // active member list contains all members at start
		inactiveMembers = new ArrayList<Member>(); // inactive members list empty

		for(Member member : memberList)
		{
			member.setStatus(Status.ACTIVE);
			for(Requirement req : reqList)
			{
				boolean reqMet = processRequirement(member, req);
				if(!reqMet) // if requirement not met, set status inactive and break
				{
					member.setStatus(Status.INACTIVE); // set member inactive
					activeMembers.remove(member); // remove member from active list
					inactiveMembers.add(member); // add member to inactive list
					break;
				}
			}
		}
	}

	private boolean processRequirement(Member member, Requirement req)
	{
		ArrayList<Event> eventList = eventMap.get(req.getEventType());
		switch(req.getType())
		{
		case ABSOLUTE:
			int numAbs = 0;
			for(Event event : eventList)
			{
				if(event.getAttendance().contains(member))
				{
					numAbs++;
					if(numAbs >= req.getAmount())
					{
						return true;
					}
				}
			}
			return false;
		case CONSECUTIVE:
			int numConsecutive = 0;
			for(Event event : eventList)
			{
				if(event.getAttendance().contains(member))
				{
					numConsecutive++;
					if(numConsecutive >= req.getAmount())
					{
						return true;
					}
				}
				else
				{
					numConsecutive = 0;
				}
			}
			return false;
		case LAST:
			int numLast = 0;
			for(Event event : eventList)
			{
				if(event.getAttendance().contains(member))
				{
					numLast++;
					if(numLast >= req.getAmount())
					{
						return true;
					}
				}
				else
				{
					return false;
				}
			}
			return false;
		}
		return false;
	}

	/**
	  Returns a list of active members. This method will return null if evaluateMembership() has not been called yet.
	  **/
	public ArrayList<Member> getActiveMembers()
	{
		return activeMembers;
	}

	/**
	  Returns a list of inactive members. This method will return null if evaluateMembership() has not been called yet.
	  **/
	public ArrayList<Member> getNonactiveMembers()
	{
		return inactiveMembers;
	}

	/**
	  Returns a list of all members. This method will return null if parseEvents() has not been called yet.
	  **/
	public ArrayList<Member> getAllMembers()
	{
		return memberList;
	}

}
