package me.limitless.tabcompleteplus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TabCompletePlus extends JavaPlugin
{
	private static TabCompletePlus instance;
	private ArrayList<CommandData> commands = new ArrayList<CommandData>();
	
	@Override
	public void onEnable()
	{
		instance = this;
		//TESTING
		Bukkit.getConsoleSender().sendMessage("TabCompletePlus");
	}
	
	public TabCompletePlus addCommand(CommandData command)
	{
		if(containsCommand(command.getCommand().getName()))
			return this;
		
		CustomTabCompleter tabCompleter = new CustomTabCompleter();
		tabCompleter.setCommandData(command);
		
		commands.add(command);
		return this;
	}
	
	public void removeCommand(String text)
	{
		for(CommandData command : commands)
		{
			if(command.getCommand().getName().equalsIgnoreCase(text))
			{
				command.reset();
				commands.remove(command);
				return;
			}
		}
	}
	
	public boolean containsCommand(String text)
	{
		for(CommandData localCommand : commands)
			if(localCommand.getCommand().getName().equalsIgnoreCase(text))
				return true;
		
		return false;
	}
	
	public static TabCompletePlus getInstance() { return instance; }
}
