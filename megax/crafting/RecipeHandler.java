package zornco.megax.crafting;

import zornco.megax.MegaX;
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

		//TEMP! will make a block that will infuse the ingots with the material, might keep and have this be an option
		GameRegistry.addShapelessRecipe(new ItemStack(MegaX.reploidPlate, 1, 16), // basic plate
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
			addShapelessOreRecipe(new ItemStack(MegaX.reploidPlate, 1, i), // blue plate
					new Object[]{new ItemStack(MegaX.reploidPlate, 1, 16), dyes[i] } );

		GameRegistry.addShapelessRecipe(new ItemStack(MegaX.reploidPlate, 1, 15), // white plate	
				new Object[]{ new ItemStack(MegaX.component, 1, 0), new ItemStack(MegaX.reploidPlate, 1, 4) } );

		GameRegistry.addRecipe(new ItemStack(MegaX.component, 4, 0), //diamond dust
				new Object[] { 
			"ttt", "tdt", "ttt", 
			Character.valueOf('t'), Block.tnt, 
			Character.valueOf('d'), Item.diamond // diamond dust\
		}
				);

		GameRegistry.addRecipe(new ItemStack(MegaX.component, 1, 1), // AEDS
				new Object[] { 
			"rdr", "dpd", "rdr", 
			Character.valueOf('r'), Item.redstone, 
			Character.valueOf('d'), new ItemStack(MegaX.component, 1, 0), // diamond dust
			Character.valueOf('p'), Item.enderPearl // use quartz block
		}
				);

		GameRegistry.addRecipe(new ItemStack(MegaX.healthTank, 1, 30), 
				new Object[] { 
			"ihi", "bHb", "bdb", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('h'), MegaX.healthBit,
			Character.valueOf('H'), MegaX.healthByte,
			Character.valueOf('d'), Item.diamond
		}
				);

		GameRegistry.addRecipe(new ItemStack(MegaX.upgradeStation), 
				new Object[] { 
			"pcp", "pbp", "pdp",  
			Character.valueOf('p'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('b'), Block.blockSteel,
			Character.valueOf('c'), MegaX.weaponChip,
			Character.valueOf('d'), Item.redstone
		}
				);

		GameRegistry.addRecipe(new ItemStack(MegaX.megaX1Helm), 
				new Object[] { 
			"pep", "d d",  
			Character.valueOf('p'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(MegaX.reploidPlate, 1, 1),
			Character.valueOf('d'), new ItemStack(MegaX.reploidPlate, 1, 12)
		}
				);

		GameRegistry.addRecipe(new ItemStack(MegaX.megaX1Chest), 
				new Object[] { 
			"p p", "pep", "ddd",  
			Character.valueOf('p'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(MegaX.component, 1, 1),
			Character.valueOf('d'), new ItemStack(MegaX.reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(MegaX.megaX1Belt), 
				new Object[] { 
			"ppp", "d d", "d d",  
			Character.valueOf('p'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('d'), new ItemStack(MegaX.reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(MegaX.megaX1Boots), 
				new Object[] { 
			"p p", "d d",   
			Character.valueOf('p'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('d'), new ItemStack(MegaX.reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(MegaX.buster), 
				new Object[] { 
			"pcp", "dep", "ppp",  
			Character.valueOf('p'), new ItemStack(MegaX.reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(MegaX.component, 1, 1),
			Character.valueOf('c'), Item.lightStoneDust,
			Character.valueOf('d'), new ItemStack(MegaX.reploidPlate, 1, 8)
		}
				);

		GameRegistry.addRecipe(new ItemStack(MegaX.spikes, 8), 
				new Object[] { 
			" i ", " i ", "bbb", 
			Character.valueOf('i'), new ItemStack(MegaX.reploidPlate, 1, 7), 
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
