package com.jgardella.app.backend;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>
{
	private String name;
	private String type;
	private LocalDateTime date;

	public Event(String name, String type, LocalDateTime date)
	{
		this.name = name;
		this.type = type;
		this.date = date;
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

	public int compareTo(Event event)
	{
		return date.compareTo(event.date);
	}

}
