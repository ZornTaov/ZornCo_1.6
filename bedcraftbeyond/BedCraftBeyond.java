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
import zornco.bedcraftbeyond.blocks.BlockColoredChestBed;
import zornco.bedcraftbeyond.blocks.BlockRug;
import zornco.bedcraftbeyond.blocks.BlockStoneBed;
import zornco.bedcraftbeyond.blocks.TileColoredBed;
import zornco.bedcraftbeyond.blocks.TileColoredChestBed;
import zornco.bedcraftbeyond.blocks.TileStoneBed;
import zornco.bedcraftbeyond.client.TabBedCraftBeyond;
import zornco.bedcraftbeyond.core.CommonProxy;
import zornco.bedcraftbeyond.item.ItemColoredBed;
import zornco.bedcraftbeyond.item.ItemColoredChestBed;
import zornco.bedcraftbeyond.item.ItemRug;
import zornco.bedcraftbeyond.item.ItemScissors;
import zornco.bedcraftbeyond.item.ItemStoneBed;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
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
	public static Item chestBedItem;
	public static Item stoneBedItem;
	public static Item scissors;

	public static Block rugBlock;
	public static Block bedBlock;
	public static Block chestBedBlock;
	public static Block stoneBedBlock;

	//ID's
	public static int rugRI = -1;
	public static int bedRI = -1;
	public static int chestBedRI = -1;
	public static int stoneBedRI = -1;

	private int rugItemID;
	private int scissorsID;
	private int bedItemID;
	private int chestBedItemID;
	private int stoneBedItemID;

	private int rugBlockID;
	private int bedBlockID;
	private int chestBedBlockID;
	private int stoneBedBlockID;

	public void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		//Blocks
		int blockID = 2600;
		rugBlockID = config.get(Configuration.CATEGORY_BLOCK,"Rug Block", blockID++).getInt();
		bedBlockID = config.get(Configuration.CATEGORY_BLOCK,"Bed Block", blockID++).getInt();
		chestBedBlockID = config.get(Configuration.CATEGORY_BLOCK,"Chest Bed Block", blockID++).getInt();
		stoneBedBlockID = config.get(Configuration.CATEGORY_BLOCK,"Stone Bed Block", blockID++).getInt();

		//Items
		int itemID = 26000;
		rugItemID = config.getItem(Configuration.CATEGORY_ITEM,"Rug Item", itemID++).getInt();
		scissorsID = config.getItem(Configuration.CATEGORY_ITEM,"Scissors", itemID++).getInt();
		bedItemID = config.getItem(Configuration.CATEGORY_ITEM,"Bed Item", itemID++).getInt();
		chestBedItemID = config.getItem(Configuration.CATEGORY_ITEM,"Chest Bed Item", itemID++).getInt();
		stoneBedItemID = config.getItem(Configuration.CATEGORY_ITEM,"Stone Bed Item", itemID++).getInt();

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
		chestBedBlock = new BlockColoredChestBed(chestBedBlockID).setHardness(1.0f).setStepSound(new StepSound("cloth", 1.0F, 1.0F)).setUnlocalizedName("CCbed");
		stoneBedBlock = new BlockStoneBed(stoneBedBlockID).setHardness(1.0f).setStepSound(new StepSound("cloth", 1.0F, 1.0F)).setUnlocalizedName("Sbed");
		
		/** Items **/
		rugItem = new ItemRug(rugItemID, rugBlockID).setUnlocalizedName("rug").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
		scissors = new ItemScissors(scissorsID).setUnlocalizedName("scissors").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
		bedItem = new ItemColoredBed(bedItemID).setMaxStackSize(1).setUnlocalizedName("Cbed").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
		chestBedItem = new ItemColoredChestBed(chestBedItemID).setMaxStackSize(1).setUnlocalizedName("CCbed").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
		stoneBedItem = new ItemStoneBed(stoneBedItemID).setMaxStackSize(1).setUnlocalizedName("Sbed").setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);

		GameRegistry.registerItem(rugItem, "rugitem");
		GameRegistry.registerBlock(rugBlock, "rugblock");
		GameRegistry.registerItem(bedItem, "bedItem");
		GameRegistry.registerBlock(bedBlock, "bedBlock");
		GameRegistry.registerItem(chestBedItem, "chestBedItem");
		GameRegistry.registerBlock(chestBedBlock, "chestBedBlock");
		GameRegistry.registerItem(stoneBedItem, "stoneBedItem");
		GameRegistry.registerBlock(stoneBedBlock, "stoneBedBlock");
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
		LanguageRegistry.instance().addStringLocalization("item.CCbed.name", "en_US", "Colored Chest Bed");
		LanguageRegistry.instance().addStringLocalization("tile.CCbed.name", "en_US", "Colored Chest Bed");
		LanguageRegistry.instance().addStringLocalization("item.Sbed.name", "en_US", "Stone Bed");
		LanguageRegistry.instance().addStringLocalization("tile.Sbed.name", "en_US", "Stone Bed");



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
		addOreRecipe(new ItemStack(scissors, 1),
				new Object[] { " x ", "xxy", " y ", 
			'x', Item.ingotIron, 
			'x', "dyeRed"
				}
			);
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
						'f', new ItemStack(Block.planks, 1, k)
						}
					);
					GameRegistry.addRecipe(new ItemStack(BedCraftBeyond.chestBedItem, 1, getFreqFromColours(k, BlockColored.getDyeFromBlock(j), BlockColored.getDyeFromBlock(i))), new Object[]{
						"bbp",
						"fcf",
						'b', new ItemStack(Block.cloth, 1, i),
						'p', new ItemStack(Block.cloth, 1, j),
						'f', new ItemStack(Block.planks, 1, k),
						'c', new ItemStack(Block.chest, 1)
						}
					);
				}
			}
		}
		GameRegistry.addRecipe(new ItemStack(BedCraftBeyond.stoneBedItem, 1, 0), new Object[]{
			"SSS",
			"sss",
			'S', new ItemStack(Block.stone, 1),
			's', new ItemStack(Block.stoneSingleSlab, 1, 0)
		});
		/** Registers **/
		proxy.registerRenderInformation();
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		GameRegistry.registerTileEntity(TileColoredBed.class, "Cbed");
		GameRegistry.registerTileEntity(TileColoredChestBed.class, "CCbed");
		GameRegistry.registerTileEntity(TileStoneBed.class, "Sbed");
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