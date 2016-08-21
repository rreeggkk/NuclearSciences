package io.github.rreeggkk.nuclearsciences.common.util;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityUtil {
    @CapabilityInject(ITeslaConsumer.class)
    public static Capability<ITeslaConsumer> CAPABILITY_CONSUMER = null;
    
    @CapabilityInject(ITeslaProducer.class)
    public static Capability<ITeslaProducer> CAPABILITY_PRODUCER = null;
    
    @CapabilityInject(ITeslaHolder.class)
    public static Capability<ITeslaHolder> CAPABILITY_HOLDER = null;
}
