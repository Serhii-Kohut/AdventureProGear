package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.PostDTO;
import com.example.adventureprogearjava.entity.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper MAPPER = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "author.id", target = "user_id")
    @Mapping(source = "titleEn", target = "titleEn")
    @Mapping(source = "titleUa", target = "titleUa")
    @Mapping(source = "contentEn", target = "contentEn")
    @Mapping(source = "contentUa", target = "contentUa")
    @Mapping(source = "createdAt", target = "createdAt")
    PostDTO postToDto(Post post);

    @InheritInverseConfiguration
    Post postDtoToEntity(PostDTO postDTO);
}
