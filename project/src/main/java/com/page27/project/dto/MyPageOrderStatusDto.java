package com.page27.project.dto;

import lombok.Data;

@Data
public class MyPageOrderStatusDto {

    private int payWaitingNum;
    private int preShipNum;
    private int inShipNUm;
    private int completeShipNum;
    private int orderCancelNum;
    private int orderChangeNum;
    private int orderRefundNum;

    public MyPageOrderStatusDto(int payWaitingNum, int preShipNum, int inShipNUm, int completeShipNum, int orderCancelNum, int orderChangeNum, int orderRefundNum) {
        this.payWaitingNum = payWaitingNum;
        this.preShipNum = preShipNum;
        this.inShipNUm = inShipNUm;
        this.completeShipNum = completeShipNum;
        this.orderCancelNum = orderCancelNum;
        this.orderChangeNum = orderChangeNum;
        this.orderRefundNum = orderRefundNum;
    }
}
