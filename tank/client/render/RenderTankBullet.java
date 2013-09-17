package zornco.tank.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import zornco.tank.entity.EntityTankBullet;

public class RenderTankBullet extends Render
{ 
	private static final ResourceLocation bulletTex = new ResourceLocation("tank:textures/entity/tank/TankBullet.png");
	private float UVs[] = new float[9];
	
	public RenderTankBullet()
	{
	}

	private void bulletTypeSetup(float u1, float u2, float v1, float v2, float u3, float u4, float v3, float v4, float diameter)
	{
		UVs[0] = u1 / 32F;
		UVs[1] = u2 / 32F;
		UVs[2] = v1 / 32F;
		UVs[3] = v2 / 32F;
		UVs[4] = u3 / 32F;
		UVs[5] = u4 / 32F;
		UVs[6] = v3 / 32F;
		UVs[7] = v4 / 32F;
		UVs[8] = diameter;
	}

	public void renderArrow(EntityTankBullet entitytankbullet, double d, double d1, double d2, 
			float f, float f1)
	{
		int type = entitytankbullet.getBulletType();
		if(type >=0 && type <=3 || type == 20)
		{
	        this.bindEntityTexture(entitytankbullet);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d, (float)d1, (float)d2);
		GL11.glRotatef((entitytankbullet.prevRotationYaw + (entitytankbullet.rotationYaw - entitytankbullet.prevRotationYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entitytankbullet.prevRotationPitch + (entitytankbullet.rotationPitch - entitytankbullet.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		switch(type)
		{
		case 0:
			bulletTypeSetup(0, 16, 0, 10, 0, 10, 10, 20, 4);
			break;
		case 1:
			bulletTypeSetup(0, 16, 20, 25, 0, 5, 25, 30, 2);
			break;
		case 2:
			bulletTypeSetup(16, 32, 0, 10, 16, 26, 10, 20, 4);
			break;
		case 3:
			bulletTypeSetup(16, 32, 20, 25, 16, 21, 25, 30, 2);
			break;
		default:
			bulletTypeSetup(0, 16, 30, 31, 0, 1, 31, 32, 1);
		}
		
		float f10 = 0.05625F;
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		float f11 = entitytankbullet.tankBulletShake - f1;
		if(f11 > 0.0F)
		{
			float f12 = -MathHelper.sin(f11 * 3F) * f11;
			GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
		}
		GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(f10, f10, f10);
		GL11.glTranslatef(-4F, 0.0F, 0.0F);
		GL11.glNormal3f(f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-8D, -UVs[8], -UVs[8], UVs[4], UVs[6]);
		tessellator.addVertexWithUV(-8D, -UVs[8], UVs[8], UVs[5], UVs[6]);
		tessellator.addVertexWithUV(-8D, UVs[8], UVs[8], UVs[5], UVs[7]);
		tessellator.addVertexWithUV(-8D, UVs[8], -UVs[8], UVs[4], UVs[7]);
		tessellator.draw();
		GL11.glNormal3f(-f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-8D, UVs[8], -UVs[8], UVs[4], UVs[6]);
		tessellator.addVertexWithUV(-8D, UVs[8], UVs[8], UVs[5], UVs[6]);
		tessellator.addVertexWithUV(-8D, -UVs[8], UVs[8], UVs[5], UVs[7]);
		tessellator.addVertexWithUV(-8D, -UVs[8], -UVs[8], UVs[4], UVs[7]);
		tessellator.draw();
		for(int j = 0; j < 4; j++)
		{
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f10);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8D, -UVs[8], 0.0D, UVs[0], UVs[2]);
			tessellator.addVertexWithUV(8D, -UVs[8], 0.0D, UVs[1], UVs[2]);
			tessellator.addVertexWithUV(8D, UVs[8], 0.0D, UVs[1], UVs[3]);
			tessellator.addVertexWithUV(-8D, UVs[8], 0.0D, UVs[0], UVs[3]);
			tessellator.draw();
		}

		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, 
			float f, float f1)
	{
		renderArrow((EntityTankBullet)entity, d, d1, d2, f, f1);
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return bulletTex;
	}
}
