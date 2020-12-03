package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eye.admin.vo.RegionVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeRegion;
import com.eye.db.service.EyeRegionService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(description = "地区信息管理")
@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
    private final Log logger = LogFactory.getLog(AdminRegionController.class);

    @Autowired
    private EyeRegionService regionService;

    @ApiOperation(value = "查询所有省信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "行政区域id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/clist")
    public Object clist(@NotNull Integer id) {
        String info = "查询所有的省信息";
        logger.error(getClass()+"----------------------------"+info);
        List<EyeRegion> regionList = regionService.queryByPid(id);
        return ResponseUtil.okList(regionList);
    }

    @ApiOperation(value = "查询所有省市区信息")
    @GetMapping("/list")
    public Object list() {
        String info = "查询所有省市区信息";
        logger.error(getClass()+"----------------------------"+info);
        List<RegionVo> regionVoList = new ArrayList<>();

        List<EyeRegion> EyeRegions = regionService.getAll();
        Map<Byte, List<EyeRegion>> collect = EyeRegions.stream().collect(Collectors.groupingBy(EyeRegion::getType));
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
