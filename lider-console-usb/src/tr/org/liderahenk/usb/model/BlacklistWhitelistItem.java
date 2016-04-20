package tr.org.liderahenk.usb.model;

import java.io.Serializable;

/**
 * Model class for blacklist/whitelist items.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class BlacklistWhitelistItem implements Serializable {

	private static final long serialVersionUID = -1215191189845829199L;

	private String vendor;

	private String model;

	private String serialNumber;

	public BlacklistWhitelistItem() {
		super();
	}

	public BlacklistWhitelistItem(String vendor, String model, String serialNumber) {
		super();
		this.vendor = vendor;
		this.model = model;
		this.serialNumber = serialNumber;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
