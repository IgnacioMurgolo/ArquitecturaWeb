package org.tudai.tripservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class CustomTripRepositoryImpl implements CustomTripRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTripRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Document> getBenefitsReport(int year, int startMonth, int endMonth) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where("startDateTime")
                                .gte(getStartOfMonth(year, startMonth))
                                .lte(getEndOfMonth(year, endMonth))
                ),
                Aggregation.project()
                        .andExpression("month(startDateTime)").as("month")
                        .andExpression("creditsConsumed").as("totalCredits"),
                Aggregation.group("month")
                        .sum("totalCredits").as("totalCredits")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "trip", Document.class);
        return results.getMappedResults();
    }

    private Date getStartOfMonth(int year, int month) {
        return Date.from(LocalDate.of(year, month, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Date getEndOfMonth(int year, int month) {
        return Date.from(LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth())
                .atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
    }
}

