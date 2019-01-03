package com.maia.workflowimporter.migrator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WorkflowInstanceMigratorTest {

    File file;
    File contractorsData;
    File employeesData;
    File workflowsData;
    File workflowInstancesData;
    WorkflowInstanceMigrator migrator;

    @Before
    public void setUp() throws Exception {
        contractorsData = new ClassPathResource("contractors.data", getClass().getClassLoader()).getFile();
        employeesData = new ClassPathResource("employees.data", getClass().getClassLoader()).getFile();
        workflowsData = new ClassPathResource("workflows.data", getClass().getClassLoader()).getFile();
        workflowInstancesData = new ClassPathResource("workflowInstances.data", getClass().getClassLoader()).getFile();
        file = new ClassPathResource("workflowInstances.data", getClass().getClassLoader()).getFile();

        migrator = new WorkflowInstanceMigrator(
                file,
                new EmployeeMigrator(employeesData),
                new ContractorMigrator(contractorsData),
                new WorkflowMigrator(workflowsData));


    }

    @Test
    public void should_read_eachline_and_populate_object() {
        assertEquals(migrator.getWorkflowInstances().size(), 18);
        assertEquals(migrator.getWorkflowInstancesWithError().size(), 11);
        assertEquals(migrator.getLinesOutsideLoop().size(), 2);
        migrator.printResults();
    }

    @Test
    public void should_capture_all() throws IOException {
        File file = new ClassPathResource("workflowInstances.inconsistent.data", getClass().getClassLoader()).getFile();

        WorkflowInstanceMigrator migrator = new WorkflowInstanceMigrator(file,
                new EmployeeMigrator(employeesData),
                new ContractorMigrator(contractorsData),
                new WorkflowMigrator(workflowsData));

        assertEquals(migrator.getWorkflowInstances().size(), 4);
        assertEquals(migrator.getWorkflowInstancesWithError().size(), 4);
        assertEquals(migrator.getLinesOutsideLoop().size(), 4);
        migrator.printResults();
    }

}
