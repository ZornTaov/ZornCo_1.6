package zornco.tank.client.render;

import zornco.tank.entity.EntityTankBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTank extends ModelBase {

	//public Angle3D muzzleAngle;
	//public Vec3 muzzlePos;
	private final int boxes = 42;
	/*public Bone Origin;
	public Bone muzzle;
	public Bone end;
	public Bone end2;
	public Bone turret;*/
	// public Bone smokeStackA;
	// public Bone smokeStackB;
	public ModelRenderer[] sideModels = new ModelRenderer[boxes];
	
	public ModelTank() {
		// Origin
		/*Origin = new Bone(0, 0, 0, 0);
		Origin.setOffset(3.0F, -7F, 0.0F);
		// bones
		turret = new Bone(0, -3.141593F / 2, 0, 8, Origin);
		muzzle = new Bone(0, -3.141593F / 2, 0, 18, turret);
		end = new Bone(0, -3.141593F / 2, 0, 2, muzzle);
		end2 = new Bone(0, -3.141593F / 2, 0, 2, end);*/

		
		// texture placement
		sideModels[0] = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);// body
		sideModels[1] = new ModelRenderer(this, 100, -4).setTextureSize(128, 64);// inner
																		// front
		sideModels[2] = new ModelRenderer(this, 100, -4).setTextureSize(128, 64);// inner
																		// back
		sideModels[3] = new ModelRenderer(this, 64, 10).setTextureSize(128, 64);// inner
																		// left
		sideModels[4] = new ModelRenderer(this, 64, 10).setTextureSize(128, 64);// inner
																		// right
		sideModels[5] = new ModelRenderer(this, 52, 0).setTextureSize(128, 64);// wheels
		sideModels[6] = new ModelRenderer(this, 52, 0).setTextureSize(128, 64);
		sideModels[7] = new ModelRenderer(this, 52, 0).setTextureSize(128, 64);
		sideModels[8] = new ModelRenderer(this, 52, 0).setTextureSize(128, 64);
		sideModels[9] = new ModelRenderer(this, 52, 0).setTextureSize(128, 64);
		sideModels[10] = new ModelRenderer(this, 52, 0).setTextureSize(128, 64);
		sideModels[11] = new ModelRenderer(this, 72, 28).setTextureSize(128, 64);// seat
		sideModels[12] = new ModelRenderer(this, 86, 20).setTextureSize(128, 64);// seat
																		// left
		sideModels[13] = new ModelRenderer(this, 86, 20).setTextureSize(128, 64);// seat
																		// right
		sideModels[14] = new ModelRenderer(this, 94, 20).setTextureSize(128, 64);// seat
																		// back
		sideModels[15] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);// front
																		// claws
		sideModels[16] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);
		sideModels[17] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);
		sideModels[18] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);
		sideModels[19] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);
		sideModels[20] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);
		sideModels[21] = new ModelRenderer(this, 28, 33).setTextureSize(128, 64);// front
																		// claws(bottom
																		// half).setTextureSize(128, 64)
		sideModels[22] = new ModelRenderer(this, 28, 33).setTextureSize(128, 64);
		sideModels[23] = new ModelRenderer(this, 28, 33).setTextureSize(128, 64);
		sideModels[24] = new ModelRenderer(this, 28, 33).setTextureSize(128, 64);
		sideModels[25] = new ModelRenderer(this, 28, 33).setTextureSize(128, 64);
		sideModels[26] = new ModelRenderer(this, 28, 33).setTextureSize(128, 64);
		sideModels[27] = new ModelRenderer(this, 66, 0).setTextureSize(128, 64);// front
																		// plates
		sideModels[28] = new ModelRenderer(this, 95, 0).setTextureSize(128, 64);
		sideModels[29] = new ModelRenderer(this, 32, 20).setTextureSize(128, 64);// inner
																		// bottom
		sideModels[30] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);// Turret
		sideModels[31] = new ModelRenderer(this, 0, 42).setTextureSize(128, 64);
		sideModels[32] = new ModelRenderer(this, 0, 42).setTextureSize(128, 64);
		sideModels[33] = new ModelRenderer(this, 28, 28).setTextureSize(128, 64);
		sideModels[34] = new ModelRenderer(this, 0, 36).setTextureSize(128, 64);
		sideModels[35] = new ModelRenderer(this, 0, 36).setTextureSize(128, 64);
		sideModels[36] = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);// not used
		sideModels[37] = new ModelRenderer(this, 0, 28).setTextureSize(128, 64);// muzzle
		sideModels[38] = new ModelRenderer(this, 0, 28).setTextureSize(128, 64);//
		sideModels[39] = new ModelRenderer(this, 82, 20).setTextureSize(128, 64);// control
																		// stick
																		// left
		sideModels[40] = new ModelRenderer(this, 82, 20).setTextureSize(128, 64);// control
																		// stick
																		// right
		sideModels[41] = new ModelRenderer(this, 58, 34).setTextureSize(128, 64);// control
																		// panal

		// box creation and COR movement
		sideModels[0].addBox(-10F, -8F, -1F, 20, 16, 12, 0.0F);// body
		sideModels[0].setRotationPoint(0.0F, 6F, 0.0F);
		sideModels[1].addBox(-9F, -7F, -7F, 0, 10, 14, 0.0F);// inner front
		sideModels[1].setRotationPoint(0F, 3F, 0F);
		sideModels[2].addBox(9F, -7F, -7F, 0, 10, 14, 0.0F);// inner back
		sideModels[2].setRotationPoint(0F, 3F, 0F);
		sideModels[3].addBox(-9F, -7F, -7F, 18, 10, 0, 0.0F);// inner left
		sideModels[3].setRotationPoint(0F, 3F, 0F);
		sideModels[4].addBox(-9F, -7F, 7F, 18, 10, 0, 0.0F);// inner right
		sideModels[4].setRotationPoint(0F, 3F, 0F);
		sideModels[5].addBox(-2F, -2F, 0F, 4, 4, 3, 0.0F);// wheels
		sideModels[5].setRotationPoint(0.0F, 6F, 8F);
		sideModels[6].addBox(-2F, -2F, 0F, 4, 4, 3, 0.0F);
		sideModels[6].setRotationPoint(6.0F, 6F, 8F);
		sideModels[7].addBox(-2F, -2F, 0F, 4, 4, 3, 0.0F);
		sideModels[7].setRotationPoint(-6.0F, 6F, 8F);
		sideModels[8].addBox(-2F, -2F, 0F, 4, 4, 3, 0.0F);
		sideModels[8].setRotationPoint(0.0F, 6F, -8F);
		sideModels[9].addBox(-2F, -2F, 0F, 4, 4, 3, 0.0F);
		sideModels[9].setRotationPoint(6.0F, 6F, -8F);
		sideModels[10].addBox(-2F, -2F, 0F, 4, 4, 3, 0.0F);
		sideModels[10].setRotationPoint(-6.0F, 6F, -8F);
		sideModels[11].addBox(-3F, 0F, -3F, 4, 0, 6, 0F); // seat
		sideModels[11].setRotationPoint(6F, 3F, 0F);
		sideModels[12].addBox(-2F, -3F, -5F, 2, 6, 2, 0F); // seat left
		sideModels[12].setRotationPoint(6F, 3F, 0F);
		sideModels[13].addBox(0F, -3F, 3F, 2, 6, 2, 0F); // seat right
		sideModels[13].setRotationPoint(6F, 3F, 0F);
		sideModels[14].addBox(-3F, -6F, 1F, 6, 6, 2, 0F); // seat back
		sideModels[14].setRotationPoint(6F, 3F, 0F);
		sideModels[15].addBox(-1F, -2F, -2F, 1, 4, 1, 0.0F);// front claws
		sideModels[15].setRotationPoint(-15.0F, 5F, 0F);
		sideModels[16].addBox(-1F, -2F, -4F, 1, 4, 1, 0.0F);
		sideModels[16].setRotationPoint(-15.0F, 5F, 0F);
		sideModels[17].addBox(-1F, -2F, -6F, 1, 4, 1, 0.0F);
		sideModels[17].setRotationPoint(-15.0F, 5F, 0F);
		sideModels[18].addBox(-1F, -2F, 1F, 1, 4, 1, 0.0F);
		sideModels[18].setRotationPoint(-15.0F, 5F, 0F);
		sideModels[19].addBox(-1F, -2F, 3F, 1, 4, 1, 0.0F);
		sideModels[19].setRotationPoint(-15.0F, 5F, 0F);
		sideModels[20].addBox(-1F, -2F, 5F, 1, 4, 1, 0.0F);
		sideModels[20].setRotationPoint(-15.0F, 5F, 0F);
		sideModels[21].addBox(0F, 0F, -2F, 3, 1, 1, 0.0F);// front claws(bottom
															// half)
		sideModels[21].setRotationPoint(-15.0F, 6F, 0F);
		sideModels[22].addBox(0F, 0F, -4F, 3, 1, 1, 0.0F);
		sideModels[22].setRotationPoint(-15.0F, 6F, 0F);
		sideModels[23].addBox(0F, 0F, -6F, 3, 1, 1, 0.0F);
		sideModels[23].setRotationPoint(-15.0F, 6F, 0F);
		sideModels[24].addBox(0F, 0F, 1F, 3, 1, 1, 0.0F);
		sideModels[24].setRotationPoint(-15.0F, 6F, 0F);
		sideModels[25].addBox(0F, 0F, 3F, 3, 1, 1, 0.0F);
		sideModels[25].setRotationPoint(-15.0F, 6F, 0F);
		sideModels[26].addBox(0F, 0F, 5F, 3, 1, 1, 0.0F);
		sideModels[26].setRotationPoint(-15.0F, 6F, 0F);
		sideModels[27].addBox(-7F, -7F, 0F, 14, 9, 1, 0.0F);// front plates
		sideModels[27].setRotationPoint(-11.0F, 5F, 0F);
		sideModels[28].addBox(-6F, -7F, 0F, 12, 8, 1, 0.0F);
		sideModels[28].setRotationPoint(-12.0F, 6F, 0F);
		sideModels[29].addBox(-9F, -3F, -7F, 18, 0, 14, 0.0F);// inner bottom
		sideModels[29].setRotationPoint(0.0F, 3F, 0.0F);
		sideModels[30].addBox(-6F, -2F, -6F, 12, 4, 12, 0.0F);// turret
		sideModels[30].setRotationPoint(3.0F, -7F, 0.0F);
		sideModels[31].addBox(-4F, 0F, -8F, 8, 2, 2, 0.0F);
		sideModels[31].setRotationPoint(3.0F, -7F, 0.0F);
		sideModels[32].addBox(-4F, 0F, -8F, 8, 2, 2, 0.0F);
		sideModels[32].setRotationPoint(3.0F, -7F, 0.0F);
		//sideModels[32].doMirror(false, false, true);
		sideModels[33].addBox(6F, 0F, -4F, 2, 2, 8, 0.0F);
		sideModels[33].setRotationPoint(3.0F, -7F, 0.0F);
		sideModels[34].addBox(-10F, -2F, -4F, 4, 4, 2, 0.0F);
		sideModels[34].setRotationPoint(3.0F, -7F, 0.0F);
		sideModels[35].addBox(-10F, -2F, -4F, 4, 4, 2, 0.0F);
		sideModels[35].setRotationPoint(3.0F, -7F, 0.0F);
		//sideModels[35].doMirror(false, false, true);
		sideModels[36].addBox(-5F, -2F, -3F, 0, 0, 0, 0F);// not used
		sideModels[36].setRotationPoint(-8F, 1F, 0F);
		sideModels[37].addBox(-2F, -2F, -2F, 4, 4, 20, 0.1F);// muzzle
		sideModels[37].setRotationPoint(0.0F, 0F, 0.0F);
		sideModels[38].addBox(-3F, -3F, -1F, 6, 6, 2, 0.0F);// muzzle
		sideModels[38].setRotationPoint(0.0F, 0F, 0.0F);
		sideModels[39].addBox(-0.5F, -6F, -0.5F, 1, 7, 1, 0F);// control stick
																// left
		sideModels[39].setRotationPoint(-1F, 5F, -3F);
		sideModels[40].addBox(-0.5F, -6F, -0.5F, 1, 7, 1, 0F);// control stick
																// right
		sideModels[40].setRotationPoint(-1F, 5F, 3F);
		sideModels[41].addBox(-5F, -2F, -3F, 10, 0, 6, 0F);// control panal
		sideModels[41].setRotationPoint(-8F, 1F, 0F);
		// rotations
		sideModels[0].rotateAngleX = 1.570796F;// floor
		/*
		 * sideModels[1].rotateAngleY = 4.712389F;//not used
		 * sideModels[2].rotateAngleY = 1.570796F; sideModels[3].rotateAngleY =
		 * 3.141593F;
		 */
		for (int i = 8; i < 11; i++)// wheels
		{
			sideModels[i].rotateAngleY = 3.141593F;
		}
		sideModels[11].rotateAngleX = 3.141593F;// seat
		sideModels[12].rotateAngleZ = 1.570796F;// seat left
		sideModels[13].rotateAngleZ = -1.570796F;// seat right
		sideModels[14].rotateAngleY = 1.570796F;// seat back
		sideModels[27].rotateAngleY = 1.570796F;// front plates
		sideModels[28].rotateAngleY = 1.570796F;
		sideModels[29].rotateAngleX = 3.141593F;// inner bottom
		sideModels[39].rotateAngleZ = -0.3926991F;// control stick left
		sideModels[40].rotateAngleZ = -0.3926991F;// control stick right
		sideModels[41].rotateAngleX = -0.7853982F;// control panal
		sideModels[41].rotateAngleY = 1.570796F;

		/*muzzle.addModel(sideModels[37], true);
		end.addModel(sideModels[38], true);*/

		// muzzle.relativeAngles.angleZ = 1.570796F;
	}
	@Override
	public void render(Entity par1Entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		EntityTankBase tankCS = (EntityTankBase)par1Entity;
		setRotationAngles(f, f1, f2, f3, f4, f5, par1Entity);
		/*Origin.prepareDraw();
		Origin.setAnglesToModels();*/
		for (int i = 0; i < boxes; ++i) {// if(i <= 11 && i >= 14)
			sideModels[i].render(f5*2F);
		}
		for(int i = 5; i < 11; i++) 
		{ 
			sideModels[i].rotateAngleZ -= (tankCS.getSpeed()/Math.PI); 
		}
		// sideModels[1].render(f5*2F);
		/*
		 * if(tankCS.tankCanShoot == 0) { muzzleAngle = end.getAbsoluteAngle();
		 * muzzlePos = end.getPosition(); tankCS.tankCanShoot = 40; }
		 */
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity par7Entity) {

		//double d11 = tankCS.getSpeed(); //where tankSpeed = Math.sqrt(motionX * motionX + motionZ * motionZ);
		//System.out.println((newStringBuilder("tankspeed "))
		//.append(d11).append(" motionX ").append(motionX)
		//.append(" motionZ ").append(motionZ).toString()); 
		


	}
}
