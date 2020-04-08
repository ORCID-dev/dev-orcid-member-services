package org.orcid.user.repository;

import java.util.List;
import java.util.Optional;

import org.orcid.user.domain.UserSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the MemberServicesUser entity.
 */
@Repository
public interface UserSettingsRepository extends MongoRepository<UserSettings, String> {
    
	Optional<UserSettings> findByJhiUserId(String jhiUserId);

    List<UserSettings> findBySalesforceId(String salesforceId);
    
    Page<UserSettings> findByDeletedFalse(Pageable pageable);
    
}
