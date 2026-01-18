package com.travis.framework.jackson.core;

import tools.jackson.databind.DatabindContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

/**
 * 全局放行的校验器
 *
 * @author travis
 */
public class LaissezFaireSubTypeValidator extends PolymorphicTypeValidator.Base {

    @Override
    public Validity validateSubClassName(DatabindContext ctxt, JavaType baseType, String subClassName) {
        return Validity.ALLOWED;
    }

    @Override
    public Validity validateSubType(DatabindContext ctxt, JavaType baseType, JavaType subType) {
        return Validity.ALLOWED;
    }
}