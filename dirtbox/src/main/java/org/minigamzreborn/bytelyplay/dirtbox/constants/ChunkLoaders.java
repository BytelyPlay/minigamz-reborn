package org.minigamzreborn.bytelyplay.dirtbox.constants;

import net.minestom.server.instance.ChunkLoader;
import net.minestom.server.instance.anvil.AnvilLoader;

import java.nio.file.Path;

public class ChunkLoaders {
    public static ChunkLoader dirtboxChunkLoader = new AnvilLoader(Path.of("./configuration/dirtbox_world/"));
}
