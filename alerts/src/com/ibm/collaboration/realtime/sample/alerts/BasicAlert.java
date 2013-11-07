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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.ibm.collaboration.realtime.messages.AlertUserActionMessage;
import com.ibm.collaboration.realtime.messages.AlertUserMessage;
import com.ibm.collaboration.realtime.ui.alert.AlertButton;
import com.ibm.collaboration.realtime.ui.alert.AlertFactory;
import com.ibm.collaboration.realtime.ui.alert.AlertOptions;

/**
 * This class demonstrates how to generate and process a basic pop-up alert.
 */
public class BasicAlert
{
    /**
     * Unique ID used to identify this application in the AlertUserActionMessage
     * generated by the alert handler. Although we use the class name here, that's
     * not a requirement.
     */
    public static final String ALERT_APPLICATION_ID = "com.ibm.collaboration.realtime.sample.alerts.BasicAlert";

    public static final String ALERT_BUTTON_LABEL_1 = "Button 1";
    public static final String ALERT_BUTTON_LABEL_2 = "Button 2";

    /**
     * This class contains only static methods, so hide the default
     * constructor to prevent instantiation.
     */
    private BasicAlert() {
    }

    /**
     * Creates a pop-up alert by posting an AlertUserMessage. This method is called
     * by the SampleContactListToolsAlertAction action (in the .actions subpackage),
     * which is invoked from a menu item on contact list window Tools menu.
     */
    public static void createAlert() {

        // First, create an AlertOptions object and set the title and message text.
        AlertOptions options = AlertFactory.createAlertOptions();
        options.setTitle("Lotus Sametime SDK Sample Alert");
        options.setMessage("Click one of my buttons and see what happens!");

        // Create the buttons and add them to the options. Each button will be
        // associated with an ApplicationDefinedMessage message event that will
        // be generated when the button is pressed. For each message, a property
        // will be set with the button label as its value.
        AlertUserActionMessage message1 = new AlertUserActionMessage();
        message1.setSourceId(ALERT_APPLICATION_ID);
        message1.setActionId(ALERT_BUTTON_LABEL_1);

        AlertButton button1 = AlertFactory.createAlertButton();
        button1.setLabel(ALERT_BUTTON_LABEL_1);
        button1.setToolTipText("Please click me, I'm the button you want!");
        button1.setMessage(message1);
        options.addAlertButton(button1);

        AlertUserActionMessage message2 = new AlertUserActionMessage();
        message2.setSourceId(ALERT_APPLICATION_ID);
        message2.setActionId(ALERT_BUTTON_LABEL_2);

        AlertButton button2 = AlertFactory.createAlertButton();
        button2.setLabel(ALERT_BUTTON_LABEL_2);
        button2.setToolTipText("Click me -- I'm much better than the other button!");
        button2.setMessage(message2);
        options.addAlertButton(button2);

        // Create and post an AlertUserMessage to generate the alert.
        // AlertUserActionMessageHandler (in this package) will listen for an
        // ApplicationDefinedMessage, generated when a button is pressed,
        // and call the handleMessage method in this class.
        AlertUserMessage alertMessage = new AlertUserMessage();
        alertMessage.setAlertOptions(options);
        alertMessage.post();
    }

    /**
     * Handles an AlertUserActionMessage that was generated by pressing a
     * button on a pop-up alert. This method is called by the message handler
     * in AlertUserActionMessageHandler (in this package).
     */
    public static void handleMessage(AlertUserActionMessage message) {

        // If this message has our application ID, display the value of
        // the property that contains the button label.
        if (message != null && ALERT_APPLICATION_ID.equals(message.getSourceId())) {
            final String whichButton = message.getActionId();
            Display.getDefault().asyncExec(
                    new Runnable() {
                        public void run() {
                            MessageDialog.openInformation(null, "Sample Alert Button Pressed",
                                    "The following button was pressed: " + whichButton);
                        }
                    }
            );
        }
    }
}
