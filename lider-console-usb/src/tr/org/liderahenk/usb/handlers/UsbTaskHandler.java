package tr.org.liderahenk.usb.handlers;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.liderconsole.core.handlers.MultipleSelectionHandler;

//TODO use MultipleSelectionHandler if this task support multiple LDAP entries/DNs otherwise use SingleSelectionHandler.

public class UsbTaskHandler extends MultipleSelectionHandler {
	
	private Logger logger = LoggerFactory.getLogger(UsbTaskHandler.class);
	
	@Override
	public void executeWithDNSet(Set<String> dnSet) {
		// TODO dnSet contains distinguished names (DN) of the selected LDAP entries.
		// TODO open a Task dialog here, inside the dialog use TaskUtils to execute it!
	}
	
}
