package com.misobuild.utils.factory;

import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.constants.SocialType;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.validator.AppleValidator;
import com.misobuild.utils.validator.KakaoValidator;
import com.misobuild.utils.validator.Validator;

public class ValidatorFactory {
    public static Validator of(String socialId, String socialType, String socialToken){
        switch (SocialType.getEnum(socialType)){
            case KAKAO:
                return new KakaoValidator(socialId, socialToken);
            case APPLE:
                return new AppleValidator(socialId, socialToken);
            default:
                throw new ApiCustomException(HttpStatusEnum.NOT_FOUND);
        }
    }
}
