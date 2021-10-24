package net.jmb19905.medievalworlds.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.LanceItem;
import net.jmb19905.medievalworlds.common.registries.ContainerTypeRegistryHandler;
import net.jmb19905.medievalworlds.client.screen.AlloyFurnaceScreen;
import net.jmb19905.medievalworlds.client.screen.BloomeryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.OverlayRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    private static ModelRenderer customArm;
    private static float counter = 0;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt){
        ScreenManager.registerFactory(ContainerTypeRegistryHandler.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(ContainerTypeRegistryHandler.BLOOMERY.get(), BloomeryScreen::new);
        System.out.println("Client setup");
    }

    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event){
        PlayerRenderer renderer = event.getRenderer();
        ClientPlayerEntity player = (ClientPlayerEntity) event.getPlayer();
        if(player.getActiveHand() == Hand.MAIN_HAND) {
            if (player.getActiveItemStack().getItem() instanceof LanceItem) {
                renderer.getEntityModel().bipedRightArm.showModel = false;
                renderer.getEntityModel().bipedRightArmwear.showModel = false;
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerPost(RenderPlayerEvent.Post event){
        PlayerRenderer renderer = event.getRenderer();
        ClientPlayerEntity player = (ClientPlayerEntity) event.getPlayer();
        if(player.getActiveHand() == Hand.MAIN_HAND) {
            if (player.getActiveItemStack().getItem() instanceof LanceItem) {
                renderer.getEntityModel().bipedRightArm.showModel = true;
                //renderer.getEntityModel().bipedRightArmwear.showModel = true;

                renderer.getEntityModel().bipedRightArm.rotationPointX = -MathHelper.cos((float) Math.toRadians(player.renderYawOffset)) * 4.2f;
                renderer.getEntityModel().bipedRightArm.rotationPointY = player.isSneaking() ? 16.5f : 20.5f;
                renderer.getEntityModel().bipedRightArm.rotationPointZ = -MathHelper.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0f;
                renderer.getEntityModel().bipedRightArm.rotateAngleX = (float) Math.toRadians(180.0f);
                renderer.getEntityModel().bipedRightArm.rotateAngleY = (float) -Math.toRadians(player.renderYawOffset);
                renderer.getEntityModel().bipedRightArm.rotateAngleZ = 0.0f;
                renderer.getEntityModel().bipedRightArm.render(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getEntitySolid(player.getLocationSkin())), event.getLight(), OverlayTexture.NO_OVERLAY);
                renderer.getEntityModel().bipedRightArm.rotationPointY = 2.0f;

                renderer.getEntityModel().bipedRightArmwear.rotationPointX = -MathHelper.cos((float) Math.toRadians(player.renderYawOffset)) * 4.2f;
                renderer.getEntityModel().bipedRightArmwear.rotationPointY = player.isSneaking() ? 16.5f : 20.5f;
                renderer.getEntityModel().bipedRightArmwear.rotationPointZ = -MathHelper.sin((float) Math.toRadians(player.renderYawOffset)) * 5.0f;
                renderer.getEntityModel().bipedRightArmwear.rotateAngleX = (float) Math.toRadians(180.0f);
                renderer.getEntityModel().bipedRightArmwear.rotateAngleY = (float) -Math.toRadians(player.renderYawOffset);
                renderer.getEntityModel().bipedRightArmwear.rotateAngleZ = 0.0f;
                renderer.getEntityModel().bipedRightArmwear.render(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getEntitySolid(player.getLocationSkin())), event.getLight(), OverlayTexture.NO_OVERLAY);
                renderer.getEntityModel().bipedRightArmwear.rotationPointY = 2.0f;
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerHand(RenderHandEvent event){
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.renderArmYaw = 90;
        Minecraft.getInstance().player.renderArmPitch = 90;
        Minecraft.getInstance().player.renderArmPitch = (float)((double)Minecraft.getInstance().player.renderArmPitch + (double)(Minecraft.getInstance().player.rotationPitch - Minecraft.getInstance().player.renderArmPitch) * 0.5D);
        Minecraft.getInstance().player.renderArmYaw = (float)((double)Minecraft.getInstance().player.renderArmYaw + (double)(Minecraft.getInstance().player.rotationYaw - Minecraft.getInstance().player.renderArmYaw) * 0.5D);
    }

}
