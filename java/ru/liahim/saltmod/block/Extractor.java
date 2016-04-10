package ru.liahim.saltmod.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.tileEntity.TileEntityExtractor;

public class Extractor extends BlockContainer {
    
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	private final boolean isBurning;
	private final boolean isExtract;
	private static boolean keepInventory;
	
	public Extractor(boolean burn, boolean ext, String name, CreativeTabs tab) {
		super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.isBurning = burn;
		this.isExtract = ext;
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setHardness(5F);
		this.setResistance(10F);
	}
    
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.extractor);
    }

    @Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            Block block = worldIn.getBlockState(pos.north()).getBlock();
            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (this.isBurning)
        {
        	EnumFacing enumfacing = state.getValue(FACING);
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = pos.getX() + rand.nextDouble() * 0.4F + 0.3F;
            double d6 = pos.getZ() + rand.nextDouble() * 0.4F + 0.3F;
            double d7 = (double)pos.getX() + (double)rand.nextFloat();
            double d8 = (double)pos.getZ() + (double)rand.nextFloat();
            boolean clear = !worldIn.isSideSolid(pos.up(), EnumFacing.DOWN) &&
            		FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(pos.up()).getBlock()) == null;
            boolean ceiling = worldIn.isSideSolid(pos.up(2), EnumFacing.DOWN);

            switch (Extractor.SwitchEnumFacing.FACING_LOOKUP[enumfacing.ordinal()])
            {
                case 1:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case 2:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case 3:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case 4:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
            }
            
        	if (isExtract && clear)
        	{
        		worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d5, pos.getY() + 1.1D, d6, 0.0D, 0.1D, 0.0D, new int[0]);

        		if (ceiling && rand.nextInt(10) == 0)
        		worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d7, pos.getY() + 1.95D, d8, 0.0D, 0.0D, 0.0D, new int[0]);
        	}
        }
    }

    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        
        else
        {
        	TileEntityExtractor te = (TileEntityExtractor) worldIn.getTileEntity(pos);

            if (te != null)
            {
            	ItemStack itemstack = playerIn.inventory.getCurrentItem();
            	
				if (!fillTank(worldIn, pos, te, itemstack, playerIn))			
				{
					if (!drainTank(worldIn, pos, te, itemstack, playerIn))
					{
						playerIn.openGui(SaltMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
					}
				}
            }
            
            return true;
        }
	}
        
    public static boolean fillTank(World worldIn, BlockPos pos, IFluidHandler tank, ItemStack held, EntityPlayer playerIn)
    {
    	if (held != null)
    	{
    		FluidStack heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
    		
    		if (heldContents != null && held.getItem() != Items.potionitem)
    		{
    			int used = tank.fill(EnumFacing.UP, heldContents, true);

    			if (used > 0)
    			{
    				ItemStack consumed = held.getItem().getContainerItem(held);
    				
    				if (consumed != null && !playerIn.capabilities.isCreativeMode)
    				{
    					playerInvChange(worldIn, pos, held, playerIn, consumed);
    				}

    				return true;
    			}
    		}
    		
    		else if (held.getItem() == Items.potionitem)
    		{
    			heldContents = new FluidStack(FluidRegistry.WATER, 333);
    			int used = tank.fill(EnumFacing.UP, heldContents, true);
    			
    			if (used > 0)
    			{
    				if (!playerIn.capabilities.isCreativeMode)
    				{
    					playerInvChange(worldIn, pos, held, playerIn, new ItemStack(Items.glass_bottle));
     				}
    				
    				return true;
    			}
    		}
    	}

    	return false;
    }
    
	private boolean drainTank(World worldIn, BlockPos pos, IFluidHandler tank, ItemStack held, EntityPlayer playerIn)
	{
		if (held != null)
		{
			FluidStack heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
			FluidStack available = tank.drain(EnumFacing.UP, Integer.MAX_VALUE, false);

			if (available != null)
			{
				ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, held);
				heldContents = FluidContainerRegistry.getFluidForFilledItem(filled);

				if (available.getFluid() == FluidRegistry.WATER && held.getItem() == Items.glass_bottle && available.amount >= 333)
				{
					if (!playerIn.capabilities.isCreativeMode)
					{
						playerInvChange(worldIn, pos, held, playerIn, new ItemStack(Items.potionitem));
					}
					
					tank.drain(EnumFacing.UP, 333, true);
					
					return true;
				}
				
				else if (heldContents != null)
				{
					if (!playerIn.capabilities.isCreativeMode)
					{
						playerInvChange(worldIn, pos, held, playerIn, filled);
					}

					tank.drain(EnumFacing.UP, heldContents.amount, true);

					return true;
				}
			}
		}

		return false;
	}
	
    private static void playerInvChange(World worldIn, BlockPos pos, ItemStack held, EntityPlayer playerIn, ItemStack stack)
    {
		if (--held.stackSize <= 0)
		{
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
		}

		if (playerIn.inventory.getCurrentItem() != null)
		{
			if (!playerIn.inventory.addItemStackToInventory(stack))
			{
				worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, stack));
			}
			
			else if (playerIn instanceof EntityPlayerMP)
			{
				((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
			}
		}
		
		else
		{
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, stack);
			
			if (playerIn instanceof EntityPlayerMP)
			{
				((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
			}
		}
    }
    
    public static void setState(boolean active, boolean extract, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active)
        {
        	if (extract)
        	{
        		worldIn.setBlockState(pos, ModBlocks.extractorSteam.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        		worldIn.setBlockState(pos, ModBlocks.extractorSteam.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        	}
        	
        	else
        	{
        		worldIn.setBlockState(pos, ModBlocks.extractorLit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        		worldIn.setBlockState(pos, ModBlocks.extractorLit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        	}
        }
        
        else
        {
        	worldIn.setBlockState(pos, ModBlocks.extractor.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
    		worldIn.setBlockState(pos, ModBlocks.extractor.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
		return new TileEntityExtractor();
	}
    
    @Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TileEntityExtractor)
            {
                ((TileEntityExtractor)te).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TileEntityExtractor)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityExtractor)te);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
	public boolean hasComparatorInputOverride()
    {
        return true;
    }
    
    @Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos)
    {
    	TileEntityExtractor te = (TileEntityExtractor)worldIn.getTileEntity(pos);
    	return te.tank.getFluid() != null ? te.getFluidAmountScaled(15) : 0;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return Item.getItemFromBlock(ModBlocks.extractor);
    }
    
    @Override
	public int getRenderType()
    {
        return 3;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    @Override
	public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
	public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
	protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }
    
    @SideOnly(Side.CLIENT)
    static final class SwitchEnumFacing
	{
    	static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];

    	static
    	{
    		try
    		{
    			FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 1;
    		}
    		catch (NoSuchFieldError var4)
    		{
    			;
    		}
	
    		try
    		{
    			FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
    		}
    		catch (NoSuchFieldError var3)
    		{
    			;
    		}

    		try
    		{
    			FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
    		}
    		catch (NoSuchFieldError var2)
    		{
    			;
    		}

    		try
    		{
    			FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
    		}
    		catch (NoSuchFieldError var1)
    		{
    			;
    		}
    	}
	}
}