package com.zhu.aqs.model;

import javax.persistence.*;
import javax.persistence.Table;

import com.zhu.aqs.common.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_order")
public class Order {
    @Id
    private Integer id;

    private SexEnum stock;
}