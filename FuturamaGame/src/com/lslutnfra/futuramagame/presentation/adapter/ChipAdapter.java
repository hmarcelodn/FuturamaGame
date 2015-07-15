package com.lslutnfra.futuramagame.presentation.adapter;

import java.util.ArrayList;

import com.lslutnfra.futuramagame.R;
import com.lslutnfra.futuramagame.domain.entity.ChipStatusType;
import com.lslutnfra.futuramagame.presentation.model.ChipModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ChipAdapter extends BaseAdapter{
	private LayoutInflater lInflater;
	private ArrayList<ChipModel> chipItems;
	
	public ChipAdapter(Context context){
		this.lInflater = LayoutInflater.from(context);
		this.chipItems = new ArrayList<ChipModel>(); 
	}
	
	@SuppressLint("InflateParams") 
	@Override public View getView(int position, View convertView, ViewGroup parent) {			
		ChipModel chip = this.getItem(position);
		
		View chipView = null;			
		if(convertView == null){
			chipView = lInflater.inflate(R.layout.chip, null);
		}
		else{
			chipView = convertView;
		}
		
		ChipViewHolder chipWrapper = null;
		if(chipView.getTag() != null){
			chipWrapper = (ChipViewHolder) chipView.getTag();
		}
		else{
			chipWrapper = new ChipViewHolder();
			chipWrapper.imgChip = (ImageView) chipView.findViewById(R.id.chipImageView);			
			chipView.setTag(chipWrapper);
		}
		
		if(chip.getStatusChip() == ChipStatusType.BACK){
			chipWrapper.imgChip.setImageResource(R.drawable.benderback);			
		}
		else{
			chipWrapper.imgChip.setImageResource(chip.getChipImage());
		}
		
		return chipView;
	}	
	
	@Override public int getCount() {
		return this.chipItems.size();
	}

	@Override public ChipModel getItem(int position) {
		return this.chipItems.get(position);
	}

	@Override public long getItemId(int position) {
		return 0;
	}	
	
	class ChipViewHolder{
		public ImageView imgChip;
	}

	public void addAll(ArrayList<ChipModel> chipItems){
		this.chipItems.addAll(chipItems);
	}
	
	public ArrayList<ChipModel> getItems(){
		return this.chipItems;
	}
}
