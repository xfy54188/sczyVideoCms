package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author xiefengyu
 * @since 2019-08-30
 */
@TableName("activity_log")
public class ActivityLog extends Model<ActivityLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 手机号
     */
    @TableField(value = "msdn")
    private String msdn;
    /**
     * 子渠道标识
     */
    @TableField(value = "subchannel")
    private String subchannel;
    /**
     * 操作类型(viewPage页面访问、openMonth开通包月、openRing开通视频彩铃功能、OrderRing设置彩铃)
     */
    @TableField("action")
    private String action;
    /**
     * 页面名称
     */
    @TableField("pageName")
    private String pageName;
    /**
     * opensdk 返回码
     */
    @TableField(value = "returncode")
    private String returncode;
    /**
     * opensdk 返回描述
     */
    @TableField(value = "returndesc")
    private String returndesc;
    /**
     * 版权ID
     */
    @TableField(value = "cpid")
    private String cpid;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(value = "updatetime")
    private Date updatetime;
    /**
     * 页面url
     */
    @TableField("pageUrl")
    private String pageUrl;
    /**
     * 用户ip地址
     */
    @TableField("ip")
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsdn() {
        return msdn;
    }

    public void setMsdn(String msdn) {
        this.msdn = msdn;
    }

    public String getSubchannel() {
        return subchannel;
    }

    public void setSubchannel(String subchannel) {
        this.subchannel = subchannel;
    }


    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getReturndesc() {
        return returndesc;
    }

    public void setReturndesc(String returndesc) {
        this.returndesc = returndesc;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivityLog{" +
                "id=" + id +
                ", msdn='" + msdn + '\'' +
                ", subchannel='" + subchannel + '\'' +
                ", action=" + action +
                ", pageName='" + pageName + '\'' +
                ", returncode='" + returncode + '\'' +
                ", returndesc='" + returndesc + '\'' +
                ", cpid='" + cpid + '\'' +
                ", updatetime=" + updatetime + '\'' +
                ", pageUrl=" + pageUrl + '\'' +
                ", ip=" + ip +
                '}';
    }
}
