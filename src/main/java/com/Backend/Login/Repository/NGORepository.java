package com.Backend.Login.Repository;
import com.Backend.Login.Model.NGO;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NGORepository extends MongoRepository<NGO, String> {
    List<NGO> findByUserEmail(String email);
    List<NGO> findByApproved(boolean approved);
    List<NGO> findByApprovedTrue();
    List<NGO> findByApprovedFalse();
}

