package com.miyake.demo;

import java.awt.Rectangle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miyake.demo.entities.CableEntity;
import com.miyake.demo.entities.ConnectorEntity;
import com.miyake.demo.entities.EquipmentCategoryEntity;
import com.miyake.demo.entities.EquipmentEntity;
import com.miyake.demo.entities.EquipmentEntitySimple;
import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.entities.PassFailEnum;
import com.miyake.demo.entities.PortCategoryEntity;
import com.miyake.demo.entities.PortDirectionEntity;
import com.miyake.demo.entities.PortEntity;
import com.miyake.demo.entities.PortEntitySimple;
import com.miyake.demo.entities.PortPresentationEntity;
import com.miyake.demo.entities.PortTestEntity;
import com.miyake.demo.entities.PortTestEntitySimple;
import com.miyake.demo.entities.PortTestGroupEntity;
import com.miyake.demo.entities.PortTestTemplateBindEntity;
import com.miyake.demo.entities.PortTestTemplateEntity;
import com.miyake.demo.entities.ProductType;
import com.miyake.demo.entities.ProjectEntity;
import com.miyake.demo.entities.ProjectEntitySimple;
import com.miyake.demo.entities.TestItemCategoryEntity;
import com.miyake.demo.entities.TestItemEntity;
import com.miyake.demo.entities.TestScenarioEntity;
import com.miyake.demo.entities.TestScenarioItemEntity;
import com.miyake.demo.entities.TesterCapabilityEntity;
import com.miyake.demo.entities.TesterCapabilityEntitySimple;
import com.miyake.demo.entities.TesterCategoryEntity;
import com.miyake.demo.entities.TesterCategoryRelationEntity;
import com.miyake.demo.entities.TesterEntity;
import com.miyake.demo.entities.TesterOptionRelationEntity;
import com.miyake.demo.entities.TesterVendorEntity;
import com.miyake.demo.entities.EquipmentPresentationEntity;
import com.miyake.demo.entities.UserEntity;
import com.miyake.demo.entities.UserGroupEntity;
import com.miyake.demo.jsonobject.CopyConditionJson;
import com.miyake.demo.jsonobject.DiagramItem;
import com.miyake.demo.jsonobject.DiagramItemContainer;
import com.miyake.demo.jsonobject.DiagramItemContainers;
import com.miyake.demo.jsonobject.ElementJson;
import com.miyake.demo.jsonobject.EquipmentNameJson;
import com.miyake.demo.jsonobject.IdValue;
import com.miyake.demo.jsonobject.LinkContainer;
import com.miyake.demo.jsonobject.MouseEventJson;
import com.miyake.demo.jsonobject.MyTesterJson;
import com.miyake.demo.jsonobject.MyTesterRegistration;
import com.miyake.demo.jsonobject.ParentTester;
import com.miyake.demo.jsonobject.ParentTestersJson;
import com.miyake.demo.jsonobject.EquipmentEditJson;
import com.miyake.demo.jsonobject.EquipmentJson;
import com.miyake.demo.jsonobject.PortSummaryJson;
import com.miyake.demo.jsonobject.PortTemplate;
import com.miyake.demo.jsonobject.PortTestJson;
import com.miyake.demo.jsonobject.PrimitiveRect;
import com.miyake.demo.jsonobject.ProjectJson;
import com.miyake.demo.jsonobject.ProjectSummaryJson;
import com.miyake.demo.jsonobject.TestCaseRequest;
import com.miyake.demo.jsonobject.TestItemDefJson;
import com.miyake.demo.jsonobject.TestItemJson;
import com.miyake.demo.jsonobject.TestItemList;
import com.miyake.demo.jsonobject.TestItemListElement;
import com.miyake.demo.jsonobject.TestItemNewJson;
import com.miyake.demo.jsonobject.TestPlan;
import com.miyake.demo.jsonobject.TestPlan2Element;
import com.miyake.demo.jsonobject.TesterJson;
import com.miyake.demo.jsonobject.WebSocketSignal;
import com.miyake.demo.repository.CableRepository;
import com.miyake.demo.repository.ConnectorRepository;
import com.miyake.demo.repository.EquipmentCategoryRepository;
import com.miyake.demo.repository.EquipmentRepository;
import com.miyake.demo.repository.EquipmentRepositorySimple;
import com.miyake.demo.repository.MyTesterRelationRepository;
import com.miyake.demo.repository.MyTesterRepository;
import com.miyake.demo.repository.PortCategoryRepository;
import com.miyake.demo.repository.PortDirectionRepository;
import com.miyake.demo.repository.PortPresentationRepository;
import com.miyake.demo.repository.PortRepository;
import com.miyake.demo.repository.PortRepositorySimple;
import com.miyake.demo.repository.PortTestGroupRepository;
import com.miyake.demo.repository.PortTestRepository;
import com.miyake.demo.repository.PortTestRepositorySimple;
import com.miyake.demo.repository.PortTestTemplateBindRepositoty;
import com.miyake.demo.repository.PortTestTemplateRepositoty;
import com.miyake.demo.repository.ProjectRepository;
import com.miyake.demo.repository.ProjectRepositorySimple;
import com.miyake.demo.repository.TestItemCategoryRepository;
import com.miyake.demo.repository.TestItemRepository;
import com.miyake.demo.repository.TestScenarioItemRepository;
import com.miyake.demo.repository.TestScenarioRepository;
import com.miyake.demo.repository.TesterCapabilityRepository;
import com.miyake.demo.repository.TesterCapabilityRepositorySimple;
import com.miyake.demo.repository.TesterCategoryRelationRepository;
import com.miyake.demo.repository.TesterCategoryRepository;
import com.miyake.demo.repository.TesterOptionRelationRepository;
import com.miyake.demo.repository.TesterRepository;
import com.miyake.demo.repository.TesterVendorRepository;
import com.miyake.demo.repository.EquipmentPresentationRepository;
import com.miyake.demo.repository.UserGroupRepository;
import com.miyake.demo.repository.UserRepository;
import com.miyake.demo.shared.PassFailCalculator;

@RestController
public class TestRestController {
    @Autowired
    private CableRepository cableRepository;
    
    @Autowired
    private TesterVendorRepository testerVendorRepository;
    
    @Autowired
    private PortRepository portRepository;

    @Autowired
    private PortRepositorySimple portRepositorySimple;
    
    @Autowired
    private TesterCategoryRepository testerCategoryRepository;
    
    @Autowired
    private TesterRepository testerRepository;
    
    @Autowired
    private PortTestRepository portTestRepository;

    @Autowired
    private PortTestRepositorySimple portTestRepositorySimple;
    
    @Autowired
    private PortTestGroupRepository portTestGroupRepository;
    
    @Autowired
    private TestItemRepository testItemRepository;

    @Autowired
    private TestItemCategoryRepository testItemCategoryRepository;

    @Autowired
    private TesterCapabilityRepository testerCapabilityRepository;

    @Autowired
    private TesterCapabilityRepositorySimple testerCapabilityRepositorySimple;
    
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentRepositorySimple equipmentRepositorySimple;
    
    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    private PortCategoryRepository portCategoryRepository;
    
    @Autowired
    private ConnectorRepository connectorRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectRepositorySimple projectRepositorySimple;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private MyTesterRepository myTesterRepository;

    @Autowired
    private PortDirectionRepository portDirectionRepository;

    @Autowired
    private EquipmentPresentationRepository equipmentPresentationRepository;
    
    @Autowired
    private PortPresentationRepository portPresentationRepository;
    
    @Autowired
    private PortTestTemplateRepositoty portTestTemplateRepository;

    @Autowired
    private PortTestTemplateBindRepositoty portTestTemplateBindRepository;
    
    @Autowired
    private TestScenarioRepository testScenarioRepository;
    
    @Autowired
    private TestScenarioItemRepository testScenarioItemRepository;
    
    @Autowired
    private TesterCategoryRelationRepository testerCategoryRelationRepository;
    
    @Autowired
    private TesterOptionRelationRepository testerOptionRelationRepository;
    
    @Autowired
    private MyTesterRelationRepository myTesterRelationRepository;
    
    @Autowired
    private MyMessageHandler messageHandler;
    
    @GetMapping("/CableEntityS")
    public List<CableEntity> CableEntityS() {
    	return this.cableRepository.findAll();
    }
    
    @PostMapping("/updateCable")
    public String createPort(@RequestBody ElementJson cable) {
    	CableEntity e = null;
    	if (cable.id != null) {
    		e = this.cableRepository.getById(cable.id);
    	}
    	else {
    		e = new CableEntity();
    	}
		if (cable.field.equals("name")) {
			e.setName(cable.value);
		}
		else if (cable.field.equals("description")) {
			e.setDescription(cable.value);
		}
    	this.cableRepository.save(e);
    	return "OK";
    }
    
    @GetMapping("/PortEntityS")
    public List<PortEntity> getAllPorts(@RequestParam(value = "parent", required=false) Long parent) {
    	if (parent == null) {
    		return portRepository.findAll();
    	}
    	else {
    		List<PortEntity> ret = portRepository.findByEquipment(parent);
    		return ret;
    	}
    }
    
    @GetMapping("/PortEntity")
    public PortEntity portEntity(@RequestParam(value = "id", required=true) Long id) {
    	return portRepository.getById(id);
    }
    
    @DeleteMapping("/PortEntity")
    public String deletePort(@RequestParam(value = "id", required=true) Long id) {
    	this.portPresentationRepository.deleteByPort(id);
    	
    	List<PortEntity> opposites = this.portRepository.findByOpposite(id);
    	for (PortEntity o : opposites) {
    		o.setOpposite(null);
    		this.portRepository.save(o);
    	}
    	this.portRepository.deleteById(id);
    	
    	return "OK";
    }
    
    @DeleteMapping("/PortEntityS")
    public String deletePorts(@RequestParam(value = "id", required=true) String ids) {
    	for (Long id : convertToLongArray(ids)) {
	    	this.portPresentationRepository.deleteByPort(id);
	    	this.portRepository.deleteById(id);
    	}
    	return "OK";
    }
    
    @PostMapping("/PortEntity")
    public PortEntity createPort(@RequestBody PortEntity port/*, @RequestParam(value = "parent", required=false) Long parent*/) {
//    	port.setEquipment(parent);
    	this.portRepository.save(port);
    	return port;
    }
    
    @PostMapping("/updatePort")
    public String PortCable(@RequestBody ElementJson element) {
    	PortEntity e = this.portRepository.getById(element.id);
    	if (element.field.equals("cabletype")) {
    		Long cabletype = Long.valueOf(element.value); 
    		e.setCabletype(cabletype);
    	}
    	else if (element.field.equals("port_name")) {
    		e.setPort_name(element.value);
    	}
    	this.portRepository.save(e);
    	return "OK";
    }
    
    @PostMapping("/portName")
    public String portName(@RequestBody ElementJson element) {
    	PortEntity portEntity = this.portRepository.getById(element.id);
    	portEntity.setPort_name(element.value);
    	this.portRepository.save(portEntity);
    	
    	return "OK";
    }
    
    @PostMapping("/editProjectSummary")
    public String editProjectSummary(@RequestBody EquipmentEditJson equipmentEditJson) {
    	if (equipmentEditJson.editType.equals("copy")) {
    		this.copyEquipment(equipmentEditJson.id, equipmentEditJson.count);
    	}
    	else if (equipmentEditJson.editType.equals("delete")) {
//    		this.equipmentRepository.deleteById(equipmentEditJson.id);
    		this.deleteequiement(equipmentEditJson.id);
    	}
    	return "OK";
    }
    
