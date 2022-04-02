package com.miyake.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miyake.demo.entities.PropertyEntity;
import com.miyake.demo.entities.PropertyOptionEntity;
import com.miyake.demo.entities.PropertyType;
import com.miyake.demo.entities.PropertyUnitEntity;
import com.miyake.demo.entities.TestInstrumentEntity;
import com.miyake.demo.repository.PropertyOptionRepository;
import com.miyake.demo.repository.PropertyRepository;
import com.miyake.demo.repository.TestInstrumentRepository;
import com.miyake.demo.repository.UnitRepository;

@RestController
public class PropertyRestController {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyOptionRepository propertyOptionRepository;
    
    @Autowired
    private TestInstrumentRepository testInstrumentRepository;

    @Autowired
    private UnitRepository propertyUnitRepository;
    
    @GetMapping("/PropertyEntityS")
    public List<PropertyEntity> getProperties(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "parent", required=false) Long parent) {
    	if (parent == null) {
    		return this.propertyRepository.findAll();
    	}
    	else {
    		return propertyRepository.findByTestinstrument(parent);
    	}
    }
    
    @GetMapping("/PropertyEntity")
    public PropertyEntity getProperty(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "id", required=true) Long id) {
    	PropertyEntity ret = propertyRepository.getById(id);
    	return ret;
    }
    
    @PostMapping("/PropertyEntity")
    public String setProperty(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PropertyEntity entity) {
    	entity.setName(entity.getName().toUpperCase().replace(" ", "_"));
    	this.propertyRepository.save(entity);
    	return "OK";
    }
    
    @GetMapping("/PropertyOptionEntityS")
    public List<PropertyOptionEntity> getPropertyOptions(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "parent", required=true) Long id) {
    	return propertyOptionRepository.findByProperty(id);
    }
    
    @PostMapping("/PropertyOptionEntity")
    public String setPropertyOption(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PropertyOptionEntity entity) {
    	if (entity.getName() != null) {
    		entity.setName(entity.getName().toUpperCase().replace(" ", "_"));
    	}
    	this.propertyOptionRepository.save(entity);
    	return "OK";
    }
    
    @GetMapping("/TestInstrumentEntityS")
    public List<TestInstrumentEntity> getTestInstruments(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return testInstrumentRepository.findAll();
    }
    
    @PostMapping("/TestInstrumentEntity")
    public String setTestInstrument(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody TestInstrumentEntity entity) {
    	this.testInstrumentRepository.save(entity);
    	return "OK";
    }
    
    @GetMapping("/PropertyUnitEntityS")
    public List<PropertyUnitEntity> getPropertyUnits(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	return propertyUnitRepository.findAll();
    }
    
    @PostMapping("/PropertyUnitEntity")
    public String setPropertyUnit(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PropertyUnitEntity entity) {
    	this.propertyUnitRepository.save(entity);
    	return "OK";
    }
    
    @GetMapping("instsource")
    public String getInstSource(@AuthenticationPrincipal CustomUserDetails userDetails, 
    		@RequestParam(value = "instrument", required=true) Long instrument) {
    	TestInstrumentEntity inst = this.testInstrumentRepository.getById(instrument);
    	
    	List<PropertyEntity> props = this.propertyRepository.findByTestinstrument(instrument);
    	StringBuilder sb = new StringBuilder();
    	sb.append("public class " + "InstDef_" + inst.getName() + " {\n");
    	for (PropertyEntity pe : props) {
    		sb.append("\tpublic static final long " + pe.getName() + " = " + pe.getId().toString() + ";\n");
    		
    		if (pe.getType().compareTo(PropertyType.List) == 0) {
    			for (PropertyOptionEntity option : pe.getOption_list()) {
    				sb.append("\tpublic static final long " + pe.getName() + "__" + option.getName() + " = " + option.getId() + ";\n");
    			}
    		}
    	}
    	sb.append("}\n");
    	return sb.toString();
    }
}
