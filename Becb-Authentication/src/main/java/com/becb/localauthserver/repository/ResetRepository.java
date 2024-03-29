package com.becb.localauthserver.repository;

import com.becb.localauthserver.domain.ResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ResetRepository  extends JpaRepository<ResetRequest, Long> {

    ArrayList<ResetRequest> findByEmail(String email);

}
