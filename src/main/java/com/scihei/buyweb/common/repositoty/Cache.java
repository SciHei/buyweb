package com.scihei.buyweb.common.repositoty;

import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.common.repositoty.exception.RepositoryException;

public interface Cache {
    Object get(CacheKindConstants kind, String key) throws RepositoryException;
    void set(CacheKindConstants kind, String key, Object value) throws RepositoryException;
    void delete(CacheKindConstants kind, String key) throws RepositoryException;
}
