package me.limitless.tabcompleteplus;

import java.util.ArrayList;

public class CommandArgumentData
{
	private final String text;
	private final ArrayList<CommandArgumentData> subarguments;
	
	public CommandArgumentData(String text)
	{
		if(text.equals("%PLAYER_NAME%"))
			this.text = text;
		else
			this.text = text.toLowerCase();
		
		this.subarguments = new ArrayList<CommandArgumentData>();
	}
	
	public CommandArgumentData addSubargument(CommandArgumentData subargument)
	{
		if(containsSubargument(subargument))
			return this;
		
		subarguments.add(subargument);
		return this;
	}
	
	protected boolean containsSubargument(CommandArgumentData subargument)
	{
		for(CommandArgumentData localSubargument : subarguments)
			if(localSubargument.getText().equalsIgnoreCase(subargument.getText()))
				return true;
		
		return false;
	}
	
	public CommandArgumentData getArgument(String text)
	{
		for(CommandArgumentData subargument : subarguments)
			if(subargument.getText().equalsIgnoreCase(text))
				return subargument;
		
		return null;
	}
	
	protected String getText() { return text; }
	protected ArrayList<CommandArgumentData> getSubarguments() { return subarguments; }
}
