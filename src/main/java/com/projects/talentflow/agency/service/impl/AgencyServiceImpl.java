package com.projects.talentflow.agency.service.impl;

import com.projects.talentflow.agency.dto.AgencyResponse;
import com.projects.talentflow.agency.dto.CreateAgencyRequest;
import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.agency.repository.AgencyRepository;
import com.projects.talentflow.agency.service.AgencyService;

import com.projects.talentflow.common.exception.DuplicateResourceException;
import com.projects.talentflow.common.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AgencyServiceImpl
        extends BaseServiceImpl<Agency>
        implements AgencyService {

    private final AgencyRepository agencyRepository;

    public AgencyServiceImpl(AgencyRepository agencyRepository) {

        super(agencyRepository);

        this.agencyRepository = agencyRepository;
    }

    @Override
    public AgencyResponse createAgency(CreateAgencyRequest request) {

        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                agencyRepository.existsByEmail(request.getEmail().trim())) {

            throw new DuplicateResourceException(
                    "Agency email already exists"
            );
        }

        Agency agency = new Agency();

        agency.setName(request.getName().trim());
        agency.setEmail(
                request.getEmail() == null
                        ? null
                        : request.getEmail().trim()
        );
        agency.setPhone(request.getPhone());
        agency.setCountry(request.getCountry());
        agency.setAddress(request.getAddress());

        // Reusing BaseServiceImpl
        Agency savedAgency = saveEntity(agency);

        return mapToResponse(savedAgency);
    }

    @Override
    public AgencyResponse getAgencyById(UUID id) {

        // Reusing BaseServiceImpl
        Agency agency = getEntityById(id);

        return mapToResponse(agency);
    }

    @Override
    public List<AgencyResponse> getAllAgencies() {

        // Reusing BaseServiceImpl
        return getAllEntities()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AgencyResponse mapToResponse(Agency agency) {

        return new AgencyResponse(
                agency.getId(),
                agency.getName(),
                agency.getEmail(),
                agency.getPhone(),
                agency.getCountry(),
                agency.getAddress(),
                agency.getStatus(),
                agency.getCreatedAt()
        );
    }
}