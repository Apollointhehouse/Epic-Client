package apollointhehouse.epicclient.mixins;

import apollointhehouse.epicclient.IKeybinds;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = GuiIngame.class, remap = false)
public class TileEntitesOverlayMixin {

    @Shadow
    private Minecraft mc;

    @Unique
    private int tsp;

    @Unique
    private int lineHeight;

    @Unique
    private int line;

    private boolean shouldDrawEntityOverlay = true;

    private boolean getViewChestOverlayLast;

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "renderGameOverlay",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/src/GameSettings;showDebugScreen:Lnet/minecraft/src/option/BooleanOption;"
            ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/src/MovingObjectPosition;sideHit:I"),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;fpsInOverlay:Lnet/minecraft/src/option/BooleanOption;")
            )
    )
    private void captureLocals1(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci, @Local(ordinal = 5) int tsp, @Local(ordinal = 6) int line, @Local(ordinal = 7) int lineHeight) {
        this.tsp = tsp;
        this.lineHeight = lineHeight;
        this.line = line;
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "renderGameOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/GuiIngame;drawString(Lnet/minecraft/src/FontRenderer;Ljava/lang/String;III)V"
            ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;showDebugScreen:Lnet/minecraft/src/option/BooleanOption;"),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;armorDurabilityOverlay:Lnet/minecraft/src/option/BooleanOption;")
            )
    )
    private void captureLocals2(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci, @Local(ordinal = 5) int tsp, @Local(ordinal = 6) int line, @Local(ordinal = 7) int lineHeight) {
        this.tsp = tsp;
        this.lineHeight = lineHeight;
        this.line = line;
    }

    private void drawEntityOverlay() {

        GuiIngame guiIngame = (GuiIngame) (Object) this;
        List<TileEntity> loadedTileEntities = mc.theWorld.loadedTileEntityList;
        int chestCount = 0;

        for (int i = 0; i < loadedTileEntities.size(); i++) {
            TileEntity tileEntity = loadedTileEntities.get(i);

            if (tileEntity instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) tileEntity;
                String coordStr = "Chest at (" + chest.xCoord + ", " + chest.yCoord + ", " + chest.zCoord + ")";
                guiIngame.drawString(mc.fontRenderer, coordStr, tsp, tsp + 230 + this.line * 10 + chestCount * 10, 0xFFFFFF);
                chestCount++;
            }
        }

        guiIngame.drawString(mc.fontRenderer, "Found " + chestCount + " chests:", tsp, tsp + 220 + this.line * 10, 0xFFFFFF);
    }

    @Inject(method = "renderGameOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;armorDurabilityOverlay:Lnet/minecraft/src/option/BooleanOption;"))
    private void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.mc.gameSettings.showDebugScreen.value) {
            return;
        }
        boolean getViewChestOverlay = (((IKeybinds) this.mc.gameSettings).getViewChestOverlay().isPressed());

        if (getViewChestOverlay != getViewChestOverlayLast && getViewChestOverlay) shouldDrawEntityOverlay = !shouldDrawEntityOverlay; {
            if (shouldDrawEntityOverlay) drawEntityOverlay();
            getViewChestOverlayLast = getViewChestOverlay;

        }
    }
}
