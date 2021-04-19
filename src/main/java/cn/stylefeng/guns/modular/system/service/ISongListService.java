package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.SongList;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 歌单表 服务类
 * </p>
 *
 * @author xiefengyu
 * @since 2019-08-31
 */
public interface ISongListService extends IService<SongList> {
    /**
     * 批量上传
     */
    boolean batchImport(String fileName, MultipartFile file,String chartId) throws Exception;
}
