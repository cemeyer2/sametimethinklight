package com.ibm.lnja.sametimegooglevoice.message;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.ibm.collaboration.realtime.chatwindow.ChatControllerService;
import com.ibm.collaboration.realtime.contacts.search.DirectoryInfo;
import com.ibm.collaboration.realtime.messages.DefaultMessageHandler;
import com.ibm.collaboration.realtime.messages.FileReceivedMessage;
import com.ibm.collaboration.realtime.messages.Message;
import com.ibm.collaboration.realtime.messages.im.ImTextReceivedMessage;
import com.ibm.collaboration.realtime.messages.im.ManyToManyTextReceivedMessage;
import com.ibm.collaboration.realtime.people.PeopleUtil;
import com.ibm.collaboration.realtime.people.Person;
import com.ibm.collaboration.realtime.servicehub.ServiceException;
import com.ibm.collaboration.realtime.servicehub.ServiceHub;
import com.ibm.lnja.sametimegooglevoice.activator.SametimeGoogleVoicePlugin;
import com.ibm.lnja.sametimegooglevoice.gvoice.GoogleVoiceDaemon;
import com.lotus.sametime.core.comparch.STSession;

/**
 * Handler that actually does the notifications
 * @author Charlie Meyer <cemeyer@us.ibm.com>
 * @version 2012.10.09
 */
public class ChatMessageHandler extends DefaultMessageHandler {

	
	private static STSession session = null; 
    private static Person me = null;

	public ChatMessageHandler(){
		SametimeGoogleVoicePlugin.getDefault().debug("Handler Created");
		if(me == null){
			me = PeopleUtil.getLocalPerson(); 
		}
		GoogleVoiceDaemon.getDaemon("charlie@charliemeyer.net", "password");
	}
	

	private static synchronized void notify(final Person from, final String text, final boolean isFile, final String convoId){
		System.out.println("gvoice got a message");
	}
	
	

	public void handleMessage(FileReceivedMessage arg0) {
		super.handleMessage(arg0);
	}

	public void handleMessage(ImTextReceivedMessage arg0) {
		SametimeGoogleVoicePlugin.getDefault().debug("ImTextReceivedMessage");
		notify(PeopleUtil.getPersonById(arg0.getPartnerUniqueId()), arg0.getText(), false, arg0.getConversationId());
		super.handleMessage(arg0);
	}

	public void handleMessage(ManyToManyTextReceivedMessage arg0) {
		SametimeGoogleVoicePlugin.getDefault().debug("ManyToManyTextReceivedMessage");
		if(!me.getId().equals(arg0.getPartnerUniqueId())){ //dont show notifications in group chats for messages you send
			notify(PeopleUtil.getPersonById(arg0.getPartnerUniqueId()), arg0.getText(), false, arg0.getConversationId());
		}
		super.handleMessage(arg0);
	}

	@Override
	public void handleDefaultMessage(Message arg0) {
		//dont do anything special
	}

	public static void testNotification(){
		if(me == null){
			me = PeopleUtil.getLocalPerson(); 
		}
		notify(me, "Hello, World!", false, null);
	}
}
