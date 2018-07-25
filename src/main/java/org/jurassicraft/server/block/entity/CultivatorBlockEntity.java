package org.jurassicraft.server.block.entity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.Dinosaur;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.food.FoodNutrients;
import org.jurassicraft.server.api.DinosaurProvider;
import org.jurassicraft.server.item.ItemHandler;
import org.jurassicraft.server.message.CultivatorSyncNutrients;

import java.util.Random;

public class CultivatorBlockEntity extends MachineBaseBlockEntity implements TemperatureControl {
    private static final int[] INPUTS = new int[] { 0, 1, 2, 3 };
    private static final int[] OUTPUTS = new int[] { 4 };
    public static final int MAX_NUTRIENTS = 3000;
    private int waterLevel;
    private int lipids;
    private int proximates;
    private int minerals;
    private int vitamins;
    private int temperature;

    private DinosaurEntity dinosaurEntity; //Used for rendering entities

    @Override
    protected int getProcessFromSlot(int slot) {
        return 0;
    }

    @Override
    protected int getInventorySize() {
        return 5;
    }

    @Override
    protected boolean canProcess(int process) {
    	ItemStack itemstack = this.inventory.getStackInSlot(0);
        if (itemstack.getItem() == ItemHandler.SYRINGE && this.waterLevel == 2) {
            DinosaurProvider provider = DinosaurProvider.getFromStack(itemstack);
//            if(provider.isMissing()) //TODO: wtf
            {
                Dinosaur dino = provider.getValue(itemstack);
                if (dino != null && dino.birthType == Dinosaur.BirthType.LIVE_BIRTH) {
                    return this.lipids >= dino.lipids && this.minerals >= dino.minerals && this.proximates >= dino.proximates && this.vitamins >= dino.vitamins;
                }
            }
        }

        return false;
    }

    @Override
    protected void processItem(int process) {
        ItemStack syringe = this.inventory.getStackInSlot(0);
        Dinosaur dinosaur = DinosaurProvider.getFromStack(syringe).getValue(syringe);

        if (dinosaur != null) {
            this.lipids -= dinosaur.lipids;
            this.minerals -= dinosaur.minerals;
            this.vitamins -= dinosaur.vitamins;
            this.proximates -= dinosaur.proximates;
            this.waterLevel--;


            ItemStack hatchedEgg = new ItemStack(ItemHandler.HATCHED_EGG);

            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("Gender", this.temperature > 50);

            NBTTagCompound syringeTag = syringe.getTagCompound();
            if (syringeTag != null) {
                compound.setString("Genetics", syringeTag.getString("Genetics"));
                compound.setInteger("DNAQuality", syringeTag.getInteger("DNAQuality"));
            }

            hatchedEgg.setTagCompound(compound);
            this.inventory.setStackInSlot(0, ItemHandler.HATCHED_EGG.putValue(hatchedEgg, DinosaurProvider.getFromStack(syringe).getValue(syringe)));
        }
    }

    @Override
    public void update() {
        super.update();
        boolean sync = false;
        if (!this.world.isRemote) {
            if (this.waterLevel < 2 && this.inventory.getStackInSlot(2).getItem() == Items.WATER_BUCKET) {
                if (this.inventory.getStackInSlot(3).getCount() < 16) {
                    this.inventory.getStackInSlot(2).shrink(1);

                    this.waterLevel++;

                    ItemStack stack = this.inventory.getStackInSlot(3);
                    if (stack.getItem() == Items.BUCKET) {
                        stack.grow(1);
                    } else {
                        this.inventory.setStackInSlot(3, new ItemStack(Items.BUCKET));
                    }

                    sync = true;
                }
            }

            ItemStack stack = this.inventory.getStackInSlot(1);
            if (!stack.isEmpty()) {
                if ((this.proximates < MAX_NUTRIENTS) || (this.minerals < MAX_NUTRIENTS) || (this.vitamins < MAX_NUTRIENTS) || (this.lipids < MAX_NUTRIENTS)) {
                    this.consumeNutrients();
                    sync = true;

                }
            }
        }

        if (sync) {
            this.markDirty();
            JurassiCraft.NETWORK_WRAPPER.sendToAll(new CultivatorSyncNutrients(this));
        }
    }

