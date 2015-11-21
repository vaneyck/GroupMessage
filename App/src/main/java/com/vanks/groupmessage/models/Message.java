package com.vanks.groupmessage.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by vaneyck on 11/21/15.
 */
public class Message extends SugarRecord<Message> {
	String text;
	Long groupId;

	public Message () {}

	/**
	 * Retrieve a list of Recipients assigned to a Message object
	 * @return a List of Recipients
	 */
	public List<Recipient> getRecipients (){
		String messageIdAsString = this.getId().toString();
		return Recipient.find(Recipient.class, "message = ?", messageIdAsString);
	}

	/**
	 * Store Recipient against a Message
	 * @param phoneNumber the Recipients phone number
	 */
	public void addRecipient (String phoneNumber) {
		Recipient recipient =  new Recipient();

	}
}
