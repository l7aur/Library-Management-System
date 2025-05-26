package com.laur.bookshop.config.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class FullOrderDTO {
    private List<ConfirmationEmailData> items;
    private Date date;

    public FullOrderDTO(List<ConfirmationEmailData> confirmationEmailData, Date orderDate) {
        this.items = confirmationEmailData;
        this.date = orderDate;
    }
}
