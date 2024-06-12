package com.kkrotello.setofskills.events;

import com.kkrotello.setofskills.SetOfSkills;
import com.kkrotello.setofskills.capabilities.PlayerStatProvider;
import com.kkrotello.setofskills.capabilities.PlayerStats;
import com.kkrotello.setofskills.capabilities.SkillList;
import com.kkrotello.setofskills.capabilities.SkillListProvider;
import com.kkrotello.setofskills.network.ModMessages;
import com.kkrotello.setofskills.network.PlayerStatSyncS2C;
import com.kkrotello.setofskills.skill.GrabNThrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = SetOfSkills.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(SkillListProvider.SKILL_LIST).isPresent()) {
                event.addCapability(new ResourceLocation(SetOfSkills.MODID, "properties"), new SkillListProvider());
            }
            if(!event.getObject().getCapability(PlayerStatProvider.PlAYER_STATS).isPresent()) {
                event.addCapability(new ResourceLocation(SetOfSkills.MODID, "stats"), new PlayerStatProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            Player og = event.getOriginal();
            Player now = event.getEntity();
            og.reviveCaps();
            SkillList nowskill = SkillListProvider.getCap(now);
            SkillList ogskill = SkillListProvider.getCap(og);
            nowskill.copyFrom(ogskill);
            PlayerStats nowstats = PlayerStatProvider.getCap(now);
            PlayerStats ogstats = PlayerStatProvider.getCap(og);
            nowstats.copyFrom(ogstats);
            og.invalidateCaps();

//            event.getOriginal().getCapability(SkillListProvider.SKILL_LIST).ifPresent(oldStore -> {
//                event.getOriginal().getCapability(SkillListProvider.SKILL_LIST).ifPresent(newStore -> {
//                    newStore.copyFrom(oldStore);
//                });
//            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SkillList.class);
        event.register(PlayerStats.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            Player player = event.player;
            GrabNThrow.tick(player);
//            event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
//                if(thirst.getThirst() > 0 && event.player.getRandom().nextFloat() < 0.005f) { // Once Every 10 Seconds on Avg
//                    thirst.subThirst(1);
//                    event.player.sendSystemMessage(Component.literal("Subtracted Thirst"));
//                }
//            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerStatProvider.PlAYER_STATS).ifPresent(playerStats -> {
                    ModMessages.sendToPlayer(new PlayerStatSyncS2C(playerStats.strength), player);
                });
            }
        }
    }
}
