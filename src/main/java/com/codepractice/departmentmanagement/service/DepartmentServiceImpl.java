package com.codepractice.departmentmanagement.service;

import com.codepractice.departmentmanagement.entity.Department;
import com.codepractice.departmentmanagement.errorHandling.DepartmentNotFoundException;
import com.codepractice.departmentmanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> fetchDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department fetchDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        Optional<Department> department =  departmentRepository.findById(departmentId);

        if (!department.isPresent()){
            throw new DepartmentNotFoundException("Department not found in Database : ");
        }

        return department.get();
    }

    @Override
    public void deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department updateDepartmentById(Long departmentId, Department department) {
        Department depTemp = departmentRepository.findById(departmentId).get();

        //Checking for not Null & Empty values and then updating data in the database
        //This is how we will be not updating any empty data to database
        if(Objects.nonNull(department.getDepartmentName()) &&
                !"".equalsIgnoreCase(department.getDepartmentName())) {
            depTemp.setDepartmentName(department.getDepartmentName());
        }

        if(Objects.nonNull(department.getDepartmentCode()) &&
                !"".equalsIgnoreCase(department.getDepartmentCode())) {
            depTemp.setDepartmentCode(department.getDepartmentCode());
        }

        if(Objects.nonNull(department.getDepartmentAddress()) &&
                !"".equalsIgnoreCase(department.getDepartmentAddress())) {
            depTemp.setDepartmentAddress(department.getDepartmentAddress());
        }

        return departmentRepository.save(depTemp);
    }

    @Override
    public Department fetchDepartmentByName(String departmentName) {
        //return departmentRepository.findByDepartmentName(departmentName);
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName);//For Case Ignoring
    }
}
