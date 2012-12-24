package zornco.megax.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import zornco.megax.entities.EntityMet;
 
public class RenderTurret extends RenderLiving {
 
        public RenderTurret(ModelBase modelbase, float f) {
                super(modelbase, f);
                                                               
        }
 
        public void func_177_a(EntityMet entityturret, double d, double d1, double d2, float f, float f1) {
                super.doRenderLiving(entityturret, d, d1, d2, f, f1);
        }
 
        public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
                func_177_a((EntityMet) entityliving, d, d1, d2, f, f1);
        }
 
        public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
                func_177_a((EntityMet) entity, d, d1, d2, f, f1);
        }
}
