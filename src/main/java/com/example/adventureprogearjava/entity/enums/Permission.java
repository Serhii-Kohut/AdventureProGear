package com.example.adventureprogearjava.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    TEST_READ("test:read"),
    TEST_UPDATE("test:update");

    private final String permission;
}
