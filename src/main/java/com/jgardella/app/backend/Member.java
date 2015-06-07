package com.jgardella.app.backend;

public class Member
{
	private String firstName;
	private String lastName;
	private int id;
	private Status status;

	public enum Status { ACTIVE, INACTIVE, UNDEF };

	public Member(String firstName, String lastName, int id)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.status = Status.UNDEF;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String name)
	{
		this.firstName = name;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String name)
	{
		this.lastName = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public Status getStatus()
	{
		return status;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Member)
		{
			Member member = (Member) obj;
			return this.id == member.id;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return id;
	}

}
