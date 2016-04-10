package ru.liahim.saltmod.block;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.ModItems;

public class MudBlock extends BlockFalling {
	
	private static int tick = 0;
	
	public MudBlock(String name, CreativeTabs tab) {
		super(Material.ground);
		this.setStepSound(soundTypeGravel);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setHardness(0.5F);
		this.setResistance(1F);
		this.setHarvestLevel("shovel", 0);
	}
	
    @Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        float f = 0.125F;
        return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 - f, pos.getZ() + 1);
    }
	
    @Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }
	
    @Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
        return ModItems.mineralMud;
    }

    @Override
	public int quantityDropped(Random rand)
    {
        return 4;
    }
    
    @Override
	public MapColor getMapColor(IBlockState state)
    {
        return MapColor.grayColor;
    }
}