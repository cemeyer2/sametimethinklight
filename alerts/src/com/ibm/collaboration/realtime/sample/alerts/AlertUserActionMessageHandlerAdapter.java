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

import com.ibm.collaboration.realtime.messages.MessageHandlerAdapter;

/**
 * Message handler adapter that simply forwards to its
 * handler class; more sophisticated implementations could
 * do initialization in this class.
 *
 * <b>See Also:</b> com.ibm.collaboration.realtime.messages.MessageHandlerListener extension point.
 */

public class AlertUserActionMessageHandlerAdapter extends MessageHandlerAdapter {
	public AlertUserActionMessageHandlerAdapter() {
		super(new AlertUserActionMessageHandler());
	}
}