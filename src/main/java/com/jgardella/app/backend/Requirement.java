package com.jgardella.app.backend;

// Attendance requirement definition
public class Requirement
{
	private ReqType reqType;
	private int amount;
	private int eventNumLimit;
	private String eventType;

	public enum ReqType { CONSECUTIVE, ABSOLUTE, LAST };

	public Requirement(ReqType type, int amount, String eventType, int eventNumLimit)
	{
		this.reqType = type;
		this.amount = amount;
		this.eventType = eventType;
		this.eventNumLimit = eventNumLimit;
	}

	public ReqType getType()
	{
		return reqType;
	}

	public int getAmount()
	{
		return amount;
	}

	public String getEventType()
	{
		return eventType;
	}

	public int getEventNumLimit()
	{
		return eventNumLimit;
	}

}
