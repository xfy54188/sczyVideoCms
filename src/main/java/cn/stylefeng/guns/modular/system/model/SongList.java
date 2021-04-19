package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 歌单表
 * </p>
 *
 * @author xiefengyu
 * @since 2019-08-31
 */
@TableName("song_list")
public class SongList extends Model<SongList> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 榜单id
     */
    @TableField(value = "chartId")
    private String chartId;
    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 歌曲版权id
     */
    @TableField(value = "crbtId")
    private String crbtId;
    /**
     * 内容id
     */
    @TableField("contentId")
    private String contentId;
    /**
     * 歌曲名称
     */
    @TableField(value = "songName")
    private String songName;
    /**
     * 播放次数
     */
    @TableField(value = "playCount")
    private String playCount;
    /**
     * 歌手名称
     */
    @TableField(value = "singerName")
    private String singerName;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "updatetime")
    private LocalDateTime updatetime;
    /**
     * 图片地址
     */
    @TableField(value = "vrbtImg")
    private String vrbtImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChartId() {
        return chartId;
    }

    public void setChartId(String chartId) {
        this.chartId = chartId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCrbtId() {
        return crbtId;
    }

    public void setCrbtId(String crbtId) {
        this.crbtId = crbtId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getVrbtImg() {
        return vrbtImg;
    }

    public void setVrbtImg(String vrbtImg) {
        this.vrbtImg = vrbtImg;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SongList{" +
        ", id=" + id +
        ", chartId=" + chartId +
        ", sort=" + sort +
        ", crbtId=" + crbtId +
        ", contentId=" + contentId +
        ", songName=" + songName +
        ", playCount=" + playCount +
        ", singerName=" + singerName +
        ", updatetime=" + updatetime +
        ", vrbtImg=" + vrbtImg +
        "}";
    }
}
