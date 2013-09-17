package zornco.tank.client.render;

import zornco.tank.entity.EntityTankBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTank2 extends ModelBase
{
	//fields
	ModelRenderer base;
	ModelRenderer baseBottom;
	ModelRenderer baseFront;
	ModelRenderer baseBack;
	ModelRenderer baseRight;
	ModelRenderer baseLeft;
	ModelRenderer wheelLB;
	ModelRenderer wheelRF;
	ModelRenderer wheelRtopfront;
	ModelRenderer wheelRB;
	ModelRenderer wheelLF;
	ModelRenderer wheelLM;
	ModelRenderer wheelRM;
	ModelRenderer wheelRtop;
	ModelRenderer wheelLtop;
	ModelRenderer wheelLtopfront;
	ModelRenderer muzzle;
	ModelRenderer turretB;
	ModelRenderer turretL;
	ModelRenderer turretR;
	ModelRenderer turretLT;
	ModelRenderer turretRT;
	ModelRenderer turretBT;
	ModelRenderer seat;
	ModelRenderer seatL;
	ModelRenderer seatB;
	ModelRenderer seatR;
	ModelRenderer stickL;
	ModelRenderer stickR;
	ModelRenderer roof;
	ModelRenderer pillar3;
	ModelRenderer pillar4;
	ModelRenderer pillar2;
	ModelRenderer pillar1;
	ModelRenderer bakcWindow;
	ModelRenderer rightWindow;
	ModelRenderer frontWindow;
	ModelRenderer leftWindow;

	public ModelTank2()
	{

		base = new ModelRenderer(this, 24, 0).setTextureSize(128, 64);
		base.addBox(-15F, -6F, 0F, 5, 12, 6);
		base.setRotationPoint(0F, 6F, 0F);
		base.setTextureSize(128, 64);
		setRotation(base, 1.570796F, 0F, 0F);
		
		baseBottom = new ModelRenderer(this, 0, 44).setTextureSize(128, 64);
		baseBottom.addBox(-5F, -6F, -1F, 14, 12, 2);
		baseBottom.setRotationPoint(0F, 6F, 0F);
		baseBottom.setTextureSize(128, 64);
		setRotation(baseBottom, 1.570796F, 0F, 0F);
		
		baseFront = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		baseFront.addBox(-10F, -8F, -1F, 5, 16, 7);
		baseFront.setRotationPoint(0F, 6F, 0F);
		baseFront.setTextureSize(128, 64);
		setRotation(baseFront, 1.570796F, 0F, 0F);
		
		baseBack = new ModelRenderer(this, 32, 42).setTextureSize(128, 64);
		baseBack.addBox(-8F, -6F, 9F, 16, 7, 2);
		baseBack.setRotationPoint(0F, 6F, 0F);
		baseBack.setTextureSize(128, 64);
		setRotation(baseBack, 0F, 1.570796F, 0F);
		
		baseRight = new ModelRenderer(this, 0, 26).setTextureSize(128, 64);
		baseRight.addBox(-5F, -6F, 6F, 14, 7, 2);
		baseRight.setRotationPoint(0F, 6F, 0F);
		baseRight.setTextureSize(128, 64);
		setRotation(baseRight, 0F, 0F, 0F);
		
		baseLeft = new ModelRenderer(this, 0, 35).setTextureSize(128, 64);
		baseLeft.addBox(-5F, -6F, -8F, 14, 7, 2);
		baseLeft.setRotationPoint(0F, 6F, 0F);
		baseLeft.setTextureSize(128, 64);
		setRotation(baseLeft, 0F, 0F, 0F);
		
		wheelLB = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		wheelLB.addBox(-2F, -2F, 0F, 4, 4, 3);
		wheelLB.setRotationPoint(6F, 6F, -8F);
		wheelLB.setTextureSize(128, 64);
		setRotation(wheelLB, 0F, 3.141593F, 0F);
		
		wheelRF = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		wheelRF.addBox(-2F, -2F, 0F, 4, 4, 3);
		wheelRF.setRotationPoint(-6F, 6F, 8F);
		wheelRF.setTextureSize(128, 64);
		setRotation(wheelRF, 0F, 0F, 0F);
		
		wheelRtopfront = new ModelRenderer(this, 78, 0).setTextureSize(128, 64);
		wheelRtopfront.addBox(-4F, 0F, -2F, 4, 1, 5);
		wheelRtopfront.setRotationPoint(-10F, 2F, 8F);
		wheelRtopfront.setTextureSize(128, 64);
		setRotation(wheelRtopfront, 0F, 0F, -0.7853982F);
		
		wheelRB = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		wheelRB.addBox(-2F, -2F, 0F, 4, 4, 3);
		wheelRB.setRotationPoint(6F, 6F, 8F);
		wheelRB.setTextureSize(128, 64);
		setRotation(wheelRB, 0F, 0F, 0F);
		
		wheelLF = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		wheelLF.addBox(-2F, -2F, 0F, 4, 4, 3);
		wheelLF.setRotationPoint(-6F, 6F, -8F);
		wheelLF.setTextureSize(128, 64);
		setRotation(wheelLF, 0F, 3.141593F, 0F);
		
		wheelLM = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		wheelLM.addBox(-2F, -2F, 0F, 4, 4, 3);
		wheelLM.setRotationPoint(0F, 6F, -8F);
		wheelLM.setTextureSize(128, 64);
		setRotation(wheelLM, 0F, 3.141593F, 0F);
		
		wheelRM = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		wheelRM.addBox(-2F, -2F, 0F, 4, 4, 3);
		wheelRM.setRotationPoint(0F, 6F, 8F);
		wheelRM.setTextureSize(128, 64);
		setRotation(wheelRM, 0F, 0F, 0F);
		
		wheelRtop = new ModelRenderer(this, 46, 12).setTextureSize(128, 64);
		wheelRtop.addBox(-10F, 0F, 0F, 20, 1, 4);
		wheelRtop.setRotationPoint(0F, 2F, 8F);
		wheelRtop.setTextureSize(128, 64);
		setRotation(wheelRtop, 0F, 0F, 0F);
		
		wheelLtop = new ModelRenderer(this, 46, 7).setTextureSize(128, 64);
		wheelLtop.addBox(-10F, 0F, 0F, 20, 1, 4);
		wheelLtop.setRotationPoint(0F, 2F, -12F);
		wheelLtop.setTextureSize(128, 64);
		setRotation(wheelLtop, 0F, 0F, 0F);
		
		wheelLtopfront = new ModelRenderer(this, 60, 0).setTextureSize(128, 64);
		wheelLtopfront.addBox(-4F, 0F, 0F, 4, 1, 5);
		wheelLtopfront.setRotationPoint(-10F, 2F, -11F);
		wheelLtopfront.setTextureSize(128, 64);
		setRotation(wheelLtopfront, 0F, 0F, -0.7853982F);
		
		muzzle = new ModelRenderer(this, 0, 58).setTextureSize(128, 64);
		muzzle.addBox(-12F, -1.5F, -1.5F, 13, 3, 3);
		muzzle.setRotationPoint(-10F, -2F, 0F);
		muzzle.setTextureSize(128, 64);
		setRotation(muzzle, 0F, 0F, 0F);
		
		turretB = new ModelRenderer(this, 46, 32).setTextureSize(128, 64);
		turretB.addBox(2F, -2F, -3F, 2, 4, 6);
		turretB.setRotationPoint(-10F, -2F, 0F);
		turretB.setTextureSize(128, 64);
		setRotation(turretB, 0F, 0F, 0F);
		
		turretL = new ModelRenderer(this, 52, 26).setTextureSize(128, 64);
		turretL.addBox(-4F, -2F, -4F, 7, 4, 2);
		turretL.setRotationPoint(-10F, -2F, 0F);
		turretL.setTextureSize(128, 64);
		setRotation(turretL, 0F, 0F, 0F);
		
		turretR = new ModelRenderer(this, 32, 26).setTextureSize(128, 64);
		turretR.addBox(-4F, -2F, 2F, 7, 4, 2);
		turretR.setRotationPoint(-10F, -2F, 0F);
		turretR.setTextureSize(128, 64);
		setRotation(turretR, 0F, 0F, 0F);
		
		turretLT = new ModelRenderer(this, 32, 39).setTextureSize(128, 64);
		turretLT.addBox(-3F, -3F, -3F, 5, 1, 1);
		turretLT.setRotationPoint(-10F, -2F, 0F);
		turretLT.setTextureSize(128, 64);
		setRotation(turretLT, 0F, 0F, 0F);
		
		turretRT = new ModelRenderer(this, 32, 37).setTextureSize(128, 64);
		turretRT.addBox(-3F, -3F, 2F, 5, 1, 1);
		turretRT.setRotationPoint(-10F, -2F, 0F);
		turretRT.setTextureSize(128, 64);
		setRotation(turretRT, 0F, 0F, 0F);
		
		turretBT = new ModelRenderer(this, 32, 32).setTextureSize(128, 64);
		turretBT.addBox(0F, -3F, -2F, 3, 1, 4);
		turretBT.setRotationPoint(-10F, -2F, 0F);
		turretBT.setTextureSize(128, 64);
		setRotation(turretBT, 0F, 0F, 0F);
		
		seat = new ModelRenderer(this, 52, 18).setTextureSize(128, 64);
		seat.addBox(-3F, 0F, -3F, 4, 2, 6);
		seat.setRotationPoint(6F, 3F, 0F);
		seat.setTextureSize(128, 64);
		setRotation(seat, 0F, 0F, 0F);
		
		seatL = new ModelRenderer(this, 28, 18).setTextureSize(128, 64);
		seatL.addBox(-2F, -3F, -5F, 2, 6, 2);
		seatL.setRotationPoint(6F, 3F, 0F);
		seatL.setTextureSize(128, 64);
		setRotation(seatL, 0F, 0F, 1.570796F);
		
		seatB = new ModelRenderer(this, 36, 18).setTextureSize(128, 64);
		seatB.addBox(-3F, -6F, 1F, 6, 6, 2);
		seatB.setRotationPoint(6F, 3F, 0F);
		seatB.setTextureSize(128, 64);
		setRotation(seatB, 0F, 1.570796F, 0F);
		
		seatR = new ModelRenderer(this, 28, 18).setTextureSize(128, 64);
		seatR.addBox(0F, -3F, 3F, 2, 6, 2);
		seatR.setRotationPoint(6F, 3F, 0F);
		seatR.setTextureSize(128, 64);
		setRotation(seatR, 0F, 0F, -1.570796F);
		
		stickL = new ModelRenderer(this, 24, 18).setTextureSize(128, 64);
		stickL.addBox(-0.5F, -6F, -0.5F, 1, 7, 1);
		stickL.setRotationPoint(-1F, 5F, -3F);
		stickL.setTextureSize(128, 64);
		setRotation(stickL, 0F, 0F, -0.3926991F);
		
		stickR = new ModelRenderer(this, 24, 18).setTextureSize(128, 64);
		stickR.addBox(-0.5F, -6F, -0.5F, 1, 7, 1);
		stickR.setRotationPoint(-1F, 5F, 3F);
		stickR.setTextureSize(128, 64);
		setRotation(stickR, 0F, 0F, -0.3926991F);
		
		roof = new ModelRenderer(this, 32, 51).setTextureSize(128, 64);
		roof.addBox(-5F, -4F, -5F, 10, 8, 1);
		roof.setRotationPoint(2F, -8F, 0F);
		roof.setTextureSize(128, 64);
		setRotation(roof, -1.570796F, 0F, 0F);
		
		pillar3 = new ModelRenderer(this, 94, 6).setTextureSize(128, 64);
		pillar3.addBox(-0.5F, 0F, -2F, 1, 14, 1);
		pillar3.setRotationPoint(-3F, -13F, 4F);
		pillar3.setTextureSize(128, 64);
		setRotation(pillar3, 0.3490659F, -0.7853982F, 0F);
		
		pillar4 = new ModelRenderer(this, 94, 6).setTextureSize(128, 64);
		pillar4.addBox(-0.5F, 0F, -2F, 1, 14, 1);
		pillar4.setRotationPoint(7F, -13F, 4F);
		pillar4.setTextureSize(128, 64);
		setRotation(pillar4, 0.3490659F, 0.7853982F, 0F);
		
		pillar2 = new ModelRenderer(this, 94, 6).setTextureSize(128, 64);
		pillar2.addBox(-0.5F, 0F, -2F, 1, 14, 1);
		pillar2.setRotationPoint(7F, -13F, -4F);
		pillar2.setTextureSize(128, 64);
		setRotation(pillar2, 0.3490659F, 2.356194F, 0F);
		
		pillar1 = new ModelRenderer(this, 94, 6).setTextureSize(128, 64);
		pillar1.addBox(-0.5F, 0F, -2F, 1, 14, 1);
		pillar1.setRotationPoint(-3F, -13F, -4F);
		pillar1.setTextureSize(128, 64);
		setRotation(pillar1, 0.3490659F, -2.356194F, 0F);
		
		bakcWindow = new ModelRenderer(this, 102, 42).setTextureSize(128, 64);
		bakcWindow.addBox(-2.5F, 0F, -1F, 13, 14, 0);
		bakcWindow.setRotationPoint(7F, -13F, 4F);
		bakcWindow.setTextureSize(128, 64);
		setRotation(bakcWindow, 0.2443461F, 1.570796F, 0F);
		
		rightWindow = new ModelRenderer(this, 98, 0).setTextureSize(128, 64);
		rightWindow.addBox(-3.5F, 0F, -1F, 15, 14, 0);
		rightWindow.setRotationPoint(-2F, -13F, 4F);
		rightWindow.setTextureSize(128, 64);
		setRotation(rightWindow, 0.2443461F, 0F, 0F);
		
		frontWindow = new ModelRenderer(this, 102, 28).setTextureSize(128, 64);
		frontWindow.addBox(-2.5F, 0F, 1F, 13, 14, 0);
		frontWindow.setRotationPoint(-3F, -13F, 4F);
		frontWindow.setTextureSize(128, 64);
		setRotation(frontWindow, -0.2443461F, 1.570796F, 0F);
		
		leftWindow = new ModelRenderer(this, 98, 14).setTextureSize(128, 64);
		leftWindow.addBox(-3.5F, 0F, 1F, 15, 14, 0);
		leftWindow.setRotationPoint(-2F, -13F, -4F);
		leftWindow.setTextureSize(128, 64);
		setRotation(leftWindow, -0.2443461F, 0F, 0F);
		
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		f5 *= 2f;
		base.render(f5);
		baseBottom.render(f5);
		baseFront.render(f5);
		baseBack.render(f5);
		baseRight.render(f5);
		baseLeft.render(f5);
		wheelLB.render(f5);
		wheelRF.render(f5);
		wheelRtopfront.render(f5);
		wheelRB.render(f5);
		wheelLF.render(f5);
		wheelLM.render(f5);
		wheelRM.render(f5);
		wheelRtop.render(f5);
		wheelLtop.render(f5);
		wheelLtopfront.render(f5);
		muzzle.renderWithRotation(f5);
		turretB.renderWithRotation(f5);
		turretL.renderWithRotation(f5);
		turretR.renderWithRotation(f5);
		turretLT.renderWithRotation(f5);
		turretRT.renderWithRotation(f5);
		turretBT.renderWithRotation(f5);
		roof.renderWithRotation(f5);
		seat.render(f5);
		seatL.render(f5);
		seatB.render(f5);
		seatR.render(f5);
		stickL.render(f5);
		stickR.render(f5);
		pillar3.render(f5);
		pillar4.render(f5);
		pillar2.render(f5);
		pillar1.render(f5);
		bakcWindow.render(f5);
		rightWindow.render(f5);
		frontWindow.render(f5);
		leftWindow.render(f5);
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
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if(entity.riddenByEntity != null)
		{
			setRotation(wheelLB, 0, (float)Math.PI, ((EntityTankBase) entity).getSpeed());
			setRotation(wheelLM, 0, (float)Math.PI, ((EntityTankBase) entity).getSpeed());
			setRotation(wheelLF, 0, (float)Math.PI, ((EntityTankBase) entity).getSpeed());
			setRotation(wheelRB, 0, 0, ((EntityTankBase) entity).getSpeed());
			setRotation(wheelRM, 0, 0, ((EntityTankBase) entity).getSpeed());
			setRotation(wheelRF, 0, 0, ((EntityTankBase) entity).getSpeed());
			setRotation(muzzle, 0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((-entity.riddenByEntity.rotationPitch)), -10, 45) * (float)Math.PI / 180.0F);
			setRotation(turretB,   0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, 0);
			setRotation(turretL,   0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, 0);
			setRotation(turretR,   0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, 0);
			setRotation(turretLT,  0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, 0);
			setRotation(turretRT,  0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, 0);
			setRotation(turretBT,  0, MathHelper.clamp_float(MathHelper.wrapAngleTo180_float((entity.riddenByEntity.rotationYaw - entity.rotationYaw - 90)), -100, 100) * (float)Math.PI / 180.0F, 0);
		}
		else
		{
			setRotation(wheelLB, 0, (float)Math.PI, 0);
			setRotation(wheelLM, 0, (float)Math.PI, 0);
			setRotation(wheelLF, 0, (float)Math.PI, 0);
			setRotation(wheelRB, 0, 0, 0);
			setRotation(wheelRM, 0, 0, 0);
			setRotation(wheelRF, 0, 0, 0);
			setRotation(muzzle, 0, 0, 0);
			setRotation(turretB,   0, 0, 0);
			setRotation(turretL,   0, 0, 0);
			setRotation(turretR,   0, 0, 0);
			setRotation(turretLT,  0, 0, 0);
			setRotation(turretRT,  0, 0, 0);
			setRotation(turretBT,  0, 0, 0);
		}
	}

}
