package com.mainapp.instanceFactoryMethod;

public class Organization {

	private String orgId;
	private String orgName;

	public Organization() {
	}

	public Organization(String orgId, String orgName) {
		this.orgId = orgId;
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "Organization [orgId=" + orgId + ", orgName=" + orgName + "]";
	}

}
