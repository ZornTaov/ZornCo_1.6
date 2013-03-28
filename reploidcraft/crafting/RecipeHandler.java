package zornco.reploidcraft.crafting;

import zornco.reploidcraft.RepliodCraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHandler {

	public void registerRecipes() {
		/** Recipes **/
		//GameRegistry.addRecipe(new ItemStack(Item.monsterPlacer, 1, metID), new Object[] { "   ", " # ", "xxx", Character.valueOf('#'), Block.dirt , Character.valueOf('x'), Block.planks });

		GameRegistry.addShapelessRecipe(new ItemStack(RepliodCraft.reploidPlate, 1, 16), // basic plate
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
				"dyeOrange"
			};
		for(int i = 0; i < 15; i++)
			addShapelessOreRecipe(new ItemStack(RepliodCraft.reploidPlate, 1, i), // blue plate
					new Object[]{new ItemStack(RepliodCraft.reploidPlate, 1, 16), dyes[i] } );

		GameRegistry.addShapelessRecipe(new ItemStack(RepliodCraft.reploidPlate, 1, 15), // white plate	
				new Object[]{ new ItemStack(RepliodCraft.component, 1, 0), new ItemStack(RepliodCraft.reploidPlate, 1, 4) } );

		GameRegistry.addRecipe(new ItemStack(RepliodCraft.component, 4, 0), //diamond dust
				new Object[] { 
			"ttt", "tdt", "ttt", 
			Character.valueOf('t'), Block.tnt, 
			Character.valueOf('d'), Item.diamond // diamond dust\
		}
				);

		GameRegistry.addRecipe(new ItemStack(RepliodCraft.component, 1, 1), // AEDS
				new Object[] { 
			"rdr", "dpd", "rdr", 
			Character.valueOf('r'), Item.redstone, 
			Character.valueOf('d'), new ItemStack(RepliodCraft.component, 1, 0), // diamond dust
			Character.valueOf('p'), Item.netherQuartz // use quartz block
		}
				);

		GameRegistry.addRecipe(new ItemStack(RepliodCraft.healthTank, 1, 30), 
				new Object[] { 
			"ihi", "bHb", "bdb", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), new ItemStack(RepliodCraft.reploidPlate, 1, 4),
			Character.valueOf('h'), RepliodCraft.healthBit,
			Character.valueOf('H'), RepliodCraft.healthByte,
			Character.valueOf('d'), Item.diamond
		}
				);

		

		GameRegistry.addRecipe(new ItemStack(RepliodCraft.reploidHelm), 
				new Object[] { 
			"pep", "d d",  
			Character.valueOf('p'), new ItemStack(RepliodCraft.reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(RepliodCraft.reploidPlate, 1, 1),
			Character.valueOf('d'), new ItemStack(RepliodCraft.reploidPlate, 1, 12)
		}
				);

		GameRegistry.addRecipe(new ItemStack(RepliodCraft.reploidChest), 
				new Object[] { 
			"p p", "pep", "ddd",  
			Character.valueOf('p'), new ItemStack(RepliodCraft.reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(RepliodCraft.component, 1, 1),
			Character.valueOf('d'), new ItemStack(RepliodCraft.reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(RepliodCraft.reploidBelt), 
				new Object[] { 
			"ppp", "d d", "d d",  
			Character.valueOf('p'), new ItemStack(RepliodCraft.reploidPlate, 1, 4),
			Character.valueOf('d'), new ItemStack(RepliodCraft.reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(RepliodCraft.reploidBoots), 
				new Object[] { 
			"p p", "d d",   
			Character.valueOf('p'), new ItemStack(RepliodCraft.reploidPlate, 1, 4),
			Character.valueOf('d'), new ItemStack(RepliodCraft.reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(RepliodCraft.buster), 
				new Object[] { 
			"pcp", "dep", "ppp",  
			Character.valueOf('p'), new ItemStack(RepliodCraft.reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(RepliodCraft.component, 1, 1),
			Character.valueOf('c'), Item.lightStoneDust,
			Character.valueOf('d'), new ItemStack(RepliodCraft.reploidPlate, 1, 8)
		}
				);

		GameRegistry.addRecipe(new ItemStack(RepliodCraft.spikes, 8), 
				new Object[] { 
			" i ", " i ", "bbb", 
			Character.valueOf('i'), new ItemStack(RepliodCraft.reploidPlate, 1, 7), 
			Character.valueOf('b'), Block.blockSteel
		}
				);

		/*GameRegistry.addRecipe(new ItemStack(doorBossItem, 1), 
				new Object[] { 
			"ipi", "i i", "ipi", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), Block.blockSteel
			}
		);*/

		/*GameRegistry.addRecipe(new ItemStack(weaponTank), 
		new Object[] { 
			"iwi", "bWb", "bdb", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), blueReploidPlate,
			Character.valueOf('w'), weaponBit,
			Character.valueOf('W'), weaponByte,
			Character.valueOf('d'), Item.diamond
			}
		);*/
	}

	public static void addOreRecipe(ItemStack output, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, new Object[] { Boolean.valueOf(true), input }));
	}

	public static void addShapelessOreRecipe(ItemStack output, Object[] input)
	{
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, input));
	}
}
