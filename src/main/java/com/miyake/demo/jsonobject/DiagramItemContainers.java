package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

interface DiagramItemContainerInterface {
	DiagramItem find(Long id);
}

public class DiagramItemContainers {

	public List<DiagramItemContainer> diagramContainers = new ArrayList<>();
	public List<DiagramLink> links = new ArrayList<>();
	public int gridx = 20;
	public int gridy = 20;
	
	private DiagramItemContainerInterface diagramItemContainerInterface = new DiagramItemContainerInterface() {
		@Override
		public DiagramItem find(Long id) {
			for (DiagramItemContainer diagramContainer : diagramContainers) {
				DiagramItem ret = diagramContainer.find(id);
				if (ret != null) {
					return ret;
				}
			}
			return null;
		}
	};
	
	public void add(DiagramItemContainer container) {
		container.setDiagramItemContainerInterface(diagramItemContainerInterface);
		this.diagramContainers.add(container);
	}

	public void setLinks(List<DiagramLink> links) {
		this.links = links;
	}

	public void pack(LinkContainer linkContainer) {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		
		Comparator<DiagramItemContainer> comparator = new Comparator<DiagramItemContainer>() {
			@Override
			public int compare(DiagramItemContainer o1, DiagramItemContainer o2) {
				return o1.x1 - o2.x1;
			}
		};
		
		Collections.sort(this.diagramContainers, comparator);
		
		Map<Integer, List<Long>> map = new LinkedHashMap<>();
		
		for (DiagramItemContainer d : diagramContainers) {
			if (!map.containsKey(d.x1)) {
				map.put(d.x1, new ArrayList<Long>());
			}
			map.get(d.x1).add(d.id);
		}
	
		int xoffset = 0;
		int maxy = 0;
		Map<Integer, Integer> heightMap = new LinkedHashMap<>();
		Map<Integer, List<DiagramItemContainer>> containerMap = new LinkedHashMap<>();
		
		for (Integer xx : map.keySet()) {
			List<Long> ids = map.get(xx);
			int max = 0;
			List<DiagramItemContainer> containers = new ArrayList<>();
			Collections.sort(containers, new Comparator<DiagramItemContainer>(){
				@Override
				public int compare(DiagramItemContainer o1, DiagramItemContainer o2) {
					return o1.y1 - o2.y1;
				}
			});
			
			containerMap.put(xx, containers);
			for (Long id : ids) {
				DiagramItemContainer container = this.find(id);
				containers.add(container);
				max = Math.max(max, container.x2 - container.x1);
			}
			
			int yoffset = 0;
			for (DiagramItemContainer container : containers) {
				container.setX(xoffset);
				container.setY(yoffset);
				
				yoffset += container.height() + 20;
				
			}
			heightMap.put(xx, yoffset);
			maxy = Math.max(maxy, yoffset);
			xoffset += max + 20;
		}
		
		for (Integer xx : containerMap.keySet()) {
			for (DiagramItemContainer container : containerMap.get(xx)) {
				int height = heightMap.get(xx);
				container.setY(container.y1 +  (maxy - height) / 2);
			}
		}
		
		linkContainer.calc();
		for (DiagramItemContainer d : diagramContainers) {
			links.addAll(d.createLink(linkContainer));
		}
	}

	private DiagramItemContainer find(Long id) {
		for (DiagramItemContainer d : diagramContainers) {
			if (d.id.equals(id)) {
				return d;
			}
		}
		return null;
	}
}
