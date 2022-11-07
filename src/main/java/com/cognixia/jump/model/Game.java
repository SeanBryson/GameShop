package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "game_id")
	private Long id;
	
	@Column(nullable = false, unique = true)
	@Schema(description = "Model for game inventory information", example = "Elden Ring", required = true)
	private String name;
	
	@Column(nullable = false)
	private String esrb;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private int qty;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
	//One to Many with Purchases
//	@JsonIgnoreProperties("game")
//	@OneToMany(mappedBy = "game",fetch = FetchType.EAGER)
//	private Set<Purchase> purchases = new HashSet<>();

	
	public Game() {
		this.id = 1L;
		this.name = "N/A";
		this.esrb = "RP";
		this.price = 0.0;
		this.qty = 0;
		this.updated = new Date();
//		this.purchases = null;
	}

	public Game(Long id, String name, String esrb, double price, int qty, Date updated, Set<Purchase> purchases) {
		super();
		this.id = id;
		this.name = name;
		this.esrb = esrb;
		this.price = price;
		this.qty = qty;
		this.updated = updated;
//		this.purchases = purchases;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEsrb() {
		return esrb;
	}

	public void setEsrb(String esrb) {
		this.esrb = esrb;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	

//	public Set<Purchase> getPurchases() {
//		return purchases;
//	}
//
//	public void setPurchases(Set<Purchase> purchases) {
//		this.purchases = purchases;
//	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", esrb=" + esrb + ", price=" + price + ", qty=" + qty
				+ ", updated=" + updated + "]";
	}
	
}
