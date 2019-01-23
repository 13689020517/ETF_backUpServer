package com.zmate.mapper;

import com.zmate.pojo.recallOrder_Bean;

import java.util.List;

public interface recallOrderRecord_Mapper {
    int batchInsert(List<recallOrder_Bean> list);
}
