
package com.projects.talentflow.ai.service;

import com.projects.talentflow.ai.dto.CvParsingResponse;

public interface CvParsingService {

    CvParsingResponse parseCv(String cvText);

}
