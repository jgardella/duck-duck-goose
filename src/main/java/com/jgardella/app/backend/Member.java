package com.jgardella.app.backend;

public class Member
{
	private String firstName;
	private String lastName;
	private int id;

	public Member(String firstName, String lastName, int id)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}

	public String getFirstName()
	{
		return frstName;
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
}
