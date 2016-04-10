package ru.liahim.saltmod.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.ModItems;

public class SaltDirtLite extends Block {
	
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", SaltDirtLite.EnumType.class);
	
	public SaltDirtLite(String name, CreativeTabs tab) {
		super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SaltDirtLite.EnumType.EMPTY));
		this.setTickRandomly(true);
		this.setStepSound(soundTypeGravel);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setHardness(0.5F);
		this.setResistance(1F);
		this.setHarvestLevel("shovel", 0);
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState();
	}
    
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, SaltDirtLite.EnumType.byMetadata(meta));
	}
    
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((SaltDirtLite.EnumType)state.getValue(VARIANT)).getMetadata();
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {VARIANT});
	}
    
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
        if (!world.isRemote)
        {
        	if (world.getBlockState(pos.up()).getBlock().getMaterial() == Material.snow)
        	{
        		world.setBlockToAir(pos.up());
        	}
		
        	else if (!world.getBlockState(pos.up()).getBlock().getMaterial().isSolid() && world.getLight(pos.up()) > 7)
        	{
        		int j = world.getBlockState(pos).getBlock().getMetaFromState(state);
        		if (j > 2)
        		{
        			int x = pos.getX();
        			int y = pos.getY();
        			int z = pos.getZ();
        			
        			for (int x1 = x - 1; x1 < x + 2; x1++) {
        			for (int z1 = z - 1; z1 < z + 2; z1++) {
        				
        				BlockPos pos2 = new BlockPos(x1, y, z1);
				
        				if ((world.getBlockState(pos2).getBlock() == Blocks.grass || world.getBlockState(pos2) == ModBlocks.saltGrass) &&
        					 world.getBlockState(pos).getBlock() == this && world.getLightFromNeighbors(pos.up()) > 7 && rand.nextInt(5) == 0)
						
        				{world.setBlockState(pos, ModBlocks.saltGrass.getStateFromMeta(j), 3);}
        			}
        			}
        		}
        	}
        }
	}
    
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		boolean S = (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.saltPinch);
		ItemStack current = player.getCurrentEquippedItem();
		
			if (S)
			{
				if (world.getBlockState(pos.up()).getBlock() == ModBlocks.saltWort) {player.addStat(AchievSalt.saltWortFarm, 1);}
				
				int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
			
				if (meta == 0 || meta > 2)
				{world.setBlockState(pos, this.getStateFromMeta(1), 3); if (!player.capabilities.isCreativeMode){--current.stackSize;}}
				else if (meta == 1)
				{world.setBlockState(pos, this.getStateFromMeta(2), 3); if (!player.capabilities.isCreativeMode){--current.stackSize;}}
				else if (meta == 2)
				{world.setBlockState(pos, ModBlocks.saltDirt.getDefaultState(), 3); if (!player.capabilities.isCreativeMode){--current.stackSize;}}
				return true;
			}
			
        return false;
    }
	
	public enum EnumType implements IStringSerializable
	{
		EMPTY(0, "empty"),
		MEDIUM(1, "medium"),
		FULL(2, "full"),
		SIDE_CNE(3, "side_cne"),
		SIDE_CES(4, "side_ces"),
		SIDE_CSW(5, "side_csw"),
		SIDE_CWN(6, "side_cwn"),
		SIDE_N(7, "side_n"),
		SIDE_E(8, "side_e"),
		SIDE_S(9, "side_s"),
		SIDE_W(10, "side_w"),
		SIDE_NE(11, "side_ne"),
		SIDE_ES(12, "side_es"),
		SIDE_SW(13, "side_sw"),
		SIDE_WN(14, "side_wn"),
		SIDE_NESW(15, "side_nesw");

        private static final SaltDirtLite.EnumType[] META_LOOKUP = new SaltDirtLite.EnumType [values().length];
	    private final int meta;
	    private final String name;

	    private EnumType(int meta, String name)
	    {
	        this.meta = meta;
	        this.name = name;
	    }
	    
        public int getMetadata()
        {
            return this.meta;
        }

		@Override
		public String getName()
		{
			return name;
		}
		
		public static SaltDirtLite.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
			return META_LOOKUP[meta];
		}
		
        static
        {
            for (SaltDirtLite.EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
	}
}