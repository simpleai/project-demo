package com.xiaoruiit.project.demo.learn.cache;

import com.sexytea.goods.dto.mds.MaterialBasicDto;
import com.sexytea.goods.dto.mds.vo.ProductBarCodeVo;

import java.util.List;

/**
 * @author wangyu
 * @version 1.0
 * @date 1/24/22 11:44 PM
 */
public interface UserService {

    UserVo getUserVoByCode(String userCode);

    UserVo getUserVoCache(String userCode);
}
