package tr.org.liderahenk.usb.utils;

import org.eclipse.swt.widgets.Combo;

public class UsbUtils {
	
	/**
	 * 
	 * @param combo
	 * @return selected value of the provided combo
	 */
	public static String getSelectedValue(Combo combo) {
		int selectionIndex = combo.getSelectionIndex();
		if (selectionIndex > -1 && combo.getItem(selectionIndex) != null
				&& combo.getData(selectionIndex + "") != null) {
			return combo.getData(selectionIndex + "").toString();
		}
		return "0";
	}

}
