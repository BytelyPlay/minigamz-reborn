package org.minigamzreborn.bytelyplay.protocol.wrappers;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashSet;

public class ServerWriteAttachment {
    public ByteBuffer toWrite;
    public AsynchronousSocketChannel client;
}
