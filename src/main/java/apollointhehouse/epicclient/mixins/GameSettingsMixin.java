package apollointhehouse.epicclient.mixins;

import apollointhehouse.epicclient.IKeybinds;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.GameSettings;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin
    implements IKeybinds
{
public KeyBinding ViewChestOverlay = new KeyBinding("key.ViewChestOverlay", 38);
public KeyBinding getViewChestOverlay() {
    return ViewChestOverlay;
}

}