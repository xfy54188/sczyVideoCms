/**
 * 榜单列表管理初始化
 */
var ChartList = {
    id: "ChartListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ChartList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {
            title: '榜单id',
            field: 'chartId',
            visible: true,
            align: 'center',
            valign: 'middle',
            sortable: true,
            cellStyle: Feng.formatTableUnit,
            formatter: Feng.paramsMatter, sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter
        },
        {
            title: '榜单名称',
            field: 'chartName',
            visible: true,
            align: 'center',
            valign: 'middle',
            sortable: true,
            cellStyle: Feng.formatTableUnit,
            formatter: Feng.paramsMatter, sortable: true, cellStyle:Feng.formatTableUnit, formatter:Feng.paramsMatter
        },
        {
            title: '排序',
            field: 'sort',
            visible: true,
            align: 'center',
            valign: 'middle',
            sortable: true,
            cellStyle: Feng.formatTableUnit,
            formatter: Feng.paramsMatter
        },
        {
            title: '操作时间',
            field: 'updatetime',
            visible: true,
            align: 'center',
            valign: 'middle',
            sortable: true,
            cellStyle: Feng.formatTableUnit,
            formatter: Feng.paramsMatter
        }
    ];

};

/**
 * 检查是否选中
 */
ChartList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        var arrays = new Array();// 声明一个数组
        $(selected).each(function () {// 通过获得别选中的来进行遍历
            arrays.push(this.id);// id为获得到的整条数据中的一列
        });
        var idcard = arrays.join(',');
        ChartList.seItem = idcard;
        return true;
    }
};

/**
 * 点击添加榜单列表
 */
ChartList.openAddChartList = function () {
    var index = layer.open({
        type: 2,
        title: '添加榜单列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/chartList/chartList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看榜单列表详情
 */
ChartList.openChartListDetail = function () {
    if (this.check()) {
        //不能批量修改
        if (ChartList.seItem.indexOf(",") != -1) {
            Feng.info("修改只能选择一记录！");
            return
        }
        var index = layer.open({
            type: 2,
            title: '榜单列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/chartList/chartList_update/' + ChartList.seItem
        });
        this.layerIndex = index;
    }
};

/**
 * 删除榜单列表
 */
ChartList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/activityLog/delete", function (data) {
            Feng.success("删除成功!");
            ChartList.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("chartListIds", this.seItem);
        ajax.start();
    }
};

/**
 * 查询榜单列表列表
 */
ChartList.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ChartList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ChartList.initColumn();
    var table = new BSTable(ChartList.id, "/chartList/list", defaultColunms);

    table.setPaginationType("client");
    //  导出excle
    table.setIsShowExport(true);
    table.setFileName("榜单列表导出");
    table.setWorksheetName("榜单信息");
    ChartList.table = table.init();
});
