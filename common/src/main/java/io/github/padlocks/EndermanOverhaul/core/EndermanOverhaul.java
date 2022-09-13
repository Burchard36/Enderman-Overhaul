package io.github.padlocks.EndermanOverhaul.core;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.EntityRendererRegistry;
import io.github.padlocks.EndermanOverhaul.client.render.entity.*;
import io.github.padlocks.EndermanOverhaul.common.registry.ModEntities;
import io.github.padlocks.EndermanOverhaul.common.registry.ModItems;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.logging.Logger;
import net.minecraft.util.Identifier;

public class EndermanOverhaul {
    public static final String MOD_ID = "endermanoverhaul";
    public static final Logger logger = Logger.getLogger("EndermanOverhaul");

    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(() -> EndermanOverhaul::onClientInit)
            .commonInit(EndermanOverhaul::onCommonInit)
            .build();

    private static void onClientInit() {
        EntityRendererRegistry.register(ModEntities.DEFAULT_ENDERMAN, EndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.BADLANDS_ENDERMAN, BadlandsEndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAVE_ENDERMAN, CaveEndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLOWER_ENDERMAN, FlowerEndermanRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(new BadlandsHoodRenderer(), ModItems.BADLANDS_HOOD);
    }

    private static void onCommonInit() {
        ModItems.ITEMS.register(PLATFORM);
        ModEntities.ENTITIES.register(PLATFORM);
        ModEntities.registerEntityAttributes();
        GeckoLib.initialize();
    }

    public static Identifier resourceLocation(String string) {
        return new Identifier(MOD_ID, string);
    }
}
