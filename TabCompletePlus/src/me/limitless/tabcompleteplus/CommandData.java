package me.limitless.tabcompleteplus;

import java.util.ArrayList;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public class CommandData
{
	private final PluginCommand command;
	private final TabCompleter oldTabCompleter;
	private final ArrayList<CommandArgumentData> arguments;
	
	public CommandData(PluginCommand command)
	{
		this.command = command;
		this.oldTabCompleter = command.getTabCompleter();
		this.arguments = new ArrayList<CommandArgumentData>();
	}
	
	public CommandData addArgument(CommandArgumentData argument)
	{
		if(containsArgument(argument))
			return this;
		
		arguments.add(argument);
		return this;
	}
	
	protected boolean containsArgument(CommandArgumentData argument)
	{
		for(CommandArgumentData localArgument : arguments)
			if(localArgument.getText().equalsIgnoreCase(argument.getText()))
				return true;
		
		return false;
	}
	
	/**
	 * @return relevant argument regardless of the characters case
	 */
	protected CommandArgumentData getArgument(String text)
	{
		for(CommandArgumentData argument : arguments)
			if(argument.getText().equalsIgnoreCase(text))
				return argument;
		
		return null;
	}
	
	protected void reset()
	{
		command.setTabCompleter(oldTabCompleter);
	}
	
	protected PluginCommand getCommand() { return command; }
	protected TabCompleter getOldTabCompleter() { return oldTabCompleter; }
	protected ArrayList<CommandArgumentData> getArguments() { return arguments; }
}
