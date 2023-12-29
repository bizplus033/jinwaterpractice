package com.example.jinwaterpractice.bom;

import com.example.jinwaterpractice.bom.dto.ListProductBOMResponse;
import com.example.jinwaterpractice.bom.dto.ProductBOMResponse;
import com.example.jinwaterpractice.bom.dto.UpdateProductBOMRequest;
import com.example.jinwaterpractice.product.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductBOMService {
    private final JpaProductRepository jpaProductRepository;
    private final JpaProductBOMRepository jpaProductBOMRepository;
//    todo private final JpaMaterialRepository jpaMaterialRepository;
    private final ProductBOMMapper productBOMMapper;

    public ProductBOMResponse findProductBOMById(Long productBOMId) {
        return jpaProductBOMRepository.findById(productBOMId)
                .map(productBOMMapper::toProductBOMResponse)
                .orElseThrow(() -> new RuntimeException("ProductBOM Not Found"));
    }

    public List<ListProductBOMResponse> findProductBOMAllByProductId(Long productId) {
        List<ProductBOM> listProductBOM = jpaProductBOMRepository.findProductBOMByProductId(productId);
        return listProductBOM.stream().map(productBOMMapper::toListProductBOMResponse).collect(Collectors.toList());
    }

    @Transactional
    public void updateProductBOM(UpdateProductBOMRequest request) {
        ProductBOM productBOMPS = jpaProductBOMRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("ProductBOM Not Found"));
        //todo material 가져오기
        //todo productBOMMapper.toEntity(request, material, productBOM);
    }

    @Transactional
    public void updateDeleteStateOfProductBOM(Long[] productBOMIds) {
        jpaProductBOMRepository.updateDeleteStateOfProductBOM(productBOMIds);
    }

}
