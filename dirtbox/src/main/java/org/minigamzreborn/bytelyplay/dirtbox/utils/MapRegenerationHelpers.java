package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.Section;
import net.minestom.server.instance.block.Block;
import org.minigamzreborn.bytelyplay.dirtbox.constants.ChunkLoaders;
import org.minigamzreborn.bytelyplay.dirtbox.constants.Instances;

import java.util.ArrayList;

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
            // this line causes some bug in the JRE and makes it do something that makes it do an invalid arithmetic operation with integers... (SIGFPE)
            // realSection.blockPalette().setAll((x, y, z) -> loadedSection.blockPalette().get(x, y, z));

            // this should be just as good performance wise, also added a few things so it is an absolute copy
            loadedSection.blockPalette().getAll(realSection.blockPalette()::set);
            loadedSection.biomePalette().getAll(realSection.biomePalette()::set);
            realSection.setBlockLight(loadedSection.blockLight().array());
            realSection.setSkyLight(loadedSection.skyLight().array());
        }
        chunk.getViewers().forEach(player -> player.sendChunk(chunk));
    }
}
