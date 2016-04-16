package ru.liahim.saltmod.common;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import ru.liahim.saltmod.SaltMod;
import ru.liahim.saltmod.api.ExtractRegistry;
import ru.liahim.saltmod.dispenser.DispenserBehaviorRainmaiker;
import ru.liahim.saltmod.dispenser.DispenserBehaviorSaltPinch;
import ru.liahim.saltmod.entity.EntityRainmaker;
import ru.liahim.saltmod.entity.EntityRainmakerDust;
import ru.liahim.saltmod.init.AchievSalt;
import ru.liahim.saltmod.init.ClientRegister;
import ru.liahim.saltmod.init.MilkBucketRecipe;
import ru.liahim.saltmod.init.ModBlocks;
import ru.liahim.saltmod.init.ModItems;
import ru.liahim.saltmod.init.SaltConfig;
import ru.liahim.saltmod.inventory.gui.GuiExtractorHandler;
import ru.liahim.saltmod.network.ExtractorButtonMessage;
import ru.liahim.saltmod.network.SaltModEvent;
import ru.liahim.saltmod.network.SaltWortMessage;
import ru.liahim.saltmod.tileEntity.TileEntityExtractor;
import ru.liahim.saltmod.world.SaltCrystalGenerator;
import ru.liahim.saltmod.world.SaltLakeGenerator;
import ru.liahim.saltmod.world.SaltOreGenerator;

public class CommonProxy {
	
	public static CreativeTabs saltTab = new SaltTab("saltTab");

	public static SaltOreGenerator saltOreGenerator = new SaltOreGenerator();
	public static SaltCrystalGenerator saltCrystalGenerator = new SaltCrystalGenerator();
	public static SaltLakeGenerator saltLakeGenerator = new SaltLakeGenerator();
	
	public static ArmorMaterial mudMaterial = EnumHelper.addArmorMaterial("mudMaterial", "saltmod:MudArmor", 4, new int[] {1, 1, 1, 1}, 15);
	
	public static Fluid milk;
	
