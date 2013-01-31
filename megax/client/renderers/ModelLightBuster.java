package zornco.megax.client.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLightBuster extends ModelBase
{
	//fields
	ModelRenderer Top;
	ModelRenderer Bottom;
	ModelRenderer Left;
	ModelRenderer Right;
	ModelRenderer Back;
	ModelRenderer button;
	ModelRenderer Nozzle;
	ModelRenderer FrontTop;
	ModelRenderer FrontBottom;
	ModelRenderer FrontLeft;
	ModelRenderer FrontRight;
	ModelRenderer nodeLeft;
	ModelRenderer nodeRight;
	ModelRenderer ventLeft;
	ModelRenderer ventRight;

	public ModelLightBuster()
	{
		textureWidth = 64;
		textureHeight = 32;

		Top = new ModelRenderer(this, 0, 9);
		Top.addBox(-3F, -3F, 3F, 7, 6, 1);
		Top.setRotationPoint(0F, 0F, 0F);
		Top.setTextureSize(64, 32);
		setRotation(Top, 1.570796F, 0F, 0F);
		
		Bottom = new ModelRenderer(this, 0, 9);
		Bottom.addBox(-3F, -3F, 3F, 7, 6, 1);
		Bottom.setRotationPoint(0F, 0F, 0F);
		Bottom.setTextureSize(64, 32);
		setRotation(Bottom, -1.570796F, 0F, 0F);
		
		Left = new ModelRenderer(this, 0, 9);
		Left.addBox(-4F, -3F, -4F, 7, 6, 1);
		Left.setRotationPoint(1F, 0F, 0F);
		Left.setTextureSize(64, 32);
		setRotation(Left, 3.141593F, 0F, 0F);
		
		Right = new ModelRenderer(this, 16, 9);
		Right.addBox(-3F, -3F, -4F, 7, 6, 1);
		Right.setRotationPoint(0F, 0F, 0F);
		Right.setTextureSize(64, 32);
		
		Back = new ModelRenderer(this, 12, 16);
		Back.addBox(-3F, -3F, 4F, 6, 6, 1);
		Back.setRotationPoint(0F, 0F, 0F);
		Back.setTextureSize(64, 32);
		setRotation(Back, 0F, 1.570796F, 0F);
		
		button = new ModelRenderer(this, 8, 5);
		button.addBox(-1.5F, -1.5F, -5F, 3, 3, 1);
		button.setRotationPoint(0F, 0F, 0F);
		button.setTextureSize(64, 32);
		
		Nozzle = new ModelRenderer(this, 20, 0);
		Nozzle.addBox(-2F, -2F, -6F, 4, 4, 1);
		Nozzle.setRotationPoint(0F, 0F, 0F);
		Nozzle.setTextureSize(64, 32);
		setRotation(Nozzle, 0F, 1.570796F, 0F);
		
		FrontTop = new ModelRenderer(this, 10, 0);
		FrontTop.addBox(-7F, -2F, -3F, 4, 4, 1);
		FrontTop.setRotationPoint(0F, 0F, 0F);
		FrontTop.setTextureSize(64, 32);
		setRotation(FrontTop, -1.570796F, 0F, 0F);

		FrontBottom = new ModelRenderer(this, 10, 0);
		FrontBottom.addBox(-7F, -2F, -3F, 4, 4, 1);
		FrontBottom.setRotationPoint(0F, 0F, 0F);
		FrontBottom.setTextureSize(64, 32);
		FrontBottom.mirror = true;
		setRotation(FrontBottom, 1.570796F, 0F, 0F);

		FrontLeft = new ModelRenderer(this, 0, 0);
		FrontLeft.addBox(-7F, -2F, -3F, 4, 4, 1);
		FrontLeft.setRotationPoint(0F, 0F, 0F);
		FrontLeft.setTextureSize(64, 32);
		FrontLeft.mirror = true;
		setRotation(FrontLeft, 3.141593F, 0F, 0F);
		
		FrontRight = new ModelRenderer(this, 0, 0);
		FrontRight.addBox(-7F, -2F, -3F, 4, 4, 1);
		FrontRight.setRotationPoint(0F, 0F, 0F);
		FrontRight.setTextureSize(64, 32);

		nodeLeft = new ModelRenderer(this, 0, 5);
		nodeLeft.addBox(-5F, 2.2F, 0F, 2, 2, 2);
		nodeLeft.setRotationPoint(0F, 0F, 0F);
		nodeLeft.setTextureSize(64, 32);
		setRotation(nodeLeft, 0.4014257F, 0F, 0F);

		nodeRight = new ModelRenderer(this, 0, 5);
		nodeRight.addBox(-5F, 2.2F, -2F, 2, 2, 2);
		nodeRight.setRotationPoint(0F, 0F, 0F);
		nodeRight.setTextureSize(64, 32);
		setRotation(nodeRight, -0.4014257F, 0F, 0F);

		ventLeft = new ModelRenderer(this, 0, 16);
		ventLeft.addBox(-5F, -1.5F, -5F, 3, 3, 3);
		ventLeft.setRotationPoint(0F, 0F, 0F);
		ventLeft.setTextureSize(64, 32);
		setRotation(ventLeft, -2.356194F, 0F, 0F);

		ventRight = new ModelRenderer(this, 0, 16);
		ventRight.addBox(-5F, -1.5F, -5F, 3, 3, 3);
		ventRight.setRotationPoint(0F, 0F, 0F);
		ventRight.setTextureSize(64, 32);
		setRotation(ventRight, -0.7853982F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Top.render(f5);
		Bottom.render(f5);
		Left.render(f5);
		Right.render(f5);
		Back.render(f5);
		button.render(f5);
		Nozzle.render(f5);
		FrontTop.render(f5);
		FrontBottom.render(f5);
		FrontLeft.render(f5);
		FrontRight.render(f5);
		nodeLeft.render(f5);
		nodeRight.render(f5);
		ventLeft.render(f5);
		ventRight.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, par7Entity);
	}

}
