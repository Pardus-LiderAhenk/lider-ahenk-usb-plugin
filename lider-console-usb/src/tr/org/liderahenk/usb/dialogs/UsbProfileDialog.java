package tr.org.liderahenk.usb.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.DocumentEvent.EventType;

import org.eclipse.e4.ui.workbench.UIEvents.EventTypes;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.liderconsole.core.constants.LiderConstants;
import tr.org.liderahenk.liderconsole.core.dialogs.IProfileDialog;
import tr.org.liderahenk.liderconsole.core.exceptions.ValidationException;
import tr.org.liderahenk.liderconsole.core.model.Profile;
import tr.org.liderahenk.liderconsole.core.utils.SWTResourceManager;
import tr.org.liderahenk.liderconsole.core.widgets.Notifier;
import tr.org.liderahenk.usb.constants.UsbConstants;
import tr.org.liderahenk.usb.i18n.Messages;
import tr.org.liderahenk.usb.model.BlacklistWhitelistItem;
import tr.org.liderahenk.usb.utils.UsbUtils;

/**
 * Profile definition dialog for USB plugin.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class UsbProfileDialog implements IProfileDialog {

	private static final Logger logger = LoggerFactory.getLogger(UsbProfileDialog.class);

	// Widgets
	private Button btnCheckWebcam;
	private Combo cmbWebcam;
	private Button btnCheckPrinter;
	private Combo cmbPrinter;
	private Button btnCheckStorage;
	private Combo cmbStorage;
	private Button btnCheckMouseKeyboard;
	private Combo cmbMouseKeyboard;
	private Button btnCheckTable;
	private Composite tableComposite;
	private Button btnBlackList;
	private Button btnWhiteList;
	private Button btnAddListItem;
	private Button btnEditListItem;
	private Button btnDeleteListItem;
	private TableViewer tableViewer;
	private BlacklistWhitelistItem item;

	// Combo values & i18n labels
	private final String[] statusArr = new String[] { "ENABLE", "DISABLE" };
	private final String[] statusValueArr = new String[] { "1", "0" };

	@Override
	public void init() {
	}

	@Override
	public void createDialogArea(Composite parent, Profile profile) {
		logger.debug("Profile recieved: {}", profile != null ? profile.toString() : null);
		createPeripheralDeviceInputs(parent, profile);
		createTableArea(parent, profile);
	}

	/**
	 * Create input widgets for peripheral devices
	 * 
	 * @param composite
	 * @param profile
	 */
	private void createPeripheralDeviceInputs(final Composite parent, final Profile profile) {

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
		boolean isSelected = selectOption(cmbWebcam, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.WEBCAM) : null);
		cmbWebcam.setEnabled(isSelected);
		btnCheckWebcam.setSelection(isSelected);

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
		isSelected = selectOption(cmbPrinter, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.PRINTER) : null);
		cmbPrinter.setEnabled(isSelected);
		btnCheckPrinter.setSelection(isSelected);

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
		isSelected = selectOption(cmbMouseKeyboard, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.MOUSE_KEYBOARD) : null);
		cmbMouseKeyboard.setEnabled(isSelected);
		btnCheckMouseKeyboard.setSelection(isSelected);

		btnCheckStorage = new Button(composite, SWT.CHECK);
		btnCheckStorage.setText(Messages.getString("STORAGE"));
		btnCheckStorage.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCheckTable.getSelection()){
					btnCheckTable.setSelection(false);
					tableComposite.setVisible(false);
					tableComposite.update();
					tableComposite.pack();
				}
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
		isSelected = selectOption(cmbStorage, profile != null && profile.getProfileData() != null
				? profile.getProfileData().get(UsbConstants.PARAMETERS.STORAGE) : null);
		cmbStorage.setEnabled(isSelected);
		btnCheckStorage.setSelection(isSelected);
	}

	/**
	 * Create blacklist/whitelist table area
	 * 
	 * @param parent
	 * @param profile
	 */
	private void createTableArea(Composite parent, Profile profile) {

		btnCheckTable = new Button(parent, SWT.CHECK);
		btnCheckTable.setText(Messages.getString("WHITELIST_BLACKLIST_TABLE"));
		btnCheckTable.setSelection(false);
		btnCheckTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cmbStorage.isEnabled()){
					btnCheckStorage.setSelection(false);
					cmbStorage.setEnabled(false);
				}
				tableComposite.setVisible(btnCheckTable.getSelection());
				tableComposite.update();
				tableComposite.pack(btnCheckTable.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		btnCheckTable.setSelection(false);
		// Table composite contains all table-related widgets and table itself!
		tableComposite = new Composite(parent, SWT.BORDER);
		tableComposite.setLayout(new GridLayout(1, false));
		tableComposite.setVisible(false);
		// Radio buttons for blacklist/whitelist types
		Composite tableTypeComposite = new Composite(tableComposite, SWT.NONE);
		tableTypeComposite.setLayout(new GridLayout(2, true));

		btnWhiteList = new Button(tableTypeComposite, SWT.RADIO);
		btnWhiteList.setText(Messages.getString("USE_WHITELIST"));

		btnBlackList = new Button(tableTypeComposite, SWT.RADIO);
		btnBlackList.setText(Messages.getString("USE_BLACKLIST"));
		
		if(profile != null && profile.getProfileData() != null){ 
			if(profile.getProfileData().containsKey(UsbConstants.PARAMETERS.LIST_TYPE)){
				btnCheckTable.setSelection(true);
				tableComposite.setVisible(btnCheckTable.getSelection());
				tableComposite.update();
				tableComposite.pack(btnCheckTable.getSelection());
				if(profile.getProfileData().get(UsbConstants.PARAMETERS.LIST_TYPE).equals("whitelist")){
					btnBlackList.setSelection(false);
					btnWhiteList.setSelection(true);
				}else{
					btnBlackList.setSelection(true);
					btnWhiteList.setSelection(false);
				}
			}else{
				btnBlackList.setSelection(false);
				btnWhiteList.setSelection(true);
			}
		}
		

		createButtons(tableComposite);
		createTable(tableComposite, profile);
	}

	/**
	 * Create add, edit, delete buttons for the table
	 * 
	 * @param parent
	 */
	private void createButtons(final Composite parent) {
		final Composite tableButtonComposite = new Composite(parent, SWT.NONE);
		tableButtonComposite.setLayout(new GridLayout(3, false));

		btnAddListItem = new Button(tableButtonComposite, SWT.NONE);
		btnAddListItem.setText(Messages.getString("ADD"));
		btnAddListItem.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btnAddListItem.setImage(
				SWTResourceManager.getImage(LiderConstants.PLUGIN_IDS.LIDER_CONSOLE_CORE, "icons/16/add.png"));
		btnAddListItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BlacklistWhitelistItemDialog dialog = new BlacklistWhitelistItemDialog(
						Display.getDefault().getActiveShell(), tableViewer);
				dialog.create();
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		btnEditListItem = new Button(tableButtonComposite, SWT.NONE);
		btnEditListItem.setText(Messages.getString("EDIT"));
		btnEditListItem.setImage(
				SWTResourceManager.getImage(LiderConstants.PLUGIN_IDS.LIDER_CONSOLE_CORE, "icons/16/edit.png"));
		btnEditListItem.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btnEditListItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (null == getItem()) {
					Notifier.warning(null, Messages.getString("PLEASE_SELECT_ITEM"));
					return;
				}
				BlacklistWhitelistItemDialog dialog = new BlacklistWhitelistItemDialog(tableButtonComposite.getShell(),
						getItem(), tableViewer);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		btnDeleteListItem = new Button(tableButtonComposite, SWT.NONE);
		btnDeleteListItem.setText(Messages.getString("DELETE"));
		btnDeleteListItem.setImage(
				SWTResourceManager.getImage(LiderConstants.PLUGIN_IDS.LIDER_CONSOLE_CORE, "icons/16/delete.png"));
		btnDeleteListItem.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btnDeleteListItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (null == getItem()) {
					Notifier.warning(null, Messages.getString("PLEASE_SELECT_ITEM"));
					return;
				}
				@SuppressWarnings("unchecked")
				List<BlacklistWhitelistItem> items = (List<BlacklistWhitelistItem>) tableViewer.getInput();
				items.remove(tableViewer.getTable().getSelectionIndex());
				tableViewer.setInput(items);
				tableViewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * Create table
	 * 
	 * @param parent
	 * @param profile
	 */
	private void createTable(final Composite parent, Profile profile) {
		tableViewer = new TableViewer(parent,
				SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// Create table columns
		createTableColumns();

		// Configure table layout
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.getVerticalBar().setEnabled(true);
		table.getVerticalBar().setVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());

		if (profile != null && profile.getProfileData() != null
				&& profile.getProfileData().get(UsbConstants.PARAMETERS.LIST_ITEMS) != null) {
			@SuppressWarnings("unchecked")
			ArrayList<LinkedHashMap<String, String>> list = (ArrayList<LinkedHashMap<String, String>>) profile
					.getProfileData().get(UsbConstants.PARAMETERS.LIST_ITEMS);
			if (list != null) {
				List<BlacklistWhitelistItem> items = new ArrayList<BlacklistWhitelistItem>();
				for (LinkedHashMap<String, String> map : list) {
					BlacklistWhitelistItem item = new BlacklistWhitelistItem((String) map.get("vendor"),
							(String) map.get("model"), (String) map.get("serialNumber"));
					items.add(item);
				}
				tableViewer.setInput(items);
				tableViewer.refresh();
			}
		}

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = 140;
		gridData.horizontalAlignment = GridData.FILL;
		tableViewer.getControl().setLayoutData(gridData);

		// Hook up listeners
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				Object firstElement = selection.getFirstElement();
				firstElement = (BlacklistWhitelistItem) firstElement;
				if (firstElement instanceof BlacklistWhitelistItem) {
					setItem((BlacklistWhitelistItem) firstElement);
				}
				btnEditListItem.setEnabled(true);
				btnDeleteListItem.setEnabled(true);
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				BlacklistWhitelistItemDialog dialog = new BlacklistWhitelistItemDialog(parent.getShell(), getItem(),
						tableViewer);
				dialog.open();
			}
		});
	}

	/**
	 * Create table columns related to blacklist/whitelist items.
	 * 
	 */
	private void createTableColumns() {

		String[] titles = { Messages.getString("VENDOR"), Messages.getString("MODEL"),
				Messages.getString("SERIAL_NUMBER") };
		int[] bounds = { 200, 200, 200 };

		TableViewerColumn vendorColumn = createTableViewerColumn(titles[0], bounds[0]);
		vendorColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof BlacklistWhitelistItem) {
					return ((BlacklistWhitelistItem) element).getVendor();
				}
				return Messages.getString("UNTITLED");
			}
		});

		TableViewerColumn modelColumn = createTableViewerColumn(titles[1], bounds[1]);
		modelColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof BlacklistWhitelistItem) {
					return ((BlacklistWhitelistItem) element).getModel();
				}
				return Messages.getString("UNTITLED");
			}
		});

		TableViewerColumn serialNumberColumn = createTableViewerColumn(titles[2], bounds[2]);
		serialNumberColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof BlacklistWhitelistItem) {
					return ((BlacklistWhitelistItem) element).getSerialNumber();
				}
				return Messages.getString("UNTITLED");
			}
		});
	}

	/**
	 * Create new table viewer column instance.
	 * 
	 * @param title
	 * @param bound
	 * @return
	 */
	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		column.setAlignment(SWT.LEFT);
		return viewerColumn;
	}

	@Override
	public Map<String, Object> getProfileData() throws Exception {
		Map<String, Object> profileData = new HashMap<String, Object>();
		if (btnCheckWebcam.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.WEBCAM, UsbUtils.getSelectedValue(cmbWebcam));
		}
		if (btnCheckPrinter.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.PRINTER, UsbUtils.getSelectedValue(cmbPrinter));
		}
		if (btnCheckStorage.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.STORAGE, UsbUtils.getSelectedValue(cmbStorage));
		}
		if (btnCheckMouseKeyboard.getSelection()) {
			profileData.put(UsbConstants.PARAMETERS.MOUSE_KEYBOARD, UsbUtils.getSelectedValue(cmbMouseKeyboard));
		}
		if (btnCheckTable.getSelection()) {
			// Put list type
			profileData.put(UsbConstants.PARAMETERS.LIST_TYPE, btnBlackList.getSelection() ? "blacklist" : "whitelist");
			// Put list items
			@SuppressWarnings("unchecked")
			List<BlacklistWhitelistItem> items = (List<BlacklistWhitelistItem>) tableViewer.getInput();
			if (items != null) {
				profileData.put(UsbConstants.PARAMETERS.LIST_ITEMS, items);
			}
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
	private boolean selectOption(Combo combo, Object value) {
		if (value == null) {
			return false;
		}
		for (int i = 0; i < statusValueArr.length; i++) {
			if (statusValueArr[i].equalsIgnoreCase(value.toString())) {
				combo.select(i);
				return true;
			}
		}
		combo.select(0); // select first option by default.
		return false;
	}

	public BlacklistWhitelistItem getItem() {
		return item;
	}

	public void setItem(BlacklistWhitelistItem item) {
		this.item = item;
	}

	@Override
	public void validateBeforeSave() throws ValidationException {
		if(btnCheckTable.getSelection() && (tableViewer == null || tableViewer.getTable() == null || tableViewer.getTable().getItems() == null || tableViewer.getTable().getItemCount() == 0)){
			btnCheckTable.setSelection(false);
		}
	}

}
