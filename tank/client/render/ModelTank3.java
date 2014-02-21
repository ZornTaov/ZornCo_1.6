package zornco.tank.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelTank3 extends ModelBase {

	private IModelCustom modelTank;
    
    public ModelTank3()
    {
        modelTank = AdvancedModelLoader.loadModel("/assets/tank/models/Tank.obj");
    }
    @Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GL11.glPushMatrix();
		GL11.glColor3ub((byte)0, (byte)166, (byte)88);
    	modelTank.renderPart("Tank");
    	GL11.glColor3f(0.5F, 0.5F, 0.5F);
    	modelTank.renderPart("TreadLeft");
    	modelTank.renderPart("TreadRight");
    	GL11.glColor3f(0.5F, 0.25F, 0F);
    	modelTank.renderPart("Chair");
    	GL11.glColor3f(1F, 1F, 1F);
    	modelTank.renderPart("Panel");
    	GL11.glColor3f(1F, 1F, 1F);
		if(entity.riddenByEntity != null)
		{
			GL11.glTranslatef(0.0F, 0.50F, 0.915F);
	        GL11.glRotatef(-MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((-entity.riddenByEntity.rotationPitch)), -25, 65), 1.0F, 0.0F, 0.0F);
	    	modelTank.renderPart("Muzzle");
		}
		else
		{	
			GL11.glTranslatef(0.915F, 0.50F, 0.0F);
	    	modelTank.renderPart("Muzzle");
		}
        GL11.glPopMatrix();
	}
}
