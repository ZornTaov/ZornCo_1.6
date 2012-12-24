package zornco.fps;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// central controller for mod
//@Mod(modid="FPS", name="FPS", version="0.0.1") // uncomment this to compile
@SideOnly(Side.CLIENT)
public class FPS {
	public static final String ARROW_LOCATION = "/zornco/fps/ArrowIconWhite.png";
	public Minecraft mc;

	public List<EntityPlayer> currentPlayerList = new ArrayList<EntityPlayer>();
	public boolean needsToRefresh;

	public boolean useOldSkinServ = false;
	public EntityClientPlayerMP clientPlayer;
	public HashMap neutralList = new HashMap();
	public HashMap friendList = new HashMap();
	public HashMap enemyList = new HashMap();
	public static final String friendFilename = "friends.list";
	public static final String enemyFilename = "enemies.list";
	public static final String optionsFilename = "conf";

	public int friendArrowColor = 0x0000FF00; // the green
	public int enemyArrowColor = 0x00FF0000; // the red
	public int neutralArrowColor = 0x00FFFF00; // the yellow

	public boolean showHudInfo = true;
	public boolean showOtherPos = true;

	// The instance of your mod that Forge uses.
	@Instance("FPS")
	public static FPS instance;

	


	public FPS() {
	}

	public int getPlayerCount() {
		return currentPlayerList.size();
	}

	@Init
	public void load(FMLInitializationEvent event) {

		this.mc = ModLoader.getMinecraftInstance();

		this.needsToRefresh = false;
		KeyBindingRegistry.registerKeyBinding(new FPSKeyHandler());
		this.loadOptions();
		this.loadLists();
		this.saveOptions();
		this.saveLists();
		System.out.println("FPS Loaded");
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
		System.out.println("FPS post");
	}
	public void saveLists()
	{
		saveList(this.friendFilename,this.friendList);
		saveList(this.enemyFilename,this.enemyList);
	}
	public void loadLists()
	{
		loadList(this.friendFilename,this.friendList);
		loadList(this.enemyFilename,this.enemyList);
	}