    @PostMapping("/editPort")
    public String editPort(@RequestBody EquipmentEditJson portEditJson) {
    	if (portEditJson.editType.equals("copy")) {
    		//this.copyEquipment(portEditJson.id, portEditJson.count);
    	}
    	else if (portEditJson.editType.equals("delete")) {
    		//this.equipmentRepository.deleteById(portEditJson.id);
    		this.deletePort(portEditJson.id);
    	}
    	return "OK";
    }

    
    @GetMapping("/PortEntitySimpleS")
    public List<PortEntitySimple> portEntitySimpleS(@RequestParam(value = "parent", required=false) Long parent) {
    	if (parent == null) {
    		return this.portRepositorySimple.findAll();
    	}
    	else {
    		List<PortEntitySimple> ret = portRepositorySimple.findByEquipment(parent);
    		return ret;
    	}
    }
    
    @GetMapping("/PortDirectionEntityS")
    public List<PortDirectionEntity> portDirection() {
    	return this.portDirectionRepository.findAll();
    }
    
    @DeleteMapping("/PortDirectionEntity")
    public String deletePortDirection(@RequestParam(value = "id", required=false) Long id) {
    	portDirectionRepository.deleteById(id);
    	return "OK";
    }
    
    @PostMapping("/PortDirectionEntity")
    public PortDirectionEntity portDirection(@RequestBody PortDirectionEntity port) {
    	this.portDirectionRepository.save(port);
    	return port;
    }
    
    @PostMapping("/PortEntity/copy")
    public PortEntity copyPort(@RequestBody PortEntity port) {
    	// TODO
    	
//    	List<PortTestEntity> tests = portTestRepository.findByPort(port.getId());
    	
    	
    	
//    	List<PortEntity> ports = portRepository.findByEquipment(port.getEquipment());
//    	for (PortEntity p : ports) {
//    		if (p.getId().equals(port.getId())) {
//    			continue;
//    		}
//    		
//    		portTestRepository.deleteByPort(p.getId());
//    		
//    		for (PortTestEntity test : tests) {
//    			PortTestEntity t = new PortTestEntity();
//    			t.setPorttestgroup(p.getId());
//    			t.setDirection(test.getDirection());
//    			t.setTestItem(test.getTestItem());
//    			portTestRepository.save(t);
//    		}
//
//    	}

    	return port;
    }
    
    @GetMapping("/TesterEntityS")
    public List<TesterEntity> getAllTesters() {
    	return this.testerRepository.findAll();
    }
  
    @GetMapping("/TesterEntitiesByVendor")
    public List<TesterEntity> testerEntitiesByVendor(@RequestParam(value = "vendor", required=true) Long vendor) {
    	return this.testerRepository.findByVendor(vendor);
    }
    
    @GetMapping("/testerListJson")
    public List<TesterJson> testerList(@RequestParam(value = "vendor", required=true) Long vendor) {
    	List<TesterJson> ret = new ArrayList<TesterJson>();
    	List<TesterEntity> testers = this.testerRepository.findByVendor(vendor);
    	for (TesterEntity e : testers) {
    		List<TesterCategoryRelationEntity> categoryRelation = testerCategoryRelationRepository.findByTester(e.getId());
    		List<Long> category = new ArrayList<>();
    		String categoryText = "";
    		for (TesterCategoryRelationEntity c : categoryRelation) {
    			category.add(c.getCategory());
    			categoryText += testerCategoryRepository.getById(c.getCategory()).getCategory_name() + "/";  			
    		}
    		if (!categoryText.isEmpty()) {
    			categoryText = categoryText.substring(0, categoryText.length()-1);
    		}
    		
    		List<TesterOptionRelationEntity> parents = this.testerOptionRelationRepository.findByChild(e.getId());
    		String parentsText = createParentsText(parents);
    		ret.add(new TesterJson(e.getVendor(), e.getId(), e.getProduct_name(), e.getDescription(), e.getProducttype().ordinal(), e.getProducttype().name(), category, categoryText, parentsText));
    	}
    	
    	return ret;
    }

	private String createParentsText(List<TesterOptionRelationEntity> parents) {
		List<String> models = new ArrayList<>();
		for (TesterOptionRelationEntity parent : parents) {
			models.add(this.testerRepository.getById(parent.getParent()).getProduct_name());
		}
		
		Collections.sort(models);
		LinkedHashSet<String> shorten = new LinkedHashSet<>();
		for (String s : models) {
			shorten.add(s.split("[-/:.]+")[0]);
		}
		
		
		
		List<String> shortList = new ArrayList<>();
		for (String s : shorten) {
			if (multipleModels(s, models)) {
				shortList.add(s + "*");
			}
			else {
				shortList.add(s);
			}
		}
		
		String parentsText = shortList.toString();
		return parentsText;
	}


	private boolean multipleModels(String s, List<String> models) {
    	int count = 0;
    	for (String model:  models) {
    		if (model.contains(s)) {
    			count++;
    		}
    	}
		return count > 1;
	}

	@GetMapping("/parentTesterJson")
    public List<ParentTester> parentTester(@RequestParam(value = "tester", required=true) Long tester, @RequestParam(value = "vendor", required=true) Long vendor) {
    	List<ParentTester> ret = new ArrayList<>();
    	    	
    	List<TesterOptionRelationEntity> parents = this.testerOptionRelationRepository.findByChild(tester);
    	
    	List<Long> ps = new ArrayList<>();
    	for (TesterOptionRelationEntity p : parents) {
    		ps.add(p.getParent());
    	}
    	for (TesterEntity e : this.testerRepository.findByVendor(vendor)) {
    		ret.add(new ParentTester(e.getId(), e.getProduct_name(), e.getDescription(), ps.contains(e.getId())));
    	}
    	return ret;
    }
    
    @PostMapping("/parentTesterJson")
    public String parentTester(@RequestBody ParentTestersJson parents) {
    	this.testerOptionRelationRepository.deleteByChild(parents.id);
    	for (Long parent : parents.selected) {
    		TesterOptionRelationEntity relation = new TesterOptionRelationEntity(parent, parents.id);
    		this.testerOptionRelationRepository.save(relation);
    	}

    	return "OK";
    }
    
    @PostMapping("/TesterEntity")
    public TesterEntity createTester(@RequestBody TesterEntity tester) {
    	this.testerRepository.save(tester);
    	return tester;
    }
    
    @PostMapping("/testerJson")
    public String testerJson(@RequestBody TesterJson testerJson) {
    	if (testerJson.id != null) {
	    	testerCategoryRelationRepository.deleteByTester(testerJson.id);
	
	    	TesterEntity testerEntity = this.testerRepository.getById(testerJson.id);
	    	saveTesterEntity(testerJson, testerEntity);
	    	
	    	for (Long category : testerJson.category) {
	    		TesterCategoryRelationEntity entity = new TesterCategoryRelationEntity();
	    		entity.setCategory(category);
	    		entity.setTester(testerJson.id);
				testerCategoryRelationRepository.save(entity);
	    	}
    	}
    	else {
    		TesterEntity e = new TesterEntity();
    		saveTesterEntity(testerJson, e);
    	}
    	return "OK";
    }

	private void saveTesterEntity(TesterJson testerJson, TesterEntity testerEntity) {
		testerEntity.setVendor(testerJson.vendorid);
		testerEntity.setProduct_name(testerJson.name);
		testerEntity.setDescription(testerJson.description);
		testerEntity.setProducttype(ProductType.values()[testerJson.type]);
		this.testerRepository.save(testerEntity);
	}
    
    @GetMapping("/TesterCategoryEntityS")
    public List<TesterCategoryEntity> getAllTesterCategories() {
    	return this.testerCategoryRepository.findAll();
    }
    
    @PostMapping("/TesterCategoryEntity")
    public TesterCategoryEntity createTesterCategory(@RequestBody TesterCategoryEntity testerCategory) {
    	this.testerCategoryRepository.save(testerCategory);
    	return testerCategory;
    }
 
    @GetMapping("/PortTestEntity")
    public PortTestEntity getPortTestEntity(@RequestParam(value = "id", required=true) Long id) {
    	return this.portTestRepository.getById(id);
    }

//    @GetMapping("/PortTestEntityByEquipment")
//    public List<PortTestEntity> getPortTestEntityByEquipment(@RequestParam(value = "equipment", required=true) Long equipment) {
//    	List<PortTestEntity> ret = new ArrayList<>();
//    	List<PortEntitySimple> ports = this.portRepositorySimple.findByEquipment(equipment);
//    	
//    	for (PortEntitySimple port : ports) {
//    		List<PortTestEntity> list = this.portTestRepository.findByPort(port.getId());
//  //  		list.forEach(l -> {l.port_name = port.getPort_name();});
//    		ret.addAll(list);
//    	}
//    	
//    	return ret;
//    }
    
    @GetMapping("/PortTestJsonByEquipment")
    public List<PortTestJson> getPortTestJsonByEquipment(@RequestParam(value = "equipment", required=true) Long equipment) {
    	List<PortTestJson> ret = new ArrayList<>();
    	
    	List<PortEntity> ports = this.portRepository.findByEquipment(equipment);
    	for (PortEntity port : ports) {
    		for (PortTestGroupEntity portTestGroupEntity : port.getTestGroup()) {
    			for (PortTestEntity test : portTestGroupEntity.getPortTests()) {
        			ret.add(new PortTestJson(test.getId(), 
        					port.getPort_name(), test.getDirectionEntity().getName(), 
        					test.getTest_itemEntity().getCategoryEntity().getCategory(),
        					test.getTestItem(),
        					test.getTest_itemEntity().getTest_item(), 
        					test.getCriteria(), test.getResult(), 
        					test.getPassfail().toString()));    				
    			}
    		}

    		
    	}

    	return ret;
    }
    
    
    
    @GetMapping("/PortTestEntityS")
    public List<PortTestEntity> createPortTesters(@RequestParam(value = "parent", required=false) Long port) {
    	List<PortTestEntity> ret = new ArrayList<>();
    	if (port == null) {
    		ret = portTestRepository.findAll();
    	}
    	else {
    		PortEntity portEntity = this.portRepository.getById(port);
    		for (PortTestGroupEntity group : portEntity.getTestGroup()) {
    			for (PortTestEntity test : group.getPortTests()) {
    				ret.add(test);
    			}
    		}
    	}

//    	ret.forEach(l -> {l.port_name = port.getPort_name();});
    	return ret;
    }

    @GetMapping("/portsummaryjson")
    public List<PortSummaryJson> portsummaryjson(@RequestParam(value = "id", required=true) Long id) {
    	List<PortSummaryJson> ret = new ArrayList<>();
    	List<PortEntity> ports = this.portRepository.findByEquipment(id);
    	for (PortEntity e : ports) {
    		String linkEquipment = "-";
    		String linkPort = "-";
    		if (e.getOpposite() != null) {
    			PortEntity oppositePortEntity = this.portEntity(e.getOpposite());
    			EquipmentEntity oppositeEquipment = this.equipmentRepository.getById(oppositePortEntity.getEquipment());
    			linkEquipment = oppositeEquipment.getName();
    			linkPort = oppositePortEntity.getPort_name();
    		}
    		Set<String> testItems = new LinkedHashSet<>();
    		Set<String> testPoints = new LinkedHashSet<>();
    		
    		String testStatus = "-";
    		String cable = "-";
    		for (PortTestGroupEntity group : e.getTestGroup()) {
        		for (PortTestEntity p : group.getPortTests()) {
//        			testItems.add( p.getTest_itemEntity().getTest_item() );
        			testItems.add( p.getTest_itemEntity().getCategoryEntity().getCategory() );
        			testPoints.add(p.getDirectionEntity().getName());
        			
        		} 		
        		//List<PortTestEntity> portTestEntities = this.portTestRepository.findByPort(e.getId());
        		testStatus = createTestStatus(group.getPortTests());
        		
        		if (e.getCabletype() != null) {
        			cable = this.cableRepository.getById(e.getCabletype()).getName();
        		}

    		}
    		ret.add(new PortSummaryJson(e.getId(), e.getPort_name(), linkEquipment, linkPort, cable, formatArrayString(testPoints), formatArrayString(testItems), testStatus));

    	}
    	return ret;
    }

	private String formatArrayString(Set<String> testPoints) {
		if (testPoints.size() > 0) {
			return testPoints.toString().replace(",","<br>").replace("[", "").replace("]", "");
		}
		else {
			return "-";
		}
	}
    
