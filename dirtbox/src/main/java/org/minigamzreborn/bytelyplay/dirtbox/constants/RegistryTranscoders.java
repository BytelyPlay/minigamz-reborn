package org.minigamzreborn.bytelyplay.dirtbox.constants;

import net.kyori.adventure.nbt.BinaryTag;
import net.minestom.server.MinecraftServer;
import net.minestom.server.codec.Transcoder;
import net.minestom.server.registry.RegistryTranscoder;

public class RegistryTranscoders {
    public static RegistryTranscoder<BinaryTag> nbtRegistryTranscoder;

    private static boolean init = false;

    public static void init() {
        if (init)
            throw new IllegalStateException("Called init twice in RegistryTranscoders.");
        init = true;

        nbtRegistryTranscoder =
                new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
    }
}
