package apollointhehouse.epicclient.mixins;

import apollointhehouse.epicclient.IKeybinds;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.GameSettings;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin
    implements IKeybinds
{
public KeyBinding KeyUtility = new KeyBinding("key.viewutility", 38);
public KeyBinding getKeyUtility() {
    return KeyUtility;
}

}