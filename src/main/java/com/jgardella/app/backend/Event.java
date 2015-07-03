package com.jgardella.app.backend;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Event implements Comparable<Event>
{
	private String name;
	private String type;
	private LocalDateTime date;
	private ArrayList<Member> attendance;

	public Event(String name, String type, LocalDateTime date)
	{
		this.name = name;
		this.type = type;
		this.date = date;
		this.attendance = new ArrayList<Member>();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public LocalDateTime getDate()
	{
		return date;
	}

	public void setDate(LocalDateTime date)
	{
		this.date = date;
	}

	public void addMemberToAttendance(Member member)
	{
		attendance.add(member);
	}

	public ArrayList<Member> getAttendance()
	{
		return attendance;
	}

	/**
	 * Compares two events (newer events come before older events).
	 */
	public int compareTo(Event event)
	{
		return event.date.compareTo(date);
	}

}
