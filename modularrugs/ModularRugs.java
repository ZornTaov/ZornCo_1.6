package zornco.modularrugs;



import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import zornco.modularrugs.blocks.BlockRug;
import zornco.modularrugs.core.CommonProxy;
import zornco.modularrugs.core.TabModularRugs;
import zornco.modularrugs.item.ItemRug;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="ModularRugs", name="ModularRugs", version="0.0.1")
public class ModularRugs {

	// The instance of your mod that Forge uses.
	@Instance("ModularRugs")
	public static ModularRugs instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.modularrugs.client.ClientProxy", serverSide="zornco.modularrugs.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs modularRugsTab = new TabModularRugs("ModularRugs");

	public static Logger logger = Logger.getLogger("ModularRugs");

	public static Item rugItem;
	public static Item scissors;

	public static Block rugBlock;

	//ID's
	public static int rugRI = -1;

	private int rugItemID;
	private int scissorsID;

	private int rugBlockID;




	public void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		//Blocks
		int blockID = 2600;
		rugBlockID = config.get(config.CATEGORY_BLOCK,"Rug Block", blockID++).getInt();

		//Items
		int itemID = 26000;
		rugItemID = config.getItem(config.CATEGORY_ITEM,"Rug Item", itemID++).getInt();
		scissorsID = config.getItem(config.CATEGORY_ITEM,"Scissors", itemID++).getInt();

		config.save();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
		logger.setParent(FMLLog.getLogger());
		loadConfig(event);

	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		/** Blocks **/
		rugBlock = new BlockRug(rugBlockID).setHardness(0.8F).setStepSound(new StepSound("cloth", 1.0F, 1.0F)).setUnlocalizedName("rug");

		/** Items **/
		rugItem = new ItemRug(rugItemID, rugBlockID).setUnlocalizedName("rug");
		scissors = new Item(scissorsID).setUnlocalizedName("scissors");

		GameRegistry.registerItem(rugItem, "rugitem");
		GameRegistry.registerBlock(rugBlock, "rugblock");
		//GameRegistry.registerTileEntity(TileRug.class, "rug");

		/** Names **/
		LanguageRegistry.addName(scissors, "Scissors");

		for(String a : ItemRug.rugColorNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.rug."+a+".name", "en_US", a+" Rug");
			LanguageRegistry.instance().addStringLocalization("tile.rug."+a+".name", "en_US", a+" Rug");
		}

		/** Recipes **/
		GameRegistry.addShapelessRecipe(new ItemStack(scissors, 1, 16), // basic plate
				new Object[]{ Item.ingotIron, Item.redstone } );
		String[] dyes = 
			{
				"dyeBlack",
				"dyeRed",
				"dyeGreen",
				"dyeBrown",
				"dyeBlue",
				"dyePurple",
				"dyeCyan",
				"dyeLightGray",
				"dyeGray",
				"dyePink",
				"dyeLime",
				"dyeYellow",
				"dyeLightBlue",
				"dyeMagenta",
				"dyeOrange",
				"dyeWhite"
			};
		for(int i = 0; i < 16; i++)
			GameRegistry.addRecipe(new ItemStack(rugItem, 4, i),
					new Object[] { 
				"yty", 
				Character.valueOf('t'), new ItemStack(Block.cloth, 1, i),
				Character.valueOf('t'), new ItemStack(Block.cloth, 1, -1)
			}
					);
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				addShapelessOreRecipe(new ItemStack(rugItem, 1, i), // blue plate
						new Object[]{new ItemStack(Block.cloth, 1, j), dyes[i] } );


		/** Registers **/
		proxy.registerRenderInformation();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
	public static void addOreRecipe(ItemStack output, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, new Object[] { Boolean.valueOf(true), input }));
	}

	public static void addShapelessOreRecipe(ItemStack output, Object[] input)
	{
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, input));
	}
}