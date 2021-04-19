package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.ActivityLog;
import cn.stylefeng.guns.modular.system.service.IActivityLogService;
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
import java.util.List;

/**
 * 日志统计控制器
 *
 * @author fengshuonan
 * @Date 2019-08-30 17:10:10
 */
@Controller
@RequestMapping("/activityLog")
public class ActivityLogController extends BaseController {

    private String PREFIX = "/system/activityLog/";

    @Autowired
    private IActivityLogService activityLogService;

    /**
     * 跳转到日志统计首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "activityLog.html";
    }

    /**
     * 跳转到添加日志统计
     */
    @RequestMapping("/activityLog_add")
    public String activityLogAdd() {
        return PREFIX + "activityLog_add.html";
    }

    /**
     * 跳转到修改日志统计
     */
    @RequestMapping("/activityLog_update/{activityLogId}")
    public String activityLogUpdate(@PathVariable Integer activityLogId, Model model) {
        ActivityLog activityLog = activityLogService.selectById(activityLogId);
        model.addAttribute("item", activityLog);
        LogObjectHolder.me().set(activityLog);
        return PREFIX + "activityLog_edit.html";
    }

    /**
     * 获取日志统计列表
     */
    @RequestMapping(value = "/list/{account}")
    @ResponseBody
    public Object list(String condition, @RequestParam(required = false) String logTime, @PathVariable String account) {
        EntityWrapper<ActivityLog> queryWrapper = new EntityWrapper<ActivityLog>();
        boolean bl = logTime != null && !"".equals(logTime) && condition != null && !"".equals(condition);
        if (account.contains("admin")) {
            if (bl) {
                queryWrapper.eq("msdn", condition)
                        .andNew().like("updateTime", logTime);
            } else if (logTime != null && logTime != "") {
                queryWrapper.like("updateTime", logTime);
            } else if (condition != null && condition != "") {
                queryWrapper.eq("msdn", condition);
            } else {
                return activityLogService.selectList(null);
            }

        } else {

            if (bl) {
                queryWrapper.like("msdn", condition).andNew().eq("subchannel", account);
            } else if (logTime != null && !"".equals(logTime)) {
                queryWrapper.like("updateTime", logTime).andNew().eq("subchannel", account);
            } else if (condition != null && !"".equals(condition)) {
                queryWrapper.like("msdn", condition).andNew().eq("subchannel", account);
            } else {
                queryWrapper.eq("subchannel", account);
            }
        }
        return activityLogService.selectList(queryWrapper);
    }

    /**
     * 新增日志统计
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ActivityLog activityLog) {
        activityLogService.insert(activityLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除日志统计
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String[] activityLogIds) {
        List<String> resultList = new ArrayList<>(Arrays.asList(activityLogIds));
        activityLogService.deleteBatchIds(resultList);
        return SUCCESS_TIP;
    }

    /**
     * 修改日志统计
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ActivityLog activityLog) {
        activityLogService.updateById(activityLog);
        return SUCCESS_TIP;
    }

    /**
     * 日志统计详情
     */
    @RequestMapping(value = "/detail/{activityLogId}")
    @ResponseBody
    public Object detail(@PathVariable("activityLogId") Integer activityLogId) {
        return activityLogService.selectById(activityLogId);
    }
}
