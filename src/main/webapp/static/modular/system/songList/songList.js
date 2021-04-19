/**
 * 歌单列表管理初始化
 */
var SongList = {
    id: "SongListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SongList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '榜单id', field: 'chartId', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '排序', field: 'sort', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '歌曲版权id', field: 'crbtId', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '内容id', field: 'contentId', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '歌曲名称', field: 'songName', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '播放次数', field: 'playCount', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '歌手名称', field: 'singerName', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '图片地址', field: 'vrbtImg', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter},
            {title: '更新时间', field: 'updatetime', visible: true, align: 'center', valign: 'middle', sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter}
    ];
};

/**
 * 检查是否选中
 */
SongList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        var arrays = new Array();// 声明一个数组
        $(selected).each(function () {// 通过获得别选中的来进行遍历
            arrays.push(this.id);// id为获得到的整条数据中的一列
        });
        var idcard = arrays.join(',');
        SongList.seItem = idcard;
        return true;
    }
};

/**
 * 点击添加歌单列表
 */
SongList.openAddSongList = function () {
    var index = layer.open({
        type: 2,
        title: '添加歌单列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/songList/songList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看歌单列表详情
 */
SongList.openSongListDetail = function () {
    if (this.check()) {
        if(SongList.seItem.indexOf(",")!= -1){
            Feng.info("修改只能选择一记录！");
            return
        }
        var index = layer.open({
            type: 2,
            title: '歌单列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/songList/songList_update/' + SongList.seItem
        });
        this.layerIndex = index;
    }
};

/**
 * 删除歌单列表
 */
SongList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/songList/delete", function (data) {
            Feng.success("删除成功!");
            SongList.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("songListIds",this.seItem);
        ajax.start();
    }
};

/**
 * 查询歌单列表列表
 */
SongList.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SongList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SongList.initColumn();
    $.ajax({
        type: 'post',
        url: '/chartList/list',
        dataType: "json",
        success: function (data) {
            //拼接下拉框
            for (var i = 0; i < data.length; i++) {
                $("#queryDevice").append("<option value='" + data[i].chartId + "' data-tokens=\\\"ketchup mustard\\\">" + data[i].chartName + "</option>");
            }
            //这一步不要忘记 不然下拉框没有数据
            $("#queryDevice").selectpicker("refresh");
        }
    });
    // 下拉框旋转事件
    $("#queryDevice").on('changed.bs.select', function (e) {
        var queryData = {};
        queryData['chartId'] = $("#queryDevice").val();
        SongList.table.refresh({query: queryData});
    });
    var table = new BSTable(SongList.id, "/songList/list", defaultColunms);
    table.setPaginationType("client");
    // 导出excle
    table.setIsShowExport(true);
    table.setFileName("歌曲列表导出");
    table.setWorksheetName("歌曲信息");
    SongList.table = table.init();
});
/**
 * 批量导入歌曲信息
 */
    $('#import').fileinput(
        {
            language: 'zh',
            uploadUrl: Feng.ctxPath + '/songList/import',
            showCaption: true,
            showUpload: true,
            showRemove: true,
            showClose: true,
            uploadExtraData: function () {
                return {'chartId': $("#queryDevice").val()};
            },
            layoutTemplates: {
                actionDelete: ''
            },
            browseClass: 'btn btn-primary'
        }).on('filebatchpreupload', function(event, data) { //该方法将在上传之前触发
        var id =$("#queryDevice").val();
        if(id == ""||id==null){
            Feng.error("请选择榜单列表!");
            return {
                message: "请选择榜单列表!",
                data:{},
            };
        }
    });

/**
 * 导入后刷新
 */
$("#import").on("fileuploaded", function(event, data, previewId, index) {
    Feng.success("批量导入成功!");
    SongList.table.refresh();
});
$('#import').on('filebatchuploaderror', function(event, data, msg) {
    Feng.error("批量导入失败!");

});
/**
 * 导入模板下载
 */
SongList.download = function () {
    var $form = $('<form method="GET"></form>');
    $form.attr('action','/static/template/歌单列表.xlsx');
    $form.appendTo($('body'));
    $form.submit();
};