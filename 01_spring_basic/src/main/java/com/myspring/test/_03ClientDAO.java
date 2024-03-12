package com.myspring.test;

import java.util.ArrayList;
import java.util.HashMap;

public class _03ClientDAO {
	
	
	
	private String schoolName;
	private ArrayList<String> clientList;

	private HashMap<String, String> clinetmap1;
	private HashMap<String, _03Client> clinetmap2;

	public HashMap<String, String> getClinetmap1() {
		return clinetmap1;
	}
	public void setClinetmap1(HashMap<String, String> clinetmap1) {
		this.clinetmap1 = clinetmap1;
	}
	public HashMap<String, _03Client> getClinetmap2() {
		return clinetmap2;
	}
	public void setClinetmap2(HashMap<String, _03Client> clinetmap2) {
		this.clinetmap2 = clinetmap2;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public ArrayList<String> getClientList() {
		return clientList;
	}
	public void setClientList(ArrayList<String> clientList) {
		this.clientList = clientList;
	}
	
	
}
