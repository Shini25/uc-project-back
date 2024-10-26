package finance.uc_project.service.chefs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import finance.uc_project.model.chefs.organizationalChart;
import finance.uc_project.model.chefs.organizationalChartAudit;
import finance.uc_project.repository.chefs.organizationalChartAuditRepository;
import finance.uc_project.repository.chefs.organizationalChartRepository;

@Service
public class organizationalChartService {
    
    @Autowired
    private organizationalChartRepository organizationalChartRepository;

    @Autowired
    private organizationalChartAuditRepository organizationalChartAuditRepository;

    public organizationalChart saveOrganizationalChart(String type, MultipartFile contenue, String addby, String filetype) {
        organizationalChart newOrganizationalChart = new organizationalChart();
        newOrganizationalChart.setType(type);
        try {
            byte[] contenueBytes = contenue.getBytes();
            newOrganizationalChart.setContent(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newOrganizationalChart.setAddby(addby);
        newOrganizationalChart.setCreatedate(LocalDateTime.now());
        newOrganizationalChart.setFiletype(filetype);
        return organizationalChartRepository.save(newOrganizationalChart);
    }

    public List<organizationalChart> getAllOrganizationalCharts() {
        return organizationalChartRepository.findAll();
    }

    public Optional<organizationalChart> getOrganizationalChartById(Long id) {
        return organizationalChartRepository.findById(id);
    }

    public organizationalChart updateOrganizationalChart(Long id, MultipartFile contenue, String updateby) {
        organizationalChart existingOrganizationalChart = organizationalChartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid organizational chart id: " + id));
        existingOrganizationalChart.setUpdateby(updateby);
        existingOrganizationalChart.setUpdatedate(LocalDateTime.now());
        try {
            byte[] contenueBytes = contenue.getBytes();
            existingOrganizationalChart.setContent(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return organizationalChartRepository.save(existingOrganizationalChart);
    }

    public organizationalChart updateOrganizationalChart(Long id, MultipartFile contenue,String fileType, String modifyby) {
        organizationalChart existingOrganizationalChart = organizationalChartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid organizational chart id: " + id));
        existingOrganizationalChart.setUpdatedate(LocalDateTime.now());
        saveOrganizationalChartAudit(existingOrganizationalChart, modifyby);
        existingOrganizationalChart.setCreatedate(LocalDateTime.now());
        try {
            byte[] contenueBytes = contenue.getBytes();
            existingOrganizationalChart.setContent(contenueBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        existingOrganizationalChart.setFiletype(fileType);
        return organizationalChartRepository.save(existingOrganizationalChart);
    }


    private void saveOrganizationalChartAudit(organizationalChart organizationalChart, String modifyby) {
        organizationalChartAudit organizationalChartAudit = new organizationalChartAudit();
        organizationalChartAudit.setIdOrganizationalChart(organizationalChart.getIdOrganizationalChart());
        organizationalChartAudit.setType(organizationalChart.getType());
        organizationalChartAudit.setContent(organizationalChart.getContent());
        organizationalChartAudit.setAddby(organizationalChart.getAddby());
        organizationalChartAudit.setCreatedate(organizationalChart.getCreatedate());
        organizationalChartAudit.setFiletype(organizationalChart.getFiletype());
        organizationalChartAudit.setAuditTimestamp(LocalDateTime.now());
        organizationalChartAudit.setModifiedBy(modifyby);
        organizationalChartAuditRepository.save(organizationalChartAudit);
    }
}

