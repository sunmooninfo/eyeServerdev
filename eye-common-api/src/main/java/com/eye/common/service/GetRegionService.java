package com.eye.common.service;

import com.eye.db.domain.EyeRegion;
import com.eye.db.service.EyeRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhy
 * @date 2019-01-17 23:07
 **/
@Component
public class GetRegionService {

	@Autowired
	private EyeRegionService regionService;

	private static List<EyeRegion> litemallRegions;

	protected List<EyeRegion> getEyeRegions() {
		if(litemallRegions==null){
			createRegion();
		}
		return litemallRegions;
	}

	private synchronized void createRegion(){
		if (litemallRegions == null) {
			litemallRegions = regionService.getAll();
		}
	}
}
