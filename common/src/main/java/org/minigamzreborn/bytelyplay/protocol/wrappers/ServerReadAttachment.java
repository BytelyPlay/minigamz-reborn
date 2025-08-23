package org.minigamzreborn.bytelyplay.protocol.wrappers;

import lombok.Builder;
import org.minigamzreborn.bytelyplay.protocol.Client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class ServerReadAttachment {
    public ByteBuffer buffer;
    public int toRead = -1;
    public AsynchronousSocketChannel clientChannel;
    public Client client;
}
