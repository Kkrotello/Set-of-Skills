package com.kkrotello.setofskills.network;

import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.SkillCasterProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class CastKeyMessage {
    int type, pressedms;

    public CastKeyMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public CastKeyMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void buffer(CastKeyMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handler(CastKeyMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            pressAction(context.getSender(), message.type, message.pressedms);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player user, int type, int pressedms) {
        Level world = user.level();
        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        // security measure to prevent arbitrary chunk generation
        if (!world.hasChunkAt(user.blockPosition()))
            return;
        if (type == 1) {

            SkillCasterProcedure.execute(world, user, x, y, z);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        SetOfSkills.addNetworkMessage(CastKeyMessage.class, CastKeyMessage::buffer, CastKeyMessage::new, CastKeyMessage::handler);
    }

}
