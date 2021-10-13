package com.teach.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import com.teach.pojo.Dictionary;
import java.util.List;

@Mapper
@Repository
public interface DictionaryMapper {

    //查询字典项信息，通过字典项类型
    List<Dictionary> findDicInfo(String dicType);
    //查询字典项个数，通过字典项类型
    int findDicInfoNum();
}
