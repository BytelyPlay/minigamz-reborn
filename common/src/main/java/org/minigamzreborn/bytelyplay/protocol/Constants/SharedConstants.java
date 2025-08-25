package org.minigamzreborn.bytelyplay.protocol.Constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SharedConstants {
    public static Path CONFIGURATION_FOLDER = Paths.get("./configuration");
    public static byte[] PACKET_DELIMITER = new byte[]{(byte) 0x99, 0x11, (byte) 0x91, (byte) 0xFF, (byte) 0xF5, (byte) 0xF2};
}
