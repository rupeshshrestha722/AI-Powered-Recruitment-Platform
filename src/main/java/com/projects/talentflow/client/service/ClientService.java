package com.projects.talentflow.client.service;

import com.projects.talentflow.client.dto.ClientResponse;
import com.projects.talentflow.client.dto.CreateClientRequest;
import com.projects.talentflow.client.entity.Client;
import com.projects.talentflow.common.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface ClientService extends BaseService<Client> {

    ClientResponse createClient(CreateClientRequest request);

    List<ClientResponse> getAllClients();

    ClientResponse getClientById(UUID clientId);
}