    @PostMapping("/PortTestJson")
    public PortTestEntitySimple postPortTestJson(@RequestBody PortTestJson port_test) {
    	PortTestEntitySimple entity = this.portTestRepositorySimple.getById(port_test.id);
    	entity.setCriteria(port_test.criteria);
    	entity.setResult(port_test.result);
    	entity.setDirection(port_test.direction);
    	entity.setTestItem(port_test.testItemId);
   
    	this.portTestRepositorySimple.save(entity);
    	return entity;
    }
    
    @PostMapping("/PortTestEntity")
    public PortTestEntity createPortTester(@RequestBody PortTestEntity port_test) {
    	PassFailEnum passFail = new PassFailCalculator().judgePassFail(port_test.getCriteria(), port_test.getResult());
    	port_test.setPassfail(passFail);
    	return portTestRepository.save(port_test);
    }

	@PostMapping("/PortTestEntityS")
    public String createPortTesterS(@RequestBody PortTestEntity[] port_testers) {
    	for (PortTestEntity port_tester : port_testers) {
    		portTestRepository.save(port_tester);
    	}
    	return "OK";
    }
    
	@PostMapping("/addTestItem")
	public String addTestItem(@RequestBody TestItemNewJson testItem) {
//		PortEntity portEntity = this.portRepository.getById(testItem.port);

		PortTestGroupEntity group = new PortTestGroupEntity();
		group.setName(testItem.testGroupName);
		group.setPort(testItem.port);
		group = this.portTestGroupRepository.save(group);
		
		for (Long testitem : testItem.testItem) {
			PortTestEntity e = new PortTestEntity();
			e.setDirection(testItem.testPoint);
			e.setPorttestgroup(group.getId());
			e.setTestItem(testitem);
			try {
				this.portTestRepository.save(e);		
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return "OK";
	}
    @DeleteMapping("/PortTestEntity")
    public String deletePortTestEntity(@RequestBody PortTestEntity port_tester) {
    	portTestRepository.deleteById(port_tester.getId());
    	return "OK";
    }
   
    @DeleteMapping("/deletePortTest")
    public String deletePortTest(@RequestParam(value = "id", required=true) Long id) {
    	portTestRepository.deleteById(id);
    	return "OK";
    }

    @DeleteMapping("/deletePortTestGroup")
    public String deletePortTestGroup(@RequestParam(value = "id", required=true) Long id) {
    	PortTestEntity portTest = this.portTestRepository.getById(id);
    	
    	List<Long> ids = new ArrayList<>();
    	for (PortTestEntity pt : portTest.getPortTestGroupEntity().getPortTests()) {
    		ids.add(pt.getId());
    	}
    	
    	this.portTestRepository.deleteAllById(ids);
    	this.portTestGroupRepository.deleteById(portTest.getPorttestgroup());

    	return "OK";
    }
    
    @PostMapping("/copyPortTest")
    public String copyPortTest(@RequestBody CopyConditionJson copyCondition) {

    	PortTestEntitySimple portTest = this.portTestRepositorySimple.getById(copyCondition.id);
    	PortTestEntitySimple newPortTest= new PortTestEntitySimple();
    		
    	newPortTest.setTestItem(portTest.getTestItem());
    	newPortTest.setDirection(portTest.getDirection());
    	newPortTest.setCriteria(portTest.getCriteria());
    	newPortTest.setPorttestgroup(portTest.getPorttestgroup());
    
    	this.portTestRepositorySimple.save(newPortTest);
    	
    	String condition = copyCondition.condition;
    	
 //   	portTestRepository.id;
    	
    	return "OK";
    }

    @PostMapping("/copyPortTestGroup")
    public String copyPortTestGroup(@RequestBody CopyConditionJson copyCondition) {
    	PortTestEntity refPortTest = this.portTestRepository.getById(copyCondition.id);
    	String condition = copyCondition.condition;
    	
    	String targetLine = null;
    	String replaceLine = null;
//    	String targetValue = null;
 //   	String changer = null;
    	
    	if (condition != null && !condition.isEmpty() && condition.contains(";")) {
    		String[] tmp = condition.split(";");
    		targetLine = tmp[0];
    		replaceLine = tmp[1];
    		//targetValue = tmp[1];
//    		targetValue = targetLine.split("=")[0];
//    		changer = tmp[1];
    	}
    	
 //   	String newCriteria = refPortTest.getCriteria().replace(targetLine, replaceLine);
    	AutoName autoName = new AutoName(copyCondition.count, refPortTest.getPortTestGroupEntity().getName());

    	ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("graal.js");
    	
    	for (int i = 0; i < autoName.size(); i++) {
    		PortTestGroupEntity group = new PortTestGroupEntity();
    		group.setName(autoName.nextName());
    		group.setPort(refPortTest.getPortTestGroupEntity().getPort());
    		group = this.portTestGroupRepository.save(group);
    		
    		for (PortTestEntity e : refPortTest.getPortTestGroupEntity().getPortTests()) {
    			PortTestEntity newPortTest = e.clone();
    			if (targetLine != null) {
    				String currentLine = replaceLine.replace("index", String.valueOf(i+1));
    				
//    				changer = changer.replace("index", String.valueOf(i+1));
    				try {
    					String leftEq = currentLine.split("=")[0];
    					String rightEq = currentLine.split("=")[1];
						String evaled = scriptEngine.eval(rightEq).toString();
						String newCriteria = newPortTest.getCriteria().replace(targetLine, leftEq + "=" + evaled);
						newPortTest.setCriteria(newCriteria);
						//newPortTest.setCriteria(newPortTest.getCriteria().replace(targetLine, condition));
					} catch (ScriptException e1) {
						e1.printStackTrace();
					}
    				
    			}
    			newPortTest.setPorttestgroup(group.getId());
    			this.portTestRepository.save(newPortTest);
    		}
    	}
//    	PortTestEntity portTest = this.portTestRepository.getById(id);
//    	
//    	List<Long> ids = new ArrayList<>();
//    	for (PortTestEntity pt : portTest.getPortTestGroupEntity().getPortTests()) {
//    		ids.add(pt.getId());
//    	}
//    	
//    	this.portTestRepository.deleteAllById(ids);
//    	this.portTestGroupRepository.deleteById(portTest.getPorttestgroup());

    	return "OK";
    }
    
//    @DeleteMapping("/PortTestEntity")
//    public String deletePortTest(@RequestParam(value = "id", required=true) String id) {
//    	for (String i : id.split(",")) {
//    		this.portTestRepository.deleteById(Long.valueOf(i));
//    	}
//    	return "OK";
//    }
    
    @DeleteMapping("/PortTestEntity/result")
    public String deletePortTestResult(@RequestParam(value = "id", required=true) String id) {
    	for (String i : id.split(",")) {
    		PortTestEntity e = this.portTestRepository.getById(Long.valueOf(i));
    		e.setResult(null);
    		portTestRepository.save(e);
    	}
    	return "OK";
    }
        
    @GetMapping("/TestItemEntityS")
    public List<TestItemEntity> findTestItems(@RequestParam(value = "category", required=false) Long category) {
    	if (category != null) {
    		return testItemRepository.findByCategory(category);
    	}
    	else {
    		return this.testItemRepository.findAll();
    	}
    }
    
    @GetMapping("/testitemdefjson")
    public List<TestItemDefJson> testitemdefjson(@RequestParam(value = "category", required=false) Long category) {
    	List<TestItemDefJson> ret = new ArrayList<TestItemDefJson>();
    	
    	if ((category == null) || (category == -1L)) {
	    	for (TestItemEntity e : this.testItemRepository.findAll()) {
	    		ret.add(new TestItemDefJson(e.getId(), e.getCategoryEntity().getCategory(), e.getTest_item(), e.getDescription()));
	    	}    		
    	}
    	else {
	    	for (TestItemEntity e : this.testItemRepository.findByCategory(category)) {
	    		ret.add(new TestItemDefJson(e.getId(), e.getCategoryEntity().getCategory(), e.getTest_item(), e.getDescription()));
	    	}
    	}
    	return ret;
    }
    
    
    @GetMapping("/TestItemEntity")
    public TestItemEntity getTestItem(@RequestParam(value = "id", required=false) Long id) {
    	return testItemRepository.getById(id);
    }
    
    @DeleteMapping("/TestItemEntity")
    public String deletetest_item(@RequestParam(value = "id", required=false) Long id) {
    	this.testItemRepository.deleteById(id);
    	this.testerCapabilityRepository.deleteByTestItem(id);
    	this.portTestRepositorySimple.deleteByTestItem(id);
    	return "OK";
    }
    @PostMapping("/TestItemEntity")
    public TestItemEntity createTestItem(@RequestBody TestItemEntity test_item) {
    	return testItemRepository.save(test_item);
    }
    
    @PostMapping("/TestItemEntityS")
    public String createTestItems(@RequestBody TestItemEntity[] test_item) {
    	for (TestItemEntity t : test_item) {
    		testItemRepository.save(t);
    	}
    	return "OK";
    }
    
    @GetMapping("/PortTestTemplateEntityS")
    public List<PortTestTemplateEntity> portTestTemplates(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return this.portTestTemplateRepository.findAll();
    }
    
    @PostMapping("/PortTestTemplate")
    public String portTestTemplate(@RequestBody PortTemplate portTemplate) {
//    	PortTestTemplateEntity portTestTemplateEntity = new PortTestTemplateEntity();
//    	portTestTemplateEntity.setName(portTemplate.name);
//    	portTestTemplateEntity = this.portTestTemplateRepository.save(portTestTemplateEntity);
//    	
//    	List<PortTestEntitySimple> refPortTests = this.portTestRepositorySimple.findByPort(portTemplate.portid);
//    	
//    	for (PortTestEntitySimple refPortTest : refPortTests) {
//    		PortTestEntitySimple newPortTest = refPortTest.clone();
//    		newPortTest = this.portTestRepositorySimple.save(newPortTest);
//    		
//    		PortTestTemplateBindEntity bind = new PortTestTemplateBindEntity();
//    		bind.setPort_test(newPortTest.getId());
//    		bind.setTemplate(portTestTemplateEntity.getId());
//    		
//    		this.portTestTemplateBindRepository.save(bind);
//    	}
    	return "OK";
    }
    
    @PostMapping("/applyPortTemplate")
    public String applyPortTemplate(@RequestBody PortTemplate portTemplate) {
//    	this.portTestRepository.deleteByPort(portTemplate.portid);
//    	
//    	List<PortTestTemplateBindEntity> items = this.portTestTemplateBindRepository.findByTemplate(portTemplate.templateid);
//    	for (PortTestTemplateBindEntity templateItem: items) {
//    		PortTestEntitySimple ref = this.portTestRepositorySimple.getById(templateItem.getPort_test());
//    		PortTestEntitySimple newPortTest = ref.clone();
//    		newPortTest.setPort(portTemplate.portid);    		
//    		this.portTestRepositorySimple.save(newPortTest);
//    	}
    	return "OK";
    }
    
    @PostMapping("/applyPortTemplates")
    public String applyPortTemplates(@RequestBody List<PortTemplate> portTemplates) {
    	for (PortTemplate portTemplate : portTemplates) {
    		applyPortTemplate(portTemplate);
    	}
    	return "OK";
    }
    
    @GetMapping("/testSummaryEquipmentJson")
    public List<TestItemJson> testSummaryEquipmentJson(@RequestParam(value = "id", required=true) Long id) {
    	List<TestItemJson> ret = new ArrayList<>();
    	
    	List<PortEntity> ports = this.portRepository.findByEquipment(id);
    	for (PortEntity port : ports) {
    		for (PortTestGroupEntity group : port.getTestGroup()) {
        		List<PortTestEntity> portTests = group.getPortTests(); //this.portTestRepository.findByPort(port.getId());
        		for (PortTestEntity portTest : portTests) {
        			String testStatus = createTestStatusLabel(portTest);
        			TestItemJson json = new TestItemJson().equipment(
        					portTest.getId(), 
        					port.getPort_name(),
        					portTest.getDirectionEntity().getName(), 
        					portTest.getTest_itemEntity().getCategoryEntity().getCategory(),
        					portTest.getTest_itemEntity().getTest_item(),
        					portTest.getCriteria(), 
        					portTest.getResult(), 
        					testStatus);
        			ret.add(json);
        		}   			
    		}


    	}
    	
    	return ret;
    }
    
    private List<PortTestEntity> findPortTestEntitiesByPort(Long port) {
    	List<PortTestEntity> ret = new ArrayList<PortTestEntity>();
    	
    	List<PortTestGroupEntity> groups = this.portTestGroupRepository.findByPort(port);
		for (PortTestGroupEntity group : groups) {
    		List<PortTestEntity> portTests = group.getPortTests();
    		ret.addAll(portTests);
		}

    	return ret;
    }
   
    private List<PortTestEntity> findPortTestEntitiesByEquipment(Long equipment) {
    	List<PortTestEntity> ret = new ArrayList<PortTestEntity>();

    	List<PortEntity> ports = this.portRepository.findByEquipment(equipment);
    	for (PortEntity port: ports) {
    		ret.addAll(this.findPortTestEntitiesByPort(port.getId()));
    	}
    	return ret;
    }
    
    @GetMapping("/testDetailPortJson")
    public List<TestItemJson> testDetailPortJson(@RequestParam(value = "id", required=true) Long id) {
    	List<TestItemJson> ret = new ArrayList<>();
    	
		List<PortTestEntity> portTests = this.findPortTestEntitiesByPort(id);
		for (PortTestEntity portTest : portTests) {
			String criteria = portTest.getCriteria();
			if (criteria == null || criteria.isEmpty()) {
				criteria = "-";
			}
			String result = portTest.getResult();
			if (result == null || result.isEmpty()) {
				result = "-";
			}
			
			String testStatus = createTestStatusLabel(portTest);
		
			TestItemJson json = new TestItemJson().port(
					portTest.getId(), 
					portTest.getDirectionEntity().getName(), 
					portTest.getPortTestGroupEntity().getName(),
					portTest.getTest_itemEntity().getCategoryEntity().getCategory(),
					portTest.getTest_itemEntity().getTest_item(),
					criteria, 
					result, 
					testStatus);
			
			json.testCategory = portTest.getTest_itemEntity().getCategoryEntity().getCategory();
			ret.add(json);
		}
    	
    	return ret;
    }

    @GetMapping("/testSummaryPortJson")
    public List<TestItemJson> testSummaryPortJson(@RequestParam(value = "id", required=true) Long id) {
    	List<TestItemJson> ret = new ArrayList<>();
    	
		List<PortTestEntity> portTests = this.findPortTestEntitiesByPort(id);
		for (PortTestEntity portTest : portTests) {
			String criteria = portTest.getCriteria();
			if (criteria == null || criteria.isEmpty()) {
				criteria = "-";
			}
			String result = portTest.getResult();
			if (result == null || result.isEmpty()) {
				result = "-";
			}
			
			String testStatus = createTestStatusLabel(portTest);
		
			TestItemJson json = new TestItemJson().port(
					portTest.getId(), 
					portTest.getDirectionEntity().getName(), 
					portTest.getPortTestGroupEntity().getName(),
					portTest.getTest_itemEntity().getCategoryEntity().getCategory(),
					portTest.getTest_itemEntity().getTest_item(),
					criteria, 
					result, 
					testStatus);
			
			ret.add(json);
		}
    	
    	return marge(ret);
    }
    
    private List<TestItemJson> marge(List<TestItemJson> original) {
    	List<TestItemJson> ret = new ArrayList<>();
    	Map<String, List<TestItemJson>> map = new LinkedHashMap<>();
    	
    	for (TestItemJson o : original) {
    		String key = o.testPoint + o.testGroup;
    		if (!map.containsKey(key)) {
    			map.put(key, new ArrayList<>());
    		}
    		map.get(key).add(o);
    	}
    	
    	for (String key : map.keySet()) {
    		TestItemJson representative = null;
    		for (TestItemJson o : map.get(key)) {
    			representative = o;
    		}
    		ret.add(representative);
    	}
    	return ret;
	}

	@GetMapping("/testSummaryProjectJson")
    public List<TestItemJson> testSummaryProjectJson(@RequestParam(value = "id", required=true) Long id) {
    	List<TestItemJson> ret = new ArrayList<>();
    	
    	List<EquipmentEntity> equipments = this.equipmentRepository.findByProject(id);
    	
    	for (EquipmentEntity equipment : equipments) {
	    	List<PortEntity> ports = this.portRepository.findByEquipment(equipment.getId());
	    	for (PortEntity port : ports) {
	    		List<PortTestEntity> portTests = this.findPortTestEntitiesByPort(port.getId());
	    		for (PortTestEntity portTest : portTests) {
	    			String testStatus = createTestStatusLabel(portTest);
	    			TestItemJson json = new TestItemJson().project(
	    					portTest.getId(), 
	    					equipment.getName(),
	    					port.getPort_name(),
	    					portTest.getDirectionEntity().getName(), 
	    					portTest.getTest_itemEntity().getCategoryEntity().getCategory(),
	    					portTest.getTest_itemEntity().getTest_item(),
	    					portTest.getCriteria(), 
	    					portTest.getResult(), 
	    					testStatus);
	    			ret.add(json);
	    		}
	    	}
    	}
    	return ret;
    }
    
	private String createTestStatusLabel(PortTestEntity portTest) {
		String cls = "";
		if (portTest.getPassfail().compareTo(PassFailEnum.Failed) == 0) {
			cls = "text-danger";
		}
		else if (portTest.getPassfail().compareTo(PassFailEnum.Passed) == 0) {
			cls = "text-success";
		}
		else if (portTest.getPassfail().compareTo(PassFailEnum.Untested) == 0) {
			cls = "text-dark";
		}
		
		String testStatus = "<label class=\"" + cls + "\">" + portTest.getPassfail().toString() + "</label>";
		return testStatus;
	}
    
    @PostMapping("/updatePortTest")
    public String updatePortTest(@RequestBody ElementJson item) {
    	PortTestEntity e = this.portTestRepository.getById(item.id);
    	
    	if (item.field.equals("criteria")) {
    		e.setCriteria(item.value);
    	}
    	else if (item.field.equals("result")) {
    		e.setResult(item.value);
    	}
    	PassFailCalculator calculator = new PassFailCalculator();
    	e.setPassfail( calculator.judgePassFail(e.getCriteria(), e.getResult()) );
    	
    	this.portTestRepository.save(e);
    	return "OK";
    }
    
    @GetMapping("/testitemlist")
    public TestItemList testItemList(@RequestParam(value = "equipmentid", required=false) Long equipmentid) {
    	List<PortEntity> ports = this.portRepository.findByEquipment(equipmentid);

    	List<TesterEntity> testers = this.testerRepository.findAll();
    	Set<Long> allcandidates = new HashSet<>();
    	
    	Map<Long, List<Long>> tmpTesterCapa = new HashMap<>();
    	for (TesterEntity tester : testers) {
    		List<Long> ids = new ArrayList<>();
    		for (TesterCapabilityEntity e : tester.getTestItems()) {
    			ids.add( e.getTestItem());
    		}
    		tmpTesterCapa.put(tester.getId(), ids);
    	}
    	TestItemList ret = new TestItemList();
    	for (PortEntity port : ports) {
    		TestItemListElement element = ret.createPort(port.getId(), port.getPort_name());

    		for (PortTestEntity portTest : this.findPortTestEntitiesByPort(port.getId())) {
    			List<String> candidates = new ArrayList<>();
    			for (TesterEntity tester : testers) {
    				if (tmpTesterCapa.get(tester.getId()).contains(portTest.getTestItem())) {
						candidates.add(tester.getProduct_name());
						allcandidates.add(tester.getId());   					
    				}
    			}			
    			element.add(portTest.getId(), portTest.getDirection(), portTest.getTestItem(), 
    					portTest.getTest_itemEntity().getTest_item(), portTest.getTester(), candidates);

    		}
    	}
    	
		Map<Long, String> map = new HashMap<>();
		testers.forEach(t -> {map.put(t.getId(), t.getProduct_name()); });
    	for (Long testerid : allcandidates) {
    		ret.addMyTesters(this.myTesterRepository.findByTester(testerid), map);
    	}
    	
    	return ret;
    }
   
    @GetMapping("/applyMyTester")
    public TestItemList applyMyTester(@RequestParam(value = "equipmentid", required=true) Long equipmentid,
    		@RequestParam(value = "mytesterid", required=true) Long mytester) {
				
    	MyTesterEntity myTester = this.myTesterRepository.getById(mytester);
    	TesterEntity tester =  this.testerRepository.getById(myTester.getTester());
    	
    	List<PortEntity> ports = this.portRepository.findByEquipment(equipmentid);
    	for (PortEntity port : ports) {
    		for (PortTestEntity portTest : this.findPortTestEntitiesByPort(port.getId())) {
    			
    			for (TesterCapabilityEntity e:  tester.getTestItems()) {
    				if (e.getTestItem() == portTest.getTestItem()) {
    					portTest.setTester(mytester);
    					this.portTestRepository.save(portTest);
    					break;
    				}
    			}
    			
    		}
    	}
    	return testItemList(equipmentid);
    	
    }
    
    @GetMapping("/TestItemCategoryEntityS")
    public List<TestItemCategoryEntity> getTestItemCategories() {
    	return testItemCategoryRepository.findAll();
    }
    
    @GetMapping("/testItemCategoryJson")
    public List<IdValue> testItemCategoryJson() {
    	List<IdValue> ret = new ArrayList<IdValue>();
    	
    	ret.add(new IdValue(-1L, "---"));
    	for (TestItemCategoryEntity e : testItemCategoryRepository.findAll()) {
    		ret.add(new IdValue(e.getId(), e.getCategory()));
    	}
    	return ret;
    }
    
    @PostMapping("/TestItemCategoryEntity")
    public TestItemCategoryEntity createTestItemCategory(@RequestBody TestItemCategoryEntity test_item) {
    	return testItemCategoryRepository.save(test_item);
    }
    
    @DeleteMapping("deleteTestItem")
    public String deleteTestItem(@RequestParam(value = "id", required=true) Long id) {
    	List<PortTestEntity> tests = this.portTestRepository.findByTestItem(id);
    	if (tests.size() == 0) {
    		this.testItemRepository.deleteById(id);
    	}
    	else {
    		for (PortTestEntity t : tests) {
    	//		System.out.println(t.port_name);
    		}
    	}
    	return "OK";
    }
    
    @DeleteMapping("deleteTestItemCategory")
    public String deleteTestItemCategory(@RequestParam(value = "id", required=true) Long id) {
    	List<TestItemEntity> tests = this.testItemRepository.findByCategory(id);
    	if (tests.size() == 0) {
    		this.testItemCategoryRepository.deleteById(id);
    	}
    	else {
    		for (TestItemEntity t : tests) {
    			System.out.println(t.getTest_item());
    		}
    	}
    	return "OK";
    }
    
    @GetMapping("/TesterCapabilityEntityS")
    public List<TesterCapabilityEntity> getTestCapability() {
    	return testerCapabilityRepository.findAll();
    }
    
    @PostMapping("/TesterCapabilityEntity")
    public TesterCapabilityEntity createTesterCapability(@RequestBody TesterCapabilityEntity test_item) {
    	return testerCapabilityRepository.save(test_item);
    }

    @PostMapping("/TesterCapabilityEntityS")
    public String tester_capabilities(@RequestBody Map<String, String> param) {
    	Long tester = Long.valueOf(param.get("id"));
    	this.testerCapabilityRepository.deleteByTester(tester);
    	String[] tmp = param.get("value").split(",");
    	for (String t :tmp) {
    		Long id = Long.valueOf(t.trim());
    		TesterCapabilityEntity e = new TesterCapabilityEntity();
    		e.setTestItem(id);
    		e.setTester(tester);
    		testerCapabilityRepository.save(e);
    	}
    	return "OK";
    }
    
    @PostMapping("/updateEquipment")
    public String updateEquipment(@RequestBody ElementJson ej) {
    	EquipmentEntitySimple equipment = this.equipmentRepositorySimple.getById(ej.id);
    	
    	if (ej.field.equals("name")) {
    		equipment.setName(ej.value);
    	}
    	else if (ej.field.equals("address")) {
    		equipment.setAddress(ej.value);
    	}
    	else if (ej.field.equals("category")) {
    		EquipmentCategoryEntity categoryEntity = this.equipmentCategoryRepository.findByCategory(ej.value);
    		equipment.setCategory(categoryEntity.getId());
    	}
    	this.equipmentRepositorySimple.save(equipment);
    	return "OK";
    }
    
    @GetMapping("/projectSummaryJson")
    public List<ProjectSummaryJson> projectsummary(@RequestParam(value = "id", required=true) Long id) {
    	List<ProjectSummaryJson> ret = new ArrayList<>();
    	List<EquipmentEntity> equipments = this.equipmentRepository.findByProject(id);
    	for (EquipmentEntity equipment : equipments) {
    		String category = "---";
    		if (equipment.getCategoryEntity() != null) {
    			category = equipment.getCategoryEntity().getCategory();
    		}
    		
    		String location = "---";
    		String ports = "---";
    		
    		List<PortEntity> portEntities = this.portRepository.findByEquipment(equipment.getId());
    		List<String> portNames = new ArrayList<>();
    		for (PortEntity p : portEntities) {
    			portNames.add(p.getPort_name());
    		}
    		ports = String.valueOf( portNames.size() );
    		
    		if (equipment.getAddress() != null) {
    			location = equipment.getAddress();
    		}
    		
    		List<PortTestEntity> portTestEntities = this.findPortTestEntitiesByEquipment(equipment.getId());
    		String testStatus = createTestStatus(portTestEntities);
    		
    		ret.add(new ProjectSummaryJson(equipment.getId(), category, equipment.getName(), location, ports, testStatus));
    	}
    	return ret;
    }

	private String createTestStatus(List<PortTestEntity> portTestEntities) {
		int totalTestCount = 0;
		int failCount = 0;
		int passCount = 0;
		int testedCount = 0;
		for (PortTestEntity p : portTestEntities) {
			if (p.getPassfail().compareTo(PassFailEnum.Failed) == 0) {
				failCount++;
			}
			else if (p.getPassfail().compareTo(PassFailEnum.Passed) == 0) {
				passCount++;
			}
			else if (p.getPassfail().compareTo(PassFailEnum.Tested) == 0) {
				testedCount++;
			}
			totalTestCount++;
		}
		int testedTotal = failCount + passCount + testedCount;
		int progress = (int)(((double)testedTotal/(double)totalTestCount) * 100.0);
		String testStatus = progress + "% (" + testedTotal + "/" + totalTestCount + ")";
		
		if (failCount > 0) {
			testStatus = testStatus + "<label class=\"text-danger\"> ,Fails:" + failCount + "</label>";
		}
		else if ((totalTestCount == passCount) && (totalTestCount > 0)) {
			testStatus = testStatus + "<label class=\"text-success\">Passed</label>";
		}
		else if (testedTotal == 0) {
			testStatus = testStatus + "<label class=\"text-dark\">Untested</label>";
		}
		return testStatus;
	}
    
    @GetMapping("/EquipmentEntityS")
    public List<EquipmentEntity> getEquipments(@RequestParam(value = "parent", required=false) Long parent) {
    	if (parent == null) {
    		return this.equipmentRepository.findAll();
    	}
    	else {
    		return equipmentRepository.findByProject(parent);
    	}
    }
    
    @GetMapping("/EquipmentEntity")
    public EquipmentEntity getEquipment(@RequestParam(value = "id", required=false) Long id) {
		return equipmentRepository.getById(id);
    }
    
    @GetMapping("/EquipmentEntitySimpleS")
    public List<EquipmentEntitySimple> getEquipmentSimple(@RequestParam(value = "parent", required=false) Long parent) {
		return this.equipmentRepositorySimple.findByProject(parent);
    }
    
    @PostMapping("/EquipmentEntity")
    public EquipmentEntity createTesterCapability(@RequestBody EquipmentEntity test_item/*, @RequestParam(value = "parent", required=false) Long parent*/) {
//    	if (parent != null) {
//    		test_item.setProject(parent);
//    	}
    	return equipmentRepository.save(test_item);
    }
    
    @PostMapping("/createEquipments")
    public String createEquipments(@RequestBody EquipmentJson json) {
    	Long projectid = json.project;
    	int width = 100;
    	int height = 30;
    	
    	int serial = 0;
    	String baseName = "";
    	boolean serialEnabled = false;
    	if (json.name.contains("#")) {
    		serialEnabled = true;
    		
    		String[] tmp = json.name.split("#");
    		if (tmp.length > 1) {
    			serial = Integer.valueOf(tmp[tmp.length-1]);
    		}
    		baseName = tmp[0];
    	}
    	else {
    		baseName = json.name;
    	}
    	
    	for (int i = 0; i < json.count; i++) {
    		String name = "";
    		if (serialEnabled) {
    			name = baseName + "#" + String.format("%02d", serial);
    			serial++;
    		}
    		else {
    			name = baseName;
    		}
        	EquipmentEntitySimple equipmentEntity = createEquipment(projectid, json.category, name, 0, i * height, width, height);
    	}
    	return "OK";
    }
    @PostMapping("/postEquipmentName")
    public String postEquipmentName(@RequestBody EquipmentNameJson equipmentName/*, @RequestParam(value = "parent", required=false) Long parent*/) {
//    	if (parent != null) {
//    		test_item.setProject(parent);
//    	}
    	EquipmentEntitySimple e = this.equipmentRepositorySimple.getById(equipmentName.id);
    	e.setName(equipmentName.name);
    	equipmentRepositorySimple.save(e);
    	return "OK";
    }
    
    @DeleteMapping("/EquipmentEntity")
    public String deleteequiement(@RequestParam(value = "id", required=true) Long id){
    	this.equipmentPresentationRepository.deleteByEquipment(id);
    	this.equipmentRepository.deleteById(id);
    	List<PortEntity> ports = this.portRepository.findByEquipment(id);
    	this.portRepository.deleteByEquipment(id);
    	for (PortEntity port : ports) {
    		this.portPresentationRepository.deleteByPort(port.getId());
    		
    		for (PortTestGroupEntity testGroup : port.getTestGroup()) {
    			this.portTestRepository.deleteByPorttestgroup(testGroup.getId());
//    			for (PortTestEntity portTest : testGroup.getPortTests()) {
//    				this.portTestRepository.deleteByPort(portTest.getId());
//    			}
    		}
    		
    	}
    	return "OK";
    }
    
    @DeleteMapping("/EquipmentEntityS")
    public String deleteequiements(@RequestParam(value = "id", required=false) String ids){
    	for (Long id : convertToLongArray(ids)) {
    		this.deleteequiement(id);
    	}
    	return "OK";
    }
    
    private List<Long> convertToLongArray(String ids) {
    	List<Long> ret = new ArrayList<>();
    	for (String s: ids.split(",")) {
    		ret.add(Long.valueOf(s.trim()));
    	}
		return ret;
	}

	@GetMapping("/copyEquipment")
    public EquipmentEntitySimple copyEquipment(@RequestParam(value = "id", required=true) Long id, 
    		@RequestParam(value = "number", required=true) Integer number) {

    	EquipmentEntitySimple equipment = this.equipmentRepositorySimple.getById(id);
    	EquipmentPresentationEntity equipmentPresentation = this.equipmentPresentationRepository.findByEquipment(id);
    	
    	AutoName autoName = new AutoName(number, equipment.getName());
    	
    	for (int i = 0; i < autoName.size; i++) {
    		EquipmentEntitySimple newEquipment = equipment.clone();
	    	EquipmentPresentationEntity newEquipmentPresentation = equipmentPresentation.clone();
	
	    	newEquipment.setName(autoName.nextName());
	    	    	
	    	newEquipment = this.equipmentRepositorySimple.save(newEquipment);
	    	newEquipmentPresentation.setX(newEquipmentPresentation.getX());
	    	newEquipmentPresentation.setY(newEquipmentPresentation.getY() + 10);
	    	newEquipmentPresentation.setEquipment(newEquipment.getId());
	    	this.equipmentPresentationRepository.save(newEquipmentPresentation);
	    	
	    	List<PortEntitySimple> ports = this.portRepositorySimple.findByEquipment(id);
	    	for (PortEntitySimple port : ports) {
	    		PortEntitySimple newPort = port.clone();
	    		newPort.setEquipment(newEquipment.getId());;
	    		newPort = this.portRepositorySimple.save(newPort);
	    		
	    		PortPresentationEntity newPortPresentation = this.portPresentationRepository.findByPort(port.getId()).clone();
	    		newPortPresentation.setPort(newPort.getId());
	    		this.portPresentationRepository.save(newPortPresentation);
	    		
	    		List<PortTestGroupEntity> portTestGroups = this.portTestGroupRepository.findByPort(port.getId());
	    		
	    		for (PortTestGroupEntity portTestGroup : portTestGroups) {
		    		//List<PortTestEntity> portTests = this.portTestRepository.findByPorttestgroup(port.getId());
	    			PortTestGroupEntity newPortTestGroup = portTestGroup.clone();
	    			
	    			newPortTestGroup.setPort(newPort.getId());
	    			newPortTestGroup = this.portTestGroupRepository.save(newPortTestGroup);
	    			
	    			for (PortTestEntity portTest : portTestGroup.getPortTests()) {		    		
	    				PortTestEntity newPortTest = portTest.clone();
	    				
	    				newPortTest.setPorttestgroup(newPortTestGroup.getId());

		    			this.portTestRepository.save(newPortTest); 	    				
	    			}
			
	    		}
	    		
	    		

	    	}
    	}
    	return equipment;
    }
    
	class AutoName {
		private String body;
		private int digit;
		private int start;
		private int size;

		private List<String> signs = Arrays.asList("#", "@");
		
		public AutoName(Integer number, String name) {
	    	if (number == null) {
	    		this.size = 1;
	    	}
	    	else {
	    		this.size = number;
	    	}
	    	this.body = name;
	    	
	    	String sign = "";
	    	for (String s : signs) {
	    		if (name.contains(s)) {
	    			sign = s;
	    		}
	    	}
	    	if (!sign.isEmpty()) {
	    		String[] tmp = name.split(sign);
	    		String startStr = tmp[1];
	    		this.digit = startStr.length();
	    		this.start = Integer.valueOf(startStr) + 1;
	    		body = tmp[0] + sign;
	    	}
		}
		
		public String nextName() {
			String ret = body + String.format("%0" + digit + "d", start++);
			return ret;
		}

		public int size() {
			return this.size;
		}
	}
	
    @PostMapping("/createPorts")
    public String createPorts(@RequestBody EquipmentJson json) {
    	Long equipment = json.project;
    	int width = 100;
    	int height = 30;
    	
    	int serial = 0;
    	String baseName = "";
    	boolean serialEnabled = false;
    	if (json.name.contains("#")) {
    		serialEnabled = true;
    		
    		String[] tmp = json.name.split("#");
    		if (tmp.length > 1) {
    			serial = Integer.valueOf(tmp[tmp.length-1]);
    		}
    		baseName = tmp[0];
    	}
    	else {
    		baseName = json.name;
    	}
    	
    	for (int i = 0; i < json.count; i++) {
    		String name = "";
    		if (serialEnabled) {
    			name = baseName + "#" + String.format("%02d", serial);
    			serial++;
    		}
    		else {
    			name = baseName;
    		}
        	this.createPort(equipment, json.category, name, 0, i * height, width, height);
    	}
    	return "OK";
    }
    
    @GetMapping("/copyPort")
    public PortEntity copyPort(@RequestParam(value = "id", required=true) Long id, @RequestParam(value = "number", required=false) Integer number) {
    	PortEntity original = this.portRepository.getById(id);
 //   	List<PortTestEntity> portTests = this(original.getId())
    	AutoName autoName = new AutoName(number, original.getPort_name());
    	
    	for (int i = 0; i < autoName.size(); i++) {
	    	PortEntity newPort = original.clone();
	    	newPort.setPort_name(autoName.nextName());
	    	newPort = this.portRepository.save(newPort);
	    	
	    	for (PortTestGroupEntity group : original.getTestGroup()) {
		    	for (PortTestEntity portTest : group.getPortTests()) {
		    		PortTestEntity newPortTest = portTest.clone();
		    		newPortTest.setPorttestgroup(group.getId());
		    		this.portTestRepository.save(newPortTest);
		    	}	    		
	    	}

	    	PortPresentationEntity presentation = this.portPresentationRepository.findByPort(original.getId()).clone();
	    	presentation.setX(presentation.getX());
	    	presentation.setY(presentation.getY() + 10 * (i+1));
	    	presentation.setPort(newPort.getId());
	    	
	    	this.portPresentationRepository.save(presentation);
    	}
    	
    	return original;
    }
    
    @GetMapping("/createEquipment")
    public EquipmentEntitySimple createEquipment(@RequestParam(value = "projectid", required=true) Long projectid) {
    	String name = "Untitled";
    	int x = 0;
    	int y = 0;
    	int width = 100;
    	int height = 30;
    	
    	EquipmentEntitySimple equipmentEntity = createEquipment(projectid, 1L, name, x, y, width, height);
    	
    	return equipmentEntity;
    }

	private EquipmentEntitySimple createEquipment(Long projectid, Long category, String name, int x, int y, int width, int height) {
		EquipmentEntitySimple equipmentEntity = new EquipmentEntitySimple();
    	equipmentEntity.setProject(projectid);
    	equipmentEntity.setCategory(category);
    	equipmentEntity.setName(name);
    	this.equipmentRepositorySimple.save(equipmentEntity);
    	
    	EquipmentPresentationEntity presentation = new EquipmentPresentationEntity();
    	presentation.setX(x);
    	presentation.setY(y);
    	presentation.setWidth(width);
    	presentation.setHeight(height);
    	presentation.setEquipment(equipmentEntity.getId());
    	this.equipmentPresentationRepository.save(presentation);
		return equipmentEntity;
	}
    
    @GetMapping("/createPort")
    public PortEntitySimple createPort(@RequestParam(value = "equipmentid", required=true) Long equipmentid) {
    	String name = "Untitled Port";
    	int x = 0;
    	int y = 0;
    	int width = 100;
    	int height = 30;
    	
    	PortEntitySimple portEntity = createPort(equipmentid, 1L, name, x, y, width, height);
    	
    	return portEntity;
    }

	private PortEntitySimple createPort(Long equipmentid, Long category, String name, int x, int y, int width, int height) {
		PortEntitySimple portEntity = new PortEntitySimple();
    	portEntity.setEquipment(equipmentid);
    	portEntity.setPort_name(name);
    	this.portRepositorySimple.save(portEntity);
    	
    	PortPresentationEntity presentation = new PortPresentationEntity();
    	presentation.setPort(portEntity.getId());
    	presentation.setX(x);
    	presentation.setY(y);
    	presentation.setWidth(width);
    	presentation.setHeight(height);
    	
    	this.portPresentationRepository.save(presentation);
		return portEntity;
	}
    
    @GetMapping("/renameEquipment")
    public String renameEquipment(@RequestParam(value = "id", required=true) Long id, @RequestParam(value = "name", required=true) String name) {
    	EquipmentEntitySimple  e = this.equipmentRepositorySimple.getById(id);
    	e.setName(name);
    	this.equipmentRepositorySimple.save(e);
    	return name;
    }
    
    @GetMapping("/EquipmentEntity/addport")
    public String addporttoequipment(@RequestParam(value = "id", required=true) Long id, @RequestParam(value = "count", required=true) Long count){
    	for (int i = 0; i < count; i++) {
    		PortEntity entity = new PortEntity();
    		entity.setEquipment(id);
    		entity.setPort_name("Port#" + i);
    		this.portRepository.save(entity);
    	}
    	return "OK";
    }
    
    @GetMapping("/EquipmentCategoryEntityS")
    public List<EquipmentCategoryEntity> getEquipmentCategories() {
    	return equipmentCategoryRepository.findAll();
    }
    
    @PostMapping("/EquipmentCategoryEntity")
    public EquipmentCategoryEntity createEquipmentCategory(@RequestBody EquipmentCategoryEntity test_item) {
    	return equipmentCategoryRepository.save(test_item);
    }

    @DeleteMapping("/EquipmentCategoryEntity")
    public String deleteEquipmentCategory(@RequestParam(value = "id", required=true) Long id) {
    	equipmentCategoryRepository.deleteById(id);
    	return "OK";
    }
    
    @PostMapping("/updateEquipmentCategory")
    public String updateEquipmentCategory(@RequestBody ElementJson json) {
    	EquipmentCategoryEntity entity = null;
    	
    	if (json.id == null) {
    		entity = new EquipmentCategoryEntity();
    		entity.setCategory(json.value);
    	}
    	else {
    		entity = this.equipmentCategoryRepository.getById(json.id);
	    	if (json.field.equals("category")) {
	    		entity.setCategory(json.value);
	    	}
	    	else if (json.field.equals("description")) {
	    		entity.setDescription(json.value);
	    	}
    	}
    	this.equipmentCategoryRepository.save(entity);
    	return "OK";
    }
    
    @GetMapping("/PortCategoryEntityS")
    public List<PortCategoryEntity> PortCategoryEntityS() {
    	return this.portCategoryRepository.findAll();
    }
    
    @PostMapping("/updatePortCategory")
    public String updatePortCategory(@RequestBody ElementJson json) {
    	PortCategoryEntity entity = null;
    	
    	if (json.id == null) {
    		entity = new PortCategoryEntity();
    		entity.setCategory(json.value);
    	}
    	else {
    		entity = this.portCategoryRepository.getById(json.id);
	    	if (json.field.equals("category")) {
	    		entity.setCategory(json.value);
	    	}
	    	else if (json.field.equals("description")) {
	    		entity.setDescription(json.value);
	    	}
    	}
    	this.portCategoryRepository.save(entity);
    	return "OK";
    }
    
    @GetMapping("/ConnectorEntityS")
    public List<ConnectorEntity> connectors() {
    	return connectorRepository.findAll();
    }
    
    @PostMapping("/ConnectorEntity")
    public ConnectorEntity connector(@RequestBody ConnectorEntity test_item) {
    	return connectorRepository.save(test_item);
    }
    
    @GetMapping("/ProjectEntityS")
    public List<ProjectEntity> projects(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return projectRepository.findByUsergroup(userDetails.getUser().getUsergroup());
    }
    
    @GetMapping("/projectList")
    public List<ProjectJson> projectsJson(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	List<ProjectJson> ret = new ArrayList<>();
    	
    	for (ProjectEntity project : projectRepository.findByUsergroup(userDetails.getUser().getUsergroup())) {
    		int totalTestCount = 0;
    		int failCount = 0;
    		int passCount = 0;
    		int testedCount = 0;
    		List<EquipmentEntity>  equipments = this.equipmentRepository.findByProject(project.getId());
    		
    		for (EquipmentEntity equipment : equipments) {
    			for (PortEntity port : equipment.getPorts()) {
    				List<PortTestEntity> portTests = this.findPortTestEntitiesByPort(port.getId());      	

    				for (PortTestEntity p : portTests) {
    					if (p.getPassfail().compareTo(PassFailEnum.Failed) == 0) {
    						failCount++;
    					}
    					else if (p.getPassfail().compareTo(PassFailEnum.Passed) == 0) {
    						passCount++;
    					}
    					else if (p.getPassfail().compareTo(PassFailEnum.Tested) == 0) {
    						testedCount++;
    					}
    					totalTestCount++;
    				}    				
    			}
    		}
    		
			int testedTotal = failCount + passCount + testedCount;
			int progress = (int)(((double)testedTotal/(double)totalTestCount) * 100.0);   		
    					
//			String progressText = progress + "% Total:" +  totalTestCount + ", Pass:" + passCount + ", Fail:<label class=\"" + cls + "\">" + failCount + "</label>";
    		ret.add(new ProjectJson(project.getId(), project.getName(), project.getComment(), totalTestCount, 
    				passCount, failCount, totalTestCount - testedTotal));
    	}
    	
    	return ret;
    }
    
    @PostMapping("/ProjectEntity")
    public ProjectEntity project(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ProjectEntity entity) {
    	entity.setUsergroup(userDetails.getUser().getUsergroup());
    	return projectRepository.save(entity);
    }
    
    @DeleteMapping("/ProjectEntity")
    public String project(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "id", required=false) Long id) {
    	projectRepository.deleteById(id);
    	return "OK";
    }
    
    @GetMapping("/ProjectEntitySimpleS")
    public List<ProjectEntitySimple> projectSimples(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return projectRepositorySimple.findByUsergroup(userDetails.getUser().getUsergroup());
    }
    
    
    @GetMapping("/UserEntityS")
    public List<UserEntity> getUsers() {
    	return userRepository.findAll();
    }
    
    @PostMapping("/UserEntity")
    public UserEntity saveUser(@RequestBody UserEntity entity) {
    	return userRepository.save(entity);
    }
    
    @GetMapping("/mytesters")
    public List<MyTesterEntity> getMyTesters(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	Long group = userDetails.getUser().getUsergroup();
    	return myTesterRepository.getByUsergroup(group);
    }

    @GetMapping("/mytestersforjs")
    public List<MyTesterJson> getMyTestersForJs(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	List<MyTesterJson> ret = new ArrayList<>();
    	Long group = userDetails.getUser().getUsergroup();
    	List<MyTesterEntity> mytesters = myTesterRepository.getByUsergroup(group);
    	
    	for (MyTesterEntity e : mytesters) {
    		TesterEntity tester = this.testerRepository.getById(e.getTester());
    		MyTesterJson json = new MyTesterJson();
    		json.setId(e.getId());
    		json.setStandalone(tester.getProducttype().compareTo(ProductType.Standalone) == 0);
    		json.setVendor(tester.getVendorEntity().getVendorname());
    		json.setTesterName(tester.getProduct_name());
    		json.setMyTesterName(e.getName());
    		json.setParentCandidates(getCandidates(e, tester, mytesters));
    		json.setStatus(e.getOnline() ? "Online" : "-");
    		json.setParentid(e.getParent());
    		json.setDescription(tester.getDescription());
    		List<TesterCategoryRelationEntity> categories = this.testerCategoryRelationRepository.findByTester(tester.getId());
    		String categoryText = "";    		for (TesterCategoryRelationEntity c : categories) {
    			TesterCategoryEntity category = testerCategoryRepository.getById(c.getCategory());
    			categoryText += category.getCategory_name() + "/";
    		}
    		if (categoryText.length() > 0) {
    			json.setCategory(categoryText.substring(0, categoryText.length()-1));
    		}
    		
    		ret.add(json);
    	}
    	return ret;
    }

    static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd h:mm");
    @GetMapping("/mytestersviewforjs")
    public List<MyTesterJson> getMyTestersViewForJs(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	List<MyTesterJson> ret = new ArrayList<>();
    	Long group = userDetails.getUser().getUsergroup();
    	List<MyTesterEntity> mytesters = myTesterRepository.getByUsergroup(group);
    	
    	Map<Long /*parent*/, List<Long> /*children*/> relation = new HashMap<>();
    	for (MyTesterEntity e : mytesters) {
    		if (!relation.containsKey(e.getParent())) {
    			relation.put(e.getParent(), new ArrayList<Long>());
    		}
    		relation.get(e.getParent()).add(e.getId());
    	}
    	
    	ProductTreeGenerator generator = new ProductTreeGenerator(mytesters);
    	
    	for (MyTesterEntity e : mytesters) {
    		TesterEntity tester = this.testerRepository.getById(e.getTester());
    		if (tester.getProducttype().compareTo(ProductType.Standalone) != 0) {
    			continue;
    		}
    		MyTesterJson json = new MyTesterJson();
    		json.setId(e.getId());
    		json.setStandalone(tester.getProducttype().compareTo(ProductType.Standalone) == 0);
    		json.setVendor(tester.getVendorEntity().getVendorname());
    		json.setCategory(generator.categories(e.getId()).toString().replace("[", "").replace("]", ""));
    		if (e.getLastaccess() != null) {
    			json.setLastAccess(format.format(e.getLastaccess()));
    		}
    		else {
    			json.setLastAccess("-");
    		}
    		String optionProduct = generator.findOptions(e.getId(), true);
    		String product = tester.getProduct_name();
    		if (!optionProduct.isEmpty()) {
    			product += "(" + optionProduct.substring(0, optionProduct.length()-1) + ")";
    		}
    		json.setTesterName(product);
    		json.setMyTesterName(e.getName());
    		json.setStatus(e.getOnline() ? "Online" : "-");
    		json.setDescription(tester.getDescription());
    		
    		List<TesterCategoryRelationEntity> categories = this.testerCategoryRelationRepository.findByTester(tester.getId());
    		String categoryText = "";
    		for (TesterCategoryRelationEntity c : categories) {
    			TesterCategoryEntity category = testerCategoryRepository.getById(c.getCategory());
    			categoryText += category.getCategory_name() + "/";
    		}
    		if (categoryText.length() > 0) {
    			json.setCategory(categoryText.substring(0, categoryText.length()-1));
    		}
    		
    		ret.add(json);
    	}
    	return ret;
    }
    
    class ProductTreeGenerator {
    	private Map<Long/*parent*/, List<Long>/*children*/> relation = new HashMap<>();
    	
    	public ProductTreeGenerator(List<MyTesterEntity> mytesters) {
    		for (MyTesterEntity myTester : mytesters) {
    			if (myTester.getParent() == null) {
    				continue;
    			}
    			if (!relation.containsKey(myTester.getParent())) {
    				relation.put(myTester.getParent(), new ArrayList<Long>());
    			}
    			relation.get(myTester.getParent()).add(myTester.getId());
    		}
    	}
    	
    	public Set<String> categories(Long id) {
    		Set<String> ret = new HashSet<>();
    		
    		 MyTesterEntity myTester = myTesterRepository.getById(id);
    		 //TesterEntity tester = testerRepository.getById(myTester.getTester());
    		 List<TesterCategoryRelationEntity> relations = testerCategoryRelationRepository.findByTester(myTester.getTester());
    		 
    		 for (TesterCategoryRelationEntity relation : relations) {
    			 TesterCategoryEntity category = testerCategoryRepository.getById(relation.getCategory());
    			 ret.add(category.getCategory_name());
    		 }


    		List<Long> children = relation.get(id);
    		if (children != null) {
	    		for (Long child : children) {
	    			Set<String> subOption = categories(child);
	    			ret.addAll(subOption);
	    		}
    		}
    		return ret;
		}

		public String findOptions(Long id, boolean initial) {
    		String ret = "";
    		
    		if (!initial) {
	    		 MyTesterEntity myTester = myTesterRepository.getById(id);
	    		 TesterEntity tester = testerRepository.getById(myTester.getTester());
	    		 ret += tester.getProduct_name() + ",";
    		}
    		List<Long> children = relation.get(id);
    		if (children != null) {
	    		for (Long child : children) {
	    			String subOption = findOptions(child, false);
	    			ret += subOption;
	    		}
    		}
    		return ret;
    	}
    }


	private List<Long> getCandidates(MyTesterEntity e, TesterEntity tester, List<MyTesterEntity> mytesters) {
		List<Long> usedParents = new ArrayList<>();
		for (MyTesterEntity mine : mytesters) {
			if (mine.getParent() != null && mine.getParent() > 0) {
				usedParents.add(mine.getParent());
			}
		}
		List<Long> ret = new ArrayList<>();
		List<TesterOptionRelationEntity> parents = this.testerOptionRelationRepository.findByChild(tester.getId());
		for (TesterOptionRelationEntity p : parents) {
			for (MyTesterEntity mine : mytesters) {
				
				if ((p.getParent() == mine.getTester())) {
//					if (!usedParents.contains(mine.getId())) {
						ret.add(mine.getId());
//					}
				}
			}
		}
		return ret;
	}

	@PostMapping("/mytester")
    public MyTesterEntity saveMyTester(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MyTesterEntity entity) {
      	UserEntity e = userRepository.getById(userDetails.getUser().getId());
    	entity.setUsergroup(e.getUsergroup());
    	
    	UserEntity tester = userRepository.findByEmail(entity.getName());
    	tester.setUsergroup(e.getUsergroup());
    	userRepository.save(tester);
    	
    	return myTesterRepository.save(entity);
    }
	
    @DeleteMapping("/mytester")
    public String deletemytester(@RequestParam(value = "id", required=false) Long id){
    	myTesterRepository.deleteById(id);
    	return "OK";
    }
    
    
    @GetMapping("/mytesters/candidates")
    public Map<Long, List<TesterEntity>> getCandidateTester(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "portid", required=true) Long portid) {

    	CandidateCalculator calculator = new CandidateCalculator(portid) {

			@Override
			protected TesterCapabilityRepository testerCapabilityRepository() {
				return testerCapabilityRepository;
			}

			@Override
			protected TesterRepository testerRepository() {
				return testerRepository;
			}

			@Override
			protected List<PortTestEntity> findByPort(Long portid) {
				return findPortTestEntitiesByPort(portid);
			}

    		
    	};
    	return calculator.getMap();
    }
    
