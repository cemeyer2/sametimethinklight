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
 * this one shows a custom alert window. See
 * <code>com.ibm.collaboration.realtime.alertmanager.alertSettings</code>
 * extension for more details.
 */
public class NewOneToOneChat extends NotifyWindowPreference {

	private PreferencePage parent;
	public static final String ALERT_SETTINGS_KEY = "/app/sample/alerts/ping";

	public NewOneToOneChat() {
		setShowBringWindowFront(false);
		setShowFlashWindow(false);
		setShowPlaySound(false);
		setShowShowAlert(true);
	}

	public String getLabel() {
		return "STIG: New one-to-one chat ping";
	}

	public boolean isVisible() {
		return true;
	}

	public PreferencePage getPrefPage() {
		return parent;
	}

	public void setPrefPage(PreferencePage page) {
		parent = page;
	}

	protected String getAlertSettingsKey() {
		return ALERT_SETTINGS_KEY;
	}
}
