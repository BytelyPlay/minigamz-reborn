# The Idea
A bungeecord server with a bungeecord plugin using a custom protocol that uses ProtoBuffers (yes i know that is VERY overkill but it might not be who knows?) to communicate between servers \
Minestom, fabric whatever is used doesn't matter! \
the only thing that DOES matter is EVERYTHING has to be custom-made (libraries are allowed)
Runs on port 9485.

# The Protocol
All packets have a PacketType and a class associated with the data. \
We want to use ProtoBuffers AS MUCH as possible... \
The Proto file (PacketWrapper) will wrap any sent packet... \
uses length prefixes since tcp is a stream based protocol.

The server is the proxy...
Client initializes the handshake server responds...

# Packets
HandShakePacketS2C (Contains 32 bit int that is 8439) \
HandShakePacketC2S (Contains 32 bit int that is 8439 too)

TransferPlayerPacketC2S (contains an enum representing a server type which the proxy has to pick which server of that type to use and the UUID of the player that should be transferred) \
RegisterServerPacketC2S (Add a server with a specific ip and port to the server list thing and set the server type)
UnregisterServerPacketC2S (Removes a server with a specific ip and port, server type shouldn't be required though.)

# TODO
1. NPCs (Base class done just need to actually add the NPCs)
2. finish the protocol
3. convert to 100% gradle KTS
4. add some way to configure where the server is and also the port and ip of which it is hosted if using minestom.