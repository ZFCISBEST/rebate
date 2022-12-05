package com.help.rebate.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GenericRowListDTO<T> implements Serializable {
    List<T> data;
    Integer total;
    Boolean success;

    public GenericRowListDTO() {
    }

    public GenericRowListDTO(List<T> data, Integer total, Boolean success) {
        this.data = data;
        this.total = total;
        this.success = success;
    }
}