package zornco.megax.client.items.busters;

import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import zornco.megax.MegaX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class XBusterRender implements IItemRenderer
{
	public XBusterModel buster;

	public XBusterRender()
	{
		this.buster = new XBusterModel();
	}

	public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
		if (type == IItemRenderer.ItemRenderType.EQUIPPED)
		{
			return true;
		}
		else return false;
	}

	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {

		return false;

	}
	float count = 0;
	@Override

	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch(type)
		{
		case ENTITY:
		{
			break;	
		}
		case EQUIPPED:
		{
			GL11.glPushMatrix();
			ForgeHooksClient.bindTexture("/zornco/megax/textures/XBusterDetailed.png", 0);

			boolean isFirstPerson = false;
			if(data[1] != null && data[1] instanceof EntityPlayer)
			{
				if(!((EntityPlayer)data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory && RenderManager.instance.playerViewY == 180.0F)))
				{
					GL11.glTranslatef(-0.22F, 1.43F, 0.15F);
				}
				else
				{
					isFirstPerson = true;
					GL11.glRotatef(-6.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(-0.22F, 1.53F, 0.19F);
				}
			}
			else
			{
				GL11.glTranslatef(-0.02F, 1.63F, 0.19F);
			}
			// GL11.glScalef(1.8F, 1.8F, 1.8F);
			// GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(8.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-140.0F, 0.0F, 0.0F, 1.0F);

			int color = item.getItem().getColorFromItemStack(item, 0);
			GL11.glColor4f((color >> 16 & 0xFF)/255.0F, (color >> 8 & 0xFF)/255.0F, (color & 0xFF)/255.0F, 1);
			this.buster.render((Entity)data[1], 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.buster.render2((Entity)data[1], 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ForgeHooksClient.unbindTexture();
			GL11.glPopMatrix();
			break;
		}
		case INVENTORY:
		{
			break;
		}
		default:
		{
			break;
		}
		}

	}
	public void renderEquippedItem(RenderBlocks render, EntityLiving entity, ItemStack item)
	  {
	    GL11.glPushMatrix();
	    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	    float scale = 0.6F;

	    if ((entity != Minecraft.getMinecraft().renderViewEntity) || (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) || !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory && RenderManager.instance.playerViewY == 180.0F))
	    {
	      GL11.glScalef(scale, scale, scale);
	      GL11.glRotatef(80.0F, 1.0F, 0.0F, 1.0F);
	      float displacement = 0.55F;
	      GL11.glTranslatef(displacement, 0.25F, -displacement);
	    }
	    renderInventoryItem(render, item);
	    GL11.glPopMatrix();
	  }
	public void renderInventoryItem(RenderBlocks render, ItemStack item)
	  {
	    int itemID = item.itemID;
	    int meta = item.getItemDamage();
	  }
}

//		Minecraft mc = ModLoader.getMinecraftInstance();
//		if (type == IItemRenderer.ItemRenderType.EQUIPPED)
//		{
//			if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() ){
//				count-=0.1F;
//				GL11.glTranslatef(0.0F, 0.7F, 1.0F);
//				GL11.glScalef(2.0F, 2.0F, 2.0F);
//				GL11.glRotatef(-45, 0.0F, 1.0F, 0.0F);
//				GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
//				if (mc.thePlayer.getItemInUseCount() > 0)
//	            {
//					EnumAction action = item.getItemUseAction();
//					if(action == MegaX.busterAction)
//						GL11.glTranslatef(0.0F, -0.1F, -0.1F);
//	            }
//			}
//			else{
//				count-=.1F;
//				GL11.glTranslatef(0.0F, 0.7F/*35F*/, 1.0F);
//				GL11.glScalef(2.0F, 2.0F, 2.0F);
//				GL11.glRotatef(/*-45F*/120F/**/, 0.0F, 1.0F, 0.0F);
//				GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(180/*190.0F*/, 0.0F, 0.0F, 1.0F);
//			}
//			count %= 10;
//
//			MinecraftForgeClient.preloadTexture("/zornco/megax/textures/XBusterDetailed.png");
//			//float lum = 1.0F;
//			int color = item.getItem().getColorFromItemStack(item, 0);
//			GL11.glColor4f((color >> 16 & 0xFF)/255.0F, (color >> 8 & 0xFF)/255.0F, (color & 0xFF)/255.0F, 1);
//			this.buster.render((Entity)data[1], 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
//			
//			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//			this.buster.render2((Entity)data[1], 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
//			
//			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		}
//	}
