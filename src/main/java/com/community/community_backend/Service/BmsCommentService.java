package com.community.community_backend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.community_backend.Model.Dto.CommentDTO;
import com.community.community_backend.Model.Entity.BmsComment;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Model.Vo.CommentVO;

import java.util.List;

public interface BmsCommentService extends IService<BmsComment> {
    /**
     *
     *
     * @param topicid
     * @return {@link BmsComment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

    BmsComment create(CommentDTO dto, UmsUser principal);

}
