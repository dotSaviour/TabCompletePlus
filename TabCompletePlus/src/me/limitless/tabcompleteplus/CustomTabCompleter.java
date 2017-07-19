package me.limitless.tabcompleteplus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CustomTabCompleter implements TabCompleter
{
	private CommandData command = null;
	
	protected void setCommandData(CommandData command)
	{
		this.command = command;
		command.getCommand().setTabCompleter(this);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length == 0 || this.command == null)
			return new ArrayList<String>();
		
		ArrayList<String> completions = new ArrayList<String>();
		CommandArgumentData argument = null;
		
		if(args.length == 1)
		{
			boolean playerSearch = false;
			
			for(CommandArgumentData value : this.command.getArguments())
			{
				if(value.getText().equals("%PLAYER_NAME%"))
				{
					playerSearch = true;
					continue;
				}
				
				if(value.getText().startsWith(args[0].toLowerCase()))
					completions.add(value.getText());
			}
			
			// adding player names completions if the current completion list is empty and player search is requested
			if(completions.size() == 0 && playerSearch)
				for(Player player : Bukkit.getOnlinePlayers())
					if(player.getName().toLowerCase().startsWith(args[0].toLowerCase()))
						completions.add(player.getName());
			
			return completions;
		}
		
		argument = this.command.getArgument(args[0]);
		
		if(argument == null)
			return completions;
		
		for(int index = 1; index < args.length; index++)
		{
			if(index != args.length - 1)
			{
				// get the next CommandArgumentData
				argument = argument.getArgument(args[index]);
				
				/*
				 * if the next argument is null that means the player is typing the wrong "root argument"
				 * what i mean by root argument is any argument before the argument that needs completion
				 * for example: /example argumentA argumentB
				 * the root argument in this example is argumentA if the player try to tab complete
				 * for example: /example wrongRoot argument
				 * this code will return an empty list
				 */
				if(argument == null)
					return completions;
			}
			else
			{
				boolean playerSearch = false;
				
				// adding completions
				for(CommandArgumentData value : argument.getSubarguments())
				{
					if(value.getText().equals("%PLAYER_NAME%"))
					{
						playerSearch = true;
						continue;
					}
					
					if(value.getText().startsWith(args[index].toLowerCase()))
						completions.add(value.getText());
				}
				
				// adding player names completions if the current completion list is empty and player search is requested
				if(completions.size() == 0 && playerSearch)
					for(Player player : Bukkit.getOnlinePlayers())
						if(player.getName().toLowerCase().startsWith(args[index].toLowerCase()))
							completions.add(player.getName());
			}
		}
		
		return completions;
	}
}
