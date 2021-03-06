package ru.liahim.saltmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.init.ModBlocks;

public class ItemSaltGrass extends ItemBlock {

	private final Block grass;
	
    public ItemSaltGrass(Block block) {
    	super(block);
    	this.grass = ModBlocks.saltGrass;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        return this.grass.getRenderColor(this.grass.getDefaultState());
    }
 }