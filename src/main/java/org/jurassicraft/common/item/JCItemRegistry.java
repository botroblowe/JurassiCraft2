package org.jurassicraft.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jurassicraft.common.creativetab.JCCreativeTabs;
import org.jurassicraft.common.paleopad.dinopedia.DinoPediaRegistry;

public class JCItemRegistry
{
    public static ItemPlasterAndBandage plaster_and_bandage;
    public static ItemDinosaurSpawnEgg spawn_egg;
    public static ItemSkull skull;
    public static ItemTooth tooth;

    public static ItemDNA dna;
    public static ItemDinosaurEgg egg;
    public static ItemPaleoPad paleo_pad;
    public static ItemSoftTissue soft_tissue;

    public static ItemDinosaurMeat dino_meat;
    public static ItemDinosaurSteak dino_steak;

    public static ItemBluePrint blue_print;
    public static ItemJurassiCraftSign jc_sign;

    public static ItemAmber amber;
    public static ItemBasic petri_dish;
    public static ItemBasic empty_test_tube;

    public static ItemSyringe syringe;
    public static ItemEmptySyringe empty_syringe;

    public static ItemEntityRemover entityRemover;

    public static ItemStorageDisc storage_disc;
    public static ItemBasic dna_base;

    public static ItemCage cage_small;

    public static ItemPlantDNA plant_dna;

    public static ItemBasic sea_lamprey;

    public static ItemBasic iron_blades;
    public static ItemBasic iron_rod;

    public static ItemBasic needle;

    public static ItemGrowthSerum growth_serum;

    public static ItemBasic plant_cells;
    public static ItemPlantCallus plant_callus;
    public static ItemBasic plant_cells_petri_dish;
    public static ItemHelicopter helicopter_spawner;
    public static ItemBasic tracker;

    //TODO more complex crafting components, eg circuit boards, ROM Drive

    public void register()
    {
        plaster_and_bandage = new ItemPlasterAndBandage();
        spawn_egg = new ItemDinosaurSpawnEgg();
        skull = new ItemSkull();
        tooth = new ItemTooth();
        dna = new ItemDNA();
        egg = new ItemDinosaurEgg();
        paleo_pad = new ItemPaleoPad();
        dino_meat = new ItemDinosaurMeat();
        dino_steak = new ItemDinosaurSteak();
        blue_print = new ItemBluePrint();
        jc_sign = new ItemJurassiCraftSign();
        soft_tissue = new ItemSoftTissue();
        amber = new ItemAmber();
        petri_dish = new ItemBasic("Petri Dish", JCCreativeTabs.items);
        empty_test_tube = new ItemBasic("Empty Test Tube", JCCreativeTabs.items);
        syringe = new ItemSyringe();
        empty_syringe = new ItemEmptySyringe();
        entityRemover = new ItemEntityRemover();
        storage_disc = new ItemStorageDisc();
        dna_base = new ItemBasic("DNA Base Material", JCCreativeTabs.items);
        cage_small = new ItemCage();
        plant_dna = new ItemPlantDNA();
        sea_lamprey = new ItemBasic("Sea Lamprey", JCCreativeTabs.items);
        iron_blades = new ItemBasic("Iron Blades", JCCreativeTabs.items);
        iron_rod = new ItemBasic("Iron Rod", JCCreativeTabs.items);
        growth_serum = new ItemGrowthSerum();
        needle = new ItemBasic("Needle", JCCreativeTabs.items);
        plant_cells = new ItemBasic("Plant Cells", JCCreativeTabs.items);
        plant_callus = new ItemPlantCallus();
        plant_cells_petri_dish = new ItemBasic("Plant Cells Petri Dish", JCCreativeTabs.items);
        tracker = new ItemBasic("Tracker", JCCreativeTabs.items);

        helicopter_spawner = new ItemHelicopter();

        registerItem(amber, "Amber");
        registerItem(sea_lamprey, "Sea Lamprey");
        registerItem(plaster_and_bandage, "Plaster And Bandage");
        registerItem(empty_test_tube, "Empty Test Tube");
        registerItem(empty_syringe, "Empty Syringe");
        registerItem(growth_serum, "Growth Serum");
        registerItem(storage_disc, "Storage Disc");
        registerItem(dna_base, "DNA Base Material");
        registerItem(petri_dish, "Petri Dish");
        registerItem(plant_cells_petri_dish, "Plant Cells Petri Dish");
        registerItem(blue_print, "Blue Print");
        registerItem(cage_small, "Cage Small");
//        registerItem(jc_sign, "JurassiCraft Sign");
        registerItem(spawn_egg, "Dino Spawn Egg");
        registerItem(skull, "Skull");
        registerItem(tooth, "Tooth");
        registerItem(dna, "DNA");
        registerItem(egg, "Dino Egg");
        registerItem(paleo_pad, "Paleo Pad");
        registerItem(soft_tissue, "Soft Tissue");
        registerItem(syringe, "Syringe");
        registerItem(plant_dna, "Plant DNA");
        registerItem(iron_blades, "Iron Blades");
        registerItem(iron_rod, "Iron Rod");
        registerItem(needle, "Needle");
        registerItem(plant_cells, "Plant Cells");
        registerItem(plant_callus, "Plant Callus");
        registerItem(tracker, "Tracker");

        registerItem(dino_meat, "Dinosaur Meat");
        registerItem(dino_steak, "Dinosaur Steak");

        registerItem(helicopter_spawner, "Helicopter Spawner");
//        registerItem(entityRemover, "Entity Remover");

//        for (int i = 0; i < JCEntityRegistry.getDinosaurs().size(); i++)
//        {
//            EcoAPI.registerEcologicalRoleFoodItem(EcoAPI.carnivore, new ItemStack(JCItemRegistry.dino_meat, 1, i));
//            EcoAPI.registerEcologicalRoleFoodItem(EcoAPI.carnivore, new ItemStack(JCItemRegistry.dino_steak, 1, i));
//            EcoAPI.registerEntityClassDropItems(JCEntityRegistry.getDinosaurs().get(i).getDinosaurClass(), new ItemStack[]{new ItemStack(JCItemRegistry.dino_meat, 1, i), new ItemStack(JCItemRegistry.dino_steak, 1, i)});
//        }
    }

    public void registerItem(Item item, String name)
    {
        DinoPediaRegistry.registerItem(new ItemStack(item));

        GameRegistry.registerItem(item, name.toLowerCase().replaceAll(" ", "_"));
    }
}