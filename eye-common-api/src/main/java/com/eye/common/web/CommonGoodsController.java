package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonGoodsService;
import com.eye.core.system.SystemConfig;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 商品服务
 */
@RestController
@RequestMapping("/common/goods")
@Validated
@Api(description = "商品服务")
public class CommonGoodsController {
	private final Log logger = LogFactory.getLog(CommonGoodsController.class);

	@Autowired
	private EyeGoodsService goodsService;

	@Autowired
	private EyeGoodsProductService productService;

	@Autowired
	private EyeIssueService goodsIssueService;

	@Autowired
	private EyeGoodsAttributeService goodsAttributeService;

	@Autowired
	private EyeGoodsKillService goodsKillService;
	
	@Autowired
	private EyeBrandService brandService;

	@Autowired
	private EyeCommentService commentService;

	@Autowired
	private EyeUserService userService;

	@Autowired
	private EyeCollectService collectService;

	@Autowired
	private EyeFootprintService footprintService;

	@Autowired
	private EyeCategoryService categoryService;

	@Autowired
	private EyeSearchHistoryService searchHistoryService;

	@Autowired
	private EyeGoodsSpecificationService goodsSpecificationService;

	@Autowired
	private EyeGrouponRulesService rulesService;

	@Autowired
	private CommonGoodsService commonGoodsService;

	private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

	private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

	private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

