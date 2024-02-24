package com.kkrotello.setofskills.init;
import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.network.CastKeyMessage;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;



@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})

public class ModKeyMappings {
    public static final KeyMapping CAST_KEY = new KeyMapping("key.set_of_skills.cast_key", GLFW.GLFW_KEY_C, "key.categories.set_of_skills") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                CAST_KEY_LASTPRESS = System.currentTimeMillis();
            } else if (isDownOld != isDown && !isDown) {
                int dt = (int) (System.currentTimeMillis() - CAST_KEY_LASTPRESS);
                SetOfSkills.PACKET_HANDLER.sendToServer(new CastKeyMessage(1, dt));
                CastKeyMessage.pressAction(Minecraft.getInstance().player, 1, dt);
            }
            isDownOld = isDown;
        }
    };
    private static long CAST_KEY_LASTPRESS;
}
