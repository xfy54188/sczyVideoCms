package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.Toneinfo;
import cn.stylefeng.guns.modular.system.service.IToneinfoService;
import cn.stylefeng.guns.modular.system.warpper.ToneInfoWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

/**
 * toneInfo控制器
 *
 * @author fengshuonan
 * @Date 2018-11-14 16:41:54
 */
@Controller
@RequestMapping("/toneinfo")
public class ToneinfoController extends BaseController {

    private String PREFIX = "/system/toneinfo/";

    @Autowired
    private IToneinfoService toneinfoService;

    /**
     * 跳转到toneInfo首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "toneinfo.html";
    }

    /**
     * 跳转到添加toneInfo
     */
    @RequestMapping("/toneinfo_add")
    public String toneinfoAdd() {
        return PREFIX + "toneinfo_add.html";
    }

    /**
     * 跳转到修改toneInfo
     */
    @RequestMapping("/toneinfo_update/{toneinfoId}")
    public String toneinfoUpdate(@PathVariable Integer toneinfoId, Model model) {
        Toneinfo toneinfo = toneinfoService.selectById(toneinfoId);
        model.addAttribute("item",toneinfo);
        LogObjectHolder.me().set(toneinfo);
        return PREFIX + "toneinfo_edit.html";
    }

    /**
     * 获取toneInfo列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.toneinfoService.list(condition);
        return super.warpObject(new ToneInfoWarpper(list));
    }

    /**
     * 新增toneInfo
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid Toneinfo toneinfo) {
    	toneinfo.setCreatetime( new Date() );
        toneinfoService.insert( toneinfo );
        return SUCCESS_TIP;
    }

    /**
     * 批量删除toneInfo
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String[] toneinfoIds) {
//        toneinfoService.deleteById(toneinfoId);
    	List<String> resultList= new ArrayList<>(Arrays.asList(toneinfoIds));
    	toneinfoService.deleteBatchIds( resultList );
        return SUCCESS_TIP;
    }

    /**
     * 修改toneInfo
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid Toneinfo toneinfo) {
    	toneinfo.setCreatetime( new Date() );
        toneinfoService.updateById(toneinfo);
        return SUCCESS_TIP;
    }

    /**
     * toneInfo详情
     */
    @RequestMapping(value = "/detail/{toneinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("toneinfoId") Integer toneinfoId) {
        return toneinfoService.selectById(toneinfoId);
    }
    /**
	 * toneInfo excle批量导入
	 */
	@PostMapping("/import")
	@ResponseBody
	public Boolean addContract( @RequestParam("file" ) MultipartFile file)
	{
		boolean a = false;
		String fileName = file.getOriginalFilename();
		try
		{
			a = toneinfoService.batchImport( fileName, file );
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
		return a;

	}

}
