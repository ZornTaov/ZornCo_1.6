package zornco.megax.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

public class BusterFX extends EntityFX
{
	private int type;

	public BusterFX(World world, double x, double y, double z, float size, int type)
	{
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);

		this.type = type;
		this.particleRed = 1.0F;
		this.particleGreen = 1.0F;
		this.particleBlue = 1.0F;
		this.particleGravity = 1.0F;
		this.motionX = (this.motionY = this.motionZ = 0.0D);
		this.particleScale *= size;
		this.particleMaxAge = 7;
		this.noClip = false;
		setSize(0.01F, 0.01F);
	}

	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
	{
		tessellator.draw();
		GL11.glPushMatrix();

		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 1);

		Minecraft.getMinecraft().renderEngine.bindTexture("/mods/MegaX/textures/fx/buster"+this.type+".png");

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);

		float var8 = 0F;
		float var9 = 1F;
		float var10 = 0.0F;
		float var11 = 1.0F;
		float var12 = this.particleScale*(this.particleMaxAge-this.particleAge)/this.particleMaxAge;

		float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
		float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
		float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
		float var16 = 1.0F;

		tessellator.startDrawingQuads();
		tessellator.setBrightness(240);

		tessellator.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F);
		tessellator.addVertexWithUV(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, var9, var11);
		tessellator.addVertexWithUV(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12, var9, var10);
		tessellator.addVertexWithUV(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, var8, var10);
		tessellator.addVertexWithUV(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12, var8, var11);

		tessellator.draw();

		GL11.glDisable(3042);
		GL11.glDepthMask(true);

		GL11.glPopMatrix();
		//GL11.glBindTexture(3553, ModLoader.getMinecraftInstance().renderEngine.getTexture("/particles.png"));

		tessellator.startDrawingQuads();
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			setDead();
		}
	}

	public void setGravity(float value)
	{
		this.particleGravity = value;
	}
}