	public void loadList(String filename, HashMap list)
	{
		File f;	
		try
		{
			list.clear();
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + filename );
			BufferedReader inFile = new BufferedReader(new FileReader(f));
			String buf = "";
			int line = 0;
			while ((buf = inFile.readLine()) != null)
			{
				list.put(buf.trim(),false);
			}

			inFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to load " + "mod_fps." + filename + " : " + e);
		}
	}

	public void saveList(String filename, HashMap list)
	{
		File f;	
		try
		{
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + filename );
			PrintWriter outFile = new PrintWriter(new FileWriter(f, false));
			Iterator items = list.keySet().iterator();

			while (items.hasNext())
			{
				outFile.println((String)(items.next()));
			}

			outFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to save " + "mod_fps." + filename + " : " + e);
		}
	}

	public void loadOptions()
	{
		File f;	
		try 
		{
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + this.optionsFilename );
			BufferedReader inFile = new BufferedReader(new FileReader(f));
			String buf = "";
			buf = inFile.readLine();
			this.friendArrowColor = (Integer.valueOf(buf.trim()));
			buf = inFile.readLine();
			this.enemyArrowColor = (Integer.valueOf(buf.trim()));
			buf = inFile.readLine();
			this.neutralArrowColor = (Integer.valueOf(buf.trim()));
			buf = inFile.readLine();
			this.useOldSkinServ = buf.toLowerCase().equals("true");
			buf = inFile.readLine();
			this.showHudInfo = buf.toLowerCase().equals("true");
			buf = inFile.readLine();
			this.showOtherPos = buf.toLowerCase().equals("true");
			inFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to load " + "mod_fps." + this.optionsFilename + " : " + e);
		}
	}

	public void saveOptions()
	{
		File f;
		try
		{
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + this.optionsFilename );
			PrintWriter outFile = new PrintWriter(new FileWriter(f, false));
			outFile.println(this.friendArrowColor);
			outFile.println(this.enemyArrowColor);
			outFile.println(this.neutralArrowColor);
			outFile.println(this.useOldSkinServ?"true":"false");
			outFile.println(this.showHudInfo?"true":"false");
			outFile.println(this.showOtherPos?"true":"false");
			outFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to save " + "mod_fps." + this.optionsFilename + " : " + e);
		}
	}

	/**
	 * Player List Sorter
	 */
	public void refresh() {
		this.currentPlayerList.clear();
		this.neutralList.clear();
		for (Entity entity : (List<Entity>) ModLoader.getMinecraftInstance().theWorld.getLoadedEntityList()) {
			if (
					entity instanceof EntityPlayer && 
					!entity.isDead && 
					((EntityPlayer)entity).username != this.mc.thePlayer.username) {
				this.currentPlayerList.add((EntityPlayer) entity);
				if(this.friendList.containsKey(((EntityPlayer)entity).username)) {
					this.friendList.put(((EntityPlayer)entity).username, true);
					continue;
				}
				if(this.enemyList.containsKey(((EntityPlayer)entity).username)) {
					this.enemyList.put(((EntityPlayer)entity).username, true);
					continue;
				}
				this.neutralList.put(((EntityPlayer)entity).username, true);
			}
		}
		Collections.sort(this.currentPlayerList, new Comparator<EntityPlayer>() {
			@Override
			public int compare(EntityPlayer o1, EntityPlayer o2) {
				int pl1 = (int)o1.getDistanceToEntity(ModLoader.getMinecraftInstance().thePlayer);
				int pl2 = (int)o2.getDistanceToEntity(ModLoader.getMinecraftInstance().thePlayer);
				if (pl1 < pl2)
					return -1;
				if (pl1 > pl2)
					return 1;
				return o1.username.compareToIgnoreCase(o2.username);
				//return 0;
			}
		});
	}


	public void drawRotatedTexturedModalRect(int posX, int posY, int texU, int texV, int width, int height, double angle, int colorIndex, Minecraft minecraft)
	{
		float texUPixel = 0.125F;
		float texVPixel = 0.125F;

		double x1, x2, x3, x4, y1, y2, y3, y4, left, right, top, bottom;
		left = -(double)width*.6D;
		right = (double)width*.6D;
		top = -(double)height*.6D;
		bottom = (double)height*.6D;

		x1= left*Math.cos(angle)-bottom*Math.sin(angle);

		y1=left*Math.sin(angle)+bottom*Math.cos(angle);

		x2=right*Math.cos(angle)-bottom*Math.sin(angle);

		y2=right*Math.sin(angle)+bottom*Math.cos(angle);

		x3=left*Math.cos(angle)-top*Math.sin(angle);

		y3=left*Math.sin(angle)+top*Math.cos(angle);

		x4=right*Math.cos(angle)-top*Math.sin(angle);

		y4=right*Math.sin(angle)+top*Math.cos(angle);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(colorIndex);
		tess.addVertexWithUV(x1+(double)posX,y1+(double)posY, 0.0F, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV(x2+(double)posX,y2+(double)posY, 0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV(x4+(double)posX,y4+(double)posY, 0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.addVertexWithUV(x3+(double)posX,y3+(double)posY, 0.0F, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.draw();
	}

	public void drawTexturedHeadModalRect(int posX, int posY, int texU, int texV, int width, int height, Minecraft minecraft)
	{
		float texUPixel = 0.015625F;
		float texVPixel = 0.03125F;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV((double)(posX + 0), (double)(posY + height), 0.0F/*(double)minecraft.ingameGUI.zLevel*/, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV((double)(posX + width), (double)(posY + height),0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV((double)(posX + width), (double)(posY + 0), 0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.addVertexWithUV((double)(posX + 0), (double)(posY + 0), 0.0F, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.draw();
	}

	protected boolean loadDownloadableImageTexture(String par1Str, String par2Str)
	{
		RenderEngine var3 = ModLoader.getMinecraftInstance().renderEngine;
		String location = par1Str;
		if(this.useOldSkinServ)
		{
			if (par1Str.contains("skins.minecraft.net"))
			{
				String newLocation = (new StringBuilder()).append(par1Str).replace(7, 26, "s3.amazonaws.com").toString();
				location = newLocation;
			}
			else
			{
				location = par1Str;
			}
		}
		int var4 = var3.getTextureForDownloadableImage(location, par2Str);

		if (var4 >= 0)
		{
			var3.bindTexture(var4);
			return true;
		}
		else
		{
			return false;
		}
	}


}
