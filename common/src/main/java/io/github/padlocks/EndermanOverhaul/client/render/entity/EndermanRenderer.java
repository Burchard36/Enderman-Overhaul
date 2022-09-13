package io.github.padlocks.EndermanOverhaul.client.render.entity;

import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class EndermanRenderer extends AnimatedEntityRenderer<BaseEnderman> {
    public static final Identifier ENDERMAN_LOCATION = new Identifier(EndermanOverhaul.MOD_ID, "default_enderman");
    private static final Identifier[] WALK_ANIMATION = new Identifier[]{new Identifier(EndermanOverhaul.MOD_ID, "enderman.setup"), new Identifier(EndermanOverhaul.MOD_ID, "enderman.walk")};
    private static final Identifier[] RUN_ANIMATION = new Identifier[]{new Identifier(EndermanOverhaul.MOD_ID, "enderman.setup"), new Identifier(EndermanOverhaul.MOD_ID, "enderman.run")};
    private static final Identifier[] ANGRY_ANIMATION = new Identifier[]{new Identifier(EndermanOverhaul.MOD_ID, "enderman.setup"), new Identifier(EndermanOverhaul.MOD_ID, "enderman.angry")};
    private static final Identifier[] ANGRY_RUN_ANIMATION = new Identifier[]{new Identifier(EndermanOverhaul.MOD_ID, "enderman.setup"), new Identifier(EndermanOverhaul.MOD_ID, "enderman.angry_run")};
    private static final Identifier[] IDLE_ANIMATION = new Identifier[]{new Identifier(EndermanOverhaul.MOD_ID, "enderman.setup"), new Identifier(EndermanOverhaul.MOD_ID, "enderman.idle")};

    private boolean isMoving = true;

    public EndermanRenderer(EntityRendererFactory.Context context) {
        super(context, new Identifier(EndermanOverhaul.MOD_ID, "default_enderman"), 1.0F);
    }

    @Override
    public Identifier[] getAnimations(BaseEnderman entity) {
        if (entity.hasAngerTime() && !isMoving)
            return ANGRY_ANIMATION;
        else if (entity.hasAngerTime() && isMoving)
            return ANGRY_RUN_ANIMATION;
        if (isMoving)
            return WALK_ANIMATION;
        else if (entity.isNoAnimationPlaying()) {
            return IDLE_ANIMATION;
        }
        return super.getAnimations(entity);
    }

    @Override
    public Identifier getTextureTableLocation(BaseEnderman entity) {
        return ENDERMAN_LOCATION;
    }

    @Override
    public void render(BaseEnderman entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight) {
        matrixStack.push();

        float limbSwingAmount = 0.0F;
        if (entity.isAlive()) {
            limbSwingAmount = MathHelper.lerp(partialTicks, entity.lastLimbDistance, entity.limbDistance);

            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }
        this.isMoving = !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F);

        matrixStack.pop();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public Identifier getTexture(BaseEnderman entity) {
        return ENDERMAN_LOCATION;
    }
}
