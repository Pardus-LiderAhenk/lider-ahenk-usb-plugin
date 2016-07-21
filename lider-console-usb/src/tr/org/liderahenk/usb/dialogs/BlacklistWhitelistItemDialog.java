package tr.org.liderahenk.usb.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import tr.org.liderahenk.liderconsole.core.dialogs.DefaultLiderTitleAreaDialog;
import tr.org.liderahenk.liderconsole.core.widgets.Notifier;
import tr.org.liderahenk.usb.i18n.Messages;
import tr.org.liderahenk.usb.model.BlacklistWhitelistItem;

/**
 * Dialog for blacklist/whitelist items
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class BlacklistWhitelistItemDialog extends DefaultLiderTitleAreaDialog {

	// Model
	private BlacklistWhitelistItem item;
	// Table
	private TableViewer tableViewer;
	// Widgets
	private Text txtVendor;
	private Text txtModel;
	private Text txtSerialNumber;

	public BlacklistWhitelistItemDialog(Shell parentShell, TableViewer tableViewer) {
		super(parentShell);
		this.tableViewer = tableViewer;
	}

	public BlacklistWhitelistItemDialog(Shell parentShell, BlacklistWhitelistItem item, TableViewer tableViewer) {
		super(parentShell);
		this.item = item;
		this.tableViewer = tableViewer;
	}

	@Override
	public void create() {
		super.create();
		setTitle(Messages.getString("BLACKLIST_WHITELIST_ITEM"));
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(2, false));

		Label lblVendor = new Label(composite, SWT.NONE);
		lblVendor.setText(Messages.getString("VENDOR"));

		txtVendor = new Text(composite, SWT.BORDER);
		txtVendor.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		if (item != null && item.getVendor() != null) {
			txtVendor.setText(item.getVendor());
		}

		Label lblModel = new Label(composite, SWT.NONE);
		lblModel.setText(Messages.getString("MODEL"));

		txtModel = new Text(composite, SWT.BORDER);
		txtModel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		if (item != null && item.getModel() != null) {
			txtModel.setText(item.getModel());
		}

		Label lblSerialNumber = new Label(composite, SWT.NONE);
		lblSerialNumber.setText(Messages.getString("SERIAL_NUMBER"));

		txtSerialNumber = new Text(composite, SWT.BORDER);
		txtSerialNumber.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		if (item != null && item.getSerialNumber() != null) {
			txtSerialNumber.setText(item.getSerialNumber());
		}

		return composite;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void okPressed() {

		setReturnCode(OK);

		if (txtVendor.getText().isEmpty() && txtModel.getText().isEmpty() && txtSerialNumber.getText().isEmpty()) {
			Notifier.error(null, Messages.getString("FILL_AT_LEAST_ONE_FIELD"));
			return;
		}

		boolean editMode = true;
		if (item == null) {
			item = new BlacklistWhitelistItem();
			editMode = false;
		}
		// Set values
		item.setModel(txtModel.getText());
		item.setVendor(txtVendor.getText());
		item.setSerialNumber(txtSerialNumber.getText());

		// Get previous items...
		List<BlacklistWhitelistItem> items = (List<BlacklistWhitelistItem>) tableViewer.getInput();
		if (items == null) {
			items = new ArrayList<BlacklistWhitelistItem>();
		}

		if (editMode) {
			int index = tableViewer.getTable().getSelectionIndex();
			if (index > -1) {
				// Override previous item!
				items.set(index, item);
			}
		} else {
			// New item!
			items.add(item);
		}

		tableViewer.setInput(items);
		tableViewer.refresh();

		close();
	}

}
