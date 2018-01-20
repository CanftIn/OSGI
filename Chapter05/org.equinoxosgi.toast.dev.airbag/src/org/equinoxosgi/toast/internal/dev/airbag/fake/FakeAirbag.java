package org.equinoxosgi.toast.internal.dev.airbag.fake;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.equinoxosgi.toast.dev.airbag.IAirbag;
import org.equinoxosgi.toast.dev.airbag.IAirbagListener;

public class FakeAirbag implements IAirbag {
	private List<IAirbagListener> listeners;
	private Job job;
	private boolean isRunning;

	public FakeAirbag() {
		super();
		listeners = new ArrayList<>();
	}

	@Override
	public synchronized void addListener(IAirbagListener listener) {
		listeners.add(listener);
	}

	@Override
	public synchronized void removeListener(IAirbagListener listener) {
		listeners.remove(listener);
	}

	private synchronized void deploy() {
		for (IAirbagListener listener : listeners) {
			listener.deployed();
		}
	}

	public synchronized void startup() {
		isRunning = true;
		job = new Job("FakeAirBag") {
			@Override
			protected IStatus run(IProgressMonitor iProgressMonitor) {
				deploy();
				if(isRunning)
				{
					schedule(5000);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule(5000);
	}
	
	public synchronized void shutdown() {
		isRunning = false;
		job.cancel();
		try {
			job.join();
		} catch (InterruptedException e) {
			// 关闭中，可忽略
		}
		job.schedule(5000);
	}
}
