package zornco.megax.client.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelXBuster extends ModelBase {

	//fields
	ModelRenderer Left;
	ModelRenderer Front;
	ModelRenderer Bottom;
	ModelRenderer InnerFront;
	ModelRenderer Right;
	ModelRenderer NozzleBottom;
	ModelRenderer Nozzle;
	ModelRenderer NozzleRight;
	ModelRenderer Back;
	ModelRenderer NozzleLeft;
	ModelRenderer NozzleTop;
	ModelRenderer Top;
	ModelRenderer Inner;
	ModelRenderer InnerBack;
	ModelRenderer InnerRight;
	ModelRenderer InnerLeft;

	public ModelXBuster()
	{
		textureWidth = 256;
		textureHeight = 256;

		Left = new ModelRenderer(this, 0, 8);
		Left.addBox(-4F, -3F, -4F, 7, 6, 1);
		Left.setRotationPoint(1F, 16F, 0F);
		Left.setTextureSize(64, 32);
		//Left.mirror = true;
		setRotation(Left, 3.141593F, 0F, 0F);
		
		Front = new ModelRenderer(this, 0, 0);
		Front.addBox(-3F, -3F, -5F, 6, 6, 2);
		Front.setRotationPoint(0F, 16F, 0F);
		Front.setTextureSize(64, 32);
		//Front.mirror = true;
		setRotation(Front, 0F, 1.570796F, 0F);
		
		Bottom = new ModelRenderer(this, 0, 8);
		Bottom.addBox(-4F, -3F, 3F, 7, 6, 1);
		Bottom.setRotationPoint(1F, 16F, 0F);
		Bottom.setTextureSize(64, 32);
		//Bottom.mirror = true;
		setRotation(Bottom, -1.570796F, 0F, 0F);
		
		Right = new ModelRenderer(this, 0, 8);
		Right.addBox(-4F, -3F, -4F, 7, 6, 1);
		Right.setRotationPoint(1F, 16F, 0F);
		Right.setTextureSize(64, 32);
		//Right.mirror = true;
		setRotation(Right, 0F, 0F, 0F);
		
		Back = new ModelRenderer(this, 16, 0);
		Back.addBox(-3F, -3F, 4F, 6, 6, 1);
		Back.setRotationPoint(0F, 16F, 0F);
		Back.setTextureSize(64, 32);
		//Back.mirror = true;
		setRotation(Back, 0F, 1.570796F, 0F);
		
		Top = new ModelRenderer(this, 16, 7);
		Top.addBox(-4F, -3F, 3F, 7, 6, 1);
		Top.setRotationPoint(1F, 16F, 0F);
		Top.setTextureSize(64, 32);
		//Top.mirror = true;
		setRotation(Top, 1.570796F, 0F, 0F);
		
		Nozzle = new ModelRenderer(this, 0, 15);
		Nozzle.addBox(-2F, -2F, -6F, 4, 4, 1);
		Nozzle.setRotationPoint(0F, 16F, 0F);
		Nozzle.setTextureSize(64, 32);
		//Nozzle.mirror = true;
		setRotation(Nozzle, 0F, 1.570796F, 0F);
		
		NozzleRight = new ModelRenderer(this, 0, 20);
		NozzleRight.addBox(-2F, -1F, -7F, 1, 2, 1);
		NozzleRight.setRotationPoint(0F, 16F, 0F);
		NozzleRight.setTextureSize(64, 32);
		//NozzleRight.mirror = true;
		setRotation(NozzleRight,  0F, 1.570796F,0F);
		
		NozzleBottom = new ModelRenderer(this, 0, 20);
		NozzleBottom.addBox(-2F, -1F, -7F, 1, 2, 1);
		NozzleBottom.setRotationPoint(0F, 16F, 0F);
		NozzleBottom.setTextureSize(64, 32);
		//NozzleBottom.mirror = true;
		setRotation(NozzleBottom, -1.570796F, 0F, -1.570796F);
		
		NozzleLeft = new ModelRenderer(this, 0, 20);
		NozzleLeft.addBox(-2F, -1F, -7F, 1, 2, 1);
		NozzleLeft.setRotationPoint(0F, 16F, 0F);
		NozzleLeft.setTextureSize(64, 32);
		//NozzleLeft.mirror = true;
		setRotation(NozzleLeft, 0F, -1.570796F, 3.141593F);
		
		NozzleTop = new ModelRenderer(this, 0, 20);
		NozzleTop.addBox(-2F, -1F, -7F, 1, 2, 1);
		NozzleTop.setRotationPoint(0F, 16F, 0F);
		NozzleTop.setTextureSize(64, 32);
		//NozzleTop.mirror = true;
		setRotation(NozzleTop, 1.570796F, 0F, 1.570796F);
		
		Inner = new ModelRenderer(this, 20, 15);
		Inner.addBox(-2F, -1F, 3.5F, 3, 2, 0);
		Inner.setRotationPoint(1F, 16F, 0F);
		Inner.setTextureSize(64, 32);
		//Inner.mirror = true;
		setRotation(Inner, 1.570796F, 0F, 0F);
		
		InnerFront = new ModelRenderer(this, 18, 14);
		InnerFront.addBox(1F, -1F, 3F, 0, 2, 1);
		InnerFront.setRotationPoint(1F, 16F, 0F);
		InnerFront.setTextureSize(64, 32);
		//InnerFront.mirror = true;
		setRotation(InnerFront, 1.570796F, 0F, 0F);
		
		InnerBack = new ModelRenderer(this, 16, 14);
		InnerBack.addBox(-2F, -1F, 3F, 0, 2, 1);
		InnerBack.setRotationPoint(1F, 16F, 0F);
		InnerBack.setTextureSize(64, 32);
		//InnerBack.mirror = true;
		setRotation(InnerBack, 1.570796F, 0F, 0F);
		
		InnerRight = new ModelRenderer(this, 14, 14);
		InnerRight.addBox(-2F, -1F, 3F, 3, 0, 1);
		InnerRight.setRotationPoint(1F, 16F, 0F);
		InnerRight.setTextureSize(64, 32);
		//InnerRight.mirror = true;
		setRotation(InnerRight, 1.570796F, 0F, 0F);
		
		InnerLeft = new ModelRenderer(this, 20, 14);
		InnerLeft.addBox(-2F, 1F, 3F, 3, 0, 1);
		InnerLeft.setRotationPoint(1F, 16F, 0F);
		InnerLeft.setTextureSize(64, 32);
		//InnerLeft.mirror = true;
		setRotation(InnerLeft, 1.570796F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Top.render(f5);
		Left.render(f5);
		Front.render(f5);
		Bottom.render(f5);
		Right.render(f5);
		Back.render(f5);
	}
	public void render2(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		Inner.render(f5);
		InnerFront.render(f5);
		InnerBack.render(f5);
		InnerRight.render(f5);
		InnerLeft.render(f5);
		Nozzle.render(f5);
		NozzleBottom.render(f5);
		NozzleRight.render(f5);
		NozzleLeft.render(f5);
		NozzleTop.render(f5);
	}
	public void renderInner(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{

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