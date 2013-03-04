package zornco.diceroller;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;

public class CommandDice extends CommandBase {

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
		return "/roll <amount> [player]";
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
            String name = "";
            if (args.length > 1)
            {
            	if (!(sender instanceof TileEntityCommandBlock)) 
            		roller = getCommandSenderAsPlayer(sender);
            	else
            		roller = func_82359_c(sender, args[1]);
                name = roller.getCommandSenderName() + "'s ";
            }
            String parsedOutput = DiceRoller.instance.equ.parse(args[0]);
            String output = name
					+ "Roll Was: " + args[0] + " and got ";
            MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(
					new Packet3Chat(output + parsedOutput));
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
}
