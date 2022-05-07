package com.miyake.demo;

import java.awt.Rectangle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.miyake.demo.entities.ConnectorEntity;
import com.miyake.demo.entities.EquipmentCategoryEntity;
import com.miyake.demo.entities.EquipmentEntity;
import com.miyake.demo.entities.EquipmentEntitySimple;
import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.entities.PassFailEnum;
import com.miyake.demo.entities.PortDirectionEntity;
import com.miyake.demo.entities.PortEntity;
import com.miyake.demo.entities.PortEntitySimple;
import com.miyake.demo.entities.PortPresentationEntity;
import com.miyake.demo.entities.PortTestEntity;
import com.miyake.demo.entities.PortTestEntitySimple;
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
import com.miyake.demo.jsonobject.DiagramItem;
import com.miyake.demo.jsonobject.DiagramItemContainer;
import com.miyake.demo.jsonobject.DiagramItemContainers;
import com.miyake.demo.jsonobject.EquipmentElementJson;
import com.miyake.demo.jsonobject.EquipmentNameJson;
import com.miyake.demo.jsonobject.IdValue;
import com.miyake.demo.jsonobject.LinkContainer;
import com.miyake.demo.jsonobject.MouseEventJson;
import com.miyake.demo.jsonobject.MyTesterJson;
import com.miyake.demo.jsonobject.MyTesterRegistration;
import com.miyake.demo.jsonobject.ParentTester;
import com.miyake.demo.jsonobject.ParentTestersJson;
import com.miyake.demo.jsonobject.PortSummaryJson;
import com.miyake.demo.jsonobject.PortTemplate;
import com.miyake.demo.jsonobject.PortTestJson;
import com.miyake.demo.jsonobject.PrimitiveRect;
import com.miyake.demo.jsonobject.ProjectJson;
import com.miyake.demo.jsonobject.ProjectSummaryJson;
import com.miyake.demo.jsonobject.TestCaseRequest;
import com.miyake.demo.jsonobject.TestItemList;
import com.miyake.demo.jsonobject.TestItemListElement;
import com.miyake.demo.jsonobject.TestPlan;
import com.miyake.demo.jsonobject.TestPlan2Element;
import com.miyake.demo.jsonobject.TesterJson;
import com.miyake.demo.jsonobject.WebSocketSignal;
import com.miyake.demo.repository.ConnectorRepository;
import com.miyake.demo.repository.EquipmentCategoryRepository;
import com.miyake.demo.repository.EquipmentRepository;
import com.miyake.demo.repository.EquipmentRepositorySimple;
import com.miyake.demo.repository.MyTesterRelationRepository;
import com.miyake.demo.repository.MyTesterRepository;
import com.miyake.demo.repository.PortDirectionRepository;
import com.miyake.demo.repository.PortPresentationRepository;
import com.miyake.demo.repository.PortRepository;
import com.miyake.demo.repository.PortRepositorySimple;
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
    	
    	List<PortTestEntity> tests = portTestRepository.findByPort(port.getId());
    	
    	List<PortEntity> ports = portRepository.findByEquipment(port.getEquipment());
    	for (PortEntity p : ports) {
    		if (p.getId().equals(port.getId())) {
    			continue;
    		}
    		
    		portTestRepository.deleteByPort(p.getId());
    		
    		for (PortTestEntity test : tests) {
    			PortTestEntity t = new PortTestEntity();
    			t.setPort(p.getId());
    			t.setDirection(test.getDirection());
    			t.setTestItem(test.getTestItem());
    			portTestRepository.save(t);
    		}

    	}
    	//this.portRepository.save(port);
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

    @GetMapping("/PortTestEntityByEquipment")
    public List<PortTestEntity> getPortTestEntityByEquipment(@RequestParam(value = "equipment", required=true) Long equipment) {
    	List<PortTestEntity> ret = new ArrayList<>();
    	List<PortEntitySimple> ports = this.portRepositorySimple.findByEquipment(equipment);
    	
    	for (PortEntitySimple port : ports) {
    		List<PortTestEntity> list = this.portTestRepository.findByPort(port.getId());
    		list.forEach(l -> {l.port_name = port.getPort_name();});
    		ret.addAll(list);
    	}
    	
    	return ret;
    }
    
    @GetMapping("/PortTestEntityS")
    public List<PortTestEntity> createPortTesters(@RequestParam(value = "parent", required=false) Long parent) {
    	List<PortTestEntity> ret = null;
    	if (parent == null) {
    		ret = portTestRepository.findAll();
    	}
    	else {
    		ret = portTestRepository.findByPort(parent);
    	}

//    	ret.forEach(l -> {l.port_name = port.getPort_name();});
    	return ret;
    }

    @GetMapping("/portsummaryjson")
    public List<PortSummaryJson> portsummaryjson(@RequestParam(value = "id", required=true) Long id) {
    	List<PortSummaryJson> ret = new ArrayList<>();
    	List<PortEntity> ports = this.portRepository.findByEquipment(id);
    	for (PortEntity e : ports) {
    		Set<String> testItems = new LinkedHashSet<>();
    		Set<String> testPoints = new LinkedHashSet<>();
    		for (PortTestEntity p : e.getPortTests()) {
    			testItems.add( p.getTest_itemEntity().getTest_item() );
    			testPoints.add(p.getDirectionEntity().getName());
    		}
    		
    		ret.add(new PortSummaryJson(e.getId(), e.getPort_name(), testPoints.toString(), testItems.toString()));
    	}
    	return ret;
    }
    
    @PostMapping("/PortTestJson")
    public PortTestEntitySimple postPortTestJson(@RequestBody PortTestJson port_test) {
    	PortTestEntitySimple entity = this.portTestRepositorySimple.getById(port_test.id);
    	entity.setCriteria(port_test.criteria);
    	entity.setResult(port_test.result);
    	entity.setDirection(port_test.direction);
    	entity.setTestItem(port_test.test_item);
   
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
    
    @DeleteMapping("/PortTestEntity")
    public String deletePortTestEntity(@RequestBody PortTestEntity port_tester) {
    	portTestRepository.deleteById(port_tester.getId());
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
    	PortTestTemplateEntity portTestTemplateEntity = new PortTestTemplateEntity();
    	portTestTemplateEntity.setName(portTemplate.name);
    	portTestTemplateEntity = this.portTestTemplateRepository.save(portTestTemplateEntity);
    	
    	List<PortTestEntitySimple> refPortTests = this.portTestRepositorySimple.findByPort(portTemplate.portid);
    	
    	for (PortTestEntitySimple refPortTest : refPortTests) {
    		PortTestEntitySimple newPortTest = refPortTest.clone();
    		newPortTest = this.portTestRepositorySimple.save(newPortTest);
    		
    		PortTestTemplateBindEntity bind = new PortTestTemplateBindEntity();
    		bind.setPort_test(newPortTest.getId());
    		bind.setTemplate(portTestTemplateEntity.getId());
    		
    		this.portTestTemplateBindRepository.save(bind);
    	}
    	return "OK";
    }
    
    @PostMapping("/applyPortTemplate")
    public String applyPortTemplate(@RequestBody PortTemplate portTemplate) {
    	this.portTestRepository.deleteByPort(portTemplate.portid);
    	
    	List<PortTestTemplateBindEntity> items = this.portTestTemplateBindRepository.findByTemplate(portTemplate.templateid);
    	for (PortTestTemplateBindEntity templateItem: items) {
    		PortTestEntitySimple ref = this.portTestRepositorySimple.getById(templateItem.getPort_test());
    		PortTestEntitySimple newPortTest = ref.clone();
    		newPortTest.setPort(portTemplate.portid);    		
    		this.portTestRepositorySimple.save(newPortTest);
    	}
    	return "OK";
    }
    
    @PostMapping("/applyPortTemplates")
    public String applyPortTemplates(@RequestBody List<PortTemplate> portTemplates) {
    	for (PortTemplate portTemplate : portTemplates) {
    		applyPortTemplate(portTemplate);
    	}
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

    		for (PortTestEntity portTest : port.getPortTests()) {
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
    		for (PortTestEntity portTest : port.getPortTests()) {
    			
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
    
    @PostMapping("/TestItemCategoryEntity")
    public TestItemCategoryEntity createTestItemCategory(@RequestBody TestItemCategoryEntity test_item) {
    	return testItemCategoryRepository.save(test_item);
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
    public String updateEquipment(@RequestBody EquipmentElementJson ej) {
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
    		String testStatus = "---";
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
    		ret.add(new ProjectSummaryJson(equipment.getId(), category, equipment.getName(), location, ports, testStatus));
    	}
    	return ret;
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
    		this.portTestRepository.deleteByPort(port.getId());
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
    		@RequestParam(value = "number", required=false) Integer number) {

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
	    		
	    		List<PortTestEntitySimple> portTests = this.portTestRepositorySimple.findByPort(port.getId());
	    		
	    		for (PortTestEntitySimple portTest : portTests) {
	    			PortTestEntitySimple newPortTest = portTest.clone();
	    			newPortTest.setPort(newPort.getId());
	    			this.portTestRepositorySimple.save(newPortTest);
	    			
	    			
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

		public AutoName(Integer number, String name) {
	    	if (number == null) {
	    		this.size = 1;
	    	}
	    	else {
	    		this.size = number;
	    	}
	    	this.body = name;
	    	
	    	if (name.contains("#")) {
	    		String[] tmp = name.split("#");
	    		String startStr = tmp[1];
	    		this.digit = startStr.length();
	    		this.start = Integer.valueOf(startStr) + 1;
	    		body = tmp[0] + "#";
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
	
    @GetMapping("/copyPort")
    public PortEntitySimple copyPort(@RequestParam(value = "id", required=true) Long id, @RequestParam(value = "number", required=false) Integer number) {
    	PortEntitySimple original = this.portRepositorySimple.getById(id);
    	List<PortTestEntity> portTests = this.portTestRepository.findByPort(original.getId());
    	
    	AutoName autoName = new AutoName(number, original.getPort_name());

    	
    	for (int i = 0; i < autoName.size(); i++) {
	    	PortEntitySimple newPort = original.clone();
	    	newPort.setPort_name(autoName.nextName());
	    	newPort = this.portRepositorySimple.save(newPort);
	    	
	    	for (PortTestEntity portTest : portTests) {
	    		PortTestEntity newPortTest = portTest.clone();
	    		newPortTest.setPort(newPort.getId());
	    		this.portTestRepository.save(newPortTest);
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
    	EquipmentEntitySimple equipmentEntity = new EquipmentEntitySimple();
    	equipmentEntity.setProject(projectid);
    	equipmentEntity.setName("Untitled");
    	this.equipmentRepositorySimple.save(equipmentEntity);
    	
    	EquipmentPresentationEntity presentation = new EquipmentPresentationEntity();
    	presentation.setX(0);
    	presentation.setY(0);
    	presentation.setWidth(100);
    	presentation.setHeight(30);
    	
    	presentation.setEquipment(equipmentEntity.getId());
    	this.equipmentPresentationRepository.save(presentation);
    	
    	return equipmentEntity;
    }
    
    @GetMapping("/createPort")
    public PortEntitySimple createPort(@RequestParam(value = "equipmentid", required=true) Long equipmentid) {
    	PortEntitySimple portEntity = new PortEntitySimple();
    	portEntity.setEquipment(equipmentid);
    	portEntity.setPort_name("Untitled Port");
    	this.portRepositorySimple.save(portEntity);
    	
    	PortPresentationEntity presentation = new PortPresentationEntity();
    	presentation.setPort(portEntity.getId());
    	presentation.setX(0);
    	presentation.setY(0);
    	presentation.setWidth(100);
    	presentation.setHeight(30);
    	
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

    @PostMapping("/updateEquipmentCategory")
    public String updateEquipmentCategory(@RequestBody EquipmentElementJson json) {
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
    	for (ProjectEntity e : projectRepository.findByUsergroup(userDetails.getUser().getUsergroup())) {
    		ret.add(new ProjectJson(e.getId(), e.getName(), e.getComment()));
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
			protected PortTestRepository portTestRepository() {
				return portTestRepository;
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
    public EquipmentPresentationEntity uiposition2(@RequestBody PrimitiveRect obj) {
    	System.out.println(obj.toString());
    	EquipmentPresentationEntity entity = this.equipmentPresentationRepository.findByEquipment(obj.id);
    	entity.setX(obj.x);
    	entity.setY(obj.y);
    	entity.setWidth(obj.width);
    	entity.setHeight(obj.height);
    	return this.equipmentPresentationRepository.save(entity);
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
    			for (PortTestEntity portTest : port.getPortTests()) {
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
    
    @GetMapping("/testPlan")
    public TestPlan testPlan(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	TestPlan testPlan = new TestPlan();
    	
    	Long mytesterid = userDetails.getUser().getId();
    	MyTesterEntity mytester = this.myTesterRepository.getById(mytesterid);
    	TesterEntity tester = this.testerRepository.getById(mytester.getTester());
    	List<Long> supportids = new ArrayList<>();
    	tester.getTestItems().forEach(c -> supportids.add(c.getTestItem()));
    	
    	List<EquipmentEntity> equipments = this.equipmentRepository.findByStatus(1);
    	for (EquipmentEntity equipment : equipments) {
    		for (PortEntity port : this.portRepository.findByEquipment(equipment.getId())) {
    	    	List<PortTestEntity> porttests = this.portTestRepository.findByPort(port.getId());
    	    	for (PortTestEntity pte : porttests) {
    	    		boolean support = supportids.contains(pte.getTestItem());
    	    		testPlan.equipment(equipment.getId()).port(pte.getPort()).testItem(pte.getId(), pte.getTestItem(), 
    	    				pte.getDirection(), pte.getTester() , pte.getResult(), support);
    	    		
    	    		testPlan.presentation().equipment(equipment.getId(), equipment.getName()).port(pte.getPort(), 
    	    				this.portRepository.getById(pte.getPort()).getPort_name()).testItem(pte.getTestItem(), pte.getTest_itemEntity().getTest_item());
    	    	}    			
    		}
    	}

    	return testPlan;
    }
    
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
					PortEntitySimple oppositePort = this.portRepositorySimple.getById(port.getOpposite());
					linkContainer.add(equipment.getId(), oppositePort.getEquipment());
				}
			}
		}
		
		//ret.setLinks(linkContainer.calc());
		ret.pack(linkContainer);
		return ret;
	}
	
	@GetMapping("/portDiagram")
	public DiagramItemContainers portDiagram(@RequestParam(value = "parent", required=true) List<Long> equipments) {
//		int width = 80;
//		int height = 20;
		
		DiagramItemContainers ret = new DiagramItemContainers();
		LinkContainer linkContainer = new LinkContainer() {
			@Override
			protected Rectangle presentation(Long id) {
				PortPresentationEntity pre = portPresentationRepository.findByPort(id);
				PortEntitySimple portEntity = portRepositorySimple.getById(id);
				EquipmentPresentationEntity equipmentPresentation = equipmentPresentationRepository.findByEquipment(portEntity.getEquipment());
				
				Rectangle rect = new Rectangle();
//				rect.width = width;
//				rect.height = height;
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
//				presentation.setWidth(width);
//				presentation.setHeight(height);
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
