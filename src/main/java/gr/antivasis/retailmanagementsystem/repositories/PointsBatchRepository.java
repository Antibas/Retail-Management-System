package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.PointsBatch;
import gr.antivasis.retailmanagementsystem.enums.PointsBatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointsBatchRepository extends JpaRepository<PointsBatch, UUID> {
    List<PointsBatch> findByStatusIsNot(PointsBatchStatus status);
}