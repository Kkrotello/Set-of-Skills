package com.kkrotello.setofskills.client;

import com.kkrotello.setofskills.SetOfSkills;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class SkillBetaScreen extends AbstractContainerScreen<SkillBeta> {
    private final static HashMap<Integer, Object> guistate = new HashMap<>();
    private final Level world;
    private final Player User;
    private static final ResourceLocation BG_LOCATION = new ResourceLocation(SetOfSkills.MODID, "textures/screens/skill_collection.png");
    private final int startIndex;

    public SkillBetaScreen(SkillBeta pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.world = pMenu.world;
        this.User = pMenu.user;
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.startIndex = 0;
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float partial, int pMouseX, int pMouseY) {
        this.renderBackground(pGuiGraphics);
        int left = this.leftPos;
        int top = this.topPos;
        pGuiGraphics.blit(BG_LOCATION, left, top, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
//        pGuiGraphics.blit(BG_LOCATION, left + 119, top + 15 + $$6, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int padleft = this.leftPos + 15;
        int padtop = this.topPos + 14;
        int last = this.startIndex + 16;
        this.renderButtons(pGuiGraphics, pMouseX, pMouseY, padleft, padtop, last);
        this.renderItems(pGuiGraphics, padleft, padtop, last);
    }

    private void renderItems(GuiGraphics pGuiGraphics, int pleft, int pTop, int LastVisible) {
        for(int index = this.startIndex; index < LastVisible && index < 27; ++index) {
            int count = index - this.startIndex;
            int Xpos = pleft + count % 4 * 16;
            int yMult = count / 4;
            int Ypos = pTop + yMult * 18 + 2;
            pGuiGraphics.renderItem(this.menu.Skills.getItem(index), Xpos, Ypos);
        }
    }

    private void renderButtons(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pleft, int pTop, int pLastVisibleElement) {
        for(int index = this.startIndex; index < pLastVisibleElement; ++index) {
            int count = index - this.startIndex;
            int Xpos = pleft + count % 4 * 16;
            int yMult = count / 4;
            int Ypos = pTop + yMult * 18 + 2;
            int height = this.imageHeight;
//            if (index == ((TestMenu)this.menu).getSelectedRecipeIndex()) {
//                height += 18;
//            } else if (pMouseX >= Xpos && pMouseY >= Ypos && pMouseX < Xpos + 16 && pMouseY < Ypos + 18) {
//                height += 36;
//            }

            pGuiGraphics.blit(BG_LOCATION, Xpos, Ypos - 1, 0, height - 20, 16, 18);
        }

    }
}
