package com.miyake.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "properties")
public class PropertyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	private PropertyType type;
	
	private String name = "NOTITLE";
	
	private Long testinstrument;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "testinstrument", insertable = false, updatable = false, nullable = true)
    private TestInstrumentEntity testinstrumentEntity;
    
	private Double default_numeric = 0.0;

	private Integer decimals;
	
	private Long default_option;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "default_option", insertable = false, updatable = false, nullable = true)
    private PropertyOptionEntity default_optionEntity;
    
	@OneToMany(mappedBy="property")
	private List<PropertyOptionEntity> option_list;

	private Long unit;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit", insertable = false, updatable = false, nullable = true)
    private PropertyUnitEntity unitEntity;
    
	public PropertyOptionEntity findOption(Long id2) {
		for (PropertyOptionEntity o : this.option_list) {
			if (o.getId().equals(id2)) {
				return o;
			}
		}
		return null;
	}

	public Long nextOption(Long id2) {
		int index = 1;
		for (PropertyOptionEntity option : this.option_list) {
			if (option.getId().equals(id2)) {
				break;
			}
			index++;
		}
		if (this.option_list.size() <= index) {
			index = 0;
		}
		return option_list.get(index).getId();
	}
}
