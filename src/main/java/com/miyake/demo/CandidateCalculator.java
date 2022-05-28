package com.miyake.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.miyake.demo.entities.PortTestEntity;
import com.miyake.demo.entities.TestItemEntity;
import com.miyake.demo.entities.TesterCapabilityEntity;
import com.miyake.demo.entities.TesterEntity;
import com.miyake.demo.repository.PortTestRepository;
import com.miyake.demo.repository.TesterCapabilityRepository;
import com.miyake.demo.repository.TesterRepository;

public abstract class CandidateCalculator {

	private Map<Long, List<TesterEntity>> map = new HashMap<>();
	
	public Map<Long, List<TesterEntity>> getMap() {
		return map;
	}

	public CandidateCalculator(Long portid) {

		List<PortTestEntity> testItems = getTestItems(portid);
		
		
		for (PortTestEntity testItem : testItems) {
			List<TesterEntity> matchTester = findMatchTesters(testItem);
			map.put(testItem.getId(), matchTester);
		}
	}

	private List<TesterEntity> findMatchTesters(PortTestEntity testItem) {
		Long testItemId = testItem.getTestItem();
		
		List<TesterEntity> testers = testerRepository().findAll();
		List<TesterEntity> ret = new ArrayList<>();
		
		for (TesterEntity tester : testers) {
			for (TesterCapabilityEntity cap : tester.getTestItems()) {
				if (cap.getTestItem() == testItemId) {
					ret.add(tester);
				}
			}
		}
		
		return ret;
	}


	private List<PortTestEntity> getTestItems(Long portid) {
		List<PortTestEntity> portTests = findByPort(portid);
		return portTests;
	}
	
	abstract protected TesterCapabilityRepository testerCapabilityRepository();

	abstract protected TesterRepository testerRepository();

	abstract protected List<PortTestEntity> findByPort(Long portid);


}
