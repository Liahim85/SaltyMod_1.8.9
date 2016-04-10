package ru.liahim.saltmod.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.common.CommonProxy;
import ru.liahim.saltmod.item.AchievItem;
import ru.liahim.saltmod.item.Escargot;
import ru.liahim.saltmod.item.FizzyDrink;
import ru.liahim.saltmod.item.MainItems;
import ru.liahim.saltmod.item.MudArmor;
import ru.liahim.saltmod.item.Muffin;
import ru.liahim.saltmod.item.Rainmaker;
import ru.liahim.saltmod.item.Salt;
import ru.liahim.saltmod.item.SaltFood;
import ru.liahim.saltmod.item.SaltWortSeed;

public class ModItems {
	
	static CreativeTabs tab = CommonProxy.saltTab;
	
	public static Item achievItem = new AchievItem("achievItem", null);
	public static Item escargot = new Escargot("escargot", 3, 2.4F, tab, "Escargot");
	
	public static Item salt = new Salt("salt", tab);
	public static Item saltPinch = new MainItems("saltPinch", tab);
	public static Item saltWortSeed = new SaltWortSeed("saltWortSeed", tab);
	public static Item soda = new MainItems("soda", tab);
	public static Item mineralMud = new MainItems("mineralMud", tab);
	
	public static Item saltBeefCooked = new SaltFood("saltBeefCooked", 9, 13.8F).setCreativeTab(tab);
	public static Item saltPorkchopCooked = new SaltFood("saltPorkchopCooked", 9, 13.8F).setCreativeTab(tab);
	public static Item saltMuttonCooked = new SaltFood("saltMuttonCooked", 7, 10.6F).setCreativeTab(tab);
	public static Item saltPotatoBaked = new SaltFood("saltPotatoBaked", 7, 8.2F).setCreativeTab(tab);
	public static Item saltChickenCooked = new SaltFood("saltChickenCooked", 7, 8.2F).setCreativeTab(tab);
	public static Item saltRabbitCooked = new SaltFood("saltRabbitCooked", 6, 7.0F).setCreativeTab(tab);
	public static Item saltFishCod = new SaltFood("saltFishCod", 5, 4.2F).setCreativeTab(tab);
	public static Item saltFishCodCooked = new SaltFood("saltFishCodCooked", 6, 7.0F).setCreativeTab(tab);
	public static Item saltFishSalmon = new SaltFood("saltFishSalmon", 6, 5.0F).setCreativeTab(tab);
	public static Item saltFishSalmonCooked = new SaltFood("saltFishSalmonCooked", 7, 8.2F).setCreativeTab(tab);
	public static Item saltFishClownfish = new SaltFood("saltFishClownfish", 3, 1.6F).setCreativeTab(tab);
	public static Item cornedBeef = new SaltFood("cornedBeef", 5, 2.8F).setCreativeTab(tab);
	public static Item saltBread = new SaltFood("saltBread", 6, 7.0F).setCreativeTab(tab);
	public static Item saltEgg = new SaltFood("saltEgg", 4, 4.8F).setMaxStackSize(16).setCreativeTab(tab);
	public static Item saltRabbitStew = new SaltFood("saltRabbitStew", 11, 13.0F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltMushroomStew = new SaltFood("saltMushroomStew", 7, 8.2F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item pumpkinPorridge = new SaltFood("pumpkinPorridge", 6, 4.8F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item vegetableStew = new SaltFood("vegetableStew", 5, 6.0F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltVegetableStew = new SaltFood("saltVegetableStew", 6, 7.2F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item potatoMushroom = new SaltFood("potatoMushroom", 5, 6.2F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltPotatoMushroom = new SaltFood("saltPotatoMushroom", 6, 7.2F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item fishSoup = new SaltFood("fishSoup", 6, 6.8F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltFishSoup = new SaltFood("saltFishSoup", 7, 7.8F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item fishSalmonSoup = new SaltFood("fishSalmonSoup", 7, 7.2F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltFishSalmonSoup = new SaltFood("saltFishSalmonSoup", 8, 8.2F, Items.bowl).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortBeef = new SaltFood("saltWortBeef", 10, 14.0F, Items.bowl, new PotionEffect(Potion.regeneration.id, 100, 0)).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortPorkchop = new SaltFood("saltWortPorkchop", 10, 14.0F, Items.bowl, new PotionEffect(Potion.regeneration.id, 100, 0)).setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortMutton = new SaltFood("saltWortMutton", 8, 10.8F, Items.bowl, new PotionEffect(Potion.regeneration.id, 100, 0)).setMaxStackSize(1).setCreativeTab(tab);
	public static Item dandelionSalad = new SaltFood("dandelionSalad", 4, 2.4F, Items.bowl, new PotionEffect(Potion.healthBoost.id, 800, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltDandelionSalad = new SaltFood("saltDandelionSalad", 5, 3.4F, Items.bowl, new PotionEffect(Potion.healthBoost.id, 1200, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item wheatSprouts = new SaltFood("wheatSprouts", 3, 3.6F, Items.bowl, new PotionEffect(Potion.healthBoost.id, 600, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWheatSprouts = new SaltFood("saltWheatSprouts", 4, 4.6F, Items.bowl, new PotionEffect(Potion.healthBoost.id, 900, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item fruitSalad = new SaltFood("fruitSalad", 5, 4.8F, Items.bowl, new PotionEffect(Potion.moveSpeed.id, 800, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item gratedCarrot = new SaltFood("gratedCarrot", 5, 4.8F, Items.bowl, new PotionEffect(Potion.nightVision.id, 800, 0)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item saltWortSalad = new SaltFood("saltWortSalad", 6, 2.4F, Items.bowl, new PotionEffect(Potion.regeneration.id, 200, 1)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item carrotPie = new SaltFood("carrotPie", 8, 7.2F).setCreativeTab(tab);
	public static Item applePie = new SaltFood("applePie", 8, 6.0F).setCreativeTab(tab);
	public static Item potatoPie = new SaltFood("potatoPie", 8, 4.8F).setCreativeTab(tab);
	public static Item onionPie = new SaltFood("onionPie", 7, 4.8F).setCreativeTab(tab);
	public static Item fishPie = new SaltFood("fishPie", 8, 8.4F).setCreativeTab(tab);
	public static Item fishSalmonPie = new SaltFood("fishSalmonPie", 9, 9.6F).setCreativeTab(tab);
	public static Item mushroomPie = new SaltFood("mushroomPie", 8, 7.2F).setCreativeTab(tab);
	public static Item pickledMushroom = new SaltFood("pickledMushroom", 8, 6.2F, Items.glass_bottle).setMaxStackSize(1).setCreativeTab(tab);
	public static Item pickledFern = new SaltFood("pickledFern", 4, 4.8F, Items.glass_bottle, new PotionEffect(Potion.resistance.id, 800)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	
	public static Item saltWortPie = new SaltFood("saltWortPie", 6, 3.6F, new PotionEffect(Potion.regeneration.id, 100, 0)).setAlwaysEdible().setCreativeTab(tab);
	public static Item fermentedSaltWort = new SaltFood("fermentedSaltWort", 5, 3.6F, Items.glass_bottle, new PotionEffect(Potion.regeneration.id, 600, 2)).setAlwaysEdible().setMaxStackSize(1).setCreativeTab(tab);
	public static Item fizzyDrink = new FizzyDrink("fizzyDrink", tab);
	public static Item muffin = new Muffin("muffin", tab);
	
	public static Item mudHelmet = new MudArmor("mudHelmet", CommonProxy.mudMaterial, 0);
	public static Item mudChestplate = new MudArmor("mudChestplate", CommonProxy.mudMaterial, 1);
	public static Item mudLeggings = new MudArmor("mudLeggings", CommonProxy.mudMaterial, 2);
	public static Item mudBoots = new MudArmor("mudBoots", CommonProxy.mudMaterial, 3);	

	public static Item powderedMilk = new MainItems("powderedMilk", tab);
	
	public static Item saltStar = new MainItems("saltStar", tab);
	public static Item rainmaker = new Rainmaker("rainmaker", tab);
	
	public static void createItems()
	{
		SaltMod.logger.info("Start to initialize Items");
		GameRegistry.registerItem(achievItem, "achievItem");
	//Main Items	
		GameRegistry.registerItem(salt, "salt");
		GameRegistry.registerItem(saltPinch, "saltPinch");
		GameRegistry.registerItem(saltWortSeed, "saltWortSeed");
		GameRegistry.registerItem(soda, "soda");
		GameRegistry.registerItem(mineralMud, "mineralMud");
	//Food Items
		GameRegistry.registerItem(saltBeefCooked, "saltBeefCooked");
		GameRegistry.registerItem(saltPorkchopCooked, "saltPorkchopCooked");
		GameRegistry.registerItem(saltMuttonCooked, "saltMuttonCooked");
		GameRegistry.registerItem(saltPotatoBaked, "saltPotatoBaked");
		GameRegistry.registerItem(saltChickenCooked, "saltChickenCooked");
		GameRegistry.registerItem(saltRabbitCooked, "saltRabbitCooked");
		GameRegistry.registerItem(saltFishCod, "saltFishCod");
		GameRegistry.registerItem(saltFishCodCooked, "saltFishCodCooked");
		GameRegistry.registerItem(saltFishSalmon, "saltFishSalmon");
		GameRegistry.registerItem(saltFishSalmonCooked, "saltFishSalmonCooked");
		GameRegistry.registerItem(saltFishClownfish, "saltFishClownfish");
		GameRegistry.registerItem(cornedBeef, "cornedBeef");
		GameRegistry.registerItem(saltBread, "saltBread");
		GameRegistry.registerItem(saltEgg, "saltEgg");
		GameRegistry.registerItem(saltRabbitStew, "saltRabbitStew");
		GameRegistry.registerItem(saltMushroomStew, "saltMushroomStew");
		GameRegistry.registerItem(pumpkinPorridge, "pumpkinPorridge");
		GameRegistry.registerItem(vegetableStew, "vegetableStew");
		GameRegistry.registerItem(saltVegetableStew, "saltVegetableStew");
		GameRegistry.registerItem(potatoMushroom, "potatoMushroom");
		GameRegistry.registerItem(saltPotatoMushroom, "saltPotatoMushroom");
		GameRegistry.registerItem(fishSoup, "fishSoup");
		GameRegistry.registerItem(saltFishSoup, "saltFishSoup");
		GameRegistry.registerItem(fishSalmonSoup, "fishSalmonSoup");
		GameRegistry.registerItem(saltFishSalmonSoup, "saltFishSalmonSoup");
		GameRegistry.registerItem(saltWortBeef, "saltWortBeef");
		GameRegistry.registerItem(saltWortPorkchop, "saltWortPorkchop");
		GameRegistry.registerItem(saltWortMutton, "saltWortMutton");
		GameRegistry.registerItem(dandelionSalad, "dandelionSalad");
		GameRegistry.registerItem(saltDandelionSalad, "saltDandelionSalad");
		GameRegistry.registerItem(wheatSprouts, "wheatSprouts");
		GameRegistry.registerItem(saltWheatSprouts, "saltWheatSprouts");
		GameRegistry.registerItem(fruitSalad, "fruitSalad");
		GameRegistry.registerItem(gratedCarrot, "gratedCarrot");
		GameRegistry.registerItem(saltWortSalad, "saltWortSalad");
		GameRegistry.registerItem(carrotPie, "carrotPie");
		GameRegistry.registerItem(applePie, "applePie");
		GameRegistry.registerItem(potatoPie, "potatoPie");
		GameRegistry.registerItem(onionPie, "onionPie");
		GameRegistry.registerItem(fishPie, "fishPie");
		GameRegistry.registerItem(fishSalmonPie, "fishSalmonPie");
		GameRegistry.registerItem(mushroomPie, "mushroomPie");
		GameRegistry.registerItem(saltWortPie, "saltWortPie");
		GameRegistry.registerItem(fermentedSaltWort, "fermentedSaltWort");
		GameRegistry.registerItem(pickledMushroom, "pickledMushroom");
		GameRegistry.registerItem(pickledFern, "pickledFern");
		GameRegistry.registerItem(fizzyDrink, "fizzyDrink");
		GameRegistry.registerItem(muffin, "muffin");
	//Armor
		GameRegistry.registerItem(mudHelmet, "mudHelmet");
		GameRegistry.registerItem(mudChestplate, "mudChestplate");
		GameRegistry.registerItem(mudLeggings, "mudLeggings");
		GameRegistry.registerItem(mudBoots, "mudBoots");
	//Milk
		GameRegistry.registerItem(powderedMilk, "powderedMilk");
		
		GameRegistry.registerItem(escargot, "escargot");
	//Rainmaker
		GameRegistry.registerItem(saltStar, "saltStar");
		GameRegistry.registerItem(rainmaker, "rainmaker");
		SaltMod.logger.info("Finished initializing Items");
    }
}