package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;

public class BlockNSBase extends Block{

	public BlockNSBase(Material materialIn, String name) {
		this(materialIn, materialIn.getMaterialMapColor(), name);
	}

	public BlockNSBase(Material blockMaterialIn, MapColor blockMapColorIn, String name) {
		super(blockMaterialIn, blockMapColorIn);

		setUnlocalizedName(Constants.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(NuclearSciences.instance.tab);
		
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		
		if (NuclearSciences.proxy.getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		}
	}
}
