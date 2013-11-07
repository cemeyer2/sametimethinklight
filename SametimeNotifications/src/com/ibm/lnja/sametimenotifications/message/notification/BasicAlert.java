package com.ibm.lnja.sametimenotifications.message.notification;

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


import org.eclipse.swt.graphics.Image;

import com.ibm.collaboration.realtime.messages.AlertUserActionMessage;
import com.ibm.collaboration.realtime.messages.AlertUserMessage;
import com.ibm.collaboration.realtime.people.Person;
import com.ibm.collaboration.realtime.ui.alert.AlertFactory;
import com.ibm.collaboration.realtime.ui.alert.AlertOptions;
import com.ibm.lnja.sametimenotifications.activator.SametimeNotificationsPlugin;

/**
 * This class demonstrates how to generate and process a basic pop-up alert.
 */
public class BasicAlert
{
    private BasicAlert() {
    }

    public static void createAlert(Person from, String text, Image image) {
    	if(text == null){
    		return;
    	}
//    	SametimeNotificationsPlugin.getDefault().debug("msg orig: "+text);
    	String replaced = text.replaceAll("\\<.*?\\>", "");
//    	SametimeNotificationsPlugin.getDefault().debug("msg rep: "+replaced);
    	if(replaced.trim().length() == 0){
    		return;
    	}
        AlertOptions options = AlertFactory.createAlertOptions();
        options.setTitle("New Message");
        options.setSenderId(from.getId());
        options.setMessage(replaced);
        options.setMessageImage(image);
        options.setAlertType(AlertOptions.TYPE_ANNOUNCEMENT);
        AlertUserMessage alertMessage = new AlertUserMessage();
        alertMessage.setAlertOptions(options);
        alertMessage.post();
    }

    public static void handleMessage(AlertUserActionMessage message) {

    }
}
