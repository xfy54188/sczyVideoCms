/**
 * 初始化日志统计详情对话框
 */
var ActivityLogInfoDlg = {
    activityLogInfoData : {}
};

/**
 * 清除数据
 */
ActivityLogInfoDlg.clearData = function() {
    this.activityLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityLogInfoDlg.set = function(key, val) {
    this.activityLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ActivityLogInfoDlg.close = function() {
    parent.layer.close(window.parent.ActivityLog.layerIndex);
}

/**
 * 收集数据
 */
ActivityLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('msdn')
    .set('subchannel')
    .set('action')
    .set('pageName')
    .set('returncode')
    .set('returndesc')
    .set('cpid')
    .set('pageUrl')
    .set('ip')
    .set('updatetime');
}

/**
 * 提交添加
 */
ActivityLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activityLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.ActivityLog.table.refresh();
        ActivityLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ActivityLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activityLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.ActivityLog.table.refresh();
        ActivityLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityLogInfoData);
    ajax.start();
}

$(function() {

});