	/**
	 * 商品详情
	 * <p>
	 * 用户可以不登录。
	 * 如果用户登录，则记录用户足迹以及返回用户收藏信息。
	 *
	 * @param userId 用户ID
	 * @param id     商品ID
	 * @return 商品详情
	 */
	@GetMapping("detail")
	@ApiOperation("商品详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value = "用户id",required=false,paramType="path",dataType="int"),
			@ApiImplicitParam(name="id",value = "商品id",required=true,paramType="path",dataType="int")
	})
	public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
		// 商品信息
		EyeGoods info = goodsService.findById(id);

		// 商品属性
		Callable<List> goodsAttributeListCallable = () -> goodsAttributeService.queryByGid(id);

		// 商品规格 返回的是定制的GoodsSpecificationVo
		Callable<Object> objectCallable = () -> goodsSpecificationService.getSpecificationVoList(id);

		// 商品规格对应的数量和价格
		Callable<List> productListCallable = () -> productService.queryByGid(id);

		// 商品问题，这里是一些通用问题
		Callable<List> issueCallable = () -> goodsIssueService.querySelective("", 1, 4, "", "");
		
		// 商品秒杀信息
		Callable<List> goodsKillListCallable = () -> goodsKillService.queryByGid(id);
		
		// 商品品牌商
		Callable<EyeBrand> brandCallable = ()->{
			Integer brandId = info.getBrandId();
			EyeBrand brand;
			if (brandId == 0) {
				brand = new EyeBrand();
			} else {
				brand = brandService.findById(info.getBrandId());
			}
			return brand;
		};

		// 评论
		Callable<Map> commentsCallable = () -> {
			List<EyeComment> comments = commentService.queryGoodsByGid(id, 0, 2);
			List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());
			long commentCount = PageInfo.of(comments).getTotal();
			for (EyeComment comment : comments) {
				Map<String, Object> c = new HashMap<>();
				c.put("id", comment.getId());
				c.put("addTime", comment.getAddTime());
				c.put("content", comment.getContent());
				c.put("adminContent", comment.getAdminContent());
				EyeUser user = userService.findById(comment.getUserId());
				c.put("nickname", user == null ? "" : user.getNickname());
				c.put("avatar", user == null ? "" : user.getAvatar());
				c.put("picList", comment.getPicUrls());
				commentsVo.add(c);
			}
			Map<String, Object> commentList = new HashMap<>();
			commentList.put("count", commentCount);
			commentList.put("data", commentsVo);
			return commentList;
		};

		//团购信息
		Callable<List> grouponRulesCallable = () ->rulesService.queryByGoodsId(id);

		// 用户收藏
		int userHasCollect = 0;
		if (userId != null) {
			userHasCollect = collectService.count(userId, id);
		}

		// 记录用户的足迹 异步处理
		if (userId != null) {
			executorService.execute(()->{
				EyeFootprint footprint = new EyeFootprint();
				footprint.setUserId(userId);
				footprint.setGoodsId(id);
				footprintService.add(footprint);
			});
		}
		FutureTask<List> goodsAttributeListTask = new FutureTask<>(goodsAttributeListCallable);
		FutureTask<Object> objectCallableTask = new FutureTask<>(objectCallable);
		FutureTask<List> productListCallableTask = new FutureTask<>(productListCallable);
		FutureTask<List> issueCallableTask = new FutureTask<>(issueCallable);
		FutureTask<Map> commentsCallableTsk = new FutureTask<>(commentsCallable);
		FutureTask<EyeBrand> brandCallableTask = new FutureTask<>(brandCallable);
        FutureTask<List> grouponRulesCallableTask = new FutureTask<>(grouponRulesCallable);
        FutureTask<List> goodsKillListTask = new FutureTask<>(goodsKillListCallable);

		executorService.submit(goodsAttributeListTask);
		executorService.submit(objectCallableTask);
		executorService.submit(productListCallableTask);
		executorService.submit(issueCallableTask);
		executorService.submit(commentsCallableTsk);
		executorService.submit(brandCallableTask);
		executorService.submit(grouponRulesCallableTask);
		executorService.submit(goodsKillListTask);
		
		Map<String, Object> data = new HashMap<>();

		try {
			data.put("info", info);
			data.put("userHasCollect", userHasCollect);
			data.put("issue", issueCallableTask.get());
			data.put("comment", commentsCallableTsk.get());
			data.put("specificationList", objectCallableTask.get());
			data.put("productList", productListCallableTask.get());
			data.put("attribute", goodsAttributeListTask.get());
			data.put("brand", brandCallableTask.get());
			data.put("groupon", grouponRulesCallableTask.get());
			//SystemConfig.isAutoCreateShareImage()
			data.put("share", SystemConfig.isAutoCreateShareImage());
			data.put("goodskill", goodsKillListTask.get());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//商品分享图片地址
		data.put("shareImage", info.getShareUrl());
		return ResponseUtil.ok(data);
	}

	/**
	 * 商品分类类目
	 *
	 * @param id 分类类目ID
	 * @return 商品分类类目
	 */
	@GetMapping("category")
	@ApiOperation("商品分类类目")
	@ApiImplicitParams({
			@ApiImplicitParam(name="categoryId",value = "分类类目ID",required=false,paramType="path",dataType="int"),
			@ApiImplicitParam(name="isShown",value = "是否在小程序展示",required=false,paramType="path",dataType="Boolean")
	})
	public Object category(@NotNull Integer id, Boolean isShown) {
		EyeCategory cur = categoryService.findById(id);
		EyeCategory parent = null;
		List<EyeCategory> children = null;

		if (cur.getPid() == 0) {
			parent = cur;
			children = categoryService.queryByPid(cur.getId(),isShown);
			cur = children.size() > 0 ? children.get(0) : cur;
		} else {
			parent = categoryService.findById(cur.getPid());
			children = categoryService.queryByPid(cur.getPid(),isShown);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("currentCategory", cur);
		data.put("parentCategory", parent);
		data.put("brotherCategory", children);
		return ResponseUtil.ok(data);
	}

	/**
	 * 根据条件搜素商品
	 * <p>
	 * 1. 这里的前五个参数都是可选的，甚至都是空
	 * 2. 用户是可选登录，如果登录，则记录用户的搜索关键字
	 *
	 * @param categoryId 分类类目ID，可选
	 * @param brandId    品牌商ID，可选
	 * @param keyword    关键字，可选
	 * @param isNew      是否新品，可选
	 * @param isHot      是否热买，可选
	 * @param isKill     是否秒杀，可选
	 * @param isShown    是否在小程序上架，可选
	 * @param userId     用户ID
	 * @param page       分页页数
	 * @param limit       分页大小
	 * @param sort       排序方式，支持"add_time", "retail_price"或"name"
	 * @param order      排序类型，顺序或者降序
	 * @return 根据条件搜素的商品详情
	 */
	@GetMapping("list")
	@ApiOperation("根据条件搜索商品")
	@ApiImplicitParams({
			@ApiImplicitParam(name="categoryId",value = "分类类目ID",required=false,paramType="path",dataType="int"),
			@ApiImplicitParam(name="brandId",value = "品牌商ID",required=false,paramType="path",dataType="int"),
			@ApiImplicitParam(name="keyword",value = "关键字",required=false,paramType="path",dataType="string"),
			@ApiImplicitParam(name="isNew",value = "是否新品",required=false,paramType="path",dataType="Boolean"),
			@ApiImplicitParam(name="isShown",value = "是否在小程序展示",required=false,paramType="path",dataType="Boolean"),
			@ApiImplicitParam(name="isHot",value = "是否热买",required=false,paramType="path",dataType="Boolean"),
			@ApiImplicitParam(name="userId",value = "用户ID",required=false,paramType="path",dataType="int")
	})
	public Object list(
		Integer categoryId,
		Integer brandId,
		String keyword,
		Boolean isNew,
		Boolean isHot,
		Boolean isKill,
		Boolean isShown,
		@LoginUser Integer userId,
		@RequestParam(defaultValue = "1") Integer page,
		@RequestParam(defaultValue = "10") Integer limit,
		@Sort(accepts = {"add_time", "retail_price", "name"}) @RequestParam(defaultValue = "add_time") String sort,
		@Order @RequestParam(defaultValue = "desc") String order) {

		//添加到搜索历史
		if (userId != null && !StringUtils.isEmpty(keyword)) {
			EyeSearchHistory searchHistoryVo = new EyeSearchHistory();
			searchHistoryVo.setKeyword(keyword);
			searchHistoryVo.setUserId(userId);
			searchHistoryVo.setFrom("wx");
			searchHistoryService.save(searchHistoryVo);
		}

		//查询列表数据
		List<EyeGoods> goodsList = goodsService.querySelective(categoryId, brandId, keyword, isHot, isShown, isNew, isKill, page, limit, sort, order);

		// 查询商品所属类目列表。
		List<Integer> goodsCatIds = goodsService.getCatIds(brandId, keyword, isHot, isNew,isKill,isShown);
		List<EyeCategory> categoryList = null;
		if (goodsCatIds.size() != 0) {
			categoryList = categoryService.queryL2ByIds(goodsCatIds);
		} else {
			categoryList = new ArrayList<>(0);
		}

		PageInfo<EyeGoods> pagedList = PageInfo.of(goodsList);

		Map<String, Object> entity = new HashMap<>();
		entity.put("list", goodsList);
		entity.put("total", pagedList.getTotal());
		entity.put("page", pagedList.getPageNum());
		entity.put("limit", pagedList.getPageSize());
		entity.put("pages", pagedList.getPages());
		entity.put("filterCategoryList", categoryList);

		// 因为这里需要返回额外的filterCategoryList参数，因此不能方便使用ResponseUtil.okList
		return ResponseUtil.ok(entity);
	}

	/**
	 * 商品详情页面“大家都在看”推荐商品
	 *
	 * @param id, 商品ID
	 * @return 商品详情页面推荐商品
	 */
	@GetMapping("related")
	@ApiOperation("商品详情页面推荐商品")
	@ApiImplicitParam(name="id",value = "商品ID",required=true,paramType="path",dataType="int")
	public Object related(@NotNull Integer id,Boolean isShown) {
		EyeGoods goods = goodsService.findById(id);
		if (goods == null) {
			return ResponseUtil.badArgumentValue();
		}

		// 目前的商品推荐算法仅仅是推荐同类目的其他商品
		int cid = goods.getCategoryId();

		// 查找六个相关商品
		int related = 6;
		List<EyeGoods> goodsList = goodsService.queryByCategory(cid,0,related,isShown);
		return ResponseUtil.okList(goodsList);
	}

	/**
	 * 在售的商品总数
	 *
	 * @return 在售的商品总数
	 */
	@GetMapping("count")
	@ApiOperation("在售的商品总数")
	public Object count() {
		Integer goodsCount = goodsService.queryOnSale();
		return ResponseUtil.ok(goodsCount);
	}

	/**
	 * 判断虚拟商品用户是否付费
	 * @param userId
	 * @param body { goodId: xxx }
	 * @return
	 */
	@PostMapping("isPay")
	@ApiOperation("判断虚拟商品用户是否付费")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
			@ApiImplicitParam(name="body",value = "{\n" +
					"\t\"goodId(商品id)\":xxx\n" +
					"}",required=true,dataType="string")
	})
	public Object isPay(@LoginUser Integer userId, @RequestBody String body){

		return commonGoodsService.isPay(userId,body);
	}

}