    private void consumeNutrients() {
        ItemStack foodStack = this.inventory.getStackInSlot(1);
        FoodNutrients nutrients = FoodNutrients.get(foodStack.getItem());

        if (nutrients != null) {
            if (foodStack.getItem() instanceof ItemBucketMilk) {
            	this.inventory.setStackInSlot(1, new ItemStack(Items.BUCKET));
            } else {
                foodStack.shrink(1);
                if (foodStack.getCount() <= 0) {
                    foodStack.isEmpty();
                }
            }

            Random random = this.world.rand;
            if (this.proximates < MAX_NUTRIENTS) {
                this.proximates = (short) (this.proximates + (800 + random.nextInt(201)) * nutrients.getProximate());
                if (this.proximates > MAX_NUTRIENTS) {
                    this.proximates = (short) MAX_NUTRIENTS;
                }
            }

            if (this.minerals < MAX_NUTRIENTS) {
                this.minerals = (short) (this.minerals + (900 + random.nextInt(101)) * nutrients.getMinerals());
                if (this.minerals > MAX_NUTRIENTS) {
                    this.minerals = (short) MAX_NUTRIENTS;
                }
            }

            if (this.vitamins < MAX_NUTRIENTS) {
                this.vitamins = (short) (this.vitamins + (900 + random.nextInt(101)) * nutrients.getVitamins());
                if (this.vitamins > MAX_NUTRIENTS) {
                    this.vitamins = (short) MAX_NUTRIENTS;
                }
            }

            if (this.lipids < MAX_NUTRIENTS) {
                this.lipids = (short) (this.lipids + (980 + random.nextInt(101)) * nutrients.getLipids());
                if (this.lipids > MAX_NUTRIENTS) {
                    this.lipids = (short) MAX_NUTRIENTS;
                }
            }
        }
    }

    @Override
    protected void onSlotUpdate(int slot) {
        super.onSlotUpdate(slot);
        if(this.inventory.getStackInSlot(0).isEmpty()) {
            this.dinosaurEntity = null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.waterLevel = compound.getShort("WaterLevel");
        this.lipids = compound.getInteger("Lipids");
        this.minerals = compound.getInteger("Minerals");
        this.vitamins = compound.getInteger("Vitamins");
        this.proximates = compound.getInteger("Proximates");
        this.temperature = compound.getInteger("Temperature");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setShort("WaterLevel", (short) this.waterLevel);
        compound.setInteger("Lipids", this.lipids);
        compound.setInteger("Minerals", this.minerals);
        compound.setInteger("Vitamins", this.vitamins);
        compound.setInteger("Proximates", this.proximates);
        compound.setInteger("Temperature", this.temperature);
        return compound;
    }

    @Override
    protected int getStackProcessTime(ItemStack stack) {
        return 2000;
    }

    @Override
    protected int getProcessCount() {
        return 1;
    }

    @Override
    protected int[] getInputs(int process) {
        return INPUTS;
    }

    @Override
    protected int[] getOutputs(int process) {
        return OUTPUTS;
    }

    public DinosaurEntity getDinosaurEntity() {
        if(this.inventory.getStackInSlot(0).isEmpty()){
            return null;
        }
        return dinosaurEntity == null ? createEntity() : dinosaurEntity;
    }

    private DinosaurEntity createEntity() {
        ItemStack stack = this.inventory.getStackInSlot(0);
        this.dinosaurEntity = DinosaurProvider.getFromStack(stack).getValue(stack).createEntity(this.world);
        this.dinosaurEntity.setMale(this.temperature > 50);
        this.dinosaurEntity.setFullyGrown();
        this.dinosaurEntity.getAttributes().setScaleModifier(1f);
        return dinosaurEntity;
    }

    public int getWaterLevel() {
        return this.waterLevel;
    }

    public int getMaxNutrients() {
        return MAX_NUTRIENTS;
    }

    public int getProximates() {
        return this.proximates;
    }

    public int getMinerals() {
        return this.minerals;
    }

    public int getVitamins() {
        return this.vitamins;
    }

    public int getLipids() {
        return this.lipids;
    }

    @Override
    public int getField(int id) {
        int processCount = this.getProcessCount();

        if (id < processCount) {
            return this.processTime[id];
        } else if (id < processCount * 2) {
            return this.totalProcessTime[id - processCount];
        } else {
            int type = id - (processCount * 2);
            switch (type) {
                case 0:
                    return this.waterLevel;
                case 1:
                    return this.proximates;
                case 2:
                    return this.minerals;
                case 3:
                    return this.vitamins;
                case 4:
                    return this.lipids;
                case 5:
                    return this.temperature;
            }
        }

        return 0;
    }

    @Override
    public void setField(int id, int value) {
        int processCount = this.getProcessCount();
        if (id < processCount) {
            this.processTime[id] = value;
        } else if (id < processCount * 2) {
            this.totalProcessTime[id - processCount] = value;
        } else {
            int type = id - (processCount * 2);
            switch (type) {
                case 0:
                    this.waterLevel = value;
                    break;
                case 1:
                    this.proximates = value;
                    break;
                case 2:
                    this.minerals = value;
                    break;
                case 3:
                    this.vitamins = value;
                    break;
                case 4:
                    this.lipids = value;
                    break;
                case 5:
                    this.temperature = value;
                    break;
            }
        }
    }

    @Override
    public int getFieldCount() {
        return this.getProcessCount() * 2 + 6;
    }

    public Dinosaur getDinosaur() {
    	ItemStack stack = this.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            return DinosaurProvider.getFromStack(stack).getValue(stack);
        }
        return null;
    }

    @Override
    public void setTemperature(int index, int value) {
        this.temperature = value;
    }

    @Override
    public int getTemperature(int index) {
        return this.temperature;
    }

    @Override
    public int getTemperatureCount() {
        return 1;
    }
}
