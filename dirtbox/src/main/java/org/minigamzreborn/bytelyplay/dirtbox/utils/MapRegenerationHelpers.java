package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.Section;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.palette.Palette;
import org.minigamzreborn.bytelyplay.dirtbox.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class MapRegenerationHelpers {
    public static void regenerateDirtboxMap() {
        Instance dirtbox = Instances.dirtbox;
        ArrayList<Chunk> chunksToLoad = new ArrayList<>();
        dirtbox.getChunks().forEach(chunk -> {
            if (chunk.getViewers().isEmpty()) {
                dirtbox.unloadChunk(chunk);
            } else {
                chunksToLoad.add(chunk);
            }
        });
        chunksToLoad.forEach(MapRegenerationHelpers::regenerateChunkDirtbox);
    }
    private static void regenerateChunkDirtbox(Chunk chunk) {
        Chunk loadedChunk = ChunkLoaders.dirtboxChunkLoader.loadChunk(chunk.getInstance(), chunk.getChunkX(), chunk.getChunkZ());
        if (loadedChunk == null) {
            chunk.getSections().forEach(section -> section.blockPalette().fill(Block.AIR.id()));
            return;
        }
        for (int sectionIndex = chunk.getMinSection(); sectionIndex < chunk.getMaxSection(); sectionIndex++) {
            Section realSection = chunk.getSection(sectionIndex);

            Section loadedSection = loadedChunk.getSection(sectionIndex);

            realSection.blockPalette().fill(Block.AIR.id());
            realSection.blockPalette().setAll((x, y, z) -> loadedSection.blockPalette().get(x, y, z));
        }
        chunk.getViewers().forEach(player -> player.sendChunk(chunk));
    }
}
