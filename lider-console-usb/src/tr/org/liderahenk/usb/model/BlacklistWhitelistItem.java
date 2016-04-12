package tr.org.liderahenk.usb.model;

public class BlacklistWhitelistItem {

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
