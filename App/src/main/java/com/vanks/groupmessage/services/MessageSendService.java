package com.vanks.groupmessage.services;

import android.app.IntentService;
import android.content.Intent;

import com.crashlytics.android.Crashlytics;
import com.vanks.groupmessage.enums.DispatchStatus;
import com.vanks.groupmessage.models.persisted.Dispatch;
import com.vanks.groupmessage.utils.DispatchUtil;
import com.vanks.groupmessage.utils.PreferenceUtil;
import com.vanks.groupmessage.utils.ScheduleUtil;

import java.util.List;

public class MessageSendService extends IntentService {

	public MessageSendService() {
		super("MessageSendService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent != null) {
			retrieveAndSendDispatches();
			ScheduleUtil.scheduleMessageSendService(getApplicationContext());
		}
	}

	private void retrieveAndSendDispatches () {
		List<Dispatch> dispatchListToSend = DispatchUtil.getDispatchesToSend(getApplicationContext());
		markDispatchesAsInFlight(dispatchListToSend);
		for (Dispatch dispatch : dispatchListToSend) {
			sendDispatch(dispatch);
		}
	}

	private void sendDispatch (Dispatch dispatch) {
		DispatchUtil.sendDispatch(getApplicationContext(), dispatch);

		int delay = PreferenceUtil.getDispatchDelay(getApplicationContext()) * 1000;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Crashlytics.logException(e);
		}
	}

	private void markDispatchesAsInFlight (List<Dispatch> dispatchList) {
		for (Dispatch dispatch : dispatchList) {
			DispatchUtil.updateDispatch(dispatch, DispatchStatus.PENDING);
		}
	}
}