    @PostMapping("/setParent")
    public String setParent(@RequestBody MyTesterJson myTester) {
    	//MyTesterRelationEntity e = this.myTesterRelationRepository.getById(myTester.getId());
    	MyTesterEntity e = this.myTesterRepository.getById(myTester.getId());
    	e.setParent(myTester.getParentid());
    	this.myTesterRepository.save(e);
//    	Long parent = myTester.getParentid();
//    	Long child = myTester.getId();
//    	MyTesterRelationEntity e = new MyTesterRelationEntity();
//    	e.setChild(child);
//    	e.setParent(parent);
//    	myTesterRelationRepository.save(e);
    	return "OK";
    }
    
    @GetMapping("/UserGroupEntityS")
    public List<UserGroupEntity> getUserGroups() {
    	return userGroupRepository.findAll();
    }
    
    @PostMapping("/UserGroupEntity")
    public UserGroupEntity saveUserGroup(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserGroupEntity entity) {
    	//userDetails.getUser().getId();
      	
    	UserGroupEntity ret = userGroupRepository.save(entity);
    	
      	UserEntity e = userRepository.getById(userDetails.getUser().getId());
    	e.setUsergroup(ret.getId());
    	userRepository.save(e);
    	
    	return ret;
    }
    
    @GetMapping("/EquipmentPresentationEntityS")
    public List<EquipmentPresentationEntity> uiposition(@RequestParam(value = "parent", required=true) Long project) {
    	if (project != null) {
    		List<EquipmentPresentationEntity> ret = new ArrayList<EquipmentPresentationEntity>();
    		
    		int y = 0;
    		for (EquipmentEntity e : this.equipmentRepository.findByProject(project)) {
	    		EquipmentPresentationEntity u = equipmentPresentationRepository.findByEquipment(e.getId());
	    		if (u == null) {
	    			u = new EquipmentPresentationEntity();
	    			u.setEquipment(e.getId());
	    			u.setX(0);
	    			u.setY(y += 10);
	    			equipmentPresentationRepository.save(u);
	    		}
	    		ret.add(u);   			
    		}
    		
    		return ret;
    	}
    	else {
    		return this.equipmentPresentationRepository.findAll();
    	}
    }
    
