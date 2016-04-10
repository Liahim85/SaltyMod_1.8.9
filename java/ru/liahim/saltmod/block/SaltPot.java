package ru.liahim.saltmod.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.init.ModItems;

public class SaltPot extends BlockContainer {	

    public static final PropertyEnum CONTENTS = PropertyEnum.create("contents", SaltPot.EnumFlowerType.class);
	
	public SaltPot(String name, CreativeTabs tab) {
		super(Material.circuits);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(CONTENTS, SaltPot.EnumFlowerType.EMPTY));
        this.setBlockBoundsForItemRender();
	}
	
	@Override
    public void setBlockBoundsForItemRender()
    {
        float f = 0.375F;
        float f1 = f / 2.0F;
        this.setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
    }
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
    public int getRenderType()
    {
        return 3;
    }
	
	@Override
    public boolean isFullCube()
    {
        return false;
    }
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.inventory.getCurrentItem();
        
		if (itemstack != null && itemstack.getItem() != ModItems.saltWortSeed && this.getTileEntity(worldIn, pos).getFlowerPotItem() == null)
		{
			worldIn.setBlockState(pos, Blocks.flower_pot.getDefaultState());
			return Blocks.flower_pot.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
		}
		
        else
        {
            return false;
        }
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
        return tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null ? tileentityflowerpot.getFlowerPotItem() : Items.flower_pot;
    }
	
	@Override
    public int getDamageValue(World worldIn, BlockPos pos)
    {
        TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);
        return tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null ? tileentityflowerpot.getFlowerPotData() : 0;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean isFlowerPot()
    {
        return true;
    }
	
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
    }
	
	@Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
    }
	
	@Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (player.capabilities.isCreativeMode)
        {
            TileEntityFlowerPot tileentityflowerpot = this.getTileEntity(worldIn, pos);

            if (tileentityflowerpot != null)
            {
                tileentityflowerpot.setFlowerPotData((Item)null, 0);
            }
        }
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.flower_pot;
    }
	
    private TileEntityFlowerPot getTileEntity(World worldIn, BlockPos pos)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)tileentity : null;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        Object object = null;
        int j = 0;

        switch (meta)
        {
            case 1:
                object = ModItems.saltWortSeed;
                j = SaltWort.EnumType.STAGE_0.getMetadata();
                break;
            case 2:
            	object = ModItems.saltWortSeed;
                j = SaltWort.EnumType.STAGE_1.getMetadata();
                break;
        }

        return new TileEntityFlowerPot((Item)object, j);
    }
	
	@Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {CONTENTS});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(CONTENTS, SaltPot.EnumFlowerType.byMetadata(meta));
	}
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return ((SaltPot.EnumFlowerType)state.getValue(CONTENTS)).getMetadata();
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        SaltPot.EnumFlowerType enumflowertype = SaltPot.EnumFlowerType.EMPTY;
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityFlowerPot)
        {
            TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
            Item item = tileentityflowerpot.getFlowerPotItem();

            if (item == ModItems.saltWortSeed)
            {
                int i = tileentityflowerpot.getFlowerPotData();

                switch (i)
                {
                	case 0:
                		enumflowertype = SaltPot.EnumFlowerType.SALTWORT_0;
                		break;
                	case 1:
                		enumflowertype = SaltPot.EnumFlowerType.SALTWORT_1;
                		break;
                	default:
                		enumflowertype = SaltPot.EnumFlowerType.EMPTY;
                }
            }
        }

        return state.withProperty(CONTENTS, enumflowertype);
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        java.util.List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        TileEntityFlowerPot te = world.getTileEntity(pos) instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)world.getTileEntity(pos) : null;
        if (te != null && te.getFlowerPotItem() != null)
        	if (te.getFlowerPotItem() == ModItems.saltWortSeed) ret.add(new ItemStack(te.getFlowerPotItem(), 1, 0));
        	else ret.add(new ItemStack(te.getFlowerPotItem(), 1, te.getFlowerPotData()));
        return ret;
    }
    
    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true;
        return super.removedByPlayer(world, pos, player, willHarvest);
    }
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
    {
        super.harvestBlock(world, player, pos, state, te);
        world.setBlockToAir(pos);
    }
    
    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
		return false;
    }
	
	public static enum EnumFlowerType implements IStringSerializable
    {
        EMPTY(0, "empty"),
        SALTWORT_0(1, "saltwort_0"),
        SALTWORT_1(2, "saltwort_1");
        
        private static final SaltPot.EnumFlowerType[] META_LOOKUP = new SaltPot.EnumFlowerType[values().length];
        private final int meta;
        private final String name;

        private EnumFlowerType(int meta, String name)
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
            return this.name;
        }
        
        @Override
		public String toString()
        {
            return getName();
        }

        public static SaltPot.EnumFlowerType byMetadata(int meta)
        {
        	if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
        	return META_LOOKUP[meta];
        }
	
        static
        {
        	for (SaltPot.EnumFlowerType type : values())
        	{
        		META_LOOKUP[type.getMetadata()] = type;
        	}
        }
    }
}
