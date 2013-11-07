package com.ibm.lnja.sametimegooglevoice.message;


import com.ibm.collaboration.realtime.messages.MessageHandler;
import com.ibm.collaboration.realtime.messages.MessageHandlerAdapter;
import com.ibm.lnja.sametimegooglevoice.activator.SametimeGoogleVoicePlugin;


/**
 * Adapter to register to the extension point exposed by sametime
 * @author Charlie Meyer <cemeyer@us.ibm.com>
 * @version 2012.08.29
 */
public class ChatMessageHandlerAdapter extends MessageHandlerAdapter {


	public ChatMessageHandlerAdapter() {

		//Register the actual message handler with the adapter
		super(new ChatMessageHandler());
		SametimeGoogleVoicePlugin.getDefault().debug("Adapter created default");


	}


	public ChatMessageHandlerAdapter(MessageHandler handler) {
		super(handler);
		SametimeGoogleVoicePlugin.getDefault().debug("Adapter created with handler");
	}


}