    @PostMapping("/EquipmentPresentationEntity")
    public EquipmentPresentationEntity uiposition(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody EquipmentPresentationEntity entity) {
    	return this.equipmentPresentationRepository.save(entity);
    }
    
    @PostMapping("/EquipmentPresentationRect")
    public EquipmentPresentationEntity EquipmentPresentationRect(@RequestBody PrimitiveRect obj) {
    	System.out.println(obj.toString());
    	EquipmentPresentationEntity entity = this.equipmentPresentationRepository.findByEquipment(obj.id);
    	entity.setX(obj.x);
    	entity.setY(obj.y);
    	entity.setWidth(obj.width);
    	entity.setHeight(obj.height);
    	return this.equipmentPresentationRepository.save(entity);
    } 
    
    @PostMapping("/PortPresentationRect")
    public PortPresentationEntity PortPresentationRect(@RequestBody PrimitiveRect obj) {
    	System.out.println(obj.toString());
    	PortPresentationEntity entity = this.portPresentationRepository.findByPort(obj.id);
    	entity.setX(obj.x);
    	entity.setY(obj.y);
    	entity.setWidth(obj.width);
    	entity.setHeight(obj.height);
    	return this.portPresentationRepository.save(entity);
    } 
    
    @GetMapping("/PortPresentationEntityS")
    public List<PortPresentationEntity> portPresentation(@RequestParam(value = "parent", required=true) Long equipment) {
    	if (equipment != null) {
    		List<PortPresentationEntity> ret = new ArrayList<PortPresentationEntity>();
    		
    		int y = 0;
    		for (PortEntity e : this.portRepository.findByEquipment(equipment)) {
	    		PortPresentationEntity u = portPresentationRepository.findByPort(e.getId());
	    		if (u == null) {
	    			u = new PortPresentationEntity();
	    			u.setPort(e.getId());
	    			u.setX(0);
	    			u.setY(y+=10);
	    			portPresentationRepository.save(u);
	    		}
	    		ret.add(u);
    		}
    		
    		return ret;
    	}
    	else {
    		return this.portPresentationRepository.findAll();
    	}
    }
    
