package com.kkrotello.setofskills.network;

import com.kkrotello.setofskills.SkillCasterProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillCastC2S {
    int type, pressedms;
    public SkillCastC2S (){    }
    public SkillCastC2S (int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public SkillCastC2S(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }
    public static void buffer(SkillCastC2S message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }
    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            pressAction(context.getSender(), type, pressedms);
        });
        return true;
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
}
