package com.ibm.lnja.sametimethinklight.preferences;

import java.util.Arrays;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ibm.lnja.sametimethinklight.activator.SametimeThinklightPlugin;
import com.ibm.lnja.sametimethinklight.message.ChatMessageHandler;

/**
 * Preference page for adding, removing, and editing
 * quick responses.
 */

public class SametimeThinklightPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private Text imTextSpeed;
	private Text imTextCount;
	private Text fileSpeed;
	private Text fileCount;
	private Text newKeyword;
	private Button enableAll, enableIMText, enableFile, enableKeywords, addKeyword, delKeyword;
	private List keywords;
	
	private Composite cmpParent;
	
	
	
	/**
	 * Create the preference page.
	 */
	public SametimeThinklightPreferencesPage() {
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		cmpParent = parent;
		Composite container = new Composite(parent, SWT.NULL);
		
		enableAll = new Button(container, SWT.CHECK);
		enableAll.setBounds(10, 23, 160, 24);
		enableAll.setText("Enable ThinkLight");
		enableAll.setSelection(getBool(SametimeThinklightPlugin.GLOBAL_ENABLED));
		enableAll.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toggleAll();
			}
		});
		
		enableIMText = new Button(container, SWT.CHECK);
		enableIMText.setBounds(10, 61, 160, 24);
		enableIMText.setText("On Message Receive");
		enableIMText.setSelection(getBool(SametimeThinklightPlugin.IM_TEXT_ENABLED));
		enableIMText.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				imTextCount.setEnabled(enableIMText.getSelection());
				imTextSpeed.setEnabled(enableIMText.getSelection());
			}
		});
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 91, 87, 17);
		lblNewLabel.setText("Blink Count");
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setBounds(10, 125, 87, 17);
		lblNewLabel_1.setText("Blink Speed");
		
		imTextSpeed = new Text(container, SWT.BORDER);
		imTextSpeed.setBounds(95, 125, 75, 27);
		imTextSpeed.setText(String.valueOf(getInt(SametimeThinklightPlugin.IM_TEXT_SPEED)));
		imTextSpeed.setEnabled(getBool(SametimeThinklightPlugin.IM_TEXT_ENABLED));
		
		imTextCount = new Text(container, SWT.BORDER);
		imTextCount.setBounds(95, 91, 75, 27);
		imTextCount.setText(String.valueOf(getInt(SametimeThinklightPlugin.IM_TEXT_COUNT)));
		imTextCount.setEnabled(getBool(SametimeThinklightPlugin.IM_TEXT_ENABLED));
		
		fileSpeed = new Text(container, SWT.BORDER);
		fileSpeed.setBounds(95, 236, 75, 27);
		fileSpeed.setText(String.valueOf(getInt(SametimeThinklightPlugin.FILE_SPEED)));
		fileSpeed.setEnabled(getBool(SametimeThinklightPlugin.FILE_ENABLED));
		
		fileCount = new Text(container, SWT.BORDER);
		fileCount.setBounds(95, 202, 75, 27);
		fileCount.setText(String.valueOf(getInt(SametimeThinklightPlugin.FILE_COUNT)));
		fileCount.setEnabled(getBool(SametimeThinklightPlugin.FILE_ENABLED));
		
		Label label = new Label(container, SWT.NONE);
		label.setText("Blink Speed");
		label.setBounds(10, 236, 87, 17);
		
		Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("Blink Count");
		label_1.setBounds(10, 202, 87, 17);
		
		enableFile = new Button(container, SWT.CHECK);
		enableFile.setText("On File Receive");
		enableFile.setBounds(10, 172, 160, 24);
		enableFile.setSelection(getBool(SametimeThinklightPlugin.FILE_ENABLED));
		enableFile.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				fileCount.setEnabled(enableFile.getSelection());
				fileSpeed.setEnabled(enableFile.getSelection());
			}
		});
		

		
		Button testButton = new Button(container, SWT.NONE);
		testButton.setBounds(10, 300, 160, 29);
		testButton.setText("Test ThinkLight");
		testButton.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ChatMessageHandler.testBlink();
			}
		});
		
		enableKeywords = new Button(container, SWT.CHECK);
		enableKeywords.setBounds(212, 23, 158, 24);
		enableKeywords.setText("Only on key words");
		enableKeywords.setSelection(getBool(SametimeThinklightPlugin.KEYWORDS_ENABLED));
		
		keywords = new List(container, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
		keywords.setBounds(212, 63, 158, 166);
		loadKeywords();
		
		newKeyword = new Text(container, SWT.BORDER);
		newKeyword.setBounds(212, 236, 101, 30);
		
		addKeyword = new Button(container, SWT.NONE);
		addKeyword.setBounds(318, 236, 52, 30);
		addKeyword.setText("Add");
		addKeyword.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String keyword = newKeyword.getText();
				boolean hasKeyword = false;
				for(String word : keywords.getItems()){
					if(word.equals(keyword)){
						hasKeyword = true;
						break;
					}
				}
				if(!hasKeyword){
					keywords.add(keyword);
				}
				String[] items = keywords.getItems();
				Arrays.sort(items);
				keywords.setItems(items);
				
				newKeyword.setText("");
				newKeyword.forceFocus();
			}
		});
		
		delKeyword = new Button(container, SWT.NONE);
		delKeyword.setBounds(212, 276, 153, 30);
		delKeyword.setText("Remove Selected");
		delKeyword.addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int[] indices = keywords.getSelectionIndices();
				keywords.remove(indices);
				String[] items = keywords.getItems();
				Arrays.sort(items);
				keywords.setItems(items);
			}
		});
		
		enableKeywords.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				keywords.setEnabled(enableKeywords.getSelection());
				newKeyword.setEnabled(enableKeywords.getSelection());
				addKeyword.setEnabled(enableKeywords.getSelection());
				delKeyword.setEnabled(enableKeywords.getSelection());
			}
		});
		
		toggleAll();
		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
	}
	
	private boolean getBool(String key){
		int val = SametimeThinklightPlugin.getDefault().get(key);
		if(val > 0){
			return true;
		} else {
			return false;
		}
	}
	
	private int getInt(String key){
		return SametimeThinklightPlugin.getDefault().get(key);
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
	
	private void toggleAll(){
		if(enableAll.getSelection()){
			enableIMText.setEnabled(true);
			enableFile.setEnabled(true);
			enableKeywords.setEnabled(true);
			
			if(enableIMText.getSelection()){
				imTextCount.setEnabled(true);
				imTextSpeed.setEnabled(true);
			} else {
				imTextCount.setEnabled(false);
				imTextSpeed.setEnabled(false);
			}
			
			if(enableFile.getSelection()){
				fileCount.setEnabled(true);
				fileSpeed.setEnabled(true);
			} else {
				fileCount.setEnabled(false);
				fileSpeed.setEnabled(false);
			}
			
			if(enableKeywords.getSelection()){
				addKeyword.setEnabled(true);
				delKeyword.setEnabled(true);
				keywords.setEnabled(true);
				newKeyword.setEnabled(true);
			} else {
				addKeyword.setEnabled(false);
				delKeyword.setEnabled(false);
				keywords.setEnabled(false);
				newKeyword.setEnabled(false);
			}
		} else {
			enableIMText.setEnabled(false);
			enableFile.setEnabled(false);
			imTextCount.setEnabled(false);
			imTextSpeed.setEnabled(false);
			fileCount.setEnabled(false);
			fileSpeed.setEnabled(false);
			enableKeywords.setEnabled(false);
			addKeyword.setEnabled(false);
			delKeyword.setEnabled(false);
			keywords.setEnabled(false);
			newKeyword.setEnabled(false);
		}
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
        	SametimeThinklightPlugin.getDefault().debug("error saving preferences");
    	}
    	return saved;
    }

    public boolean performCancel() {
    	SametimeThinklightPlugin.getDefault().debug("Preferences canceled");
        return true;
    }

    private boolean saveResponses() {
    	SametimeThinklightPlugin.getDefault().debug("Saving preferences");
    	if(!checkInt(imTextCount)){
    		return false;
    	}
    	if(!checkInt(imTextSpeed)){
    		return false;
    	}
    	if(!checkInt(fileCount)){
    		return false;
    	}
    	if(!checkInt(fileSpeed)){
    		return false;
    	}
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.GLOBAL_ENABLED, getInt(enableAll));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.IM_TEXT_ENABLED, getInt(enableIMText));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.FILE_ENABLED, getInt(enableFile));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.KEYWORDS_ENABLED, getInt(enableKeywords));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.IM_TEXT_SPEED, getInt(imTextSpeed));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.IM_TEXT_COUNT, getInt(imTextCount));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.FILE_SPEED, getInt(fileSpeed));
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.FILE_COUNT, getInt(fileCount));
    	
    	saveKeywords();
    	
    	SametimeThinklightPlugin.getDefault().writeProperties();
    	SametimeThinklightPlugin.getDefault().debug("Preferences saved");
    	return true;
    }
    
    private void loadKeywords(){
    	String rawKeywords = SametimeThinklightPlugin.getDefault().getRaw(SametimeThinklightPlugin.KEYWORDS_KEYWORDS);
    	if(rawKeywords.length() == 0){
    		return;
    	}
    	String[] split = rawKeywords.split(SametimeThinklightPlugin.KEYWORDS_DELIMITER);
    	Arrays.sort(split);
    	keywords.removeAll();
    	keywords.setItems(split);
    }
    
    private void saveKeywords(){
    	String[] items = keywords.getItems();
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0; i < items.length-1; i++){
    		builder.append(items[i]);
    		builder.append(SametimeThinklightPlugin.KEYWORDS_DELIMITER);
    	}
    	if(items.length > 0){
    		builder.append(items[items.length-1]);
    	}
    	String rawKeywords = builder.toString();
    	SametimeThinklightPlugin.getDefault().debug("raw keywords: "+rawKeywords);
    	SametimeThinklightPlugin.getDefault().set(SametimeThinklightPlugin.KEYWORDS_KEYWORDS, rawKeywords);
    }
}
