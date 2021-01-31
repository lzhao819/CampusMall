$(function() {
    //获取此店铺下的商品列表URL
    var listUrl = '/o2o_war/shopamin/getproductlistbyshop?pageIndex=1&pageSize=9999';
    //商品下架URL
    var statusUrl = '/o2o_war/shopadmin/modifyproduct';
    getList();

    /**
     * 获取此店铺下的商品列表
     */
    function getList() {
        $.getJSON(listUrl, function(data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                //遍历每条商品信息拼接成一条显示
                //包括：商品名称，优先级，上/下架，编辑，预览
                productList.map(function(item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        //若状态值为0表明已下架，操作变为上架
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    //拼接每件商品的信息
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="delete" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                //将拼接好的信息赋给html
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    function changeItemStatus(id, enableStatus) {
        //定义product json对象并添加productID以及状态
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            //上下架相关商品
            $.ajax({
                url : statusUrl,
                type : 'POST',
                data : {
                    productStr : JSON.stringify(product),
                    statusChange : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }

    $('.product-wrap')
        .on(
            'click',
            'a',
            function(e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    //如果有class edit则 点击进入店铺信息编辑页面，并带有productId参数
                    window.location.href = '/o2o_war/shopadmin/productoperation?productId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('status')) {
                    //如果有class status则调用上下架功能，并带有productId参数
                    changeItemStatus(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    //如果有preview则去前台展示系统该商品 详情页预览商品
                    window.location.href = '/o2o_war/frontend/productdetail?productId='
                        + e.currentTarget.dataset.id;
                }
            });

    // $('#new').click(function() {
    //     window.location.href = '/o2o_war/shopadmin/productedit';
    // });
});