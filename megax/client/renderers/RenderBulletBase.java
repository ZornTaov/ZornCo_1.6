package zornco.megax.client.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import zornco.megax.bullets.EntityBulletBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBulletBase extends Render
{
	private String texture = "";

	private float backSide = 0;
	private float frontSide = 16.0F/32.0F;
	private float leftSide = 0;
	private float rightSide = 10.0F/32.0F;
	private float topBase = 10.0F/32.0F;
	private float bottomBase = 20.0F/32.0F;
	private float leftBase = 0;
	private float rightBase = 10.0F/32.0F;
	private float length = 16.0F/32.0F;
	private float width = 10.0F/32.0F;
	private float middle = 0;

	private float[] setup = new float[7];
	
	public void setup(String tex, float wid, float len, float mid, float u, float v, float texWidth, float texHeight)
	{
		this.texture = "/zornco/megax/textures/bullet_" + tex + ".png";
		this.backSide = u/texWidth;
		this.frontSide = (u+len)/ texWidth;
		this.leftSide = v / texHeight;
		this.rightSide = (v+wid)/ texHeight;
		this.topBase = (v+wid)/ texHeight;
		this.bottomBase = (v+wid*2)/ texHeight;
		this.leftBase = u/ texWidth;
		this.rightBase = (u+wid)/ texWidth;
		this.length = len/2.0F;
		this.width = wid/2.0F;
		this.middle = mid;
	}
	public void renderBulletBase(EntityBulletBase par1EntityBulletBase, double par2, double par4, double par6, float par8, float par9)
	{
		this.setup = par1EntityBulletBase.getTexturePlacement();
		this.texture = par1EntityBulletBase.getTexture();
		this.setup(texture, setup[0], setup[1], setup[2], setup[3], setup[4], setup[5], setup[6]);
		this.loadTexture(texture);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glRotatef(par1EntityBulletBase.prevRotationYaw + (par1EntityBulletBase.rotationYaw - par1EntityBulletBase.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(par1EntityBulletBase.prevRotationPitch + (par1EntityBulletBase.rotationPitch - par1EntityBulletBase.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
		Tessellator var10 = Tessellator.instance;
		float var20 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_NORMALIZE);
        //GL11.glEnable(GL11.GL_BLEND);
        //GL11.glDisable(GL11.GL_ALPHA_TEST);
        //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		float var21 = (float)par1EntityBulletBase.bulletShake - par9;

		if (var21 > 0.0F)
		{
			float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
			GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
		}
		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(var20, var20, var20);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(var20, 0.0F, 0.0F);
		GL11.glColor3d(255.0, 255.0, 255.0);
		//middle
		var10.startDrawingQuads();
		var10.addVertexWithUV(middle, -width, -width, (double)leftBase,  (double)topBase   );
		var10.addVertexWithUV(middle, -width,  width, (double)rightBase, (double)topBase   );
		var10.addVertexWithUV(middle,  width,  width, (double)rightBase, (double)bottomBase);
		var10.addVertexWithUV(middle,  width, -width, (double)leftBase,  (double)bottomBase);
		var10.draw();
		GL11.glNormal3f(-var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(middle,  width, -width, (double)leftBase,  (double)topBase   );
		var10.addVertexWithUV(middle,  width,  width, (double)rightBase, (double)topBase   );
		var10.addVertexWithUV(middle, -width,  width, (double)rightBase, (double)bottomBase);
		var10.addVertexWithUV(middle, -width, -width, (double)leftBase,  (double)bottomBase);
		var10.draw();
		//sides
		for (int var23 = 0; var23 < 4; ++var23)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			var10.startDrawingQuads();
			var10.addVertexWithUV(-length, -width, 0.0D, (double)backSide , (double)leftSide );    
			var10.addVertexWithUV( length, -width, 0.0D, (double)frontSide, (double)leftSide );    
			var10.addVertexWithUV( length,  width, 0.0D, (double)frontSide, (double)rightSide);   
			var10.addVertexWithUV(-length,  width, 0.0D, (double)backSide , (double)rightSide);
			var10.draw();
		}

        //GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderBulletBase((EntityBulletBase)par1Entity, par2, par4, par6, par8, par9);
	}
}
