package rreeggkk.nuclearsciences.proxy;

import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy extends CommonProxy {

	@Override
	public Side getSide() {
		return Side.SERVER;
	}
}
