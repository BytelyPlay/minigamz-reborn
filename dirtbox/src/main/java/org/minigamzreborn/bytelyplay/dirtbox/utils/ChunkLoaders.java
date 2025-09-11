package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.anvil.AnvilLoader;

import java.nio.file.Path;

public class ChunkLoaders {
    public static IChunkLoader dirtboxChunkLoader = new AnvilLoader(Path.of("./dirtbox_world/"));
}
