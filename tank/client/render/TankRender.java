package zornco.tank.client.render;


import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import zornco.tank.entity.TankEntity;

public class TankRender extends Render {

	public TankRender() {
		shadowSize = 2F;
		tankModel = new TankModel();
	}

	public void renderTank(TankEntity tankentity, double d, double d1,
			double d2, float f, float f1) {
		GL11.glPushMatrix();
		float f5 = 0;
		float f6 = 0;
		if (tankentity.riddenByEntity != null) {
			f5 = tankentity.riddenByEntity.prevRotationYaw
					+ (tankentity.riddenByEntity.rotationYaw - tankentity.riddenByEntity.prevRotationYaw)
					* f1 - tankentity.prevRotationYaw - 90;
			f6 = tankentity.riddenByEntity.prevRotationPitch
					+ (tankentity.riddenByEntity.rotationPitch - tankentity.riddenByEntity.prevRotationPitch)
					* f1;
			for (int i = 5; i < 11; i++) {
				tankModel.sideModels[i].rotateAngleZ = tankentity.wheelRotation;
			}
			for (int i = 30; i < 37; i++) {
				tankModel.sideModels[i].rotateAngleY = f5 / 57.29578F;
			}
			/*tankModel.turret.relativeAngles.angleY = f5 / 57.29578F;
			tankModel.muzzle.relativeAngles.angleX = -f6 / 57.29578F;*/

		} else {
			for (int i = 5; i < 11; i++) {
				tankModel.sideModels[i].rotateAngleZ = 0F;
			}
			for (int i = 30; i < 37; i++) {
				tankModel.sideModels[i].rotateAngleY = 0F;
			}
			/*tankModel.turret.relativeAngles.angleY = 0F;
			tankModel.muzzle.relativeAngles.angleX = 0F;*/
		}
		// float f8 = tankentity.prevRotationPitch + (tankentity.rotationPitch -
		// tankentity.prevRotationPitch) * f1;
		// float f9 = tankentity.prevRotationYaw + (tankentity.rotationYaw -
		// tankentity.prevRotationYaw) * f1;
		// d = tankentity.prevPosX + (tankentity.posX - tankentity.prevPosX) *
		// (double)f1;
		// d1 = (tankentity.prevPosY + (tankentity.posY - tankentity.prevPosY) *
		// (double)f1 + 1.6200000000000001D) - (double)tankentity.yOffset;
		// d2 = tankentity.prevPosZ + (tankentity.posZ - tankentity.prevPosZ) *
		// (double)f1;
		/*
		 * Vec3 vec3d = Vec3.createVector(d, d1, d2); float f10 =
		 * MathHelper.cos(-f9 * 0.01745329F - 3.141593F); float f11 =
		 * MathHelper.sin(-f9 * 0.01745329F - 3.141593F); float f12 =
		 * -MathHelper.cos(-f8 * 0.01745329F); float f13 = MathHelper.sin(-f8 *
		 * 0.01745329F); float f14 = f11 * f12; float f15 = f13; float f16 = f10
		 * * f12; double d3 = 5D; Vec3 vec3d1 = vec3d.addVector((double)f14 *
		 * d3, (double)f15 * d3, (double)f16 * d3); MovingObjectPosition
		 * movingobjectposition = tankentity.worldObj.rayTraceBlocks_do(vec3d,
		 * vec3d1, true); if(movingobjectposition != null) {
		 * if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) { int
		 * i = movingobjectposition.blockX; int j = movingobjectposition.blockY;
		 * int k = movingobjectposition.blockZ; //System.out.println((new
		 * StringBuilder
		 * ("found at  ")).append(i).append(" ").append(j).append(" "
		 * ).append(k).toString());
		 * 
		 * 
		 * if(movingobjectposition.sideHit >= 2 && movingobjectposition.sideHit
		 * <= 5) { j++; } if(tankentity.worldObj.isAirBlock(i, j, k) ||
		 * !tankentity.worldObj.getBlockMaterial(i, j, k).isSolid()) {
		 * 
		 * 
		 * 
		 * double d3 = tankentity.lastTickPosX + (tankentity.posX -
		 * tankentity.lastTickPosX) * (double)f1; double d4 =
		 * tankentity.lastTickPosY + (tankentity.posY - tankentity.lastTickPosY)
		 * * (double)f1; double d5 = tankentity.lastTickPosZ + (tankentity.posZ
		 * - tankentity.lastTickPosZ) * (double)f1; double d6 =
		 * 0.30000001192092896D; Vec3 vec3d = tankentity.func_514_g(d3, d4,
		 * d5); float f2 = tankentity.prevRotationPitch +
		 * (tankentity.rotationPitch - tankentity.prevRotationPitch) * f1;
		 * if(vec3d != null) { Vec3 vec3d1 = tankentity.func_515_a(d3, d4, d5,
		 * d6); Vec3 vec3d2 = tankentity.func_515_a(d3, d4, d5, -d6); if(vec3d1
		 * == null) { vec3d1 = vec3d; } if(vec3d2 == null) { vec3d2 = vec3d; } d
		 * += vec3d.xCoord - d3; d1 += (vec3d1.yCoord + vec3d2.yCoord) / 2D -
		 * d4; d2 += vec3d.zCoord - d5; Vec3 vec3d3 =
		 * vec3d.addVector(-vec3d1.xCoord, -vec3d1.yCoord, -vec3d1.zCoord);
		 * if(vec3d3.lengthVector() != 0.0D) { vec3d3 = vec3d3.normalize(); f =
		 * (float)((Math.atan2(vec3d3.zCoord, vec3d3.xCoord) * 180D) /
		 * 3.1415926535897931D); f8 = (float)(Math.atan(vec3d3.yCoord) * 73D); }
		 * }
		 */// }}
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
		// GL11.glRotatef(-f8, 0.0F, 0.0F, 1.0F);
		float f2 = (float) tankentity.tankTimeSinceHit - f1;
		float f3 = (float) tankentity.tankCurrentDamage - f1;
		if (f3 < 0.0F) {
			f3 = 0.0F;
		}
		if (f2 > 0.0F) {
			GL11.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F)
					* (float) tankentity.tankRockDirection, 1.0F, 0.0F, 0.0F);
		}

		tankModel.tankCS = tankentity;
		float f4 = 0.75F;
		GL11.glScalef(f4, f4, f4);
		GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		//loadTexture("/item/tanktexture.png");
		GL11.glScalef(-1F, -1F, 1.0F);
		tankModel.render(0.0F, 0.0F, -0.1F, f5, f6, 0.0625F);
		if (tankentity.tankCanShootMachineGun == 0) {
			/*tankentity.currentAngle = tankModel.end.getAbsoluteAngle();
			tankentity.currentPos = tankModel.end.getPosition();
			tankentity.currentEndPos = tankModel.end2.getPosition();*/
			tankentity.tankCanShootMachineGun = 3;
		}
		if (tankentity.tankCanShoot == 0) {
			/*tankentity.currentAngle = tankModel.end.getAbsoluteAngle();
			tankentity.currentPos = tankModel.end.getPosition();
			tankentity.currentEndPos = tankModel.end2.getPosition();*/
			if(!tankentity.rider.capabilities.isCreativeMode)
			{
			tankentity.tankCanShoot = 120;
			}else{tankentity.tankCanShoot = 3;}
		}
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		renderTank((TankEntity) entity, d, d1, d2, f, f1);
	}

	public TankModel tankModel;

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
