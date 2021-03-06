package com.community.community_backend.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.community_backend.Mapper.BmsTagMapper;
import com.community.community_backend.Mapper.BmsTopicMapper;
import com.community.community_backend.Mapper.UmsUserMapper;
import com.community.community_backend.Model.Dto.CreateTopicDTO;
import com.community.community_backend.Model.Entity.BmsPost;
import com.community.community_backend.Model.Entity.BmsTag;
import com.community.community_backend.Model.Entity.BmsTopicTag;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Model.Vo.PostVO;
import com.community.community_backend.Model.Vo.ProfileVO;
import com.community.community_backend.Service.BmsPostService;
import com.community.community_backend.Service.BmsTagService;
import com.community.community_backend.Service.BmsTopicTagService;
import com.community.community_backend.Service.UmsUserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("all")
public class BmsPostServicelmpl extends ServiceImpl<BmsTopicMapper, BmsPost> implements BmsPostService {

    @Autowired
    private BmsTagMapper bmsTagMapper;

    @Autowired
    private UmsUserMapper userMapper;

    @Autowired
    private BmsTagService bmsTagService;
    @Autowired
    private BmsTopicTagService bmsTopicTagService;

    @Autowired
    private UmsUserService umsUserService;

    //????????????????????????
    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // ?????????????????????
        setTopicTags(iPage);
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BmsPost create(CreateTopicDTO dto, UmsUser user) {
        BmsPost topic1 = this.baseMapper
                .selectOne(new LambdaQueryWrapper<BmsPost>()
                        .eq(BmsPost::getTitle, dto.getTitle()));
        Assert.isNull(topic1, "???????????????????????????");


        BmsPost topic = BmsPost.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(topic);

        // ??????????????????
        int newScore = user.getScore() + 1;
        userMapper.updateById(user.setScore(newScore));
        // ??????
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            // ????????????
            List<BmsTag> tags = bmsTagService.insertTags(dto.getTags());
            // ??????????????????????????????
            bmsTopicTagService.createTopicTag(topic.getId(), tags);
        }
        return topic;
    }


    @Override
    public Map<String, Object> viewTopic(String id) {
        Map<String, Object> map = new HashMap<>();
        BmsPost topic = this.baseMapper.selectById(id);
        Assert.notNull(topic, "?????????????????????,?????????????????????");

        //  ????????????
        topic.setView(topic.getView() + 1);
        this.baseMapper.updateById(topic);
        // emoji??????
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);

        // ????????????????????????????????????
        QueryWrapper<BmsTopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BmsTopicTag::getTopicId, topic.getId());
        Set<String> set = new HashSet<>();

        for (BmsTopicTag articleTag : bmsTopicTagService.list(wrapper)) {
            set.add(articleTag.getTagId());
        }
        List<BmsTag> tags = bmsTagService.listByIds(set);
        map.put("tags", tags);


        //  ????????????,???????????????
        ProfileVO userProfile = umsUserService.getUserProfile(topic.getUserId());
        map.put("user", userProfile);


        return map;
    }

    @Override
    public List<BmsPost> getRecommend(String id) {
        return this.baseMapper.selectRecommend(id);
    }

    @Override
    public Page<PostVO> searchByKey(String keyword, Page<PostVO> page) {
        return this.baseMapper.searchByKey(page,keyword);
    }


    private void setTopicTags(Page<PostVO> iPage) {
        iPage.getRecords().forEach(topic -> {
            List<BmsTopicTag> topicTags = bmsTopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(BmsTopicTag::getTagId).collect(Collectors.toList());
                List<BmsTag> tags = bmsTagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
    }
}
