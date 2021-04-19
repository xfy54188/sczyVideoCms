package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.core.util.Contrast;
import cn.stylefeng.guns.core.util.MyException;
import cn.stylefeng.guns.modular.system.dao.SongListMapper;
import cn.stylefeng.guns.modular.system.model.SongList;
import cn.stylefeng.guns.modular.system.service.ISongListService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 *
 * @author xiefengyu
 * @since 2019-08-31
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements ISongListService {
    @Autowired
    private SongListMapper songListMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file,String chartId) throws Exception {

        boolean notNull = false;
        List<SongList> songListList = new ArrayList<SongList>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        SongList songList;
        System.out.println(sheet.getLastRowNum());
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            songList = new SongList();
            songList.setChartId(chartId);
            songList.setCrbtId(Contrast.getData(row, 0));
            songList.setContentId(Contrast.getData(row, 1));
            songList.setSongName(Contrast.getData(row, 2));
            songList.setSingerName(Contrast.getData(row, 3));
            songList.setVrbtImg(Contrast.getData(row, 4));
            songList.setSort(Integer.parseInt(Contrast.getData(row, 5)));
            songList.setUpdatetime(LocalDateTime.now());
            Random random = new Random();
            int number = random.nextInt(9999);
            songList.setPlayCount(number + "万");
            songListList.add(songList);

        }
        for (SongList songListResord : songListList) {
            //省去代码千万行
            EntityWrapper<SongList> songListEntityWrapper = new EntityWrapper<SongList>();
            String crbtId = songListResord.getCrbtId();
            String chartId1 = songListResord.getChartId();
            songListEntityWrapper.eq("crbtId", crbtId).eq("chartId", chartId1);//同一个歌单判重
            Integer count = songListMapper.selectCount(songListEntityWrapper);
            if (count == 0) {
                songListMapper.insert(songListResord);
                System.out.println(" 插入 " + songListResord);
            } else {
                songListMapper.update(songListResord, songListEntityWrapper);

                System.out.println(" 更新 " + songListResord);
            }
        }
        notNull = true;
        return notNull;
    }
}
