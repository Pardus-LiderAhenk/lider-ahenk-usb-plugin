package tr.org.liderahenk.usb.dialogs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.liderconsole.core.dialogs.IProfileDialog;
import tr.org.liderahenk.liderconsole.core.model.Profile;
import tr.org.liderahenk.usb.constants.UsbConstants;
import tr.org.liderahenk.usb.i18n.Messages;

/**
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class UsbProfileDialog implements IProfileDialog {

	private static final Logger logger = LoggerFactory.getLogger(UsbProfileDialog.class);

	private Button btnCheckWebcam;
	private Combo cmbWebcam;
	private Button btnCheckPrinter;
	private Combo cmbPrinter;
	private Button btnCheckStorage;
	private Combo cmbStorage;
	private Button btnCheckMouseKeyboard;
	private Combo cmbMouseKeyboard;

	// Combo values & i18n labels
	private final String[] statusArr = new String[] { "ENABLE", "DISABLE" };
	private final String[] statusValueArr = new String[] { "1", "0" };

	@Override
	public void init() {
	}

	@Override
	public void createDialogArea(Composite parent, Profile profile) {

		logger.debug("Profile recieved: {}", profile != null ? profile.toString() : null);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		btnCheckWebcam = new Button(composite, SWT.CHECK);
		btnCheckWebcam.setText(Messages.getString("WEBCAM"));
		btnCheckWebcam.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmbWebcam.setEnabled(btnCheckWebcam.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		cmbWebcam = new Combo(composite, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbWebcam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		for (int i = 0; i < statusArr.length; i++) {
			String i18n = Messages.getString(statusArr[i]);
			if (i18n != null && !i18n.isEmpty()) {
				cmbWebcam.add(i18n);
				cmbWebcam.setData(i + "", statusValueArr[i]);
			}
		}
		cmbWebcam.setEnabled(false);
		selectOption(cmbWebcam, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.WEBCAM) : null);

		btnCheckPrinter = new Button(composite, SWT.CHECK);
		btnCheckPrinter.setText(Messages.getString("PRINTER"));
		btnCheckPrinter.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmbPrinter.setEnabled(btnCheckPrinter.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		cmbPrinter = new Combo(composite, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbPrinter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		for (int i = 0; i < statusArr.length; i++) {
			String i18n = Messages.getString(statusArr[i]);
			if (i18n != null && !i18n.isEmpty()) {
				cmbPrinter.add(i18n);
				cmbPrinter.setData(i + "", statusValueArr[i]);
			}
		}
		cmbPrinter.setEnabled(false);
		selectOption(cmbPrinter, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.PRINTER) : null);

		btnCheckStorage = new Button(composite, SWT.CHECK);
		btnCheckStorage.setText(Messages.getString("STORAGE"));
		btnCheckStorage.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmbStorage.setEnabled(btnCheckStorage.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		cmbStorage = new Combo(composite, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbStorage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		for (int i = 0; i < statusArr.length; i++) {
			String i18n = Messages.getString(statusArr[i]);
			if (i18n != null && !i18n.isEmpty()) {
				cmbStorage.add(i18n);
				cmbStorage.setData(i + "", statusValueArr[i]);
			}
		}
		cmbStorage.setEnabled(false);
		selectOption(cmbStorage, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.STORAGE) : null);

		btnCheckMouseKeyboard = new Button(composite, SWT.CHECK);
		btnCheckMouseKeyboard.setText(Messages.getString("MOUSE_KEYBOARD"));
		btnCheckMouseKeyboard.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cmbMouseKeyboard.setEnabled(btnCheckMouseKeyboard.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		cmbMouseKeyboard = new Combo(composite, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbMouseKeyboard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		for (int i = 0; i < statusArr.length; i++) {
			String i18n = Messages.getString(statusArr[i]);
			if (i18n != null && !i18n.isEmpty()) {
				cmbMouseKeyboard.add(i18n);
				cmbMouseKeyboard.setData(i + "", statusValueArr[i]);
			}
		}
		cmbMouseKeyboard.setEnabled(false);
		selectOption(cmbMouseKeyboard, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.MOUSE_KEYBOARD) : null);
	}

	@Override
	public Map<String, Object> getProfileData() throws Exception {
		Map<String, Object> profileData = new HashMap<String, Object>();
		if (btnCheckWebcam.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.WEBCAM, getSelectedValue(cmbWebcam));
		}
		if (btnCheckPrinter.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.PRINTER, getSelectedValue(cmbPrinter));
		}
		if (btnCheckStorage.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.STORAGE, getSelectedValue(cmbStorage));
		}
		if (btnCheckMouseKeyboard.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.MOUSE_KEYBOARD, getSelectedValue(cmbMouseKeyboard));
		}
		return profileData;
	}

	/**
	 * If the provided value is not null and matches one of the combo options,
	 * the matching option will be selected. Otherwise first option will be
	 * selected by default.
	 * 
	 * @param combo
	 * @param value
	 */
	private void selectOption(Combo combo, Object value) {
		if (value == null) {
			return;
		}
		boolean isSelected = false;
		for (int i = 0; i < statusValueArr.length; i++) {
			if (statusValueArr[i].equalsIgnoreCase(value.toString())) {
				combo.select(i);
				isSelected = true;
				break;
			}
		}
		if (!isSelected) {
			combo.select(0); // select first option by default.
		}
	}

	/**
	 * 
	 * @param combo
	 * @return selected value of the provided combo
	 */
	private String getSelectedValue(Combo combo) {
		int selectionIndex = combo.getSelectionIndex();
		if (selectionIndex > -1 && combo.getItem(selectionIndex) != null
				&& combo.getData(selectionIndex + "") != null) {
			return combo.getData(selectionIndex + "").toString();
		}
		return "0";
	}

}
