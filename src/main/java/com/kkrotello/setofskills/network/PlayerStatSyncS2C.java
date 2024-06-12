package com.kkrotello.setofskills.network;

import com.kkrotello.setofskills.client.ClientStatData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerStatSyncS2C {
    private final int thirst;

    public PlayerStatSyncS2C(int thirst) {
        this.thirst = thirst;
    }

    public PlayerStatSyncS2C(FriendlyByteBuf buf) {
        this.thirst = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(thirst);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientStatData.copyFrom(thirst);
        });
        return true;
    }
}
