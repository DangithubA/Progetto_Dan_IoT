package com.station.cooking.cheese.utils;


/**
 +---------------+------+---------+
 |         SenML | JSON | Type    |
 +---------------+------+---------+
 |     Base Name | bn   | String  |
 |     Base Time | bt   | Number  |
 |     Base Unit | bu   | String  |
 |    Base Value | bv   | Number  |
 |       Version | bver | Number  |
 |          Name | n    | String  |
 |          Unit | u    | String  |
 |         Value | v    | Number  |
 |  String Value | vs   | String  |
 | Boolean Value | vb   | Boolean |
 |    Data Value | vd   | String  |
 |     Value Sum | s    | Number  |
 |          Time | t    | Number  |
 |   Update Time | ut   | Number  |
 +---------------+------+---------+
 */

public class SenMLRecord {

	private String bn;

	private String u;

	private Number v;

	private Number t;

	public SenMLRecord() {
	}

	public SenMLRecord(String bn, String bu, Number bv, Number bver, String n, String u, Number v, String vs, Boolean vb, String vd, Number s, Number t, Number ut) {
		this.bn = bn;
		this.u = u;
		this.v = v;
		this.t = t;
	}

	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public Number getV() {
		return v;
	}

	public void setV(Number v) {
		this.v = v;
	}

	public Number getT() {
		return t;
	}

	public void setT(Number t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "SenML [ " + (bn != null ? "bn=" + bn + "  " : "")
				+ (u != null ? "u=" + u + "  " : "") + (v != null ? "v=" + v + "  " : "")
				+ (t != null ? "t=" + t + "  " : "") + "]";
	}

}
