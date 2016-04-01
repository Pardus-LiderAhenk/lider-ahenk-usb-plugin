package tr.org.liderahenk.usb.dialogs;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.liderconsole.core.dialogs.IProfileDialog;
import tr.org.liderahenk.liderconsole.core.model.Profile;

public class UsbProfileDialog implements IProfileDialog {
	
	private static final Logger logger = LoggerFactory.getLogger(UsbProfileDialog.class);
	
	@Override
	public void init() {
		// TODO initialize 
	}
	
	@Override
	public void createDialogArea(Composite parent, Profile profile) {
		// TODO create input widgets
	}
	
	@Override
	public Map<String, Object> getProfileData() throws Exception {
		// TODO return profile data collected from the input widgets
		return null;
	}
	
}
