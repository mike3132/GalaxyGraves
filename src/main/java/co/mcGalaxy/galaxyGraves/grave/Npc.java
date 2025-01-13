package co.mcGalaxy.galaxyGraves.grave;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

public class Npc {

    private final Location location;
    private final Player player;
    private final String name;
    private final String skin;
    public ServerPlayer serverPlayer;

    public Npc(Player player, Location location, String name, String skin) {
        this.location = location;
        this.player = player;
        this.name = name;
        this.skin = skin;
    }

    public void spawn(Location location) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer sp = craftPlayer.getHandle();
        MinecraftServer server = sp.getServer();
        ServerLevel level = sp.serverLevel();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        String[] name = getSkin(this.player, this.skin);
        profile.getProperties().put("textures", new Property("textures", name[0], name[1]));

        ServerPlayer serverPlayer = new ServerPlayer(server, level, profile, ClientInformation.createDefault());

        serverPlayer.connection = new ServerGamePacketListenerImpl(server, new Connection(PacketFlow.SERVERBOUND),
                serverPlayer, CommonListenerCookie.createInitial(profile, false));

        ServerGamePacketListenerImpl gamePacketListener = ((CraftPlayer) player).getHandle().connection;
        ServerEntity serverEntity = new ServerEntity(level, serverPlayer, 0, false, packet -> {

        }, Set.of());

        serverPlayer.absMoveTo(location.getX(), location.getY(), location.getZ(),
                (float) Math.toRadians(location.getYaw()), (float) Math.toRadians(location.getPitch()));

        gamePacketListener.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer));

        serverPlayer.setPose(Pose.SLEEPING);

        level.addFreshEntity(serverPlayer);
        serverPlayer.setInvulnerable(true);

        gamePacketListener.send(serverPlayer.getAddEntityPacket(serverEntity));
        gamePacketListener.send(new ClientboundSetEntityDataPacket(serverPlayer.getId(), serverPlayer.getEntityData().packAll()));

        this.serverPlayer = serverPlayer;
    }

    public void remove(Location location) {
        ServerPlayer onlinePlayer = ((CraftPlayer) player).getHandle();
        serverPlayer.remove(Entity.RemovalReason.DISCARDED);
        onlinePlayer.connection.send(new ClientboundRemoveEntitiesPacket(serverPlayer.getId()));
    }

    private String[] getSkin(Player player, String name) {
        try {
            URL userProfile = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader inputStreamReader = new InputStreamReader(userProfile.openStream());
            String uuid = new JsonParser().parse(inputStreamReader).getAsJsonObject().get("id").getAsString();

            URL userSession = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader inputStreamReader2 = new InputStreamReader(userSession.openStream());
            JsonObject property = new JsonParser().parse(inputStreamReader2).getAsJsonObject().get("properties")
                    .getAsJsonArray().get(0).getAsJsonObject();

            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[]{
                    texture,
                    signature
            };
        } catch (Exception e) {
            ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
            GameProfile profile = serverPlayer.getGameProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.value();
            String signature = property.signature();
            return new String[]{
                    texture,
                    signature
            };
        }
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
