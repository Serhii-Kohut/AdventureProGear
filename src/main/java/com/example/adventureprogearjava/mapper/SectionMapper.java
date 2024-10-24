package com.example.adventureprogearjava.mapper;

import com.example.adventureprogearjava.dto.SectionDTO;
import com.example.adventureprogearjava.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    String api = "https://empowering-happiness-production.up.railway.app/api/public/sections";

    String categoriesApi = "https://prime-tax-production.up.railway.app/api/public/categories/";

    @Mapping(target = "selfLink", source = "section.id", qualifiedByName = "idToLink")
    @Mapping(target = "categoryCreationLink", source = "section.id", qualifiedByName = "idToCategoryLink")
    SectionDTO toDto(Section section);

    Section toEntity(SectionDTO dto);

    @Named("idToLink")
    default String getLink(Long id) {
        return api + id;
    }

    @Named("idToCategoryLink")
    default String getCategoryLink(Long id) {
        return categoriesApi + id;
    }
}
