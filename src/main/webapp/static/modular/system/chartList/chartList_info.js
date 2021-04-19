/**
 * 初始化榜单列表详情对话框
 */
var ChartListInfoDlg = {
    chartListInfoData : {}
};

/**
 * 清除数据
 */
ChartListInfoDlg.clearData = function() {
    this.chartListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChartListInfoDlg.set = function(key, val) {
    this.chartListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChartListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ChartListInfoDlg.close = function() {
    parent.layer.close(window.parent.ChartList.layerIndex);
}

/**
 * 收集数据
 */
ChartListInfoDlg.collectData = function() {
    this
    .set('id')
    .set('chartId')
    .set('chartName')
    .set('sort')
    .set('updatetime');
}

/**
 * 提交添加
 */
ChartListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/chartList/add", function(data){
        Feng.success("添加成功!");
        window.parent.ChartList.table.refresh();
        ChartListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.chartListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ChartListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/chartList/update", function(data){
        Feng.success("修改成功!");
        window.parent.ChartList.table.refresh();
        ChartListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.chartListInfoData);
    ajax.start();
}

$(function() {

});
