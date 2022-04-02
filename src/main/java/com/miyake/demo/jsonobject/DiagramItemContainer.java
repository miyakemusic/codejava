package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiagramItemContainer {
	public int x1 = Integer.MAX_VALUE;
	public int y1 = Integer.MAX_VALUE;
	public int x2 = 0;
	public int y2 = 0;
	
	public Map<Long, DiagramItem> items = new HashMap<>();
	public Long id;
	public String name;
	
	private DiagramItemContainerInterface diagramItemContainerInterface;
	

	public DiagramItemContainer(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addItem(DiagramItem item) {
		this.items.put(item.id, item);
		
		this.x1 = Math.min(this.x1, item.x);
		this.y1 = Math.min(this.y1, item.y);
		this.x2 = Math.max(this.x2, item.x + item.width);
		this.y2 = Math.max(this.y2, item.y + item.height);
		
		//width = Math.max(width, item.x + item.width);
		//height = Math.max(height, item.y + item.height);
	}
//	public void setLinks(List<DiagramLink> calc) {
//		this.links = calc;
//	}
	
	public void shiftx(int shiftx) {
		for (DiagramItem item : this.items.values()) {
			item.x += shiftx;
		}
		this.x1 += shiftx;
		this.x2 += shiftx;
	}
	
	public void shifty(int shifty) {
		for (DiagramItem item : this.items.values()) {
			item.y += shifty;
		}
		this.y1 += shifty;
		this.y2 += shifty;
	}

	public void setX(int x) {
		int width = this.x2 - this.x1;
		int xoffset = x - this.x1;
		this.x1 = x;
		this.x2 = this.x1 + width;
		for (DiagramItem item : items.values()) {
			item.x += xoffset;
		}
	}


	public void setY(int y) {
		int height = this.y2  - this.y1;
		int yoffset = y - this.y1;
		this.y1 = y;
		this.y2 = this.y1 + height;
		for (DiagramItem item : items.values()) {
			item.y += yoffset;
		}
	}
	
	public List<DiagramLink> createLink(LinkContainer linkContainer) {
		List<DiagramLink> ret = new ArrayList<>();
		for (DiagramItem item : this.items.values()) {
			Set<Long> pairs = linkContainer.getPair(item.id);
			if (pairs == null) {
				continue;
			}
			for (Long pair : pairs) {
				DiagramItem pairItem = getItem(pair);
				if (pairItem == null) {
					continue;
				}
				
				
				if (item.x < pairItem.x) {
					DiagramLink link = new DiagramLink();
					link.x1 = item.x + item.width;
					link.x2 = pairItem.x;
					link.y1 = item.y + item.height / 2;
					link.y2 = pairItem.y + pairItem.height / 2;					
					ret.add(link);
				}
			}
		}
		return ret;
	}

	private DiagramItem getItem(Long pair) {
		DiagramItem ret = this.items.get(pair);
		if (ret == null) {
			ret = this.diagramItemContainerInterface.find(pair);
		}
		return ret;
	}

	public void setDiagramItemContainerInterface(DiagramItemContainerInterface diagramItemContainerInterface) {
		this.diagramItemContainerInterface = diagramItemContainerInterface;
	}

	public DiagramItem find(Long id2) {
		return this.items.get(id2);
	}

	public int height() {
		return this.y2 - this.y1;
	}

}
