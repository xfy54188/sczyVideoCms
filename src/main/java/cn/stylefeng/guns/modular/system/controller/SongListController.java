package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.system.model.SongList;
import cn.stylefeng.guns.modular.system.service.ISongListService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 歌单列表控制器
 *
 * @author fengshuonan
 * @Date 2019-08-31 13:34:17
 */
@Controller
@RequestMapping("/songList")
public class SongListController extends BaseController {

    private String PREFIX = "/system/songList/";

    @Autowired
    private ISongListService songListService;

    /**
     * 跳转到歌单列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "songList.html";
    }

    /**
     * 跳转到添加歌单列表
     */
    @RequestMapping("/songList_add")
    public String songListAdd() {
        return PREFIX + "songList_add.html";
    }

    /**
     * 跳转到修改歌单列表
     */
    @RequestMapping("/songList_update/{songListId}")
    public String songListUpdate(@PathVariable Integer songListId, Model model) {
        SongList songList = songListService.selectById(songListId);
        model.addAttribute("item", songList);
        LogObjectHolder.me().set(songList);
        return PREFIX + "songList_edit.html";
    }

    /**
     * 获取歌单列表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, String chartId) {
        EntityWrapper<SongList> queryWrapper = new EntityWrapper<SongList>();
        if (chartId != null && chartId != "" && condition != null && condition != "") {
            queryWrapper.eq("chartId", chartId).andNew()
                    .like("crbtId", condition).or()
                    .like("contentId", condition).or()
                    .like("songName", condition).or()
                    .like("singerName", condition);
        } else if (chartId != null && chartId != "") {
            queryWrapper.eq("chartId", chartId);
        } else if (condition != null && condition != "") {
            queryWrapper.like("crbtId", condition).or()
                    .like("contentId", condition).or()
                    .like("songName", condition).or()
                    .like("singerName", condition);
        } else {
            return songListService.selectList(null);
        }
        return songListService.selectList(queryWrapper);
    }

    /**
     * 新增歌单列表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SongList songList) {
        songList.setUpdatetime(LocalDateTime.now());
        songListService.insert(songList);
        return SUCCESS_TIP;
    }

    /**
     * 删除歌单列表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String[] songListIds) {
        List<String> resultList = new ArrayList<>(Arrays.asList(songListIds));
        songListService.deleteBatchIds(resultList);
        return SUCCESS_TIP;
    }

    /**
     * 修改歌单列表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SongList songList) {
        songList.setUpdatetime(LocalDateTime.now());
        songListService.updateById(songList);
        return SUCCESS_TIP;
    }

    /**
     * 歌单列表详情
     */
    @RequestMapping(value = "/detail/{songListId}")
    @ResponseBody
    public Object detail(@PathVariable("songListId") Integer songListId) {
        return songListService.selectById(songListId);
    }
    /**
     * 歌单 excle批量导入
     */
    @PostMapping("/import")
    @ResponseBody
    public Boolean addContract( @RequestParam("file") MultipartFile file, @RequestParam String chartId)
    {
        boolean a = false;
        String fileName = file.getOriginalFilename();
        try
        {
            a = songListService.batchImport( fileName, file ,chartId);
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        return a;

    }
}
