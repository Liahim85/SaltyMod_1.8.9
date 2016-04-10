package ru.liahim.saltmod.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.SaltConfig;

public abstract class SaltSlab extends BlockSlab {

    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", SaltSlab.EnumType.class);
	protected boolean crystal = true;
	
	public SaltSlab(String name) {
		super(Material.rock);
		CreativeTabs tab = null;
        IBlockState iblockstate = this.blockState.getBaseState();
        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
            tab = CommonProxy.saltTab;
        }
        this.setDefaultState(iblockstate.withProperty(VARIANT, SaltSlab.EnumType.BLOCK));
		this.setTickRandomly(true);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
        this.setHardness(5F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 1);
		useNeighborBrightness = !this.isDouble();
	}

	@Override
	public String getUnlocalizedName(int meta)
	{
		return super.getUnlocalizedName() + "." + SaltSlab.EnumType.byMetadata(meta).getUnlocalizedName();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.saltSlab);
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return Item.getItemFromBlock(ModBlocks.saltSlab);
    }
    
    @Override
	public IProperty getVariantProperty()
    {
        return VARIANT;
    }

    @Override
	public Object getVariant(ItemStack stack)
    {
        return SaltSlab.EnumType.byMetadata(stack.getMetadata() & 7);
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
        if (itemIn != Item.getItemFromBlock(ModBlocks.saltSlabDouble))
        {
            for (SaltSlab.EnumType type : SaltSlab.EnumType.values())
            {
            	list.add(new ItemStack(itemIn, 1, type.getMetadata()));
            }
        }
    }
	
    @Override
	public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, SaltSlab.EnumType.byMetadata(meta & 7));

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }
    
    @Override
	public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((SaltSlab.EnumType)state.getValue(VARIANT)).getMetadata();

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    @Override
	protected BlockState createBlockState()
    {
        return this.isDouble() ? new BlockState(this, new IProperty[] {VARIANT}): new BlockState(this, new IProperty[] {HALF, VARIANT});
    }
    
    @Override
	public int damageDropped(IBlockState state)
    {
        return ((SaltSlab.EnumType)state.getValue(VARIANT)).getMetadata();
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

			//Crystal Growth
			
			BlockPos posUp = new BlockPos(pos.up());
	
			if ((world.getBlockState(posUp.east()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.east()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.west()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.west()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.north()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.north()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.south()).getBlock() == ModBlocks.saltBlock || world.getBlockState(posUp.south()).getBlock() == ModBlocks.saltSlabDouble) &&
				(world.getBlockState(posUp.add(1, 0, 1)).getBlock().getMaterial() == Material.water) &&
				(world.getBlockState(posUp.add(1, 0, -1)).getBlock().getMaterial() == Material.water) &&
				(world.getBlockState(posUp.add(-1, 0, 1)).getBlock().getMaterial() == Material.water) &&
				(world.getBlockState(posUp.add(-1, 0, -1)).getBlock().getMaterial() == Material.water) &&
				(world.getLightFromNeighbors(posUp) < 15))
			{
				if (rand.nextInt(SaltConfig.saltCrystalGrowSpeed) == 0 && crystal)
				{
					if(world.isAirBlock(posUp))
					{
						world.setBlockState(posUp, ModBlocks.saltCrystal.getStateFromMeta(2), 3);
					}
				
					else if (world.getBlockState(posUp).getBlock() == ModBlocks.saltCrystal &&
							 world.getBlockState(posUp).getBlock().getMetaFromState(world.getBlockState(posUp)) == 2)
					{
						world.setBlockState(posUp, ModBlocks.saltCrystal.getStateFromMeta(1), 3);
					}
				
					else if (world.getBlockState(posUp).getBlock() == ModBlocks.saltCrystal &&
							 world.getBlockState(posUp).getBlock().getMetaFromState(world.getBlockState(posUp)) == 1)
					{
						world.setBlockState(posUp, ModBlocks.saltCrystal.getDefaultState(), 3);
					}
				}
			
				crystal = true;
			}
	
			//Melting Ice
		
			int yUP;
			int yDOWN;
		
			if (this == ModBlocks.saltSlabDouble){yUP = 2; yDOWN = 1;}
			else if (World.doesBlockHaveSolidTopSurface(world, pos)){yUP = 2; yDOWN = 0;}
			else {yUP = 1; yDOWN = 1;}
		
			for (int x2 = x - 1; x2 < x + 2; x2++) {
			for (int y2 = y - yDOWN; y2 < y + yUP; y2++) {
			for (int z2 = z - 1; z2 < z + 2; z2++) {
				BlockPos pos2 = new BlockPos(x2, y2, z2);
				Block block = world.getBlockState(pos2).getBlock();
				if ((block == Blocks.ice || block == Blocks.snow || (block == Blocks.snow_layer && y2 != y-1 && yDOWN == 1)) &&
						((x2-1 == x && (world.getBlockState(pos2.west()).getBlock() == this || world.getBlockState(pos2.west()).getBlock().getMaterial() == Material.water)) ||
						 (x2+1 == x && (world.getBlockState(pos2.east()).getBlock() == this || world.getBlockState(pos2.east()).getBlock().getMaterial() == Material.water)) ||
						 (y2-1 == y && (world.getBlockState(pos2.down()).getBlock() == this || world.getBlockState(pos2.down()).getBlock().getMaterial() == Material.water)) ||
						 (y2+1 == y && (world.getBlockState(pos2.up()).getBlock() == this || world.getBlockState(pos2.up()).getBlock().getMaterial() == Material.water)) ||
						 (z2-1 == z && (world.getBlockState(pos2.north()).getBlock() == this || world.getBlockState(pos2.north()).getBlock().getMaterial() == Material.water)) ||
						 (z2+1 == z && (world.getBlockState(pos2.south()).getBlock() == this || world.getBlockState(pos2.south()).getBlock().getMaterial() == Material.water))))

				{crystal = false; world.scheduleUpdate(pos, this, 5);

					if (rand.nextInt(20) == 0)
					{
						if(block == Blocks.ice || block == Blocks.snow)
						{world.setBlockState(pos2, Blocks.water.getDefaultState(), 3); crystal = true;}
						if(block == Blocks.snow_layer && y2 != y-1)
						{world.setBlockToAir(pos2); crystal = true;}
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
	
	public static enum EnumType implements IStringSerializable
    {
        BLOCK(0, "block", "Block"),
        BRICK(1, "brick", "Brick"),
        PILLAR(2, "pillar", "Pillar");

        private static final SaltSlab.EnumType[] META_LOOKUP = new SaltSlab.EnumType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private EnumType(int meta, String name, String unlocalizedName)
        {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        @Override
		public String toString()
        {
            return this.name;
        }

        public static SaltSlab.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
		public String getName()
        {
            return this.name;
        }
        
        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        static
        {
            for (SaltSlab.EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
    }
}