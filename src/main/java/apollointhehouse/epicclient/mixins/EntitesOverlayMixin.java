package apollointhehouse.epicclient.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.HashMap;

@Mixin(value = GuiIngame.class, remap = false)
public class EntitesOverlayMixin {

    @Shadow
    private Minecraft mc;

    @Unique
    private int tsp;

    @Unique
    private int lineHeight;

    @Unique
    private int line;

    @Unique
    private Map<Class<? extends Entity>, Integer> entityCounts = new HashMap<>();

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

    @Inject(method = "renderGameOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;armorDurabilityOverlay:Lnet/minecraft/src/option/BooleanOption;"))
    private void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.mc.gameSettings.showDebugScreen.value) {
            return;
        }

        boolean EntitesOverlay = true;
        if (EntitesOverlay) {
            GuiIngame guiIngame = (GuiIngame) (Object) this;
            int numZombies = 0;
            int numSkeletons = 0;
            int numSpiders = 0;
            int numChickens = 0;
            int numPigs = 0;
            int numCows = 0;
            int numPlayers = 0;
            int numSlimes = 0;
            int numItems = 0;
            int numGhasts = 0;
            int numPigZombies = 0;
            int numSheep = 0;
            int numCreepers = 0;
            int numSquids = 0;
            int numWolves = 0;
            int numBoats = 0;
            int numGiants = 0;
            int numFallingSand = 0;
            int numPaintings = 0;
            int numSnowballs = 0;
            int numCannonballs = 0;
            int numArrows = 0;
            int numPurpleArrows = 0;
            int numMinecarts = 0;
            int numPrimedTnt = 0;
            int numMisc = 0;

            for (Entity entity : this.mc.theWorld.getLoadedEntityList()) {
                if (entity instanceof EntityZombie) {
                    numZombies++;
                } else if (entity instanceof EntitySkeleton) {
                    numSkeletons++;
                } else if (entity instanceof EntitySpider) {
                    numSpiders++;
                } else if (entity instanceof EntityChicken) {
                    numChickens++;
                } else if (entity instanceof EntityPig) {
                    numPigs++;
                } else if (entity instanceof EntityCow) {
                    numCows++;
                } else if (entity instanceof EntitySlime) {
                    numSlimes++;
                } else if (entity instanceof EntityItem) {
                    numItems++;
                } else if (entity instanceof EntityPlayer) {
                    numMisc++;
                } else if (entity instanceof EntityGhast) {
                    numGhasts++;
                } else if (entity instanceof EntityPigZombie) {
                    numPigZombies++;
                } else if (entity instanceof EntitySheep) {
                    numSheep++;
                } else if (entity instanceof EntityCreeper) {
                    numCreepers++;
                } else if (entity instanceof EntitySquid) {
                    numSquids++;
                } else if (entity instanceof EntityWolf) {
                    numWolves++;
                } else if (entity instanceof EntityBoat) {
                    numMisc++;
                } else if (entity instanceof EntityGiantZombie) {
                    numMisc++;
                } else if (entity instanceof EntityFallingSand) {
                    numMisc++;
                } else if (entity instanceof EntityPainting) {
                    numMisc++;
                } else if (entity instanceof EntitySnowball) {
                    numMisc++;
                } else if (entity instanceof EntityCannonball) {
                    numMisc++;
                } else if (entity instanceof EntityArrow) {
                    numMisc++;
                } else if (entity instanceof EntityArrowPurple) {
                    numMisc++;
                } else if (entity instanceof EntityMinecart) {
                    numMisc++;
                } else if (entity instanceof EntityTNTPrimed) {
                    numMisc++;
                }
            }

            guiIngame.drawString(this.mc.fontRenderer, "Found " + this.mc.theWorld.getLoadedEntityList().size() + " Entities: ", this.tsp, this.tsp + 50 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Zombies: " + numZombies, this.tsp, this.tsp + 60 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Zombie Pigmen: " + numPigZombies, this.tsp, this.tsp + 70 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Skeletons: " + numSkeletons, this.tsp, this.tsp + 80 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Spiders: " + numSpiders, this.tsp, this.tsp + 90 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Slimes: " + numSlimes, this.tsp, this.tsp + 100 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Chickens: " + numChickens, this.tsp, this.tsp + 110 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Pigs: " + numPigs, this.tsp, this.tsp + 120 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Cows: " + numCows, this.tsp, this.tsp + 130 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Ghasts: " + numGhasts, this.tsp, this.tsp + 140 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Sheep: " + numSheep, this.tsp, this.tsp + 150 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Creepers: " + numCreepers, this.tsp, this.tsp + 160 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Squids: " + numSquids, this.tsp, this.tsp + 170 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Wolves: " + numWolves, this.tsp, this.tsp + 180 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Items: " + numItems, this.tsp, this.tsp + 190 + this.line * 10, 0xFFFFFF);
            guiIngame.drawString(this.mc.fontRenderer, "Misc: " + numMisc, this.tsp, this.tsp + 200 + this.line * 10, 0xFFFFFF);
        }
    }
}
