package com.community.community_backend.Controller;

import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Dto.CommentDTO;
import com.community.community_backend.Model.Entity.BmsComment;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Model.Vo.CommentVO;
import com.community.community_backend.Service.BmsCommentService;
import com.community.community_backend.Service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.community.community_backend.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/comment")
public class BmsCommentController {
    @Autowired
    private BmsCommentService bmsCommentService;

    @Autowired
    private UmsUserService userService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid",defaultValue = "1")String topicid) {
        List<CommentVO> lstBmsComment = bmsCommentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }

    @PostMapping("/add_comment")
    public ApiResult<BmsComment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                                 @RequestBody CommentDTO dto){
        UmsUser user = userService.getUserByUsername(userName);
        BmsComment comment = bmsCommentService.create(dto, user);
        return ApiResult.success(comment);
    }
}
