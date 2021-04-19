package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.modular.system.model.Toneinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 歌曲信息表 服务类
 * </p>
 *
 * @author xiefengyu
 * @since 2018-11-14
 */
public interface IToneinfoService extends IService<Toneinfo> {
	 /**
     * 获取所有歌曲列表
     */
    List<Map<String, Object>> list(@Param("condition") String condition);
    /**
	 * 批量上传
	 */
	  boolean batchImport(String fileName, MultipartFile file) throws Exception;
}
