package ru.liahim.saltmod.item;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.ModItems;
import ru.liahim.saltmod.init.SaltConfig;

public class MudArmor extends ItemArmor {
	
	public MudArmor(String name, ArmorMaterial material, int type) {
	    super(material, 0, type);
	    this.setUnlocalizedName(name);
	    this.setCreativeTab(CommonProxy.saltTab);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack material)
	{
		return material.getItem() == ModItems.mineralMud  || super.getIsRepairable(toRepair, material);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
		if (!world.isRemote && stack.getItem() != null && SaltConfig.mudArmorWaterDam)
		{
			Random rand = new Random();
			
			if (stack.getItem() == ModItems.mudHelmet)
			{
				if (((world.isRaining() && player.isWet() && !player.isInsideOfMaterial(Material.water)) ||
					player.isInsideOfMaterial(Material.water)) && rand.nextInt(100) == 0)
				{
					stack.damageItem(1, player);
				}

				if (stack.getItemDamage() >= stack.getMaxDamage()) {player.setCurrentItemOrArmor(4, null); player.addStat(AchievSalt.discomfiture, 1);}
			}
			
			if (stack.getItem() == ModItems.mudChestplate)
			{
				if ((world.isRaining() || (player.isInsideOfMaterial(Material.water) &&
					player.isInWater())) && player.isWet() && rand.nextInt(100) == 0)
				{
					stack.damageItem(1, player);
				}
				
				if (!world.isRaining() && !player.isInsideOfMaterial(Material.water)
					&& player.isInWater() && player.isWet() && rand.nextInt(200) == 0)
				{
					stack.damageItem(1, player);
				}

				if (stack.getItemDamage() >= stack.getMaxDamage()) {player.setCurrentItemOrArmor(3, null); player.addStat(AchievSalt.discomfiture, 1);}
			}
			
			if (stack.getItem() == ModItems.mudLeggings)
			{
				if (player.isInWater() && player.isWet() && rand.nextInt(100) == 0)
				{
					stack.damageItem(1, player);
				}
				
				if (world.isRaining() && !player.isInWater() && player.isWet() && rand.nextInt(200) == 0)
				{
					stack.damageItem(1, player);
				}

				if (stack.getItemDamage() >= stack.getMaxDamage()) {player.setCurrentItemOrArmor(2, null); player.addStat(AchievSalt.discomfiture, 1);}
			}
			
			if (stack.getItem() == ModItems.mudBoots)
			{
				if (player.isInWater() && player.isWet() && rand.nextInt(100) == 0)
				{
					stack.damageItem(1, player);
				}
				
				if (!player.isInWater() && world.isRaining() && player.isWet() && rand.nextInt(200) == 0)
				{
					stack.damageItem(1, player);
				}

				if (stack.getItemDamage() >= stack.getMaxDamage()) {player.setCurrentItemOrArmor(1, null); player.addStat(AchievSalt.discomfiture, 1);}
			}
		}
    }
}