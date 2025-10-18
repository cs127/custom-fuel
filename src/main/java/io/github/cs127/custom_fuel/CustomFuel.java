package io.github.cs127.custom_fuel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class CustomFuel implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("custom_fuel");
	public static CustomFuelConfig config;

	private static Item getItem(String id) {
		return Registries.ITEM.getOptionalValue(Identifier.of(id)).orElseThrow();
	}

	private static TagKey<Item> getTag(String id) {
		return TagKey.of(RegistryKeys.ITEM, Identifier.of(id.substring(1)));
	}

	@Override
	public void onInitialize() {
		File configFile = new File(FabricLoader.getInstance().getConfigDir() + "/custom_fuel.json");
		config = CustomFuelConfig.getConfig(configFile);

		FuelRegistryEvents.EXCLUSIONS.register((builder, context) -> {
			for (Map.Entry<String, Integer> entry : config.include.entrySet()) {
				String id = entry.getKey();
				int duration = entry.getValue();

				try {
					if (id.startsWith("#"))
						builder.add(getTag(id), duration);
					else
						builder.add(getItem(id), duration);
				} catch (RuntimeException e) {
					LOGGER.error("cannot register `{}` as fuel", id, e);
				}
			}
			for (String id : config.exclude) {
				try {
					if (id.startsWith("#"))
						builder.remove(getTag(id));
					else
						throw new IllegalArgumentException("exclusions must be tags"); // TODO
				} catch (RuntimeException e) {
					LOGGER.error("cannot exclude `{}` from fuel", id, e);
				}
			}
		});
	}
}
