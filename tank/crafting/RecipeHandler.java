package zornco.tank.crafting;

import zornco.tank.Tank;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.RecipesCrafting;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHandler {

	public static void addOreRecipe(ItemStack output, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, new Object[] { Boolean.valueOf(true), input }));
	}

	public static void addShapelessOreRecipe(ItemStack output, Object[] input)
	{
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, input));
	}

	public void registerRecipes() {
		addOreRecipe(new ItemStack(Tank.tankItem, 1), new Object[] { " ##",
			"#X#", "###", Character.valueOf('X'), Block.furnaceIdle,
			Character.valueOf('#'), Item.ingotIron });
		addOreRecipe(new ItemStack(Tank.tankBullet[0], 1), new Object[] { 
			" Y ", "#X#", "###", Character.valueOf('X'), Block.tnt, 
			Character.valueOf('#'), Block.blockIron, Character.valueOf('Y'), 
			Block.pressurePlateStone });
		addOreRecipe(new ItemStack(Tank.tankBullet[1], 1), new Object[] {
			" Y ", "#X#", "###", Character.valueOf('X'), Block.tnt,
			Character.valueOf('#'), Item.ingotIron, Character.valueOf('Y'),
			Block.stoneButton });
		addOreRecipe(new ItemStack(Tank.tankBullet[2], 1), new Object[] {
			" Y ", "#X#", "###", Character.valueOf('X'), Block.tnt,
			Character.valueOf('#'), Block.blockGold, Character.valueOf('Y'), 
			Block.pressurePlateStone });
		addOreRecipe(new ItemStack(Tank.tankBullet[3], 1), new Object[] {
			" Y ", "#X#", "###", Character.valueOf('X'), Block.tnt,
			Character.valueOf('#'), Item.ingotGold, Character.valueOf('Y'),
			Block.stoneButton });

	}
}
