package com.community.community_backend.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.community_backend.Model.Entity.BmsComment;
import com.community.community_backend.Model.Vo.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BmsCommentMapper extends BaseMapper<BmsComment> {
    List<CommentVO> getCommentsByTopicID(@Param("topicid") String topicid);

}
