package tr.org.liderahenk.usb.plugininfo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.lider.core.api.plugin.IPluginInfo;
import tr.org.liderahenk.lider.core.api.pluginmanager.IPluginDistro;
import tr.org.liderahenk.lider.core.api.pluginmanager.PluginDistroSSH;

public class PluginInfoImpl implements IPluginInfo {
	
	private static Logger logger = LoggerFactory.getLogger(PluginInfoImpl.class);

	private String pluginName;

	private String pluginVersion;

	private String description;

	private Boolean machineOriented;

	private Boolean userOriented;

	private Boolean policyPlugin;

	private Boolean xbased;

	// Distro configuration

	private String distroHost;

	private String distroUsername;

	private String distroPassword;

	private String distroPath;

	public void refresh() {
		logger.info("Configuration updated using blueprint: {}", prettyPrintConfig());
	}

	@Override
	public String toString() {
		return "PluginInfoImpl [pluginName=" + pluginName + ", pluginVersion=" + pluginVersion + ", description="
				+ description + ", machineOriented=" + machineOriented + ", userOriented=" + userOriented
				+ ", policyPlugin=" + policyPlugin + ", xBased=" + xbased + ", distroHost=" + distroHost
				+ ", distroUsername=" + distroUsername + ", distroPassword=" + distroPassword + ", distroPath="
				+ distroPath + "]";
	}

	public String prettyPrintConfig() {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (Exception e) {
		}
		return toString();
	}

	@Override
	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	@Override
	public String getPluginVersion() {
		return pluginVersion;
	}

	public void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Boolean getMachineOriented() {
		return machineOriented;
	}

	public void setMachineOriented(Boolean machineOriented) {
		this.machineOriented = machineOriented;
	}

	@Override
	public Boolean getUserOriented() {
		return userOriented;
	}

	public void setUserOriented(Boolean userOriented) {
		this.userOriented = userOriented;
	}

	@Override
	public Boolean getPolicyPlugin() {
		return policyPlugin;
	}

	public void setPolicyPlugin(Boolean policyPlugin) {
		this.policyPlugin = policyPlugin;
	}

	@Override
	public Boolean getXbased() {
		return xbased;
	}

	public void setXbased(Boolean xbased) {
		this.xbased = xbased;
	}

	public String getDistroHost() {
		return distroHost;
	}

	public void setDistroHost(String distroHost) {
		this.distroHost = distroHost;
	}

	public String getDistroUsername() {
		return distroUsername;
	}

	public void setDistroUsername(String distroUsername) {
		this.distroUsername = distroUsername;
	}

	public String getDistroPassword() {
		return distroPassword;
	}

	public void setDistroPassword(String distroPassword) {
		this.distroPassword = distroPassword;
	}

	public String getDistroPath() {
		return distroPath;
	}

	public void setDistroPath(String distroPath) {
		this.distroPath = distroPath;
	}

	@JsonIgnore
	@Override
	public IPluginDistro getDistro() {
		return new PluginDistroSSH() {
			private static final long serialVersionUID = 242815723673154512L;

			@Override
			public String getUsername() {
				return getDistroUsername();
			}

			@Override
			public String getPublicKey() {
				return null;
			}

			@Override
			public String getPath() {
				return getDistroPath();
			}

			@Override
			public String getPassword() {
				return getDistroPassword();
			}

			@Override
			public String getHost() {
				return getDistroHost();
			}
		};
	}
	
}