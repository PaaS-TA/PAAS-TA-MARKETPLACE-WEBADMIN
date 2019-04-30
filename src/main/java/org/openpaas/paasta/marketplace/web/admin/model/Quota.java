package org.openpaas.paasta.marketplace.web.admin.model;

import lombok.Data;

import java.util.Map;

@Data
public class Quota {

	private Map entity;
	private Map metadata;

	private String name;
    private boolean nonBasicServicesAllowed = false;
    private int totalServices;
    private int totalRoutes;
    private int memoryLimit;
	private int instanceMemoryLimit;

	// add
	private int appInstanceLimit;
	private int totalReservedRoutePorts;
	private String guid;  // Definition GUID(Organization or Space)
	private String orgQuotaGuid;
	private String spaceQuotaGuid;

	private String organizationName; // 정의 지정시 필요
}