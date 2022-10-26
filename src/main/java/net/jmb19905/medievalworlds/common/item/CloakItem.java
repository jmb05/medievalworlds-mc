package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.renderers.curio.CloakRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CloakItem extends Item implements ICurioItem {

    public static final ResourceLocation NORMAL_TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/cloak.png");
    public static final ResourceLocation DARK_TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/dark_cloak.png");
    public static final ResourceLocation LIGHT_TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/light_cloak.png");


    private final ResourceLocation renderTexture;

    public CloakItem(ResourceLocation renderTexture, Properties properties) {
        super(properties);
        this.renderTexture = renderTexture;
        CuriosRendererRegistry.register(this, CloakRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getRenderTexture() {
        return renderTexture;
    }

    @Nullable
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
}
