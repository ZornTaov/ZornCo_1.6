package zornco.bedcraftbeyond;

import java.util.Iterator;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.StepSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import zornco.bedcraftbeyond.blocks.BlockColoredBed;
import zornco.bedcraftbeyond.blocks.BlockRug;
import zornco.bedcraftbeyond.blocks.TileColoredBed;
import zornco.bedcraftbeyond.client.TabBedCraftBeyond;
import zornco.bedcraftbeyond.core.CommonProxy;
import zornco.bedcraftbeyond.item.ItemColoredBed;
import zornco.bedcraftbeyond.item.ItemRug;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="BedCraftBeyond", name="BedCraftBeyond", version="0.0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class BedCraftBeyond {

	// The instance of your mod that Forge uses.
	@Instance("BedCraftBeyond")
	public static BedCraftBeyond instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.bedcraftbeyond.client.ClientProxy", serverSide="zornco.bedcraftbeyond.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs bedCraftBeyondTab = new TabBedCraftBeyond("BedCraftBeyond");

	public static Logger logger = Logger.getLogger("BedCraftBeyond");

	public static Item rugItem;
	public static Item bedItem;
	public static Item scissors;

	public static Block rugBlock;

	public static Block bedBlock;

	//ID's
	public static int rugRI = -1;
	public static int bedRI = -1;

	private int rugItemID;
	private int scissorsID;
	private int bedItemID;

	private int rugBlockID;
	private int bedBlockID;




	public void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		//Blocks
		int blockID = 2600;
		rugBlockID = config.get(config.CATEGORY_BLOCK,"Rug Block", blockID++).getInt();
		bedBlockID = config.get(config.CATEGORY_BLOCK,"Bed Block", blockID++).getInt();

		//Items
		int itemID = 26000;
		rugItemID = config.getItem(config.CATEGORY_ITEM,"Rug Item", itemID++).getInt();
		scissorsID = config.getItem(config.CATEGORY_ITEM,"Scissors", itemID++).getInt();
		bedItemID = config.getItem(config.CATEGORY_ITEM,"Bed Item", itemID++).getInt();

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
		bedBlock = new BlockColoredBed(bedBlockID).setHardness(1.0f).setStepSound(new StepSound("cloth", 1.0F, 1.0F)).setUnlocalizedName("Cbed");
		/** Items **/
		rugItem = new ItemRug(rugItemID, rugBlockID).setUnlocalizedName("rug").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
		scissors = new Item(scissorsID).setUnlocalizedName("scissors").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
		bedItem = new ItemColoredBed(bedItemID).setMaxStackSize(1).setUnlocalizedName("Cbed").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);

		GameRegistry.registerItem(rugItem, "rugitem");
		GameRegistry.registerBlock(rugBlock, "rugblock");
		GameRegistry.registerItem(bedItem, "bedItem");
		GameRegistry.registerBlock(bedBlock, "bedBlock");
		//GameRegistry.registerTileEntity(TileRug.class, "rug");

		/** Names **/
		LanguageRegistry.instance().addStringLocalization( "itemGroup.BedCraftBeyond", "Bed Craft & Beyond" );
		LanguageRegistry.addName(scissors, "Scissors");

		for(String a : ItemRug.rugColorNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.rug."+a+".name", "en_US", a+" Rug");
			LanguageRegistry.instance().addStringLocalization("tile.rug."+a+".name", "en_US", a+" Rug");
		}
		LanguageRegistry.instance().addStringLocalization("item.Cbed.name", "en_US", "Colored Bed");
		LanguageRegistry.instance().addStringLocalization("tile.Cbed.name", "en_US", "Colored Bed");



		/** Recipes **/
		Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
        while(iterator.hasNext())
        {
            ItemStack r = iterator.next().getRecipeOutput();
            if(r != null && r.itemID == Item.bed.itemID)
            {
                iterator.remove();
                logger.info("Removed Vanilla Bed.");
            }
        }
		GameRegistry.addShapelessRecipe(new ItemStack(scissors, 1, 16), // scissors
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
				new Object[] { "xxx", 
					Character.valueOf('x'), new ItemStack(Block.cloth, 1, i)
				}
			);
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				addShapelessOreRecipe(new ItemStack(rugItem, 1, 15-i), // blue plate
						new Object[]{new ItemStack(rugItem, 1, j), dyes[i] } );
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 4; k++) {
					GameRegistry.addRecipe(new ItemStack(BedCraftBeyond.bedItem, 1, getFreqFromColours(k, BlockColored.getDyeFromBlock(j), BlockColored.getDyeFromBlock(i))), new Object[]{
						"bbp",
						"fff",
						'b', new ItemStack(Block.cloth, 1, i),
						'p', new ItemStack(Block.cloth, 1, j),
						'f', new ItemStack(Block.planks, 1, k)});
				}
			}
		}
		/** Registers **/
		proxy.registerRenderInformation();
		GameRegistry.registerTileEntity(TileColoredBed.class, "Cbed");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public static int getFreqFromColours(int colour1, int colour2, int colour3)
    {
        return ((colour1 & 0xF) << 8) + ((colour2 & 0xF) << 4) + (colour3 & 0xF);
    }
	
	public static void addOreRecipe(ItemStack output, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, new Object[] { Boolean.valueOf(true), input }));
	}

	public static void addShapelessOreRecipe(ItemStack output, Object[] input)
	{
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, input));
	}
}