package com.gmail.trentech.RetroMC.Data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import com.google.common.collect.ComparisonChain;

public class ImmutablePlayerData extends AbstractImmutableData<ImmutablePlayerData, PlayerData> {

	private int lives = 0;

	public ImmutablePlayerData() {

	}

	public ImmutablePlayerData(int lives) {
		this.lives = lives;
	}
	
	public ImmutableValue<Integer> lives() {
        return Sponge.getRegistry().getValueFactory().createValue(Keys.LIVES, this.lives).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(Keys.LIVES, this::getLives);
        registerKeyValue(Keys.LIVES, this::lives);
    }

    @Override
    public <E> Optional<ImmutablePlayerData> with(Key<? extends BaseValue<E>> key, E value) {
        return Optional.empty();
    }

    @Override
    public PlayerData asMutable() {
    	return new PlayerData(this.lives);
    }

    @Override
    public int compareTo(ImmutablePlayerData o) {
        return ComparisonChain.start().compare(o.lives, this.lives).result();
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Keys.LIVES, this.lives);
    }
    
    private int getLives() {
        return this.lives;
    }
}
