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

import com.ibm.collaboration.realtime.messages.AlertUserActionMessage;
import com.ibm.collaboration.realtime.messages.DefaultMessageHandler;
import com.ibm.collaboration.realtime.messages.Message;

/**
 * Demonstrate how to "listen" for key message events using the
 * <code>com.ibm.collaboration.realtime.messages.MessageHandlerListener</code>
 * extension point.
 */

public class AlertUserActionMessageHandler extends DefaultMessageHandler {

    public void handleMessage(AlertUserActionMessage message) {
        // -----------------------------------------------------------
        // This handles messages generated when a button is pressed on
        // the sample pop-up alert. Let the Alert class handle the
        // message.
        // -----------------------------------------------------------
        BasicAlert.handleMessage(message);
    }


    public void handleDefaultMessage(Message message) {
		// -----------------------------------------------------------
		// We don't expect to get here, but we'll ignore any messages
		// we don't plan to handle
		// -----------------------------------------------------------
	}
}