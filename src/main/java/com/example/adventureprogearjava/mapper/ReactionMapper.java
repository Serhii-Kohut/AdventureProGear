package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.ReactionToPostDTO;
import com.example.adventureprogearjava.entity.ReactionToPost;
import org.mapstruct.Mapper;

@Mapper
public interface ReactionMapper {
    ReactionToPostDTO reactionToDto(ReactionToPost reactionToPost);

    ReactionToPost reactionDtoToEntity(ReactionToPostDTO reactionToPostDTO);
}
