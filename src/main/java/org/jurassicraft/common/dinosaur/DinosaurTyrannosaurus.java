package org.jurassicraft.common.dinosaur;

import org.jurassicraft.common.entity.EntityTyrannosaurus;
import org.jurassicraft.common.entity.base.EntityDinosaur;
import org.jurassicraft.common.entity.base.EnumGrowthStage;
import org.jurassicraft.common.period.EnumTimePeriod;

public class DinosaurTyrannosaurus extends Dinosaur
{
    private final String[] maleTextures;
    private final String[] femaleTextures;
    private final String[] maleOverlayTextures;
    private final String[] femaleOverlayTextures;

    public DinosaurTyrannosaurus()
    {
        this.maleTextures = new String[]{getDinosaurTexture("male")};
        this.femaleTextures = new String[]{getDinosaurTexture("female")};

        this.maleOverlayTextures = new String[]{getDinosaurTexture("male_detail")};
        this.femaleOverlayTextures = new String[]{getDinosaurTexture("female_detail")};
    }

    @Override
    public String getName()
    {
        return "Tyrannosaurus";
    }

    @Override
    public Class<? extends EntityDinosaur> getDinosaurClass()
    {
        return EntityTyrannosaurus.class;
    }

    @Override
    public EnumTimePeriod getPeriod()
    {
        return EnumTimePeriod.CRETACEOUS;
    }

    @Override
    public int getEggPrimaryColor()
    {
        return 0x6B6628;
    }

    @Override
    public int getEggSecondaryColor()
    {
        return 0x39363B;
    }

    @Override
    public double getBabyHealth()
    {
        return 35;
    }

    @Override
    public double getAdultHealth()
    {
        return 100;
    }

    @Override
    public double getBabySpeed()
    {
        return 0.46;
    }

    @Override
    public double getAdultSpeed()
    {
        return 0.42;
    }

    @Override
    public double getAttackSpeed()
    {
        return 0.50;
    }

    @Override
    public double getBabyStrength()
    {
        return 16;
    }

    @Override
    public double getAdultStrength()
    {
        return 30;
    }

    @Override
    public double getBabyKnockback()
    {
        return 0.3D;
    }

    @Override
    public double getAdultKnockback()
    {
        return 0.6D;
    }

    @Override
    public int getMaximumAge()
    {
        return fromDays(60);
    }

    @Override
    public String[] getMaleTextures(EnumGrowthStage stage)
    {
        return maleTextures;
    }

    @Override
    public String[] getFemaleTextures(EnumGrowthStage stage)
    {
        return femaleTextures;
    }

    @Override
    public String[] getMaleOverlayTextures(EnumGrowthStage stage)
    {
        return maleOverlayTextures;
    }

    @Override
    public String[] getFemaleOverlayTextures(EnumGrowthStage stage)
    {
        return femaleOverlayTextures;
    }

    @Override
    public float getBabyEyeHeight()
    {
        return 0.6F;
    }

    @Override
    public float getAdultEyeHeight()
    {
        return 3.8F;
    }

    @Override
    public float getBabySizeX()
    {
        return 0.45F;
    }

    @Override
    public float getBabySizeY()
    {
        return 0.8F;
    }

    @Override
    public float getAdultSizeX()
    {
        return 4.5F;
    }

    @Override
    public float getAdultSizeY()
    {
        return 4F;
    }

    @Override
    public int getStorage()
    {
        return 54;
    }
}