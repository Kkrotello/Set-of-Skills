package com.kkrotello.setofskills.network;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel IHANDLER;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SetOfSkills.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        IHANDLER = net;

        net.messageBuilder(SkillCastC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillCastC2S::new)
                .encoder(SkillCastC2S::buffer)
                .consumerMainThread(SkillCastC2S::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        IHANDLER.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        IHANDLER.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
