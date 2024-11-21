package org.tudai.tripservice.repository;

import org.bson.Document;

import java.util.List;

public interface CustomTripRepository {
    List<Document> getBenefitsReport(int year, int startMonth, int endMonth);
}
