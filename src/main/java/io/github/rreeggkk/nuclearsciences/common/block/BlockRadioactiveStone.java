package io.github.rreeggkk.nuclearsciences.common.block;

import io.github.rreeggkk.nuclearsciences.NuclearSciences;
import io.github.rreeggkk.nuclearsciences.common.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRadioactiveStone extends Block {
	public BlockRadioactiveStone() {
		super(Material.ROCK);
		
		setUnlocalizedName(Constants.MOD_ID + ".radioactiveStone");
		setRegistryName("radioactiveStone");
		setCreativeTab(NuclearSciences.instance.tab);
		
		setLightLevel(4/15f);
		setHardness(1.5F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}
	
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
