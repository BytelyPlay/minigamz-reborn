package org.minigamzreborn.bytelyplay.protocol.wrappers;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class ClientReadAttachment {
    public ByteBuffer buffer = null;
    public int toRead = -1;
    public AsynchronousSocketChannel client;
}
