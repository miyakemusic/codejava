package com.miyake.demo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import com.miyake.demo.entities.EquipmentEntity;
import com.miyake.demo.entities.PortEntity;
import com.miyake.demo.entities.ProjectEntitySimple;
import com.miyake.demo.entities.TesterCategoryEntity;
import com.miyake.demo.entities.UserEntity;
import com.miyake.demo.repository.EquipmentRepository;
import com.miyake.demo.repository.PortRepository;
import com.miyake.demo.repository.ProjectRepositorySimple;
import com.miyake.demo.repository.TesterCategoryRepository;
import com.miyake.demo.repository.UserRepository;
 

@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectRepositorySimple projectRepositorySimple;
    
    @Autowired
    private EquipmentRepository equipmentRepository;
    
    @Autowired
    private PortRepository portRepository;
    
    @Autowired
    private TesterCategoryRepository testerCategoryRepository;
    
    @GetMapping("")
    public String viewHomePage(Model model) {
        return "index";
    }
        
    @GetMapping("/headerout")
    public String headerout() {
    	return "headerout";
    }
    
    @GetMapping("/header")
    public String header(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
//    	model.addAttribute("name", userDetails.getUser().getFirstName() + "_" + userDetails.getUser().getLastName() + "@" + userDetails.getUser().getUsergroupEntity().getName());
    	model.addAttribute("name", userDetails.getUser().getEmail());
    	return "header";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
         
        return "signup_form";
    }
    
    @GetMapping("/newusergroup")
    public String newUserGroup(Model model) {
        return "newusergroup";
    }
    
    @PostMapping("/process_register")
    public String processRegister(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        userRepo.save(user);
         
        return "register_success";
    }
    
    @GetMapping("/ports")
    public String ports(Model model, @RequestParam(value = "id", required=true) Long id) {
        model.addAttribute("equipment", id);
        return "ports";
    }
 
    @GetMapping("/testpoints")
    public String testpoints(Model model) {
        return "testpoints";
    }
    
    @GetMapping("/cables")
    public String cables(Model model) {
        model.addAttribute("title", "Cables");
        model.addAttribute("getresource", "CableEntityS");
        model.addAttribute("setresource", "updateCable");
        model.addAttribute("dialogTitle", "New Cable");
        
        return "cables";
    }
    
    @GetMapping("/portsummary")
    public String portsummary(Model model, @RequestParam(value = "id", required=true) Long id) {
        model.addAttribute("equipment", id);
        
        EquipmentEntity equipment = this.equipmentRepository.getById(id);
        model.addAttribute("equipmentName", equipment.getName());
        
        ProjectEntitySimple project = this.projectRepositorySimple.getById(equipment.getProject());
        model.addAttribute("projectName", project.getName());
        model.addAttribute("projectid", project.getId());
        
        return "portsummary";
    }
    
    @GetMapping("/projects")
    public String projects(Model model) {
        List<ProjectEntitySimple> listProjects = projectRepositorySimple.findAll();
        model.addAttribute("listProjects", listProjects);
        return "projects";
    }
    
    @GetMapping("/project")
    public String project(Model model, @RequestParam(value = "id", required=true) Long id) {
    	List<EquipmentEntity> equipments = this.equipmentRepository.findByProject(id);
    	ProjectEntitySimple project = this.projectRepositorySimple.getById(id);
    	
    	model.addAttribute("parent", project.getId());
    	model.addAttribute("projectName", project.getName());
    	model.addAttribute("listEquipments", equipments);
    	
        return "project";
    }
    
    @GetMapping("/projectsummary")
    public String projectsummary(Model model, @RequestParam(value = "id", required=true) Long id) {
    	model.addAttribute("id", id);
    	model.addAttribute("projectName", this.projectRepositorySimple.getById(id).getName());
        return "projectsummary";
    }
    
    @GetMapping("/equipmenttopology")
    public String diagram(Model model, @RequestParam(value = "id", required=true) Long id) {
    	ProjectEntitySimple project = this.projectRepositorySimple.getById(id);
    	model.addAttribute("parent", id);
    	model.addAttribute("title", project.getName());
    	return "equipmenttopology";
    }
    
    @GetMapping("/porttopology")
    public String porttopology(Model model, @RequestParam(value = "id", required=true) String ids) {
		Long id;
		if (ids.contains(",")) {
			id = Long.valueOf(ids.split(",")[0]);
		}
		else {
			id = Long.valueOf(ids);
		}
    	EquipmentEntity equipment = this.equipmentRepository.getById(id);
    	model.addAttribute("parent", ids);
    	model.addAttribute("title", equipment.getName());      		

    	return "porttopology";
    }
    
    @GetMapping("/equipment")
    public String equipment(Model model, @RequestParam(value = "id", required=true) Long id) {   	
    	model.addAttribute("parent", id);
    	
        return "equipment";
    }
    
    @GetMapping("/equipmentdef")
    public String equipmentsdef(Model model) {   	
        return "equipmentdef";
    }
    
    @GetMapping("/portdef")
    public String portdef(Model model) {   	
        return "portdef";
    }
    
    @GetMapping("/testitems")
    public String testitems(Model model, @RequestParam(value = "id", required=true) Long id) {   
 //   	List<PortEntity> ports = this.portRepository.findByEquipment(id);
    	model.addAttribute("equipment", id);
        return "testitems";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserEntity> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }
  
    @GetMapping("/assets")
    public String assets(Model model) {
        return "assets";
    }
    
    @GetMapping("/assetsview")
    public String assetsView(Model model) {
        return "assetsview";
    }
    
	@GetMapping("/testerScreen")
    public String testerScreen(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "id", required=true) String id) {
		model.addAttribute("tester", id);
    	return "testerscreen";
    }

	@GetMapping("/testSummaryProject")
    public String testSummaryProject(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "id", required=true) Long id) {		
		ProjectEntitySimple project = this.projectRepositorySimple.getById( id );
		model.addAttribute("project", project.getId());
		model.addAttribute("projectName", project.getName());

    	return "testsummaryproject";
    }
	
	@GetMapping("/testSummaryEquipment")
    public String testSummaryEquipment(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "id", required=true) Long id) {
		model.addAttribute("equipment", id);
		
		EquipmentEntity e = this.equipmentRepository.getById(id);
		
		model.addAttribute("equipmentname", e.getName());
		
		ProjectEntitySimple project = this.projectRepositorySimple.getById( e.getProject() );
		model.addAttribute("projectid", project.getId());
		model.addAttribute("projectName", project.getName());
		
    	return "testsummaryequipment";
    }

	@GetMapping("/testSummaryPort")
    public String testSummaryPort(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(value = "id", required=true) Long id) {
		model.addAttribute("port", id);
		
		PortEntity e = this.portRepository.getById(id);
		model.addAttribute("portname", e.getPort_name());
		
		EquipmentEntity equipment = this.equipmentRepository.getById(e.getEquipment());
		
		ProjectEntitySimple project = this.projectRepositorySimple.getById( equipment.getProject() );
		model.addAttribute("projectid", project.getId());
		model.addAttribute("projectName", project.getName());
		
		model.addAttribute("equipmentid", equipment.getId());
		model.addAttribute("equipmentName", equipment.getName());
		
    	return "testsummaryport";
    }
	
	@GetMapping("/testerdef")
    public String tester(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    Map<Long, String> checkBoxCategory = new LinkedHashMap<>();
	    for (TesterCategoryEntity e : this.testerCategoryRepository.findAll()) {
	    	checkBoxCategory.put(e.getId(), e.getFullname() + " (" + e.getCategory_name() + ")");
		    model.addAttribute("categoryCheckBox", checkBoxCategory);	    	
	    }

    	return "testerdef";
    }
	
	@GetMapping("/testitemdef")
    public String testitemdef(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    	return "testitemdef";
    }
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(HtmlUtils.htmlEscape(message.getName())
                + " : "
                + HtmlUtils.htmlEscape(message.getMessage()) );
    }
}