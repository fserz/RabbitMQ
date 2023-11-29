package com.idempotent.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Orders implements Serializable {
    private Integer id;
    private String orderName;
    private BigDecimal orderMoney;
    private Date orderTime;


}
