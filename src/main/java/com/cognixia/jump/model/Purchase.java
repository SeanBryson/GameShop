package com.cognixia.jump.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "purchase_id")
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "game_id")
	Game game;
	
	@Column(nullable = false)
	private int qty;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	public Purchase() {
		this.id = 1L;
		this.user = null;
		this.game = null;
		this.qty = 0;
		this.price = 0.0;
		this.time = new Date();
	}
	
	public Purchase(Long id, User user, Game game, int qty, double price, Date time) {
		super();
		this.id = id;
		this.user = user;
		this.game = game;
		this.qty = qty;
		this.price = price;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", user=" + user + ", game=" + game + ", qty=" + qty + ", price=" + price
				+ ", time=" + time + "]";
	}	
	
}
