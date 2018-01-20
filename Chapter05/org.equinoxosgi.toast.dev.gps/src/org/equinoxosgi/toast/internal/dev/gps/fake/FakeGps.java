package org.equinoxosgi.toast.internal.dev.gps.fake;

import org.equinoxosgi.toast.dev.gps.IGps;

public class FakeGps implements IGps {

	@Override
	public int getHeading() {
		return 90; // 90度（东）
	}

	@Override
	public int getLatitude() {
		return 3776999; // 北纬37.76999度
	}

	@Override
	public int getLongitude() {
		return -12244694; // 西经122.44694度
	}

	@Override
	public int getSpeed() {
		return 50; // 50千米/小时
	}
}
