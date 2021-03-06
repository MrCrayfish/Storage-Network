package mrriegel.storagenetwork.apiimpl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import mrriegel.storagenetwork.api.IStorageNetworkPlugin;

public class PluginRegistry {

  private Set<IStorageNetworkPlugin> plugins;

  public void loadStorageNetworkPlugins() {
    plugins = new HashSet<>();
    for (IStorageNetworkPlugin plugin : AnnotatedInstanceUtil.getPlugins()) {
      plugins.add(plugin);
    }
  }

  public void forEach(Consumer<IStorageNetworkPlugin> pluginConsumer) {
    plugins.forEach(pluginConsumer);
  }
}