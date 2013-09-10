package zornco.bedcraftbeyond.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelStoneBed extends ModelBase {

	ModelRenderer bottom2;
	ModelRenderer top2;
	ModelRenderer bottom1;
	ModelRenderer top1;
	public ModelStoneBed() {
		bottom2 = new ModelRenderer(this, 0, 42).setTextureSize(64, 64);
		bottom2.addBox(-6F, 0F, -8F, 12, 4, 14);
		bottom2.setRotationPoint(0F, -4F, 0F);
		bottom2.setTextureSize(64, 64);
		bottom2.mirror = true;
		setRotation(bottom2, 0F, 3.141593F, 0F);
		top2 = new ModelRenderer(this, 0, 21).setTextureSize(64, 64);
		top2.addBox(0F, 0F, 0F, 16, 5, 16);
		top2.setRotationPoint(-8F, -9F, -8F);
		top2.setTextureSize(64, 64);
		top2.mirror = true;
		setRotation(top2, 0F, 0F, 0F);
		bottom1 = new ModelRenderer(this, 0, 42).setTextureSize(64, 64);
		bottom1.addBox(-6F, 0F, -8F, 12, 4, 14);
		bottom1.setRotationPoint(0F, -4F, 0F);
		bottom1.setTextureSize(64, 64);
		bottom1.mirror = true;
		setRotation(bottom1, 0F, 0F, 0F);
		top1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		top1.addBox(0F, 0F, 0F, 16, 5, 16);
		top1.setRotationPoint(-8F, -9F, -8F);
		top1.setTextureSize(64, 64);
		top1.mirror = true;
		setRotation(top1, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if(f == 0)
		{
			bottom2.render(f5);
			top2.render(f5);
		}
		else
		{
			bottom1.render(f5);
			top1.render(f5);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}

}
