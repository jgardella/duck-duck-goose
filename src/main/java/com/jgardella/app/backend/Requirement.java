package com.jgardella.app.backend;

// Attendance requirement definition
public class Requirement
{
	private ReqType reqType;
	private int amount;
	private String[] eventTypes;

	public enum ReqType { CONSECUTIVE, ABSOLUTE, LAST };

	public Requirement(ReqType type, int amount, String... eventTypes)
	{
		this.reqType = type;
		this.amount = amount;
		this.eventTypes = eventTypes;
	}

	public ReqType getType()
	{
		return reqType;
	}

	public int getAmount()
	{
		return amount;
	}

	public String[] getEventTypes()
	{
		return eventTypes;
	}

}
