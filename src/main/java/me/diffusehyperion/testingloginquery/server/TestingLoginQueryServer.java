package me.diffusehyperion.testingloginquery.server;

import me.diffusehyperion.testingloginquery.Constants;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;

public class TestingLoginQueryServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        Constants.LOGGER.error("Initializing server");
        //ServerLoginNetworking.registerGlobalReceiver(Constants.QUERY_ID, this::onGlobalQueryResponse);
        ServerLoginConnectionEvents.QUERY_START.register(this::onQueryStart);
    }

    private void onQueryStart(ServerLoginNetworkHandler serverLoginNetworkHandler, MinecraftServer minecraftServer, PacketSender packetSender, ServerLoginNetworking.LoginSynchronizer loginSynchronizer) {
        ServerLoginNetworking.registerReceiver(serverLoginNetworkHandler, Constants.QUERY_ID, this::onQueryResponse);
        packetSender.sendPacket(Constants.QUERY_ID, PacketByteBufs.empty());
    }

    private void onGlobalQueryResponse(MinecraftServer minecraftServer, ServerLoginNetworkHandler serverLoginNetworkHandler, boolean b, PacketByteBuf packetByteBuf, ServerLoginNetworking.LoginSynchronizer loginSynchronizer, PacketSender packetSender) {
        Constants.LOGGER.error("Received query response from global receiver");
        Constants.LOGGER.error("Understood: " + b);
        if (!b) {
            serverLoginNetworkHandler.disconnect(Text.of("Not understood"));
        }
    }

    private void onQueryResponse(MinecraftServer minecraftServer, ServerLoginNetworkHandler serverLoginNetworkHandler, boolean b, PacketByteBuf packetByteBuf, ServerLoginNetworking.LoginSynchronizer loginSynchronizer, PacketSender packetSender) {
        Constants.LOGGER.error("Received query response from normal receiver");
        Constants.LOGGER.error("Understood: " + b);
        if (!b) {
            serverLoginNetworkHandler.disconnect(Text.of("Not understood"));
        }
    }


}
