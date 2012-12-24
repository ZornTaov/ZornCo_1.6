package zornco.megax.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import zornco.megax.entities.EntityMet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMet extends RenderLiving
{
	public RenderMet(ModelBase par1ModelBase, ModelBase par2ModelBase, float shadowSize)
    {
        super(par1ModelBase, shadowSize);
        this.setRenderPassModel(par2ModelBase);
    }

    protected int setHatTypeAndRender(EntityMet par1EntityMet, int pass, float partialTickTime)
    {
        if (pass == 0 && !par1EntityMet.getIsHatWorn())
        {
        	MinecraftForgeClient.preloadTexture("/zornco/megax/textures/MetHat.png");
            float white = 1.0F;
            int type = par1EntityMet.getMetHatType();
            GL11.glColor3f(white * EntityMet.fleeceColorTable[type][0], white * EntityMet.fleeceColorTable[type][1], white * EntityMet.fleeceColorTable[type][2]);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void doRenderMet(EntityMet par1EntitySheep, double par2, double par4, double par6, float par8, float partialTickTime)
    {
        super.doRenderLiving(par1EntitySheep, par2, par4, par6, par8, partialTickTime);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int pass, float partialTickTime)
    {
        return this.setHatTypeAndRender((EntityMet)par1EntityLiving, pass, partialTickTime);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float partialTickTime)
    {
        this.doRenderMet((EntityMet)par1EntityLiving, par2, par4, par6, par8, partialTickTime);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float partialTickTime)
    {
        this.doRenderMet((EntityMet)par1Entity, par2, par4, par6, par8, partialTickTime);
    }
}
