package co.mcGalaxy.galaxyGraves.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Pose;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class NpcUtils {

    private final Location location;
    private final Player player;
    private final String name;
    public ServerPlayer serverPlayer;

    public NpcUtils(Player player, Location location, String name) {
        this.location = location;
        this.player = player;
        this.name = name;
    }

    public void spawn(Location location) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();
        MinecraftServer server = sp.getServer();
        ServerLevel level = sp.serverLevel();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        //TODO: Skin stuff to be implemented
        String signature = "";
        String texture = "";

        ServerPlayer serverPlayer = new ServerPlayer(server, level, profile, ClientInformation.createDefault());
        serverPlayer.absMoveTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        serverPlayer.connection = new ServerGamePacketListenerImpl(server, new Connection(PacketFlow.SERVERBOUND),
                serverPlayer, CommonListenerCookie.createInitial(profile, false));

        ServerGamePacketListenerImpl gamePacketListener = ((CraftPlayer) player).getHandle().connection;
        ServerEntity serverEntity = new ServerEntity(level, serverPlayer, 0, false, packet -> {

        }, Set.of());

        gamePacketListener.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer));

        serverPlayer.setPose(Pose.SLEEPING);

        gamePacketListener.send(serverPlayer.getAddEntityPacket(serverEntity));
        gamePacketListener.send(new ClientboundSetEntityDataPacket(serverPlayer.getId(), serverPlayer.getEntityData().packAll()));
        this.serverPlayer = serverPlayer;
    }

    public void remove(Location location) {
        ServerPlayer onlinePlayers = ((CraftPlayer) player).getHandle();
        onlinePlayers.connection.send(new ClientboundRemoveEntitiesPacket(serverPlayer.getId()));
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }
}