    @PostMapping("/PortPresentationEntity")
    public PortPresentationEntity portPresentation(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PortPresentationEntity entity) {
    	return this.portPresentationRepository.save(entity);
    }

        
    @GetMapping("/linkport")
    public String linkPort(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "port1", required=true) Long port1, @RequestParam(value = "port2", required=true) Long port2) {

    	try {
	    	List<PortEntity> oldP1 = portRepository.findByOpposite(port1);
	    	oldP1.forEach(o -> o.setOpposite(null));
    	}
    	catch (Exception e) {
    		
    	}
    	
    	try {
	    	List<PortEntity> oldP2 = portRepository.findByOpposite(port2);
	    	oldP2.forEach(o -> o.setOpposite(null));
    	}
    	catch (Exception e) {
    		
    	}
    	
    	PortEntity p1 = this.portRepository.getById(port1);
    	PortEntity p2 = this.portRepository.getById(port2);

    	p1.setOpposite(p2.getId());
    	p2.setOpposite(p1.getId());
    	
    	portRepository.save(p1);
    	portRepository.save(p2);
    	return "OK";
    }
        
    @DeleteMapping("/clear_results")
    public String clearResults(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "scenario_id", required=true) Long scenario_id) {
    	List<TestScenarioItemEntity> testScenario = this.testScenarioItemRepository.findByTestScenario(scenario_id);

    	for (TestScenarioItemEntity scenarioItem : testScenario) {
    		EquipmentEntitySimple equipment = this.equipmentRepositorySimple.getById(scenarioItem.getEquipment());
    		
    		List<PortEntity> ports = this.portRepository.findByEquipment(equipment.getId());
    		
    		for (PortEntity port : ports) {
    			for (PortTestEntity portTest : this.findPortTestEntitiesByPort(port.getId())) {
        			portTest.setResult("");
        			portTest.setPassfail(PassFailEnum.Untested);
        			
        			this.portTestRepository.save(portTest);   				
    			}
    		}
    	}
    	return "OK";
    }
    
    @GetMapping("/EquipmentEntity/publish")
    public String testPlan(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "equipmentids", required=true) String equipmentids) {
    	
    	for (String id : equipmentids.split(",")) {
    		Long equipmentid = Long.valueOf(id.trim());
    		EquipmentEntity equipment = this.equipmentRepository.getById(equipmentid);
    		equipment.setStatus(1);
    		this.equipmentRepository.save(equipment);
    	}
    	return "OK";
    }
    
    @PostMapping("/createTestScenario")
    public TestScenarioEntity createTestScenario(@RequestBody TestCaseRequest request) {
    	TestScenarioEntity e = new TestScenarioEntity();
    	e.setName(request.name);
    	e = this.testScenarioRepository.save(e);
    	
    	for (Long equipment : request.equipments) {
    		TestScenarioItemEntity item = new TestScenarioItemEntity();
    		item.setEquipment(equipment);
    		item.setTestScenario(e.getId());
    		this.testScenarioItemRepository.save(item);
    	}   
    	return e;
    }
    
