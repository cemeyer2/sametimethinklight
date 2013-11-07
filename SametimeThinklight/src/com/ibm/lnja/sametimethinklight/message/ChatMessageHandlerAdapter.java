package com.ibm.lnja.sametimethinklight.message;


import com.ibm.collaboration.realtime.messages.MessageHandler;

import com.ibm.collaboration.realtime.messages.MessageHandlerAdapter;
import com.ibm.lnja.sametimethinklight.activator.SametimeThinklightPlugin;


/**
 * Adapter to register to the extension point exposed by sametime
 * @author Charlie Meyer <cemeyer@us.ibm.com>
 * @version 2012.08.29
 */
public class ChatMessageHandlerAdapter extends MessageHandlerAdapter {


	public ChatMessageHandlerAdapter() {

		//Register the actual message handler with the adapter
		super(new ChatMessageHandler());
		SametimeThinklightPlugin.getDefault().debug("Adapter created default");


	}


	public ChatMessageHandlerAdapter(MessageHandler handler) {
		super(handler);
		SametimeThinklightPlugin.getDefault().debug("Adapter created with handler");
	}


}
