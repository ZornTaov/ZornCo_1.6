package zornco.megax.client;

import org.lwjgl.opengl.GL11;

import zornco.megax.*;

public class MegaXRenderPlayerBase{/* extends RenderPlayerBase
{

	private ModelBiped modelBipedMain;
	private ModelBiped modelArmorChestplate;
	private ModelBiped modelArmor;
	public MegaXRenderPlayerBase(RenderPlayerAPI renderPlayerAPI)
	{
		super(renderPlayerAPI);
	}
	@Override
	public final void renderPlayer(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9)
	{
		modelBipedMain = this.renderPlayer.getModelBipedMainField();
		modelArmorChestplate = this.renderPlayer.getModelArmorChestplateField();
		modelArmor = this.renderPlayer.getModelArmorField();
		ItemStack var10 = var1.inventory.getCurrentItem();
		this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = var10 != null ? 1 : 0;

		if (var10 != null && var1.getItemInUseCount() > 0)
		{
			EnumAction var11 = var10.getItemUseAction();

			if (var11 == MegaX.busterAction)
			{
				this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
			}
		}

		this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = var1.isSneaking();
		double var13 = var4 - (double)var1.yOffset;

		if (var1.isSneaking() && !(var1 instanceof EntityPlayerSP))
		{
			var13 -= 0.125D;
		}

		renderPlayer.localRenderPlayer(var1, var2, var4, var6, var8, var9);
		this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
		this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
		this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
	}
	/*@Override
	public final int setArmorModel(EntityPlayer var1, int var2, float var3)
    {
		modelBipedMain = this.renderPlayer.getModelBipedMainField();
        ItemStack var4 = var1.inventory.armorItemInSlot(3 - var2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture(renderPlayer.GetArmorTexture(var4, "/armor/" + renderPlayer.armorFilenamePrefix[var6.renderIndex] + "_" + (var2 == 2 ? 2 : 1) + ".png"));
                ModelBiped var7 = var2 == 2 ? this.modelArmor : this.modelArmorChestplate;
                var7.bipedHead.showModel = var2 == 0;
                var7.bipedHeadwear.showModel = var2 == 0;
                var7.bipedBody.showModel = var2 == 1 || var2 == 2;
                var7.bipedRightArm.showModel = var2 == 1;
                var7.bipedLeftArm.showModel = var2 == 1;
                var7.bipedRightLeg.showModel = var2 == 2 || var2 == 3;
                var7.bipedLeftLeg.showModel = var2 == 2 || var2 == 3;
                this.setRenderPassModel(var7);

                if (var7 != null)
                {
                    var7.onGround = this.modelBipedMain.onGround;
                }

                if (var7 != null)
                {
                    var7.isRiding = this.modelBipedMain.isRiding;
                }

                if (var7 != null)
                {
                    var7.isChild = this.modelBipedMain.isChild;
                }

                float var8 = 1.0F;

                if (var6.getArmorMaterial() == EnumArmorMaterial.CLOTH)
                {
                    int var9 = var6.getColor(var4);
                    float var10 = (float)(var9 >> 16 & 255) / 255.0F;
                    float var11 = (float)(var9 >> 8 & 255) / 255.0F;
                    float var12 = (float)(var9 & 255) / 255.0F;
                    GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);

                    if (var4.isItemEnchanted())
                    {
                        return 31;
                    }

                    return 16;
                }

                GL11.glColor3f(var8, var8, var8);

                if (var4.isItemEnchanted())
                {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }
}
private static String GetArmorTexture(ItemStack var0, String var1)
{
    if (getArmorTexture == null)
    {
        return var1;
    }
    else
    {
        try
        {
            return (String)getArmorTexture.invoke((Object)null, new Object[] {var0, var1});
        }
        catch (Exception var3)
        {
            throw new RuntimeException(getArmorTexture.getName(), var3);
        }
    }*/
}
