package ru.liahim.saltmod.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.ModBlocks;

public class SaltBrickStair extends BlockStairs {
	
	public SaltBrickStair(String name, CreativeTabs tab) {
        super(ModBlocks.saltBlock.getStateFromMeta(5));
        this.setTickRandomly(true);
        this.setUnlocalizedName(name);
        this.setCreativeTab(tab);
        this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 1);
		useNeighborBrightness = true;
    }
	
    @Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
    {
        this.saltDamage(worldIn, entityIn);
        super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
    }
    
    private void saltDamage(World worldIn, Entity entity)
    {
   		if (entity instanceof EntityLivingBase && EntityList.getEntityString(entity) != null &&
   	   	  ((EntityList.getEntityString(entity).toLowerCase().contains("slime") && !EntityList.getEntityString(entity).toLowerCase().contains("lava")) ||
   	   		EntityList.getEntityString(entity).toLowerCase().contains("witch")))
   	   		{entity.attackEntityFrom(DamageSource.cactus, 1.0F);}
    }
	
    @Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			
			for (int x2 = x - 1; x2 < x + 2; x2++) {
			for (int y2 = y - 1; y2 < y + 2; y2++) {
			for (int z2 = z - 1; z2 < z + 2; z2++) {
				BlockPos pos2 = new BlockPos(x2, y2, z2);
				Block block = world.getBlockState(pos2).getBlock();
				if ((block == Blocks.ice || block == Blocks.snow || (block == Blocks.snow_layer && y2 != y-1)) &&
					((x2-1 == x && (world.getBlockState(pos2.west()).getBlock() == this || world.getBlockState(pos2.west()).getBlock().getMaterial() == Material.water)) ||
					(x2+1 == x && (world.getBlockState(pos2.east()).getBlock() == this || world.getBlockState(pos2.east()).getBlock().getMaterial() == Material.water)) ||
					(y2-1 == y && (world.getBlockState(pos2.down()).getBlock() == this || world.getBlockState(pos2.down()).getBlock().getMaterial() == Material.water)) ||
					(y2+1 == y && (world.getBlockState(pos2.up()).getBlock() == this || world.getBlockState(pos2.up()).getBlock().getMaterial() == Material.water)) ||
					(z2-1 == z && (world.getBlockState(pos2.north()).getBlock() == this || world.getBlockState(pos2.north()).getBlock().getMaterial() == Material.water)) ||
					(z2+1 == z && (world.getBlockState(pos2.south()).getBlock() == this || world.getBlockState(pos2.south()).getBlock().getMaterial() == Material.water))))
				
				{world.scheduleUpdate(pos, this, 5);
				
					if (rand.nextInt(20) == 0)
					{
						if(block == Blocks.ice || block == Blocks.snow)
						{world.setBlockState(pos2, Blocks.water.getDefaultState(), 3);}
						if(block == Blocks.snow_layer && y2 != y-1)
						{world.setBlockToAir(pos2);}
					}
				}
			}
			}
			}
		}
	}
	
	@Override
	public MapColor getMapColor(IBlockState state)
	{
        return MapColor.quartzColor;
    }
}