package zornco.reploidcraft.sounds;

import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.MathHelper;
import zornco.reploidcraft.ReploidCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SoundUpdaterBuster implements IUpdatePlayerListBox
{
    private final SoundManager theSoundManager;

    /** The player that is getting the minecart sound updates. */
    private final EntityPlayerSP thePlayer;
    private boolean playerSPHoldingGun = false;
    private boolean playerIsDead = false;
    public boolean playerIsCharging = false;
    private boolean silent = false;
    private float busterSoundPitch = 0.0F;
    private float busterChargeUpSoundVolume = 0.0F;
    private float busterChargeConstantSoundVolume = 0.0F;
    private int busterChargeTime = 0;

    public SoundUpdaterBuster(Object manager, EntityPlayer player)
    {
        this.theSoundManager = (SoundManager) manager;
        this.thePlayer = (EntityPlayerSP) player;
    }

    /**
     * Updates the JList with a new model.
     */
    public void update()
    {
    	if(this.thePlayer == null || this.theSoundManager == null ) return;
        boolean flag = false;
        boolean flag1 = this.playerSPHoldingGun;
        boolean flag2 = this.playerIsDead;
        boolean flag3 = this.playerIsCharging;
        float f = this.busterChargeUpSoundVolume;
        float f1 = this.busterSoundPitch;
        float f2 = this.busterChargeConstantSoundVolume;
        double d0 = this.busterChargeTime;

    	if(this.thePlayer.getItemInUse() == null || !this.playerIsCharging) {
            this.theSoundManager.stopEntitySound(this.thePlayer);
            this.silent = true;
            return;
    	}
        this.playerSPHoldingGun = this.thePlayer != null && this.thePlayer.getItemInUse().itemID == ReploidCraft.buster.itemID;
        this.playerIsDead = this.thePlayer.isDead;
        this.busterChargeTime = this.thePlayer.getItemInUseCount();

        if (flag1 && !this.playerSPHoldingGun)
        {
            this.theSoundManager.stopEntitySound(this.thePlayer);
        }

        if (!this.playerIsCharging || this.playerIsDead || !this.silent && this.busterChargeUpSoundVolume == 0.0F && this.busterChargeConstantSoundVolume == 0.0F)
        {
            if (!flag2)
            {
                this.theSoundManager.stopEntitySound(this.thePlayer);

                if (flag1 || this.playerSPHoldingGun)
                {
                    this.theSoundManager.stopEntitySound(this.thePlayer);
                }
            }

            this.silent = true;

            if (this.playerIsDead)
            {
                return;
            }
        }

        if (!this.theSoundManager.isEntitySoundPlaying(this.thePlayer) && this.busterChargeUpSoundVolume > 0.0F)
        {
            this.theSoundManager.playEntitySound(Sounds.CHARGEUP, this.thePlayer, this.busterChargeUpSoundVolume, this.busterSoundPitch, false);
            this.silent = false;
            flag = true;
        }

        if (this.playerSPHoldingGun && !this.theSoundManager.isEntitySoundPlaying(this.thePlayer) && this.busterChargeConstantSoundVolume > 0.0F)
        {
            this.theSoundManager.playEntitySound("minecart.inside", this.thePlayer, this.busterChargeConstantSoundVolume, 1.0F, true);
            this.silent = false;
            flag = true;
        }

        if (this.playerIsCharging)
        {
            if (this.busterSoundPitch < 1.0F)
            {
                this.busterSoundPitch += 0.0025F;
            }

            if (this.busterSoundPitch > 1.0F)
            {
                this.busterSoundPitch = 1.0F;
            }

            float f3 = MathHelper.clamp_float((float)this.busterChargeTime, 0.0F, 500F) / 40.0F;
            this.busterChargeConstantSoundVolume = 0.0F + f3 * 0.75F;
            f3 = MathHelper.clamp_float(f3 * 2.0F, 0.0F, 1.0F);
            this.busterChargeUpSoundVolume = 0.0F + f3 * 0.7F;
        }
        else if (flag3)
        {
            this.busterChargeUpSoundVolume = 0.0F;
            this.busterSoundPitch = 0.0F;
            this.busterChargeConstantSoundVolume = 0.0F;
        }

        if (!this.silent)
        {
            if (this.busterSoundPitch != f1)
            {
                this.theSoundManager.setEntitySoundPitch(this.thePlayer, this.busterSoundPitch);
            }

            if (this.busterChargeUpSoundVolume != f)
            {
                this.theSoundManager.setEntitySoundVolume(this.thePlayer, this.busterChargeUpSoundVolume);
            }

            if (this.busterChargeConstantSoundVolume != f2)
            {
                this.theSoundManager.setEntitySoundVolume(this.thePlayer, this.busterChargeConstantSoundVolume);
            }
        }

        if (!flag && (this.busterChargeUpSoundVolume > 0.0F || this.busterChargeConstantSoundVolume > 0.0F))
        {
            this.theSoundManager.updateSoundLocation(this.thePlayer);

            if (this.playerSPHoldingGun)
            {
                this.theSoundManager.updateSoundLocation(this.thePlayer, this.thePlayer);
            }
        }
        else
        {
            if (this.theSoundManager.isEntitySoundPlaying(this.thePlayer))
            {
                this.theSoundManager.stopEntitySound(this.thePlayer);
            }

            if (this.playerSPHoldingGun && this.theSoundManager.isEntitySoundPlaying(this.thePlayer))
            {
                this.theSoundManager.stopEntitySound(this.thePlayer);
            }
        }
    }
}
