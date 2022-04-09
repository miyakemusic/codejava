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
@Table(name = "tester")
public class TesterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	private Long category;
	
	private Long vendor;
	
	private String product_name;

	private String description;
	
    @Enumerated(EnumType.ORDINAL)
    private ProductType producttype;
    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "category", insertable = false, updatable = false)
//    private TesterCategoryEntity categoryEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor", insertable = false, updatable = false)
    private TesterVendorEntity vendorEntity;
    
	@OneToMany(mappedBy="tester")
	private List<TesterCapabilityEntity> testItems;
	
	@Override
	public String toString() {
		return "[" + this.vendorEntity.getVendorname() + "] " + this.product_name;
	}
}
