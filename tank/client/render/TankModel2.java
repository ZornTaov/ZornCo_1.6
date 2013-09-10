package zornco.tank.client.render;

import zornco.tank.entity.TankEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class TankModel2 extends ModelBase
{
	//fields
	ModelRenderer Base;
	ModelRenderer WheelLB;
	ModelRenderer WheelRF;
	ModelRenderer WheelRtopfront;
	ModelRenderer WheelRB;
	ModelRenderer WheelLF;
	ModelRenderer WheelLM;
	ModelRenderer WheelRM;
	ModelRenderer WheelRtop;
	ModelRenderer WheelLtop;
	ModelRenderer WheelLtopfront;
	ModelRenderer BaseLeft;
	ModelRenderer muzzle;
	ModelRenderer turretB;
	ModelRenderer turretL;
	ModelRenderer turretR;
	ModelRenderer turretLT;
	ModelRenderer turretRT;
	ModelRenderer turretBT;
	ModelRenderer Seat;
	ModelRenderer SeatL;
	ModelRenderer SeatB;
	ModelRenderer SeatR;
	ModelRenderer StickL;
	ModelRenderer StickR;
	ModelRenderer BaseBottom;
	ModelRenderer BaseFront;
	ModelRenderer BaseBack;
	ModelRenderer BaseRight;
	ModelRenderer TurretTop;
	ModelRenderer pillar3;
	ModelRenderer pillar4;
	ModelRenderer pillar2;
	ModelRenderer pillar1;
	ModelRenderer bakcWindow;
	ModelRenderer rightWindow;
	ModelRenderer frontWindow;
	ModelRenderer leftWindow;

	public TankModel2()
	{

		Base = new ModelRenderer(this, 24, 0).setTextureSize(128, 64);
		Base.addBox(-15F, -6F, 0F, 5, 12, 6);
		Base.setRotationPoint(0F, 6F, 0F);
		Base.setTextureSize(128, 64);
		setRotation(Base, 1.570796F, 0F, 0F);
		
		WheelLB = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		WheelLB.addBox(-2F, -2F, 0F, 4, 4, 3);
		WheelLB.setRotationPoint(6F, 6F, -8F);
		WheelLB.setTextureSize(128, 64);
		setRotation(WheelLB, 0F, 3.141593F, 0F);
		
		WheelRF = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		WheelRF.addBox(-2F, -2F, 0F, 4, 4, 3);
		WheelRF.setRotationPoint(-6F, 6F, 8F);
		WheelRF.setTextureSize(128, 64);
		setRotation(WheelRF, 0F, 0F, 0F);
		
		WheelRtopfront = new ModelRenderer(this, 78, 0).setTextureSize(128, 64);
		WheelRtopfront.addBox(-4F, 0F, -2F, 4, 1, 5);
		WheelRtopfront.setRotationPoint(-10F, 2F, 8F);
		WheelRtopfront.setTextureSize(128, 64);
		setRotation(WheelRtopfront, 0F, 0F, -0.7853982F);
		
		WheelRB = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		WheelRB.addBox(-2F, -2F, 0F, 4, 4, 3);
		WheelRB.setRotationPoint(6F, 6F, 8F);
		WheelRB.setTextureSize(128, 64);
		setRotation(WheelRB, 0F, 0F, 0F);
		
		WheelLF = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		WheelLF.addBox(-2F, -2F, 0F, 4, 4, 3);
		WheelLF.setRotationPoint(-6F, 6F, -8F);
		WheelLF.setTextureSize(128, 64);
		setRotation(WheelLF, 0F, 3.141593F, 0F);
		
		WheelLM = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		WheelLM.addBox(-2F, -2F, 0F, 4, 4, 3);
		WheelLM.setRotationPoint(0F, 6F, -8F);
		WheelLM.setTextureSize(128, 64);
		setRotation(WheelLM, 0F, 3.141593F, 0F);
		
		WheelRM = new ModelRenderer(this, 46, 0).setTextureSize(128, 64);
		WheelRM.addBox(-2F, -2F, 0F, 4, 4, 3);
		WheelRM.setRotationPoint(0F, 6F, 8F);
		WheelRM.setTextureSize(128, 64);
		setRotation(WheelRM, 0F, 0F, 0F);
		
		WheelRtop = new ModelRenderer(this, 46, 12).setTextureSize(128, 64);
		WheelRtop.addBox(-10F, 0F, 0F, 20, 1, 4);
		WheelRtop.setRotationPoint(0F, 2F, 8F);
		WheelRtop.setTextureSize(128, 64);
		setRotation(WheelRtop, 0F, 0F, 0F);
		
		WheelLtop = new ModelRenderer(this, 46, 7).setTextureSize(128, 64);
		WheelLtop.addBox(-10F, 0F, 0F, 20, 1, 4);
		WheelLtop.setRotationPoint(0F, 2F, -12F);
		WheelLtop.setTextureSize(128, 64);
		setRotation(WheelLtop, 0F, 0F, 0F);
		
		WheelLtopfront = new ModelRenderer(this, 60, 0).setTextureSize(128, 64);
		WheelLtopfront.addBox(-4F, 0F, 0F, 4, 1, 5);
		WheelLtopfront.setRotationPoint(-10F, 2F, -11F);
		WheelLtopfront.setTextureSize(128, 64);
		setRotation(WheelLtopfront, 0F, 0F, -0.7853982F);
		
		BaseLeft = new ModelRenderer(this, 0, 35).setTextureSize(128, 64);
		BaseLeft.addBox(-5F, -6F, -8F, 14, 7, 2);
		BaseLeft.setRotationPoint(0F, 6F, 0F);
		BaseLeft.setTextureSize(128, 64);
		setRotation(BaseLeft, 0F, 0F, 0F);
		
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
		
		Seat = new ModelRenderer(this, 52, 18).setTextureSize(128, 64);
		Seat.addBox(-3F, 0F, -3F, 4, 2, 6);
		Seat.setRotationPoint(6F, 3F, 0F);
		Seat.setTextureSize(128, 64);
		setRotation(Seat, 0F, 0F, 0F);
		
		SeatL = new ModelRenderer(this, 28, 18).setTextureSize(128, 64);
		SeatL.addBox(-2F, -3F, -5F, 2, 6, 2);
		SeatL.setRotationPoint(6F, 3F, 0F);
		SeatL.setTextureSize(128, 64);
		setRotation(SeatL, 0F, 0F, 1.570796F);
		
		SeatB = new ModelRenderer(this, 36, 18).setTextureSize(128, 64);
		SeatB.addBox(-3F, -6F, 1F, 6, 6, 2);
		SeatB.setRotationPoint(6F, 3F, 0F);
		SeatB.setTextureSize(128, 64);
		setRotation(SeatB, 0F, 1.570796F, 0F);
		
		SeatR = new ModelRenderer(this, 28, 18).setTextureSize(128, 64);
		SeatR.addBox(0F, -3F, 3F, 2, 6, 2);
		SeatR.setRotationPoint(6F, 3F, 0F);
		SeatR.setTextureSize(128, 64);
		setRotation(SeatR, 0F, 0F, -1.570796F);
		
		StickL = new ModelRenderer(this, 24, 18).setTextureSize(128, 64);
		StickL.addBox(-0.5F, -6F, -0.5F, 1, 7, 1);
		StickL.setRotationPoint(-1F, 5F, -3F);
		StickL.setTextureSize(128, 64);
		setRotation(StickL, 0F, 0F, -0.3926991F);
		
		StickR = new ModelRenderer(this, 24, 18).setTextureSize(128, 64);
		StickR.addBox(-0.5F, -6F, -0.5F, 1, 7, 1);
		StickR.setRotationPoint(-1F, 5F, 3F);
		StickR.setTextureSize(128, 64);
		setRotation(StickR, 0F, 0F, -0.3926991F);
		
		BaseBottom = new ModelRenderer(this, 0, 44).setTextureSize(128, 64);
		BaseBottom.addBox(-5F, -6F, -1F, 14, 12, 2);
		BaseBottom.setRotationPoint(0F, 6F, 0F);
		BaseBottom.setTextureSize(128, 64);
		setRotation(BaseBottom, 1.570796F, 0F, 0F);
		
		BaseFront = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		BaseFront.addBox(-10F, -8F, -1F, 5, 16, 7);
		BaseFront.setRotationPoint(0F, 6F, 0F);
		BaseFront.setTextureSize(128, 64);
		setRotation(BaseFront, 1.570796F, 0F, 0F);
		
		BaseBack = new ModelRenderer(this, 32, 42).setTextureSize(128, 64);
		BaseBack.addBox(-8F, -6F, 9F, 16, 7, 2);
		BaseBack.setRotationPoint(0F, 6F, 0F);
		BaseBack.setTextureSize(128, 64);
		setRotation(BaseBack, 0F, 1.570796F, 0F);
		
		BaseRight = new ModelRenderer(this, 0, 26).setTextureSize(128, 64);
		BaseRight.addBox(-5F, -6F, 6F, 14, 7, 2);
		BaseRight.setRotationPoint(0F, 6F, 0F);
		BaseRight.setTextureSize(128, 64);
		setRotation(BaseRight, 0F, 0F, 0F);
		
		TurretTop = new ModelRenderer(this, 32, 51).setTextureSize(128, 64);
		TurretTop.addBox(-5F, -4F, -5F, 10, 8, 1);
		TurretTop.setRotationPoint(2F, -8F, 0F);
		TurretTop.setTextureSize(128, 64);
		setRotation(TurretTop, -1.570796F, 0F, 0F);
		
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
		Base.render(f5);
		WheelLB.render(f5);
		WheelRF.render(f5);
		WheelRtopfront.render(f5);
		WheelRB.render(f5);
		WheelLF.render(f5);
		WheelLM.render(f5);
		WheelRM.render(f5);
		WheelRtop.render(f5);
		WheelLtop.render(f5);
		WheelLtopfront.render(f5);
		BaseLeft.render(f5);
		muzzle.renderWithRotation(f5);
		turretB.renderWithRotation(f5);
		turretL.renderWithRotation(f5);
		turretR.renderWithRotation(f5);
		turretLT.renderWithRotation(f5);
		turretRT.renderWithRotation(f5);
		turretBT.renderWithRotation(f5);
		TurretTop.renderWithRotation(f5);
		Seat.render(f5);
		SeatL.render(f5);
		SeatB.render(f5);
		SeatR.render(f5);
		StickL.render(f5);
		StickR.render(f5);
		BaseBottom.render(f5);
		BaseFront.render(f5);
		BaseBack.render(f5);
		BaseRight.render(f5);
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
			setRotation(WheelLB, 0, (float)Math.PI, ((TankEntity) entity).getSpeed());
			setRotation(WheelLM, 0, (float)Math.PI, ((TankEntity) entity).getSpeed());
			setRotation(WheelLF, 0, (float)Math.PI, ((TankEntity) entity).getSpeed());
			setRotation(WheelRB, 0, 0, ((TankEntity) entity).getSpeed());
			setRotation(WheelRM, 0, 0, ((TankEntity) entity).getSpeed());
			setRotation(WheelRF, 0, 0, ((TankEntity) entity).getSpeed());
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
			setRotation(WheelLB, 0, (float)Math.PI, 0);
			setRotation(WheelLM, 0, (float)Math.PI, 0);
			setRotation(WheelLF, 0, (float)Math.PI, 0);
			setRotation(WheelRB, 0, 0, 0);
			setRotation(WheelRM, 0, 0, 0);
			setRotation(WheelRF, 0, 0, 0);
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
