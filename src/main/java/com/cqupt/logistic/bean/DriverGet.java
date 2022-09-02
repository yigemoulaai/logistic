package com.cqupt.logistic.bean;

import javax.persistence.*;

/**
 *  司机报酬明细表
 */
@Entity(name = "driverget")
public class DriverGet {
	/*{ title: 'ID', fixed: 'left', sort: true, type: 'numbers', align: 'center' },
	{ field: 'driverId', title: '司机ID', align: 'center' },
	{ field: 'goodsId', title: '运单号', align: 'center' },
	{ field: 'goodsState', title: '运单状态', align: 'center' },
	{ field: 'cargoStartSite', title: '车号', align: 'center' },
	{ field: 'cargoOrderSite', title: '货物目的地', align: 'center' },
	{ field: 'cargoGet' , title: '货运金额', align: 'center'}*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 50)
	private String driverId;
	@Column(length = 50)
	private String customerId;

	@Column(length = 50)
	private String goodsId;

	@Column(length = 50)
	private String  goodsState;


	@Column(length = 50)
	private String cargoStartSite;
	@Column(length = 50)
	private String cargoOrderSite;

	@Column(length = 50)
	private Double cargoGet;

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public String getCargoStartSite() {
		return cargoStartSite;
	}

	public void setCargoStartSite(String cargoStartSite) {
		this.cargoStartSite = cargoStartSite;
	}

	public String getCargoOrderSite() {
		return cargoOrderSite;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCargoOrderSite(String cargoOrderSite) {
		this.cargoOrderSite = cargoOrderSite;
	}

	public Double getCargoGet() {
		return cargoGet;
	}

	public void setCargoGet(Double cargoGet) {
		this.cargoGet = cargoGet;
	}
}
