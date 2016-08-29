package rreeggkk.nuclearsciences.common.energy;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({
	@Optional.Interface(iface="net.darkhax.tesla.api.ITeslaConsumer", modid="tesla"),
	@Optional.Interface(iface="net.darkhax.tesla.api.ITeslaProducer", modid="tesla"),
	@Optional.Interface(iface="net.darkhax.tesla.api.ITeslaHolder", modid="tesla")
})
public abstract class AEnergyContainer implements ITeslaConsumer, ITeslaProducer, ITeslaHolder, INBTSerializable<NBTTagCompound> {
	
	public AEnergyContainer() {}
	
	public AEnergyContainer(NBTTagCompound nbt) {
		deserializeNBT(nbt);
	}
	
	public abstract double getFraction();
}