//    @GetMapping("/testPlan")
//    public TestPlan testPlan(@AuthenticationPrincipal CustomUserDetails userDetails) {
//    	TestPlan testPlan = new TestPlan();
//    	
//    	Long mytesterid = userDetails.getUser().getId();
//    	MyTesterEntity mytester = this.myTesterRepository.getById(mytesterid);
//    	TesterEntity tester = this.testerRepository.getById(mytester.getTester());
//    	List<Long> supportids = new ArrayList<>();
//    	tester.getTestItems().forEach(c -> supportids.add(c.getTestItem()));
//    	
//    	List<EquipmentEntity> equipments = this.equipmentRepository.findByStatus(1);
//    	for (EquipmentEntity equipment : equipments) {
//    		for (PortEntity port : this.portRepository.findByEquipment(equipment.getId())) {
//    	    	List<PortTestEntity> porttests = this.portTestRepository.findByPort(port.getId());
//    	    	for (PortTestEntity pte : porttests) {
//    	    		boolean support = supportids.contains(pte.getTestItem());
//    	    		testPlan.equipment(equipment.getId()).port(pte.getPort()).testItem(pte.getId(), pte.getTestItem(), 
//    	    				pte.getDirection(), pte.getTester() , pte.getResult(), support);
//    	    		
//    	    		testPlan.presentation().equipment(equipment.getId(), equipment.getName()).port(pte.getPort(), 
//    	    				this.portRepository.getById(pte.getPort()).getPort_name()).testItem(pte.getTestItem(), pte.getTest_itemEntity().getTest_item());
//    	    	}    			
//    		}
//    	}
//
//    	return testPlan;
//    }
    
    @GetMapping("/TestScenarioEntityS")
    public List<TestScenarioEntity> testScenarioEntityS(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return this.testScenarioRepository.findAll();
    }
    
    @GetMapping("/TestScenarioItemEntityS")
    public List<TestScenarioItemEntity> testScenarioItemEntityS(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "scenario", required=true) Long scenario) {
    	return this.testScenarioItemRepository.findByTestScenario(scenario);
    }
    


	private void cleanUpEntities() {
		// Clean up entities
    	List<TestItemEntity> testItems = this.testItemRepository.findAll();
    	List<Long> allTestItemIds = new ArrayList<>();
    	testItems.forEach(c -> allTestItemIds.add(c.getId()));
    	for (TesterCapabilityEntitySimple e : this.testerCapabilityRepositorySimple.findAll()) {
    		if (!allTestItemIds.contains(e.getTestItem()) ) {
    			this.testerCapabilityRepositorySimple.delete(e);
    		}
    	}
	}
    
    @GetMapping("/javasource")
    public String getSource() {
    	StringBuilder ret = new StringBuilder();
    	
    	ret.append("public class TestItemDef {\n");
    	
    	for (TestItemCategoryEntity category : this.testItemCategoryRepository.findAll()) {
    		ret.append("	public static class " + escape(category.getCategory()) + "{\n");
    		List<TestItemEntity> testItems = this.testItemRepository.findByCategory(category.getId());
    		for (TestItemEntity testItem : testItems) {
    			ret.append("		public final static Long " + escape(testItem.getTest_item()) +  " = " + testItem.getId() + "L;\n");
    		}
    		ret.append("	}\n");
    	}
    	
    	ret.append("}");

    	return ret.toString();
    }

    @PostMapping("/requestOtherTester")
    public String requestOtherTester(@RequestBody TestPlan2Element element) throws JsonProcessingException {
    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.RequestTest, element);
    	TextMessage json = new TextMessage(new ObjectMapper().writeValueAsString(signal));
    	this.messageHandler.getTesterSessions().forEach(ws -> {
    		try {
				ws.sendMessage(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	
    	return "OK";
    }
    
	private String escape(String text) {
		return text.replace(" ", "_").toUpperCase().replace("@", "_") .replace(":", "_").replace("(", "_").replace(")", "").replace("/", "_");
	}
	
	class Link {
		public Link(Long from2, Long to2) {
			if (from2 < to2) {
				link = String.valueOf(from2) + "," + String.valueOf(to2);
			}
			else {
				link = String.valueOf(to2) + "," + String.valueOf(from2);
			}
		}
		public String link;

		@Override
		public String toString() {
			return this.link;
		}
		
		@Override
		public int hashCode() {
			return 1;
		}
	}
	
	@GetMapping("/equipmentDiagram")
	public DiagramItemContainers equipmentsDiagram(@RequestParam(value = "parent", required=true) Long project) {	
		DiagramItemContainers ret = new DiagramItemContainers();
		List<EquipmentEntitySimple> equipments = this.equipmentRepositorySimple.findByProject(project);
		
		LinkContainer linkContainer = new LinkContainer() {
			@Override
			protected Rectangle presentation(Long id) {
				EquipmentPresentationEntity pre = equipmentPresentationRepository.findByEquipment(id);

				Rectangle ret = new Rectangle();
				ret.width = pre.getWidth();
				ret.height = pre.getHeight();
				ret.x = pre.getX();
				ret.y = pre.getY();
				return ret;
			}
		};
		
		DiagramItemContainer container = new DiagramItemContainer(-1L, "");
		ret.add(container);
		
		for (EquipmentEntitySimple equipment : equipments) {
			EquipmentPresentationEntity presentation = this.equipmentPresentationRepository.findByEquipment(equipment.getId());
//			presentation.setWidth(width);
//			presentation.setHeight(height);
			container.addItem(new DiagramItem(equipment.getId(), equipment.getName(), 
					presentation.getX(), presentation.getY(), presentation.getWidth(), presentation.getHeight()));
			
			List<PortEntitySimple> ports = this.portRepositorySimple.findByEquipment(equipment.getId());
			for (PortEntitySimple port: ports) {
				if (port.getOpposite() != null) {
					if (this.portRepositorySimple.existsById(port.getOpposite())) {
						PortEntitySimple oppositePort = this.portRepositorySimple.getById(port.getOpposite());
						linkContainer.add(equipment.getId(), oppositePort.getEquipment());
					}
					else {
						port.setOpposite(null);
						this.portRepositorySimple.save(port);
					}
				}
					
			}
		}

		//ret.pack(linkContainer);
		ret.createLink(linkContainer);
		return ret;
	}
	
	@GetMapping("/portDiagram")
	public DiagramItemContainers portDiagram(@RequestParam(value = "parent", required=true) List<Long> equipments) {
		DiagramItemContainers ret = new DiagramItemContainers();
		LinkContainer linkContainer = new LinkContainer() {
			@Override
			protected Rectangle presentation(Long id) {
				PortPresentationEntity pre = portPresentationRepository.findByPort(id);
				PortEntitySimple portEntity = portRepositorySimple.getById(id);
				EquipmentPresentationEntity equipmentPresentation = equipmentPresentationRepository.findByEquipment(portEntity.getEquipment());
				
				Rectangle rect = new Rectangle();
				rect.x = pre.getX() + equipmentPresentation.getX();
				rect.y = pre.getY() + equipmentPresentation.getY();
				return rect;
			}
		};
		
		for (Long equipment : equipments) {
			EquipmentPresentationEntity equipmentPresentation = this.equipmentPresentationRepository.findByEquipment(equipment);
			EquipmentEntitySimple equipmentEntity = this.equipmentRepositorySimple.getById(equipment);
			DiagramItemContainer diagram = new DiagramItemContainer(equipment, equipmentEntity.getName());
			
			diagram.x1 = equipmentPresentation.getX();
			diagram.y1 = equipmentPresentation.getY();
			
			ret.add(diagram);
			List<PortEntitySimple> ports = this.portRepositorySimple.findByEquipment(equipment);
			
			for (PortEntitySimple port : ports) {
				PortPresentationEntity presentation = this.portPresentationRepository.findByPort(port.getId());
				diagram.addItem(new DiagramItem(port.getId(), port.getPort_name(), 
						presentation.getX() + equipmentPresentation.getX(), 
						presentation.getY() + equipmentPresentation.getY(), 
						presentation.getWidth(), presentation.getHeight()));
				
				if (port.getOpposite() != null) {
					linkContainer.add(port.getId(), port.getOpposite());
				}
			}
		}
		
		ret.pack(linkContainer);

		return ret;
	}
	
	@PostMapping("/registerTester")
	public String registerTester(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MyTesterRegistration reg) {
		MyTesterEntity entity = new MyTesterEntity();
		entity.setName(reg.name);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(reg.password);
        entity.setPassword(encodedPassword);
        entity.setTester(reg.tester);
        entity.setUsergroup(userDetails.getUser().getUsergroup());
        entity.setOnline(false);
        
        this.myTesterRepository.save(entity);
		return "OK";
	} 
	
	@GetMapping("/testerVendors")
    public List<TesterVendorEntity> testerVendors(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return testerVendorRepository.findAll();
    }

	@PostMapping("/testerVendor")
	public TesterVendorEntity testerVendor(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody TesterVendorEntity entity) {
		return this.testerVendorRepository.save(entity);
	}
	
	@GetMapping("/productTypeList")
	public List<IdValue> productTypeList() {
		List<IdValue> ret = new ArrayList<>();
		for (ProductType p : ProductType.values()) {
			ret.add(new IdValue(p.ordinal(), p.name()));
		}
		return ret;
	}

	@GetMapping("/requestImage")
	public String getImage(@RequestParam(value = "name", required=true) String name) throws IOException {
    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.RequestImage, null);
    	TextMessage json = new TextMessage(new ObjectMapper().writeValueAsString(signal));
    	this.messageHandler.getTesterSessions().forEach(ws -> {
    		try {
				ws.sendMessage(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	return "OK";
	} 
	
	@GetMapping("/stopRequestImage")
	public String stopRequestImage(@RequestParam(value = "name", required=true) String name) throws IOException {
    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.StopRequestImage, null);
    	TextMessage json = new TextMessage(new ObjectMapper().writeValueAsString(signal));
    	this.messageHandler.getTesterSessions().forEach(ws -> {
    		try {
				ws.sendMessage(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
    	return "OK";
	} 
	
	@GetMapping("/screenImage")
	public String screenImage(@RequestParam(value = "name", required=true) String name) throws IOException {
		if (TesterRestController.image != null) {
			return TesterRestController.image.get(name);
		}
		else {
			return "";
		}
	} 
	
	@PostMapping("/mouseEvent")
	public String mouseEvent(@RequestBody MouseEventJson mouseEvent) throws JsonProcessingException {
		System.out.println("x=" + mouseEvent.x + ", y=" + mouseEvent.y);
    	WebSocketSignal signal = new WebSocketSignal(WebSocketSignal.SignalType.MouseEvent, mouseEvent);
    	TextMessage json = new TextMessage(new ObjectMapper().writeValueAsString(signal));
    	this.messageHandler.getTesterSessions().forEach(ws -> {
    		try {
				ws.sendMessage(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	});
		return "OK";
	}
}
