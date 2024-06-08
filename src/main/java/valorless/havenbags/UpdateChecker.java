package valorless.havenbags;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UpdateChecker {

	private final JavaPlugin plugin;
	private final int resourceId;
	private final FoliaLib foliaLib;

	public UpdateChecker(JavaPlugin plugin, int resourceId) {
		this.plugin = plugin;
		this.resourceId = resourceId;
		this.foliaLib = Main.getFoliaLib(); // Asumiendo que Main es tu clase principal que extiende JavaPlugin
	}

	public void getVersion(final Consumer<String> consumer) {
		foliaLib.getImpl().runAsync(wrappedTask -> {
            try (InputStream inputStream = new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=" + UpdateChecker.this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    String version = scanner.next();
                    consumer.accept(version);
                }
            } catch (IOException exception) {
                plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
	}
}