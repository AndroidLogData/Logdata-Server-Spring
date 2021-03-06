package com.logdata.api.repository;

import com.logdata.common.model.PackageNameVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackageNameDataRepository extends MongoRepository<PackageNameVO, String> {
    @Override
    <S extends PackageNameVO> S save(S entity);
    PackageNameVO findByApiKey(String apiKey);
    PackageNameVO findPackageNameVOByApiKey(String apiKey);
}
