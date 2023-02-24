package apollointhehouse.epicclient.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.ItemStack;
import net.minecraft.src.helper.Utils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import apollointhehouse.epicclient.EpicClient;
import apollointhehouse.epicclient.IKeybinds;
@Mixin(value = GuiIngame.class, remap = false)
public class SpeedOverlayMixin {

    @Shadow
    private Minecraft mc;

    @Unique
    private int tsp;

    @Unique
    private int lineHeight;

    @Unique
    private int lineHeightX;

    @Unique
    private int lineHeightY;

    @Unique
    private int lineHeightZ;

    @Unique
    private int line;

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
    private void captureLocals1(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci,  @Local(ordinal = 5) int tsp, @Local(ordinal = 6) int line, @Local(ordinal = 7) int lineHeight, @Local(ordinal = 8) int lineHeightX, @Local(ordinal = 9) int lineHeightY, @Local(ordinal = 10) int lineHeightZ) {
        this.tsp = tsp;
        this.lineHeight = lineHeight;
        this.lineHeightX = lineHeightX;
        this.lineHeightY = lineHeightY;
        this.lineHeightZ = lineHeightZ;
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
    private void captureLocals2(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci,  @Local(ordinal = 5) int tsp, @Local(ordinal = 6) int line, @Local(ordinal = 7) int lineHeight, @Local(ordinal = 8) int lineHeightX, @Local(ordinal = 9) int lineHeightY, @Local(ordinal = 10) int lineHeightZ) {
        this.tsp = tsp;
        this.lineHeight = lineHeight;
        this.lineHeightX = lineHeightX;
        this.lineHeightY = lineHeightY;
        this.lineHeightZ = lineHeightZ;
        this.line = line;
    }

    @Inject(method = "renderGameOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;armorDurabilityOverlay:Lnet/minecraft/src/option/BooleanOption;"))
    private void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.mc.gameSettings.showDebugScreen.value) {
            return;
        }

        boolean renderSpeedometer = true;
        if (renderSpeedometer) {
            GuiIngame guiIngame = (GuiIngame) (Object) this;
            guiIngame.drawString(this.mc.fontRenderer, "Speed: " + calculateSpeed(this.mc.thePlayer), this.tsp, this.tsp + this.lineHeight * this.line, 0xFFFFFF);
        }
        if (renderSpeedometer) {
            GuiIngame guiIngame = (GuiIngame) (Object) this;
            guiIngame.drawString(this.mc.fontRenderer, "X Velocity: " + calculateXVelocity(this.mc.thePlayer), this.tsp, this.tsp + this.lineHeightX * this.line, 0xFFFFFF);
        }
        if (renderSpeedometer) {
            GuiIngame guiIngame = (GuiIngame) (Object) this;
            guiIngame.drawString(this.mc.fontRenderer, "Y Velocity: " + calculateYVelocity(this.mc.thePlayer), this.tsp, this.tsp + this.lineHeightY * this.line, 0xFFFFFF);
        }
        if (renderSpeedometer) {
            GuiIngame guiIngame = (GuiIngame) (Object) this;
            guiIngame.drawString(this.mc.fontRenderer, "Z Velocity: " + calculateZVelocity(this.mc.thePlayer), this.tsp, this.tsp + this.lineHeightZ * this.line, 0xFFFFFF);
        }
    }

    private double calculateSpeed(EntityPlayer player) {
        double x = (player.posX - player.lastTickPosX) * 20;
        double y = (player.posY - player.lastTickPosY) * 20;
        double z = (player.posZ - player.lastTickPosZ) * 20;
        double speed = Math.sqrt(x*x + y*y + z*z);
        return Utils.floor100(speed);
    }
    private double calculateXVelocity(EntityPlayer player) {
        double x = (player.posX - player.lastTickPosX) * 20;
        double speed = Math.sqrt(x*x);
        return Utils.floor100(speed);
    }
    private double calculateYVelocity(EntityPlayer player) {
        double y = (player.posY - player.lastTickPosY) * 20;
        double speed = Math.sqrt(y*y);
        return Utils.floor100(speed);
    }
    private double calculateZVelocity(EntityPlayer player) {
        double z = (player.posZ - player.lastTickPosZ) * 20;
        double speed = Math.sqrt(z*z);
        return Utils.floor100(speed);
    }
}