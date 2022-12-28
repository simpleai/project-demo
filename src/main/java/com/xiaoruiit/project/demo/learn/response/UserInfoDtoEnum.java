package com.xiaoruiit.project.demo.learn.response;

/**
 * @author hanxiaorui
 * @date 2022/12/28
 */
public enum UserInfoDtoEnum {
    BASE_DTO(UserBaseDto.class, "id+code"),
    INFO_DTO_V1(UserInfoDtoV1.class, "id+code+name"),
    ALL_INFO_DTO(UserAllInfoDto.class, "全部信息"),
    ;

    /**
     * 返回值数据类型
     */
    private final Class<? extends UserBaseDto> clazz;
    /**
     * 返回值数据类型字段描述
     */
    private final String description;

    UserInfoDtoEnum(Class<? extends UserBaseDto> clazz, String description) {
        this.clazz = clazz;
        this.description = description;
    }

    public Class<? extends UserBaseDto> getClazz() {
        return clazz;
    }

    public String getDescription() {
        return description;
    }
}
