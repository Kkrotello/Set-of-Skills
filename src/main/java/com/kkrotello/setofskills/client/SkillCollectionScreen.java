package com.kkrotello.setofskills.client;
import com.kkrotello.setofskills.SetOfSkills;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class SkillCollectionScreen extends AbstractContainerScreen<SkillCollectionMenu>{

    private final static HashMap<Integer, Object> guistate = new HashMap<>();
    private final static HashMap<Integer, Object> buttonstate = new HashMap<>();
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    Button button_1;

    public SkillCollectionScreen(SkillCollectionMenu container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.user;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation(SetOfSkills.MODID, "textures/screens/skill_collection.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
//        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void init() {
        super.init();
        button_1 = Button.builder(Component.literal("e"), e -> {
            if (true) {
                buttonstate.put(1, true);
            }
        }).bounds(this.leftPos + 46, this.topPos + 57, 30, 20).build();

        guistate.put(1, button_1);
        this.addRenderableWidget(button_1);

    }

}
