package com.lslutnfra.futuramagame.presentation.model;

import com.lslutnfra.futuramagame.domain.entity.ChipStatusType;

public class ChipModel {
	private int chipImage;
	private ChipStatusType statusChip;

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
