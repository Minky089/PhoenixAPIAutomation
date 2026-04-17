package com.db.model;

public record JobProblemDBModel(
        int id,
        int tr_job_head_id,
        int mst_problem_id,
        String remark) {
}
