package apollointhehouse.epicclient;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EpicClient implements ModInitializer {
    public static final String MOD_ID = "epicclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Epic Client initialized.");
    }
}
