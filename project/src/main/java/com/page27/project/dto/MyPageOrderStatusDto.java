package com.page27.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageOrderStatusDto {
    private int payWaitingNum;
    private int preShipNum;
    private int inShipNum;
    private int completeShipNum;
    private int orderCancelNum;
    private int orderChangeNum;
    private int orderRefundNum;

    public MyPageOrderStatusDto(int payWaitingNum, int preShipNum, int inShipNum, int completeShipNum, int orderCancelNum, int orderChangeNum, int orderRefundNum) {
        this.payWaitingNum = payWaitingNum;
        this.preShipNum = preShipNum;
        this.inShipNum = inShipNum;
        this.completeShipNum = completeShipNum;
        this.orderCancelNum = orderCancelNum;
        this.orderChangeNum = orderChangeNum;
        this.orderRefundNum = orderRefundNum;
    }
}
