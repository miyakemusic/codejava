package com.miyake.demo.jsonobject;

public class PortTemplate {
	public PortTemplate() {}
	public PortTemplate(String templateName, Long portId, Long templateId) {
		this.portid = portId;
		this.name = templateName;
		this.templateid = templateId;
	}
	public PortTemplate(Long portId, Long templateId) {
		this.portid = portId;
		this.templateid = templateId;
	}
	public String name;
	public Long portid;
	public Long templateid;
}
