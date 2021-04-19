package cn.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.stylefeng.guns.modular.system.model.Toneinfo;

/**
 * <p>
 * 歌曲信息表 Mapper 接口
 * </p>
 *
 * @author xiefengyu
 * @since 2018-11-14
 */
public interface ToneinfoMapper extends BaseMapper<Toneinfo> {
	/**
     * 获取所有歌曲列表
     */
    List<Map<String, Object>> list(@Param("condition") String condition);
    void addToneinfo(Toneinfo toneinfo);

    int updateToneinfoBySongId(Toneinfo toneinfo);

    int selectBySongId(@Param("songId") String songId);
}
