package rreeggkk.nuclearsciences.common.command;

import java.util.ArrayList;
import java.util.List;

import org.apfloat.Apfloat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;
import scala.actors.threadpool.Arrays;

public class GiveNuclearMaterialCommand extends CommandBase {

	private final List<String> aliases;
	
	@SuppressWarnings("unchecked")
	public GiveNuclearMaterialCommand() {
		 aliases = Arrays.asList(new String[] {"gnm"});
	}
	
	@Override
	public String getCommandName() {
		return "givenuclear";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "gnm [<Material Name> <Grams of Material>] ...";
	}
	
	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)sender;
			
			ItemStack stack = new ItemStack(ModItems.nuclearMaterial);
			
			for (int i = 0; i<args.length/2; i++) {
				ModItems.nuclearMaterial.addIsotopeMass(stack, args[2*i], new Apfloat(args[2*i+1], Constants.PRECISION));
			}
			
			if (!player.inventory.addItemStackToInventory(stack)) {
				EntityItem entityitem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack);
				player.worldObj.spawnEntityInWorld(entityitem);
			}
		} else {
			sender.addChatMessage(new TextComponentString("You must be a player to use this command."));
		}
	}
	
	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		System.out.println(Arrays.toString(args));
		if (args.length == 0 || args.length % 2 == 1) {
			return new ArrayList<String>(IsotopeRegistry.getRegistry().keySet());
		} else {
			return new ArrayList<String>();
		}
	}
}
