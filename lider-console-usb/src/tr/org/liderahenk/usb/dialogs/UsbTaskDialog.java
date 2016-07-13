package tr.org.liderahenk.usb.dialogs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import tr.org.liderahenk.liderconsole.core.dialogs.DefaultTaskDialog;
import tr.org.liderahenk.liderconsole.core.exceptions.ValidationException;
import tr.org.liderahenk.usb.constants.UsbConstants;
import tr.org.liderahenk.usb.i18n.Messages;
import tr.org.liderahenk.usb.utils.UsbUtils;

/**
 * Task execution dialog for USB plugin.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class UsbTaskDialog extends DefaultTaskDialog {

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

	public UsbTaskDialog(Shell parentShell, Set<String> dnSet) {
		super(parentShell, dnSet);
	}

	@Override
	public String createTitle() {
		return Messages.getString("USB_MANAGEMENT");
	}

	@Override
	public Control createTaskDialogArea(Composite parent) {

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

		return composite;
	}

	@Override
	public void validateBeforeExecution() throws ValidationException {
		// TODO
		// TODO
		// TODO
	}

	@Override
	public Map<String, Object> getParameterMap() {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		if (btnCheckWebcam.getSelection()) {
			parameterMap.put(UsbConstants.PARAMETERS.WEBCAM, UsbUtils.getSelectedValue(cmbWebcam));
		}
		if (btnCheckPrinter.getSelection()) {
			parameterMap.put(UsbConstants.PARAMETERS.PRINTER, UsbUtils.getSelectedValue(cmbPrinter));
		}
		if (btnCheckStorage.getSelection()) {
			parameterMap.put(UsbConstants.PARAMETERS.STORAGE, UsbUtils.getSelectedValue(cmbStorage));
		}
		if (btnCheckMouseKeyboard.getSelection()) {
			parameterMap.put(UsbConstants.PARAMETERS.MOUSE_KEYBOARD, UsbUtils.getSelectedValue(cmbMouseKeyboard));
		}
		return parameterMap;
	}

	@Override
	public String getCommandId() {
		return "MANAGE-USB";
	}

	@Override
	public String getPluginName() {
		return UsbConstants.PLUGIN_NAME;
	}

	@Override
	public String getPluginVersion() {
		return UsbConstants.PLUGIN_VERSION;
	}

}
