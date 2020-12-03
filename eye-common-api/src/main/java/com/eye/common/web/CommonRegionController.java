package com.eye.common.web;

import com.eye.common.vo.RegionVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeRegion;
import com.eye.db.service.EyeRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/common/region")
@Api(description = "省市区服务")
public class CommonRegionController {

    private final Log logger = LogFactory.getLog(CommonRegionController.class);

    @Autowired
    private EyeRegionService regionService;

    @GetMapping("/list")
    @ApiOperation("查询所有省市区信息")
    public Object list(){
        String info = "查询所有省市区信息";
        logger.error(getClass()+"----------------------------"+info);
        List<RegionVo> regionVoList = new ArrayList<>();

        List<EyeRegion> litemallRegions = regionService.getAll();
        Map<Byte, List<EyeRegion>> collect = litemallRegions.stream().collect(Collectors.groupingBy(EyeRegion::getType));
        byte provinceType = 1;
        List<EyeRegion> provinceList = collect.get(provinceType);
        byte cityType = 2;
        List<EyeRegion> city = collect.get(cityType);
        Map<Integer, List<EyeRegion>> cityListMap = city.stream().collect(Collectors.groupingBy(EyeRegion::getPid));
        byte areaType = 3;
        List<EyeRegion> areas = collect.get(areaType);
        Map<Integer, List<EyeRegion>> areaListMap = areas.stream().collect(Collectors.groupingBy(EyeRegion::getPid));

        for (EyeRegion province : provinceList) {
            RegionVo provinceVO = new RegionVo();
            provinceVO.setId(province.getId());
            provinceVO.setName(province.getName());
            provinceVO.setCode(province.getCode());
            provinceVO.setType(province.getType());

            List<EyeRegion> cityList = cityListMap.get(province.getId());
            List<RegionVo> cityVOList = new ArrayList<>();
            for (EyeRegion cityVo : cityList) {
                RegionVo cityVO = new RegionVo();
                cityVO.setId(cityVo.getId());
                cityVO.setName(cityVo.getName());
                cityVO.setCode(cityVo.getCode());
                cityVO.setType(cityVo.getType());

                List<EyeRegion> areaList = areaListMap.get(cityVo.getId());
                List<RegionVo> areaVOList = new ArrayList<>();
                for (EyeRegion area : areaList) {
                    RegionVo areaVO = new RegionVo();
                    areaVO.setId(area.getId());
                    areaVO.setName(area.getName());
                    areaVO.setCode(area.getCode());
                    areaVO.setType(area.getType());
                    areaVOList.add(areaVO);
                }

                cityVO.setChildren(areaVOList);
                cityVOList.add(cityVO);
            }
            provinceVO.setChildren(cityVOList);
            regionVoList.add(provinceVO);
        }

        return ResponseUtil.okList(regionVoList);
    }

}
