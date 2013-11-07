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

import com.ibm.collaboration.realtime.alertmanager.NotifyWindowPreference;


/**
 * Sample contribution to Notifications page of the preferences dialog;
 * this one shows only the "Flash Window" standard notification controls.
 */
public class NewPrefFlashWindow extends NotifyWindowPreference {

	public NewPrefFlashWindow() {
		setShowBringWindowFront(false);
		setShowFlashWindow(true);
		setShowPlaySound(false);
		setShowShowAlert(false);
	}
	public String getLabel() {
		return "STIG: New pref flash window";
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
