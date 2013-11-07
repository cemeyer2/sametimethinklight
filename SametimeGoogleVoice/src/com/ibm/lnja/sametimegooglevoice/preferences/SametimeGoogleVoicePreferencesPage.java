package com.ibm.lnja.sametimegooglevoice.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ibm.lnja.sametimegooglevoice.activator.SametimeGoogleVoicePlugin;
import com.ibm.lnja.sametimegooglevoice.message.ChatMessageHandler;

/**
 * Preference page for adding, removing, and editing
 * quick responses.
 */

public class SametimeGoogleVoicePreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {
	
//	private Text notificationSpeed;
	private Button enableNotification;
	
	private Composite cmpParent;

	/**
	 * Create the preference page.
	 */
	public SametimeGoogleVoicePreferencesPage() {
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		cmpParent = parent;
		Composite container = new Composite(parent, SWT.NULL);
		
		
		enableNotification = new Button(container, SWT.CHECK);
		enableNotification.setBounds(10, 61, 160, 24);
		enableNotification.setText("Enable Notifications");
		enableNotification.setSelection(getBool(SametimeGoogleVoicePlugin.NOTIFICATIONS_ENABLED));
		enableNotification.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveResponses();
//				notificationSpeed.setEnabled(enableNotification.getSelection());
			}
		});
		
//		Label lblNewLabel = new Label(container, SWT.NONE);
//		lblNewLabel.setBounds(10, 91, 110, 17);
//		lblNewLabel.setText("Length (ms)");
//	
//		
//		notificationSpeed = new Text(container, SWT.BORDER);
//		notificationSpeed.setBounds(125, 91, 75, 27);
//		notificationSpeed.setText(String.valueOf(getInt(SametimeGoogleVoicePlugin.NOTIFICATIONS_SPEED)));
//		notificationSpeed.setEnabled(getBool(SametimeGoogleVoicePlugin.NOTIFICATIONS_ENABLED));		

		
		Button testButton = new Button(container, SWT.NONE);
		testButton.setBounds(10, 100, 160, 29);
		testButton.setText("Test Notifications");
		testButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveResponses();
				ChatMessageHandler.testNotification();
			}
		});
		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
	}
	
	private boolean getBool(String key){
		int val = SametimeGoogleVoicePlugin.getDefault().get(key);
		if(val > 0){
			return true;
		} else {
			return false;
		}
	}
	
	private int getInt(String key){
		return SametimeGoogleVoicePlugin.getDefault().get(key);
	}
	
	private int getInt(Button button){
		if(button.getSelection()){
			return 1;
		}
		return 0;
	}
	
	private int getInt(Text box){
		return Integer.parseInt(box.getText());
	}
	
	private boolean checkInt(Text box){
		String value = box.getText();
		try{
			Integer.parseInt(value);
			return true;
		} catch(NumberFormatException nfe){
			MessageDialog.openError(cmpParent.getShell(), "Error", "All values in text boxes must be integers");
			return false;
		}
	}

    public boolean performOk() {
    	boolean saved = saveResponses();
    	if(!saved){
        	SametimeGoogleVoicePlugin.getDefault().debug("error saving preferences");
    	}
    	return saved;
    }

    public boolean performCancel() {
    	SametimeGoogleVoicePlugin.getDefault().debug("Preferences canceled");
        return true;
    }

    private boolean saveResponses() {
    	SametimeGoogleVoicePlugin.getDefault().debug("Saving preferences");
//    	if(!checkInt(notificationSpeed)){
//    		return false;
//    	}
    	SametimeGoogleVoicePlugin.getDefault().set(SametimeGoogleVoicePlugin.NOTIFICATIONS_ENABLED, getInt(enableNotification));
//    	SametimeGoogleVoicePlugin.getDefault().set(SametimeGoogleVoicePlugin.NOTIFICATIONS_SPEED, getInt(notificationSpeed));
    	SametimeGoogleVoicePlugin.getDefault().writeProperties();
    	SametimeGoogleVoicePlugin.getDefault().debug("Preferences saved");
    	return true;
    }
}
