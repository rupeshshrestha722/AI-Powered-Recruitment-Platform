package com.projects.talentflow.client.service.impl;

import com.projects.talentflow.agency.repository.AgencyRepository;
import com.projects.talentflow.client.dto.ClientResponse;
import com.projects.talentflow.client.dto.CreateClientRequest;
import com.projects.talentflow.client.entity.Client;
import com.projects.talentflow.client.repository.ClientRepository;
import com.projects.talentflow.client.service.ClientService;
import com.projects.talentflow.common.exception.DuplicateResourceException;
import com.projects.talentflow.common.exception.ResourceNotFoundException;
import com.projects.talentflow.common.service.impl.BaseServiceImpl;
import com.projects.talentflow.security.TenantContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl
        extends BaseServiceImpl<Client>
        implements ClientService {

    private final ClientRepository clientRepository;
    private final AgencyRepository agencyRepository;

    public ClientServiceImpl(
            ClientRepository clientRepository,
            AgencyRepository agencyRepository) {

        super(clientRepository);
        this.clientRepository = clientRepository;
        this.agencyRepository = agencyRepository;
    }

    @Override
    public ClientResponse createClient(CreateClientRequest request) {

        UUID agencyId = TenantContext.getAgencyId();

        var agency = agencyRepository.findById(agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agency not found"));

        String companyName = request.getCompanyName().trim();

        if (clientRepository.existsByAgencyIdAndCompanyNameIgnoreCase(
                agencyId, companyName)) {
            throw new DuplicateResourceException(
                    "Client with this company name already exists");
        }

        Client client = new Client();
        client.setAgency(agency);
        client.setCompanyName(companyName);
        client.setIndustry(request.getIndustry());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setWebsite(request.getWebsite());
        client.setCountry(request.getCountry());
        client.setCity(request.getCity());
        client.setAddress(request.getAddress());
        client.setStatus("ACTIVE");

        Client savedClient = clientRepository.save(client);

        return mapToResponse(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {

        UUID agencyId = TenantContext.getAgencyId();

        return clientRepository.findAllByAgencyId(agencyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getClientById(UUID clientId) {

        UUID agencyId = TenantContext.getAgencyId();

        Client client = clientRepository
                .findByIdAndAgencyId(clientId, agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client not found"));

        return mapToResponse(client);
    }

    private ClientResponse mapToResponse(Client client) {

        return new ClientResponse(
                client.getId(),
                client.getAgency().getId(),
                client.getCompanyName(),
                client.getIndustry(),
                client.getEmail(),
                client.getPhone(),
                client.getWebsite(),
                client.getCountry(),
                client.getCity(),
                client.getAddress(),
                client.getStatus(),
                client.getCreatedAt()
        );
    }
}