package com.gmail.trentech.RetroMC.Data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

public class PlayerDataManipulatorBuilder implements DataManipulatorBuilder<PlayerData, ImmutablePlayerData> {

	@Override
    public PlayerData create() {
        return new PlayerData();
    }

    @Override
    public Optional<PlayerData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(PlayerData.class).orElse(new PlayerData()));
    }

    @Override
    public Optional<PlayerData> build(DataView container) throws InvalidDataException {
        if (container.contains(Keys.LIVES)) {
            final int lives = container.getInt(Keys.LIVES.getQuery()).get();

            return Optional.of(new PlayerData(lives));
        }
        return Optional.empty();
    }
}
