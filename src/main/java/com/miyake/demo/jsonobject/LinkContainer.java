package com.miyake.demo.jsonobject;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.miyake.demo.entities.EquipmentPresentationEntity;

public abstract class LinkContainer {
	abstract protected Rectangle presentation(Long id);
	private Set<String> linksString = new HashSet<>();
	private Map<Long, Set<Long>> linkMap = new HashMap<>();
	
	public void add(Long id1, Long id2) {
		if (id1 < id2) {
			linksString.add(String.valueOf(id1) + "," + String.valueOf(id2));
		}
		else {
			linksString.add(String.valueOf(id2) + "," + String.valueOf(id1));
		}
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (String link : linksString) {
			ret += link.toString() + "\n";
		}
		return ret;
	}

	public List<DiagramLink> calc() {
		List<DiagramLink> ret = new ArrayList<DiagramLink>();
		
		for (String s : linksString) {
			String[] tmp = s.split(",");
			Long id1 = Long.valueOf(tmp[0]);
			Long id2 = Long.valueOf(tmp[1]);
			
			Rectangle p1 = this.presentation(id1);
			Rectangle p2 = this.presentation(id2);
			
			if (!this.linkMap.containsKey(id1)) {
				this.linkMap.put(id1, new HashSet<Long>());
			}
			if (!this.linkMap.containsKey(id2)) {
				this.linkMap.put(id2, new HashSet<Long>());
			}
			this.linkMap.get(id1).add(id2);
			this.linkMap.get(id2).add(id1);
			
			DiagramLink dl = new DiagramLink();
			if (p1.getX() < p2.getX()) {
				dl.x1 = (int)(p1.getX() + p1.getWidth());
				dl.y1 = (int)(p1.getY() + p1.getHeight() / 2);
				
				dl.x2 = (int)p2.getX();
				dl.y2 = (int)(p2.getY() + p2.getHeight() / 2);
			}
			else {
				dl.x1 = (int)(p2.getX() + p2.getWidth());
				dl.y1 = (int)(p2.getY() + p2.getHeight() / 2);
				
				dl.x2 = (int)p1.getX();
				dl.y2 = (int)(p1.getY() + p2.getHeight() / 2);					
			}
			
			ret.add(dl);
		}
		
		return ret;
	}

	public Set<Long> getPair(Long id) {
		return this.linkMap.get(id);
	}
}