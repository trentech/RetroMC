package com.gmail.trentech.RetroMC.Data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.base.Objects;

public class PlayerData extends AbstractData<PlayerData, ImmutablePlayerData> {

	private int lives = 0;

	public PlayerData() {

	}

	public PlayerData(int lives) {
		this.lives = lives;
	}
	
	public Value<Integer> lives() {
        return Sponge.getRegistry().getValueFactory().createValue(Keys.LIVES, this.lives);
    }

	@Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(Keys.LIVES, () -> this.lives);
        registerFieldSetter(Keys.LIVES, value -> this.lives = value);
        registerKeyValue(Keys.LIVES, this::lives);
    }
	
	@Override
    public Optional<PlayerData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return Optional.empty();
    }

    @Override
    public Optional<PlayerData> from(DataContainer container) {
        if (!container.contains(Keys.LIVES.getQuery())) {
            return Optional.empty();
        }
        this.lives = container.getInt(Keys.LIVES.getQuery()).get();

        return Optional.of(this);
    }

    @Override
    public PlayerData copy() {
    	return new PlayerData(this.lives);
    }

    @Override
    public ImmutablePlayerData asImmutable() {
        return new ImmutablePlayerData(this.lives);
    }

    @Override
    public int compareTo(PlayerData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(Keys.LIVES, this.lives);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("lives", this.lives).toString();
    }
}
