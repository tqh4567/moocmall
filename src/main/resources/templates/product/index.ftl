<!DOCTYPE html>
<html lang="en">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="col-md-12 column">
                <form role="form" action="">
                    <div class="form-group">
                        <label for="exampleInputEmail1">商品Id</label><input type="text" id="exampleInputEmail1" class="form-control"  />
                        <#--value="${productInfo.productId}"-->
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">名称</label><input type="text" id="exampleInputEmail1" class="form-control" value="${productInfo.productName}"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputFile">商品图片</label><input type="file" id="exampleInputFile" />
                        <p class="help-block">
                            点击按钮上传图片
                        </p>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">单价</label><input type="text" id="exampleInputEmail1" class="form-control" value="${productInfo.productPrice}"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">库存</label><input type="text" class="form-control" id="exampleInputEmail1" value="${productInfo.productStock}"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">描述</label><input type="text" id="exampleInputEmail1" class="form-control" value="${productInfo.productDescription}"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">类目</label><input  type="text" id="exampleInputEmail1" class="form-control" value="${productInfo.categoryType}"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">创建时间</label><input type="text" id="exampleInputEmail1" class="form-control" value="${productInfo.createTime}"/>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">修改时间</label><input  type="text" id="exampleInputEmail1" class="form-control" value="${productInfo.updateTime}"/>
                    </div>
                    <div class="checkbox">
                        <#--<label><input type="checkbox" />Check me out</label>-->
                    </div> <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>