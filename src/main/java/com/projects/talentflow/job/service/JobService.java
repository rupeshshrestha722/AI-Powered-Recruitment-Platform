package com.projects.talentflow.job.service;

import com.projects.talentflow.common.service.BaseService;
import com.projects.talentflow.job.dto.CreateJobRequest;
import com.projects.talentflow.job.dto.JobResponse;
import com.projects.talentflow.job.entity.Job;

import java.util.List;
import java.util.UUID;

public interface JobService extends BaseService<Job> {

    JobResponse createJob(CreateJobRequest request);

    List<JobResponse> getAllJobs();

    JobResponse getJobById(UUID jobId);

    List<JobResponse> getJobsByStatus(String status);

    List<JobResponse> getJobsByClient(UUID clientId);
}