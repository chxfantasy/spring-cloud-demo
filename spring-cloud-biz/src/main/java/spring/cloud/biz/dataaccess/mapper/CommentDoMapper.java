package spring.cloud.biz.dataaccess.mapper;

import org.apache.ibatis.annotations.Select;
import spring.cloud.biz.dataaccess.dataobject.CommentDo;

import java.util.List;

public interface CommentDoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommentDo record);

    int insertSelective(CommentDo record);

    CommentDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommentDo record);

    int updateByPrimaryKey(CommentDo record);

    @Select("select * from comment where moment_id=#{momentId,jdbcType=BIGINT}")
    List<CommentDo> listCommentsByMomentId(Long momentId);
}