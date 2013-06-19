package zornco.monstercompress;



import zornco.monstercompress.blocks.*;
import zornco.monstercompress.core.*;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="MonsterCompressor", name="MonsterCompressor", version="0.0.1")
@NetworkMod(packetHandler = PacketHandler.class, channels={"MonsterCompress"}, clientSideRequired=true, serverSideRequired=false)
public class MonsterCompressor {

	// The instance of your mod that Forge uses.
	@Instance("MonsterCompressor")
	public static MonsterCompressor instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.monstercompress.client.ClientProxy", serverSide="zornco.monstercompress.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs compressorTab = new TabMonsterCompressor("MonsterCompressor");
	
	public static Block compressor;
	public static int compressorRI = -1;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
	}

	@Init
	public void load(FMLInitializationEvent event) {
		/** Blocks **/
		compressor = new BlockCompressor(3200).setHardness(3.5F).setStepSound(new StepSound("stone", 1.0F, 1.5F)).setUnlocalizedName("compressor");
		
		GameRegistry.registerBlock(compressor, "compressor");
		GameRegistry.registerTileEntity(TileCompressor.class, "tilecompressor");

		/** Names **/
		LanguageRegistry.addName(compressor, "Monster Compressor");

		/** Recipes **/
		
		/*GameRegistry.addRecipe(new ItemStack(weaponTank), 
		new Object[] { 
			"iwi", "bWb", "b?b", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), blueReploidPlate,
			Character.valueOf('w'), weaponBit,
			Character.valueOf('W'), weaponByte,
			Character.valueOf('?'), i dunno lol, something to do with ammo maybe?
			}
		);*/

		

		/** Registers **/
		proxy.registerRenderInformation();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
