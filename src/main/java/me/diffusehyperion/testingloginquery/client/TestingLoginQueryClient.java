package me.diffusehyperion.testingloginquery.client;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.diffusehyperion.testingloginquery.Constants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TestingLoginQueryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOGGER.error("Initializing client");
        //ClientLoginNetworking.registerGlobalReceiver(Constants.QUERY_ID, this::onGlobalQueryReceived);
        ClientLoginConnectionEvents.QUERY_START.register(this::onQueryStart);
    }

    private void onQueryStart(ClientLoginNetworkHandler clientLoginNetworkHandler, MinecraftClient minecraftClient) {
        ClientLoginNetworking.registerReceiver(Constants.QUERY_ID, this::onQueryReceived);
    }

    private CompletableFuture<PacketByteBuf> onGlobalQueryReceived(MinecraftClient minecraftClient, ClientLoginNetworkHandler clientLoginNetworkHandler, PacketByteBuf packetByteBuf, Consumer<GenericFutureListener<? extends Future<? super Void>>> genericFutureListenerConsumer) {
        Constants.LOGGER.error("Received query request from global receiver");
        return CompletableFuture.completedFuture(PacketByteBufs.empty());
    }

    private CompletableFuture<PacketByteBuf> onQueryReceived(MinecraftClient minecraftClient, ClientLoginNetworkHandler clientLoginNetworkHandler, PacketByteBuf packetByteBuf, Consumer<GenericFutureListener<? extends Future<? super Void>>> genericFutureListenerConsumer) {
        Constants.LOGGER.error("Received query request from receiver");
        return CompletableFuture.completedFuture(PacketByteBufs.empty());
    }
}
