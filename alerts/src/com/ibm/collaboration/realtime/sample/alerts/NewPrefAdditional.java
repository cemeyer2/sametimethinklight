package com.ibm.collaboration.realtime.sample.alerts;

/*
 * Licensed Materials - Property of IBM
 *
 * L-KBIM-82KJL8
 *
 * (C) Copyright IBM Corp. 2006, 2010. All rights reserved.
 *
 * US Government Users Restricted Rights- Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import com.ibm.collaboration.realtime.alertmanager.NotifyWindowPreference;

/**
 * Sample contribution to Notifications page of the preferences dialog;
 * this one adds a checkbox to the standard notification controls pane.
 */
public class NewPrefAdditional extends NotifyWindowPreference {

	protected void createNotificationControls(Composite parent) {
		super.createNotificationControls(parent);

		Button something = new Button(parent, SWT.CHECK);
		something.setText("STIG: Additional Alert Option");
	}

	public String getLabel() {
		return "STIG: New pref with additional control";
	}

	public PreferencePage getPrefPage() {
		return null;
	}

	public boolean isVisible() {
		return true;
	}

	public void setPrefPage(PreferencePage page) {
	}
}
