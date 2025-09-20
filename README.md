# The Idea
A Velocity server with a Velocity plugin using a custom protocol that uses ProtoBuffers (yes i know that is VERY overkill but it might not be who knows?) to communicate between servers \
Minestom, fabric whatever is used doesn't matter! \
the only thing that DOES matter is EVERYTHING has to be custom-made (libraries are allowed)

This project uses JDK 21

# The Protocol
All packets have a PacketType and a class associated with the data. \
We want to use ProtoBuffers AS MUCH as possible... \
The Proto files (WrappedPacketS2C and WrappedPacketC2S) will wrap any sent packet... \
uses length prefixes since tcp is a stream based protocol.

The server is the proxy... \
Client initializes the handshake and the server responds... \
Server listens on port 9485.

# Packets
HandShakePacketS2C (Contains 32 bit int that is 8439) \
HandShakePacketC2S (Contains 32 bit int that is 8439 too)

TransferPlayerPacketC2S (contains an enum representing a server type which the proxy has to pick which server of that type to use and the UUID of the player that should be transferred) \
RegisterServerPacketC2S (Add a server with a specific ip and port to the server list thing and set the server type)
UnregisterServerPacketC2S (Removes a server with a specific ip and port, server type shouldn't be required though.)

DisconnectPacketC2S (Self explanatory, its contents are irrelevant whatever it is, disconnect IMMEDIATELY when receiving this packet)
DisconnectPacketS2C (Self explanatory, its contents are irrelevant whatever it is, disconnect IMMEDIATELY when receiving this packet)

# TODO
1. convert to 100% gradle KTS
2. add some way to configure where the server is and also the port and ip of which it is hosted if using minestom.
3. employ a permission system and once again save all data in the mongodb database but permissions should be enums and ranks should be enums too, static not dynamic but you should be able to change a player's rank and change the player's permissions too but nothing else should be dynamic this would be in common and saved to the mongodb database (just because we already are using mongodb so yeah)
4. fully implement the protocol
5. (MAYBE) make an installer that installs all servers into a certain folder... written in java C++ whatever just one that works, should be fully automatic OR another option is to give users the option to run it in a docker container which would be 100% automatic too