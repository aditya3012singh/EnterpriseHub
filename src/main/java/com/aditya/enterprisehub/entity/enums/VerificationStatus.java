package com.aditya.enterprisehub.entity.enums;

public enum VerificationStatus {

    NOT_SUBMITTED,   // user has not uploaded documents
    PENDING,         // documents uploaded, awaiting review
    APPROVED,        // verified by admin
    REJECTED,        // rejected by admin
    SUSPENDED        // blocked after approval (fraud / complaints)
}
