package me.uwutaku.hexthingy;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;


@Mod(Hexthingy.MODID)
public class Hexthingy {
    public static final String MODID = "hexthingy";

    public Hexthingy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        registerAction("smite", "wweeewwweeeqeeeadweeead", HexDir.EAST, new Smite());
        registerAction("things/pasteurpurification", "aqqqqa", HexDir.NORTH_WEST, new PasteurPurification());
        registerAction("things/cleaneffect", "qqqqwaqw", HexDir.SOUTH_WEST, new CleanEffect());
        registerAction("allaycreation", "qqqqqweeweeaeeqeeeeeweeweeaeeaeeweedqqqqdadedaddwwqqq", HexDir.SOUTH_EAST, new AllayCreation());
    }

    private void registerAction(String actionId, String pattern, HexDir direction, Action action) {
        Registry.register(
            HexActions.REGISTRY,
            new ResourceLocation(MODID, actionId),
            new ActionRegistryEntry(
                HexPattern.fromAngles(pattern, direction),
                new ActionPredicateWrapper(action, (CastingEnvironment env) -> Optional.empty())
            )
        );
    }
}