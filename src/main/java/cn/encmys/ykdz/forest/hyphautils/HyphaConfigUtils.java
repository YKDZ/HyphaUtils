package cn.encmys.ykdz.forest.hyphautils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

public class HyphaConfigUtils {
    public static void loadMapIntoConfiguration(@NotNull ConfigurationSection section, @NotNull Map<?, ?> map, @NotNull String path) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            String fullPath = path.isEmpty() ? key : path + "." + key;
            if (value instanceof Map<?, ?>) {
                loadMapIntoConfiguration(section.createSection(key), (Map<?, ?>) value, fullPath);
            } else {
                section.set(key, value);
            }
        }
    }

    @NotNull
    public static Locale getLocale(@NotNull String locale) {
        String[] parts = locale.split("_", -1);
        return switch (parts.length) {
            case 1 -> Locale.of(parts[0]);  // 仅语言代码
            case 2 -> Locale.of(parts[0], parts[1]);  // 语言代码 + 国家代码
            case 3 -> new Locale.Builder()
                    .setLanguage(parts[0])
                    .setRegion(parts[1])
                    .setVariant(parts[2])
                    .build();  // 语言代码 + 国家代码 + 变体
            default -> throw new IllegalArgumentException("Invalid locale format: " + locale);
        };
    }

    @NotNull
    public static YamlConfiguration loadYamlFromResource(@NotNull InputStream resource) throws IOException, InvalidConfigurationException {
        YamlConfiguration config = new YamlConfiguration();
        config.load(new InputStreamReader(resource, StandardCharsets.UTF_8));
        return config;
    }

    @NotNull
    public static YamlConfiguration merge(@NotNull YamlConfiguration config, @NotNull YamlConfiguration newConfig, @NotNull String saveTo) throws IOException {
        if (newConfig.getInt("version") != config.getInt("version")) {
            for (String key : config.getKeys(true)) {
                if (key.equals("version")) {
                    continue;
                }
                if (newConfig.contains(key) && !(newConfig.get(key) instanceof ConfigurationSection)) {
                    newConfig.set(key, config.get(key));
                }
            }
            newConfig.save(saveTo);
            return newConfig;
        }
        return config;
    }
}
