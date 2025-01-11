package cn.encmys.ykdz.forest.hyphautils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HyphaSkullUtils {
    /**
     * @param data Player name or texture hash
     * @return ItemStack of skull
     */
    public static ItemStack getSkullFromData(@NotNull String data) {
        // 哈希
        if (data.length() >= 16) {
            data = data.toLowerCase().replace("https://textures.minecraft.net/texture/", "");

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            if (skullMeta == null) {
                return item;
            }

            // 使用现代 API 创建 PlayerProfile
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.setProperty(new ProfileProperty("textures", data));
            skullMeta.setPlayerProfile(profile);

            item.setItemMeta(skullMeta);
            return item;
        }
        // 玩家名
        else {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
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
