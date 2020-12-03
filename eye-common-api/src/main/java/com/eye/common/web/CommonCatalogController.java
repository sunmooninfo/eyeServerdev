package com.eye.common.web;

import com.eye.common.service.HomeCacheManager;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeCategory;
import com.eye.db.service.EyeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类目服务
 */
@RestController
@RequestMapping("/common/catalog")
@Validated
@Api(description = "类目管理")
public class CommonCatalogController {
    private final Log logger = LogFactory.getLog(CommonCatalogController.class);

    @Autowired
    private EyeCategoryService categoryService;

    @GetMapping("/getfirstcategory")
    @ApiOperation("显示所有一级分类目录")
    public Object getFirstCategory() {
        // 所有一级分类目录
        List<EyeCategory> l1CatList = categoryService.queryL1();
        return ResponseUtil.ok(l1CatList);
    }

    @GetMapping("/getsecondcategory")
    @ApiOperation("显示所有二级分类目录")
    @ApiImplicitParam(name="id",value ="一级分类id",required=true,paramType = "path",dataType="int")
    public Object getSecondCategory(@NotNull Integer id) {
        // 所有二级分类目录
        List<EyeCategory> currentSubCategory = categoryService.queryByPid(id);
        return ResponseUtil.ok(currentSubCategory);
    }

    /**
     * 分类详情
     *
     * @param id   分类类目ID。
     *             如果分类类目ID是空，则选择第一个分类类目。
     *             需要注意，这里分类类目是一级类目
     * @return 分类详情
     */
    @GetMapping("index")
    @ApiOperation("分类详情")
    @ApiImplicitParam(name="id",value ="分类id",required=true,paramType = "path",dataType="int")
    public Object index(Integer id) {

        // 所有一级分类目录
        List<EyeCategory> l1CatList = categoryService.queryL1();

        // 当前一级分类目录
        EyeCategory currentCategory = null;
        if (id != null) {
            currentCategory = categoryService.findById(id);
        } else {
             if (l1CatList.size() > 0) {
                currentCategory = l1CatList.get(0);
            }
        }

        // 当前一级分类目录对应的二级分类目录
        List<EyeCategory> currentSubCategory = null;
        if (null != currentCategory) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    /**
     * 所有分类数据
     *
     * @return 所有分类数据
     */
    @GetMapping("all")
    @ApiOperation("所有分类数据")
    public Object queryAll() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.CATALOG)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.CATALOG));
        }


        // 所有一级分类目录
        List<EyeCategory> l1CatList = categoryService.queryL1();

        //所有子分类列表
        Map<Integer, List<EyeCategory>> allList = new HashMap<>();
        List<EyeCategory> sub;
        for (EyeCategory category : l1CatList) {
            sub = categoryService.queryByPid(category.getId());
            allList.put(category.getId(), sub);
        }

        // 当前一级分类目录
        EyeCategory currentCategory = l1CatList.get(0);

        // 当前一级分类目录对应的二级分类目录
        List<EyeCategory> currentSubCategory = null;
        if (null != currentCategory) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("allList", allList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);

        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.CATALOG, data);
        return ResponseUtil.ok(data);
    }

    /**
     * 当前分类栏目
     *
     * @param id 分类类目ID
     * @return 当前分类栏目
     */
    @GetMapping("current")
    @ApiOperation("显示当前分类栏目")
    @ApiImplicitParam(name="id",value ="分类id",required=true,paramType = "path",dataType="int")
    public Object current(@NotNull Integer id) {
        // 当前分类
        EyeCategory currentCategory = categoryService.findById(id);
        if(currentCategory == null){
            return ResponseUtil.badArgumentValue();
        }
        List<EyeCategory> currentSubCategory = categoryService.queryByPid(currentCategory.getId());

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }
}
