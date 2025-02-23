package cn.encmys.ykdz.forest.hyphautils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

public class HyphaSkullUtils {
    /**
     * @param data Player name or texture hash
     * @return ItemStack of skull
     */
    public static @NotNull ItemStack getSkullFromData(@NotNull String data) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);

        // 链接
        if (data.startsWith("http:") || data.startsWith("https:")) {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URI(data).toURL());
            } catch (URISyntaxException | MalformedURLException ignored) {
            }
            profile.setTextures(textures);
            item.editMeta(SkullMeta.class, skullMeta -> {
                skullMeta.setPlayerProfile(profile);
            });
            return item;
        }
        // base64 JSON 数据
        else if (data.length() >= 16) {
            item.editMeta(SkullMeta.class, skullMeta -> {
                final UUID uuid = UUID.randomUUID();
                final PlayerProfile playerProfile = Bukkit.createProfile(uuid, uuid.toString().substring(0, 16));
                playerProfile.setProperty(new ProfileProperty("textures", data));

                skullMeta.setPlayerProfile(playerProfile);
            });

            return item;
        }
        // 玩家名
        else {
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            if (skullMeta == null) {
                return item;
            }

            Player player = Bukkit.getPlayer(data);

            if (player != null) {
                skullMeta.setOwningPlayer(player);
            }

            item.setItemMeta(skullMeta);
            return item;
        }
    }
}
