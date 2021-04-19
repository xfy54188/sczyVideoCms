/**
 * 初始化歌单列表详情对话框
 */
var SongListInfoDlg = {
    songListInfoData : {}
};

/**
 * 清除数据
 */
SongListInfoDlg.clearData = function() {
    this.songListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SongListInfoDlg.set = function(key, val) {
    this.songListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SongListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SongListInfoDlg.close = function() {
    parent.layer.close(window.parent.SongList.layerIndex);
}

/**
 * 收集数据
 */
SongListInfoDlg.collectData = function() {
    this
    .set('id')
    .set('chartId')
    .set('sort')
    .set('crbtId')
    .set('contentId')
    .set('songName')
    .set('playCount')
    .set('singerName')
    .set('vrbtImg')
    .set('updatetime');
}

/**
 * 提交添加
 */
SongListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/songList/add", function(data){
        Feng.success("添加成功!");
        window.parent.SongList.table.refresh();
        SongListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.songListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SongListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/songList/update", function(data){
        Feng.success("修改成功!");
        window.parent.SongList.table.refresh();
        SongListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.songListInfoData);
    ajax.start();
}

$(function() {

});