	public static SimpleNetworkWrapper network;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new SaltModEvent());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(SaltMod.instance, new GuiExtractorHandler());
		network = NetworkRegistry.INSTANCE.newSimpleChannel(SaltMod.MODID);
	    network.registerMessage(ExtractorButtonMessage.Handler.class, ExtractorButtonMessage.class, 0, Side.SERVER);
	    network.registerMessage(SaltWortMessage.Handler.class, SaltWortMessage.class, 1, Side.CLIENT);	
	}

	public void init(FMLInitializationEvent event)
	{
	    AchievSalt.init();
	    
	    GameRegistry.registerTileEntity(TileEntityExtractor.class, "tileEntityExtractor");
	    EntityRegistry.registerModEntity(EntityRainmaker.class, "entityRainmaker", 0, SaltMod.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityRainmakerDust.class, "entityRainmakerDust", 1, SaltMod.instance, 64, 20, false);		
	    BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.rainmaker, new DispenserBehaviorRainmaiker());
	    BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.saltPinch, new DispenserBehaviorSaltPinch());
	    
		GameRegistry.registerWorldGenerator(saltOreGenerator, 0);
		GameRegistry.registerWorldGenerator(saltCrystalGenerator, 10);
		GameRegistry.registerWorldGenerator(saltLakeGenerator, 15);
		
	//Recipe
		ExtractRegistry.instance().addExtracting(FluidRegistry.WATER, ModItems.saltPinch, 1000, 0.0F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 9), new ItemStack(ModItems.salt));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 7));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBlock, 1, 9));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltLamp));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.salt, 9), new ItemStack(ModBlocks.saltBrickStair));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 40), new ItemStack(ModBlocks.saltSlab, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 40), new ItemStack(ModBlocks.saltSlab, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch, 40), new ItemStack(ModBlocks.saltSlab, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPinch), new ItemStack(ModBlocks.saltCrystal));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.saltDirt), new ItemStack(ModItems.salt), new ItemStack(Blocks.dirt));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.saltDirt), new ItemStack(ModBlocks.saltDirtLite), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 2), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fizzyDrink), new ItemStack(ModItems.soda), new ItemStack(Items.potionitem));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.potionitem), new ItemStack(Items.glass_bottle), new ItemStack(Items.snowball));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mineralMud), new ItemStack(ModItems.soda), new ItemStack(ModItems.salt), new ItemStack(Items.coal), new ItemStack(Items.clay_ball));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mineralMud), new ItemStack(ModItems.soda), new ItemStack(ModItems.salt), new ItemStack(Items.coal, 1, 1), new ItemStack(Items.clay_ball));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mineralMud, 4), new ItemStack(ModBlocks.mudBlock));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBeefCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_beef));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPorkchopCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_porkchop));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoBaked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.baked_potato));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltChickenCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_chicken));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishCod), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(Items.fish));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishCodCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_fish));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmon), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(Items.fish, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmonCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_fish, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishClownfish), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.saltPinch), new ItemStack(Items.fish, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltMushroomStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.mushroom_stew));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltMushroomStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.bowl), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltBread), new ItemStack(ModItems.saltPinch), new ItemStack(Items.bread));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltEgg), new ItemStack(ModItems.saltPinch), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pumpkinPorridge), new ItemStack(Items.bowl), new ItemStack(Blocks.pumpkin));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.vegetableStew), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Blocks.brown_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.vegetableStew), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltVegetableStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Blocks.brown_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltVegetableStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltVegetableStew), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.vegetableStew));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.potatoMushroom), new ItemStack(Items.bowl), new ItemStack(Items.potato), new ItemStack(Items.potato), new ItemStack(Blocks.brown_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.potatoMushroom), new ItemStack(Items.bowl), new ItemStack(Items.potato), new ItemStack(Items.potato), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(Items.bowl), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potato), new ItemStack(Items.potato), new ItemStack(Blocks.brown_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(Items.bowl), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potato), new ItemStack(Items.potato), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(ModItems.potatoMushroom), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltPotatoMushroom), new ItemStack(ModItems.potatoMushroom), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishSoup), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Items.fish));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSoup), new ItemStack(Items.bowl), new ItemStack(ModItems.saltPinch), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Items.fish));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSoup), new ItemStack(ModItems.fishSoup), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishSalmonSoup), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Items.fish, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmonSoup), new ItemStack(Items.bowl), new ItemStack(ModItems.saltPinch), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(Items.fish, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltFishSalmonSoup), new ItemStack(ModItems.fishSalmonSoup), new ItemStack(ModItems.saltPinch));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortBeef), new ItemStack(Items.bowl), new ItemStack(Items.cooked_beef), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortPorkchop), new ItemStack(Items.bowl), new ItemStack(Items.cooked_porkchop), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortMutton), new ItemStack(Items.bowl), new ItemStack(Items.cooked_mutton), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.dandelionSalad), new ItemStack(Items.bowl), new ItemStack(Items.wheat_seeds), new ItemStack(Blocks.yellow_flower), new ItemStack(Blocks.red_flower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltDandelionSalad), new ItemStack(ModItems.saltPinch), new ItemStack(Items.bowl), new ItemStack(Items.wheat_seeds), new ItemStack(Blocks.yellow_flower), new ItemStack(Blocks.red_flower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltDandelionSalad), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.dandelionSalad));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.wheatSprouts), new ItemStack(Items.bowl), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWheatSprouts), new ItemStack(ModItems.saltPinch), new ItemStack(Items.bowl), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWheatSprouts), new ItemStack(ModItems.saltPinch), new ItemStack(ModItems.wheatSprouts));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fruitSalad), new ItemStack(Items.bowl), new ItemStack(Items.apple), new ItemStack(Items.carrot), new ItemStack(Items.melon));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gratedCarrot), new ItemStack(Items.bowl), new ItemStack(Items.carrot), new ItemStack(Items.carrot), new ItemStack(Items.sugar));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.carrotPie), new ItemStack(Items.carrot), new ItemStack(Items.carrot), new ItemStack(Items.sugar), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.applePie), new ItemStack(Items.apple), new ItemStack(Items.apple), new ItemStack(Items.sugar), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.potatoPie), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potato), new ItemStack(Items.potato), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.onionPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishPie), new ItemStack(ModItems.saltPinch), new ItemStack(Items.wheat), new ItemStack(Items.fish), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fishSalmonPie), new ItemStack(ModItems.saltPinch), new ItemStack(Items.wheat), new ItemStack(Items.fish, 1, 1), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mushroomPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mushroomPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.mushroomPie), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.red_mushroom), new ItemStack(Blocks.red_mushroom), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledMushroom), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potionitem), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.brown_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledMushroom), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potionitem), new ItemStack(Blocks.red_mushroom), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledMushroom), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potionitem), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickledFern), new ItemStack(ModItems.saltPinch), new ItemStack(Items.potionitem), new ItemStack(Blocks.tallgrass, 1, 2), new ItemStack(Blocks.tallgrass, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortPie), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(Items.wheat), new ItemStack(Items.egg));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltWortSalad), new ItemStack(Items.bowl), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fermentedSaltWort), new ItemStack(Items.glass_bottle), new ItemStack(Items.ghast_tear), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed), new ItemStack(ModItems.saltWortSeed));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.muffin), new ItemStack(ModItems.soda), new ItemStack(Items.egg), new ItemStack(Items.wheat), new ItemStack(Items.dye, 1, 3));
		GameRegistry.addRecipe(new MilkBucketRecipe());
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltStar), new ItemStack(Items.gunpowder), new ItemStack(ModItems.salt), new ItemStack(ModItems.salt), new ItemStack(ModItems.salt), new ItemStack(ModItems.salt), new ItemStack(ModItems.soda), new ItemStack(ModItems.soda), new ItemStack(ModItems.soda), new ItemStack(ModItems.soda));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rainmaker), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(ModItems.saltStar), new ItemStack(Items.paper), new ItemStack(Items.gunpowder), new ItemStack(Items.gunpowder), new ItemStack(Items.gunpowder));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltMuttonCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_mutton));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitCooked), new ItemStack(ModItems.saltPinch), new ItemStack(Items.cooked_rabbit));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(ModItems.saltPinch), new ItemStack(Items.rabbit_stew));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.bowl), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.red_mushroom), new ItemStack(Items.cooked_rabbit), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.bowl), new ItemStack(ModItems.saltPinch), new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.cooked_rabbit), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.bowl), new ItemStack(Blocks.red_mushroom), new ItemStack(ModItems.saltRabbitCooked), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.bowl), new ItemStack(Blocks.brown_mushroom), new ItemStack(ModItems.saltRabbitCooked), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.bowl), new ItemStack(Blocks.red_mushroom), new ItemStack(Items.cooked_rabbit), new ItemStack(Items.carrot), new ItemStack(ModItems.saltPotatoBaked));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.saltRabbitStew), new ItemStack(Items.bowl), new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.cooked_rabbit), new ItemStack(Items.carrot), new ItemStack(ModItems.saltPotatoBaked));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.salt), "xxx", "xxx", "xxx", 'x', ModItems.saltPinch);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock), "xxx", "xxx", "xxx", 'x', ModItems.salt);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltLamp), "x", "y", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0), 'y', Blocks.torch);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 4, 5), "xx", "xx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 2, 2), "x", "x", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.saltSlab, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 1, 8), "x", "x", 'x', new ItemStack(ModBlocks.saltSlab, 1, 1));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBlock, 1, 9), "x", "x", 'x', new ItemStack(ModBlocks.saltSlab, 1, 2));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltBrickStair, 6), "  x", " xx", "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltSlab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltSlab, 6, 1), "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.saltSlab, 6, 2), "xxx", 'x', new ItemStack(ModBlocks.saltBlock, 1, 2));		
		GameRegistry.addRecipe(new ItemStack(ModItems.cornedBeef), "xxx", "xyx", "xxx", 'x', ModItems.saltPinch, 'y', Items.rotten_flesh);
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.mudBlock), "xx", "xx", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudHelmet), "xxx", "x x", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudChestplate), "x x", "xxx", "xxx", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudLeggings), "xxx", "x x", "x x", 'x', ModItems.mineralMud);
		GameRegistry.addRecipe(new ItemStack(ModItems.mudBoots), "x x", "x x", 'x', ModItems.mineralMud);

		GameRegistry.addRecipe(new ItemStack(ModBlocks.extractor), "xyx", "x x", "xxx", 'x', Blocks.cobblestone, 'y', Items.cauldron);
		
		GameRegistry.addSmelting(ModBlocks.saltOre, new ItemStack(ModItems.salt, 1), 0.7F);
		GameRegistry.addSmelting(ModBlocks.saltLake, new ItemStack(ModItems.salt, 1), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.saltBlock, 1, 0), new ItemStack(ModBlocks.saltBlock, 1, 6), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.saltBlock, 1, 5), new ItemStack(ModBlocks.saltBlock, 1, 7), 0.0F);
		GameRegistry.addSmelting(ModItems.saltWortSeed, new ItemStack(ModItems.soda, 1), 0.0F);
		
	//Chest Content
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.salt), 2, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.salt), 2, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.saltWortSeed), 2, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.salt), 2, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(ModItems.saltWortSeed), 2, 5, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.salt), 2, 5, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.salt), 2, 5, 10));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.saltWortSeed), 2, 3, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.saltWortSeed), 2, 5, 5));

	//OreDictionary
		OreDictionary.registerOre("oreSalt", ModBlocks.saltOre);
		OreDictionary.registerOre("blockSalt", ModBlocks.saltBlock);
		OreDictionary.registerOre("blockSaltCrystal", ModBlocks.saltCrystal);
		OreDictionary.registerOre("lumpSalt", ModItems.salt);
		OreDictionary.registerOre("dustSalt", ModItems.saltPinch);
		OreDictionary.registerOre("dustSoda", ModItems.soda);
		OreDictionary.registerOre("dustMilk", ModItems.powderedMilk);
		OreDictionary.registerOre("cropSaltwort", ModItems.saltWortSeed);
		OreDictionary.registerOre("materialMineralMud", ModItems.mineralMud);
	}
	
    public void postInit(FMLPostInitializationEvent event)
    {
	//Milk
    	if (FluidRegistry.isFluidRegistered("milk"))
    	{
    		Fluid milk = FluidRegistry.getFluid("milk");
    		ExtractRegistry.instance().addExtracting(milk, ModItems.powderedMilk, 1000, 0.0F);
    	}    	
    	else
    	{
    		milk = new Fluid("milk", new ResourceLocation("saltmod:blocks/Milk"), new ResourceLocation("saltmod:blocks/Milk"));
    		FluidRegistry.registerFluid(milk);
    		FluidContainerRegistry.registerFluidContainer(new FluidStack(milk, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Items.milk_bucket), FluidContainerRegistry.EMPTY_BUCKET);
    		ExtractRegistry.instance().addExtracting(milk, ModItems.powderedMilk, 1000, 0.0F);
    	}
	//Blood
    	if (FluidRegistry.isFluidRegistered("blood"))
    	{
    		Fluid blood = FluidRegistry.getFluid("blood");
    		GameRegistry.registerItem(ModItems.hemoglobin, "hemoglobin");
    		ClientRegister.registerItems(ModItems.hemoglobin);
    		ExtractRegistry.instance().addExtracting(blood, ModItems.hemoglobin, 1000, 1.0F);
    	}
	//BOP
    	Item saladveggie = GameRegistry.findItem("BiomesOPlenty", "saladveggie");
    	if (saladveggie != null) {
    		GameRegistry.registerItem(SaltConfig.bop_saltSaladVeggie, "bop_saltSaladVeggie");
    		ClientRegister.registerItems(SaltConfig.bop_saltSaladVeggie);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltSaladVeggie), new ItemStack(saladveggie), new ItemStack(ModItems.saltPinch));
		}
    	Item saladshroom = GameRegistry.findItem("BiomesOPlenty", "saladshroom");
    	if (saladshroom != null) {
    		GameRegistry.registerItem(SaltConfig.bop_saltSaladShroom, "bop_saltSaladShroom");
    		ClientRegister.registerItems(SaltConfig.bop_saltSaladShroom);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltSaladShroom), new ItemStack(saladshroom), new ItemStack(ModItems.saltPinch));
		}
    	Item ricebowl = GameRegistry.findItem("BiomesOPlenty", "ricebowl");
    	if (ricebowl != null) {
    		GameRegistry.registerItem(SaltConfig.bop_saltRiceBowl, "bop_saltRiceBowl");
    		ClientRegister.registerItems(SaltConfig.bop_saltRiceBowl);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltRiceBowl), new ItemStack(ricebowl), new ItemStack(ModItems.saltPinch));
		}
    	Item turnip = GameRegistry.findItem("BiomesOPlenty", "turnip");
    	if (turnip != null) {
    		GameRegistry.registerItem(SaltConfig.bop_pickledTurnip, "bop_pickledTurnip");
    		ClientRegister.registerItems(SaltConfig.bop_pickledTurnip);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_pickledTurnip), new ItemStack(turnip), new ItemStack(turnip), new ItemStack(Items.potionitem), new ItemStack(ModItems.saltPinch));
		}
    	Item shroompowder = GameRegistry.findItem("BiomesOPlenty", "shroompowder");
    	if (shroompowder != null) {
    		GameRegistry.registerItem(SaltConfig.bop_saltShroomPowder, "bop_saltShroomPowder");
    		ClientRegister.registerItems(SaltConfig.bop_saltShroomPowder);
			GameRegistry.addShapelessRecipe(new ItemStack(SaltConfig.bop_saltShroomPowder), new ItemStack(shroompowder), new ItemStack(ModItems.saltPinch));
		}
    	Item bop_dart = GameRegistry.findItem("BiomesOPlenty", "dart");
        ItemStack bop_poisondart = new ItemStack(bop_dart, 1, 1);
		if (bop_dart != null && FluidRegistry.isFluidRegistered("poison")) {
			Fluid poisonFl = FluidRegistry.getFluid("poison");
    		GameRegistry.registerItem(SaltConfig.bop_poison, "bop_poison");
    		ClientRegister.registerItems(SaltConfig.bop_poison);
			ExtractRegistry.instance().addExtracting(poisonFl, SaltConfig.bop_poison, 1000, 1.0F);
			GameRegistry.addShapelessRecipe(bop_poisondart, new ItemStack(bop_dart), SaltConfig.bop_poison);
		}
    }
}