package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.ChartList;
import cn.stylefeng.guns.modular.system.service.IChartListService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 榜单列表控制器
 *
 * @author fengshuonan
 * @Date 2019-08-30 22:02:06
 */
@Controller
@RequestMapping("/chartList")
public class ChartListController extends BaseController {

    private String PREFIX = "/system/chartList/";

    @Autowired
    private IChartListService chartListService;

    /**
     * 跳转到榜单列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "chartList.html";
    }

    /**
     * 跳转到添加榜单列表
     */
    @RequestMapping("/chartList_add")
    public String chartListAdd() {
        return PREFIX + "chartList_add.html";
    }

    /**
     * 跳转到修改榜单列表
     */
    @RequestMapping("/chartList_update/{chartListId}")
    public String chartListUpdate(@PathVariable Integer chartListId, Model model) {
        ChartList chartList = chartListService.selectById(chartListId);
        model.addAttribute("item",chartList);
        LogObjectHolder.me().set(chartList);
        return PREFIX + "chartList_edit.html";
    }

    /**
     * 获取榜单列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<ChartList> queryWrapper = new EntityWrapper<ChartList>();
        queryWrapper.like("chartName",condition);
        return chartListService.selectList(queryWrapper);
    }

    /**
     * 新增榜单列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ChartList chartList) {
        chartList.setUpdatetime(new Date());
        chartListService.insert(chartList);
        return SUCCESS_TIP;
    }

    /**
     * 删除榜单列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String[] chartListIds) {
        List<String> resultList = new ArrayList<>(Arrays.asList(chartListIds));
        chartListService.deleteBatchIds(resultList);
        return SUCCESS_TIP;
    }

    /**
     * 修改榜单列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ChartList chartList) {
        chartList.setUpdatetime(new Date());
        chartListService.updateById(chartList);
        return SUCCESS_TIP;
    }

    /**
     * 榜单列表详情
     */
    @RequestMapping(value = "/detail/{chartListId}")
    @ResponseBody
    public Object detail(@PathVariable("chartListId") Integer chartListId) {
        return chartListService.selectById(chartListId);
    }
}
