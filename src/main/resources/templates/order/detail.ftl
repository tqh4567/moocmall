<!DOCTYPE html>
<html lang="en">
<#include "../common/header.ftl">
<body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-8 column">
                    <table class="table table-hover table-condensed table-bordered" style="width: 360px">
                        <thead>
                        <tr>
                            <th>
                                订单Id
                            </th>
                            <th>
                                订单金额
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                ${orderDTO.orderId}
                            </td>
                            <td>
                                ${orderDTO.orderAmount}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <h4>订单详细信息</h4>
                    <table class="table table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>
                                商品Id
                            </th>
                            <th>
                                商品名称
                            </th>
                            <th>
                                价格
                            </th>
                            <th>
                                数量
                            </th>
                            <th>
                                总金额
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as detail>
                            <tr>
                                <td>
                                ${detail.detailId}
                                </td>
                                <td>
                                    ${detail.productName}
                                </td>
                                <td>
                                    ${detail.productPrice}
                                </td>
                                <td>
                                     ${detail.productQuantity}
                                </td>
                                <td>
                                    ${detail.productQuantity*detail.productPrice}
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <#if orderDTO.orderStatus==0>
                        <a type="button" href="/sell/order/finish?orderId=${orderDTO.orderId}" class="btn btn-primary btn-default">完结订单</a>
                        <a type="button" href="/sell/order/cancel?orderId=${orderDTO.orderId}" class="btn btn-primary btn-danger">取消订单</a>
                        <#else >
                        <a type="button" class="btn btn-primary btn-default disabled">完结订单</a>
                        <a type="button" class="btn btn-default btn-danger disabled">取消订单</a>
                    </#if>

                </div>
            </div>
        </div>
</body>
</html>