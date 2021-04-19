package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.model.ActivityLog;
import cn.stylefeng.guns.modular.system.dao.ActivityLogMapper;
import cn.stylefeng.guns.modular.system.service.IActivityLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author xiefengyu
 * @since 2019-08-30
 */
@Service
public class ActivityLogServiceImpl extends ServiceImpl<ActivityLogMapper, ActivityLog> implements IActivityLogService {

}
