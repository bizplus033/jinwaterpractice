package com.example.jinwaterpractice.company;

import com.example.jinwaterpractice.company.dto.InfoCompanyRequest;
import com.example.jinwaterpractice.company.dto.InfoCompanyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final JpaCompanyRepository jpaCompanyRepository;
    private final ModelMapper modelMapper;

    public InfoCompanyResponse findCompanyById(Long companyId) {
        return jpaCompanyRepository.findById(companyId)
                .map(company -> modelMapper.map(company, InfoCompanyResponse.class))
                .orElseThrow(() -> new RuntimeException("Company Not Found"));
    }

    @Transactional
    public void createCompany(InfoCompanyRequest request) {
        jpaCompanyRepository.save(modelMapper.map(request, Company.class));
    }

    @Transactional
    public void updateCompany(InfoCompanyRequest request) {
        Company companyPS = jpaCompanyRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Company Not Found"));

        modelMapper.map(request, companyPS);
    }
}
