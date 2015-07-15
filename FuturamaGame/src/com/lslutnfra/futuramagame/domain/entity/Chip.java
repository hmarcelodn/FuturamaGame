package com.lslutnfra.futuramagame.domain.entity;


public class Chip {
	
	public static boolean BACK = false;
	public static boolean FRONT = true;	
	
	private int chipImage;
	private ChipStatusType statusChip;
	
	public Chip(ChipStatusType status,int chipImage){
		this.setStatusChip(status);
		this.setChipImage(chipImage);
	}

	public int getChipImage() {
		return chipImage;
	}

	public void setChipImage(int chipImage) {
		this.chipImage = chipImage;
	}

	public ChipStatusType getStatusChip() {
		return statusChip;
	}

	public void setStatusChip(ChipStatusType status) {
		this.statusChip = status;
	}

}
