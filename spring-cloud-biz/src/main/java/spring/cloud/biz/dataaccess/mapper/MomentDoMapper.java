package spring.cloud.biz.dataaccess.mapper;

import org.apache.ibatis.annotations.Select;
import spring.cloud.biz.dataaccess.dataobject.MomentDo;

import java.util.List;

public interface MomentDoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MomentDo record);

    int insertSelective(MomentDo record);

    MomentDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MomentDo record);

    int updateByPrimaryKey(MomentDo record);

    @Select("select * from moment where is_deleted=0")
    List<MomentDo> listMoment();
}