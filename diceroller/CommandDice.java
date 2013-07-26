package zornco.diceroller;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MathHelper;

public class CommandDice extends CommandBase {

	private static final Pattern patternA = Pattern.compile("\\G(\\w{3})=(-?\\w+)(?:$|,)");
	private static final Pattern patternB = Pattern.compile("\\G^.");//TODO: regex the args, look for each command
	
	@Override
	public String getCommandName() {
		return "roll";
	}
	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	/**
	 * Returns true if the given command sender is allowed to use this command.
	 */
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
		return true;
	}

	public String getCommandUsage(ICommandSender par1ICommandSender) {
		return "/roll <amount> [player] [min=#,max=#]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length <= 0)
		{
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}
		else
		{
			EntityPlayerMP roller;
			String name = "", checker = "";
			boolean silent = false;
			if (args.length >= 2)
			{
				if (containsUsername(getAllUsernames(), args[1]) || args[1].startsWith("@"))
				{
					roller = func_82359_c(sender, args[1]);
					checker = args.length >= 3 ? args[2] : "";
					silent = args.length >= 4 ? args[3].equalsIgnoreCase("silent")||args[3].equalsIgnoreCase("s") : false;
				}
				else if(args[1].startsWith("m"))
				{
					roller = getCommandSenderAsPlayer(sender);
					checker = args.length >= 2 ? args[1] : "";
					silent = args.length >= 3 ? args[2].equalsIgnoreCase("silent")||args[2].equalsIgnoreCase("s") : false;
				}
				else
				{
					roller = getCommandSenderAsPlayer(sender);
					silent = args.length >= 2 ? args[1].equalsIgnoreCase("silent")||args[1].equalsIgnoreCase("s") : false;
				}
				name = roller.getCommandSenderName() + "'s ";
			}
			String parsedOutput = DiceRoller.instance.equ.parse(args[0]);
			boolean checkedBool = this.parseMinMax(checker, parsedOutput);
			String output = name
					+ "Roll Was: " + args[0] + " and got ";
			if(!silent)
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.func_111066_d(output + parsedOutput));
			else System.out.println(parsedOutput);
			if(!checkedBool)
				throw new CommandException("worked, but min,max is for command blocks", new Object[0]);
		}
	}
	public static boolean parseMinMax(String par1Str, String parsed)
	{
		HashMap var1 = new HashMap();
		if (par1Str == null)
		{
			return false;
		}
		else
		{
			Matcher var2 = patternA.matcher(par1Str);

			if (par1Str.length() > -1)
			{
				while (var2.find())
				{
					var1.put(var2.group(1), var2.group(2));
				}
				//String var4 = var2.group(1);
				double min = Double.MIN_VALUE;
				double max = Double.MAX_VALUE;
				double point = Double.parseDouble(parsed);
				if (var1.containsKey("min"))
				{
					min = MathHelper.parseDoubleWithDefault( DiceRoller.instance.equ.parse((String)var1.get("min")), min);
				}

				if (var1.containsKey("max"))
				{
					max = MathHelper.parseDoubleWithDefault( DiceRoller.instance.equ.parse((String)var1.get("max")), max);
				}
				boolean result = point>=min?point<=max:false;
				return result;
			}
			else
			{
				return false;
			}
		}
	}
	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	public boolean isUsernameIndex(int par1) {
		return par1 == 1;
	}
	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllUsernames()) : null;
	}
	protected String[] getAllUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	private boolean containsUsername(String[] usernames, String anEntry)
	{
		boolean found = false;
		for (int index = 0; !found && (index < usernames.length); index++) {
			if (anEntry.toLowerCase().equals(usernames[index].toLowerCase()))
				found = true;
		} // end for

		return found;
	}
}
