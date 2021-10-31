package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ber.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.common.item.lance.LanceItem;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEventBusSubscriber {

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents{

        @SubscribeEvent
        public static void registerERs(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MWBlockEntityTypes.CUSTOM_ANVIL.get(), CustomAnvilRenderer::new);
        }

    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
            PlayerRenderer renderer = event.getRenderer();
            AbstractClientPlayer player = (AbstractClientPlayer) event.getPlayer();
            if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                if (player.getUseItem().getItem() instanceof LanceItem) {
                    renderer.getModel().rightArm.visible = false;
                    renderer.getModel().rightSleeve.visible = false;
                }
            }
        }

        @SubscribeEvent
        public static void renderPlayerPost(RenderPlayerEvent.Post event) {
            PlayerRenderer renderer = event.getRenderer();
            AbstractClientPlayer player = (AbstractClientPlayer) event.getPlayer();
            if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                if (player.getUseItem().getItem() instanceof LanceItem) {
                    renderer.getModel().rightArm.visible = true;
                    //renderer.getModel().rightSleeve.visible = true;

                    renderer.getModel().rightArm.x = -Mth.cos((float) Math.toRadians(player.yBodyRot)) * 4.2f;
                    renderer.getModel().rightArm.y = player.isCrouching() ? 16.5f : 20.5f;
                    renderer.getModel().rightArm.z = -Mth.sin((float) Math.toRadians(player.yBodyRot)) * 5.0f;
                    renderer.getModel().rightArm.xRot = (float) Math.toRadians(180.0f);
                    renderer.getModel().rightArm.yRot = (float) -Math.toRadians(player.yBodyRot);
                    renderer.getModel().rightArm.zRot = 0.0f;
                    renderer.getModel().rightArm.render(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), event.getLight(), OverlayTexture.NO_OVERLAY);
                    renderer.getModel().rightArm.y = 2.0f;

                    renderer.getModel().rightSleeve.x = -Mth.cos((float) Math.toRadians(player.yBodyRot)) * 4.2f;
                    renderer.getModel().rightSleeve.y = player.isCrouching() ? 16.5f : 20.5f;
                    renderer.getModel().rightSleeve.z = -Mth.sin((float) Math.toRadians(player.yBodyRot)) * 5.0f;
                    renderer.getModel().rightSleeve.xRot = (float) Math.toRadians(180.0f);
                    renderer.getModel().rightSleeve.yRot = (float) -Math.toRadians(player.yBodyRot);
                    renderer.getModel().rightSleeve.zRot = 0.0f;
                    renderer.getModel().rightSleeve.render(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), event.getLight(), OverlayTexture.NO_OVERLAY);
                    renderer.getModel().rightSleeve.y = 2.0f;
                }
            }
        }

        @SubscribeEvent
        public static void renderPlayerHand(RenderHandEvent event) {

        }
    }
}
