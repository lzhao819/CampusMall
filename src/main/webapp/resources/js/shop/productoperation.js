$(function() {
    var productId = getQueryString('productId');
    var infoUrl = '/o2o_war/shopadmin/getproductbyid?productId=' + productId;
    var categoryUrl = '/o2o_war/shopadmin/getproductcategorylist';
    var productPostUrl = '/o2o_war/shopadmin/modifyproduct';
    //识别本次是添加还是编辑操作
    var isEdit = false;
    if (productId) {
        //有id则为编辑操作
        getInfo(productId);
        isEdit = true;
    } else {
        getCategory();
        productPostUrl = '/o2o_war/shopadmin/addproduct';
    }
    //获取需要编辑的商品信息并赋值给表单
    function getInfo(id) {
        $
            .getJSON(
                infoUrl,
                function(data) {
                    if (data.success) {
                        //从返回的JSON中获取product对象信息并赋值给表单
                        var product = data.product;
                        $('#product-name').val(product.productName);
                        $('#product-desc').val(product.productDesc);
                        $('#priority').val(product.priority);
                        $('#normal-price').val(product.normalPrice);
                        $('#promotion-price').val(
                            product.promotionPrice);
                        //获取原本的商品类别以及该店铺所有商品类别列表
                        var optionHtml = '';
                        var optionArr = data.productCategoryList;
                        var optionSelected = product.productCategory.productCategoryId;
                        //生成前端HTML商品类别列表并默认选择编辑前的商品类别
                        optionArr
                            .map(function(item, index) {
                                var isSelect = optionSelected === item.productCategoryId ? 'selected'
                                    : '';
                                optionHtml += '<option data-value="'
                                    + item.productCategoryId
                                    + '"'
                                    + isSelect
                                    + '>'
                                    + item.productCategoryName
                                    + '</option>';
                            });
                        $('#category').html(optionHtml);
                    }
                });
    }
    //为商品添加操作提供该店铺下的所以商品类别列表
    function getCategory() {
        $.getJSON(categoryUrl, function(data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = '';
                productCategoryList.map(function(item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }
    //针对商品详情图控件组，若该控件组的最后一个元素发生变化且控件总数未到达6，则生成新的一个文件上传控件
    $('.detail-img-div').on('change', '.detail-img:last-child', function() {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });
    //提交按钮的事件响应
    $('#submit').click(
        function() {
            //创建商品json对象，并从表单里面获取对应的属性值
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#priority').val();
            product.normalPrice = $('#normal-price').val();
            product.promotionPrice = $('#promotion-price').val();
            //获取选定的商品类别
            product.productCategory = {
                productCategoryId : $('#category').find('option').not(
                    function() {
                        return !this.selected;
                    }).data('value')
            };
            product.productId = productId;
            //获取缩略图文件流
            var thumbnail = $('#small-img')[0].files[0];
            console.log(thumbnail);
            //生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append('thumbnail', thumbnail);
            //遍历商品详情图控件，获取里面的文件流
            $('.detail-img').map(
                function(index, item) {
                    //判断控件是否选择了文件
                    if ($('.detail-img')[index].files.length > 0) {
                        //将第i个文件赋值
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            formData.append('productStr', JSON.stringify(product));
            var verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            //将数据提交至后台处理
            $.ajax({
                url : productPostUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast('提交成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('提交失败！');
                        $('#captcha_img').click();
                    }
                }
            });
        });

});
