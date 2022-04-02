package com.miyake.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miyake.demo.entities.EquipmentEntity;
import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.entities.PortEntity;
import com.miyake.demo.entities.PortTestEntity;
import com.miyake.demo.entities.TestScenarioItemEntity;
import com.miyake.demo.entities.TesterCapabilityEntity;
import com.miyake.demo.entities.TesterEntity;
import com.miyake.demo.jsonobject.TestPlan2;
import com.miyake.demo.jsonobject.TestPlan2Element;
import com.miyake.demo.jsonobject.WebSocketSignal;
import com.miyake.demo.repository.EquipmentRepository;
import com.miyake.demo.repository.MyTesterRepository;
import com.miyake.demo.repository.PortRepository;
import com.miyake.demo.repository.PortTestRepository;
import com.miyake.demo.repository.TestScenarioItemRepository;
import com.miyake.demo.repository.TesterRepository;

@RestController
public class TesterRestController {

    @Autowired
    private TestScenarioItemRepository testScenarioItemRepository;
    
    @Autowired
    private MyTesterRepository myTesterRepository;
    
    @Autowired
    private TesterRepository testerRepository;
    
    @Autowired
    private EquipmentRepository equipmentRepository;
    
    @Autowired
    private PortRepository portRepository;
    
    @Autowired
    private PortTestRepository portTestRepository;
   
    @Autowired
    private MyMessageHandler messageHandler;
    
    @GetMapping("/testerOnline")
    public MyTesterEntity getMe(@AuthenticationPrincipal CustomTesterDetails userDetails) {
    	Long testerid = userDetails.getTester().getId();
    	MyTesterEntity entity = this.myTesterRepository.getById(testerid);
    	entity.setOnline(true);
    	this.myTesterRepository.save(entity);
    	
    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.Signin, entity);
    	try {
	    	TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(signal));
	    	for (WebSocketSession s: this.messageHandler.getSessions()) {
	    		s.sendMessage(message);
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return userDetails.getTester();
    }
    
    @GetMapping("/testPlan2")
    public TestPlan2 testPlan2(@AuthenticationPrincipal CustomTesterDetails userDetails, @RequestParam(value = "id", required=true) Long id) {
    	
 //   	cleanUpEntities();
    	
    	List<TestScenarioItemEntity> tests = this.testScenarioItemRepository.findByTestScenario(id);
    	
    	TestPlan2 testPlan = new TestPlan2();
    	
    	Long mytesterid = userDetails.getTester().getId();
    	MyTesterEntity mytester = this.myTesterRepository.getById(mytesterid);
    	TesterEntity tester = this.testerRepository.getById(mytester.getTester());
    	List<Long> supportids = new ArrayList<>();
    	for (TesterCapabilityEntity testItem : tester.getTestItems()) {
    		supportids.add(testItem.getTestItem());
    	}
//    	tester.getTestItems().forEach(c -> supportids.add(c.getTest_item()));

    	for (TestScenarioItemEntity testScenarioItemEntity : tests) {
    		EquipmentEntity equipment = this.equipmentRepository.getById(testScenarioItemEntity.getEquipment());
    		
    		for (PortEntity port : this.portRepository.findByEquipment(equipment.getId())) {
    	    	List<PortTestEntity> porttests = this.portTestRepository.findByPort(port.getId());
    	    	for (PortTestEntity pte : porttests) {
    	    		boolean support = supportids.contains(pte.getTestItem());
    	    		
    	    		TestPlan2Element element = new TestPlan2Element(pte.getId(), equipment.getId(), 
    	    				port.getId(), pte.getDirectionEntity().getName(), pte.getTestItem(), pte.getCriteria(), pte.getTester(),
    	    				pte.getResult(), pte.getPassfail(), support);
    	    		testPlan.add(element);
    	    		
    	    		testPlan.presentation().equipment(equipment.getId(), equipment.getName()).port(pte.getPort(), 
    	    				this.portRepository.getById(pte.getPort()).getPort_name()).testItem(pte.getTestItem(), pte.getTest_itemEntity().getTest_item());
    	    	}    			
    		}
    	}

    	return testPlan;
    }
    
    @DeleteMapping("/signout")
    public String signout(@AuthenticationPrincipal CustomTesterDetails userDetails) {
    	Long testerid = userDetails.getTester().getId();
    	MyTesterEntity entity = this.myTesterRepository.getById(testerid);
    	entity.setOnline(false);
    	this.myTesterRepository.save(entity);
    	
    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.Signout, entity);
    	try {
	    	TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(signal));
	    	for (WebSocketSession s: this.messageHandler.getSessions()) {
	    		s.sendMessage(message);
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return "OK";
    }
    
    @PostMapping("/test_result")
    public String post_test_result(@AuthenticationPrincipal CustomTesterDetails userDetails, @RequestBody TestPlan2Element result) {
    	PortTestEntity entity = this.portTestRepository.getById(result.getPorttest());
    	entity.setResult(result.getResult());
    	entity.setPassfail(result.getPassFail());
    	this.portTestRepository.save(entity);

    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.ResultUpdated, result);
    	try {
	    	TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(signal));
	    	for (WebSocketSession s: this.messageHandler.getSessions()) {
	    		s.sendMessage(message);
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return "OK";
    }
    
    @PostMapping("/test_results")
    public String post_test_results(@AuthenticationPrincipal CustomTesterDetails userDetails, @RequestBody TestPlan2Element[] results) {
    	for (TestPlan2Element e  : results) {
    		post_test_result(userDetails, e);
    	}
    	return "OK";
    }
}