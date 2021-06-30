package com.banma.bill.mp;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author binglang
 * @since 2019/6/28
 */
public interface BaseRepository<T extends BaseEntity> {

    int SELECT_MAX_SIZE = 2000;

    BaseExtendMapper<T> getBaseMapper();

    /**
     * 插入一条记录
     */
    default int insert(T entity) {
        return getBaseMapper().insert(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    default int deleteById(Serializable id) {
        return getBaseMapper().deleteById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    default int updateById(T entity) {
        return getBaseMapper().updateById(entity);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    default T selectById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * 查询（根据ID 批量查询）
     */
    default List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList) || idList.size() > SELECT_MAX_SIZE) {
            throw new IllegalArgumentException();
        }
        return getBaseMapper().selectBatchIds(idList);
    }
}
