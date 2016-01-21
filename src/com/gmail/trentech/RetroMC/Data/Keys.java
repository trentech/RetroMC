package com.gmail.trentech.RetroMC.Data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

public class Keys {

	public static final Key<Value<Integer>> LIVES = KeyFactory.makeSingleKey(Integer.class, Value.class, DataQuery.of("lives"));
}
