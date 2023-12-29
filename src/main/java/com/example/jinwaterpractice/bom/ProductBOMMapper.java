package com.example.jinwaterpractice.bom;

import com.example.jinwaterpractice.bom.dto.CreateBOMRequest;
import com.example.jinwaterpractice.bom.dto.ListProductBOMResponse;
import com.example.jinwaterpractice.bom.dto.ProductBOMResponse;
import com.example.jinwaterpractice.bom.dto.UpdateProductBOMRequest;
import com.example.jinwaterpractice.material.Material;
import com.example.jinwaterpractice.product.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ProductBOMMapper {
    public ProductBOMResponse toProductBOMResponse(ProductBOM productBOM) {
        ProductBOMResponse response = new ProductBOMResponse(
                productBOM.getId(),
                productBOM.getMaterial().getId(),
                productBOM.getProduct().getId(),
                productBOM.getMaterial().getCode(),
                productBOM.getMaterial().getName(),
                productBOM.getMaterial().getUnitPrice(),
                productBOM.getEtc()
        );
        return response;
    }

    public ListProductBOMResponse toListProductBOMResponse(ProductBOM productBOM) {
        ListProductBOMResponse response = new ListProductBOMResponse(
                productBOM.getId(),
                productBOM.getMaterial().getCode(),
                productBOM.getMaterial().getName(),
                productBOM.getMaterial().getUnitPrice(),
                productBOM.getEtc()
        );
        return response;
    }

    public ProductBOM toEntity(CreateBOMRequest request, Product product, Material material) {
        ProductBOM newProductBOM = new ProductBOM();
        newProductBOM.setProduct(product);
        newProductBOM.setMaterial(material);
        newProductBOM.setEtc(request.getEtc());
        return newProductBOM;
    }

    public ProductBOM toEntity(UpdateProductBOMRequest request, Material material, ProductBOM productBOM) {
        productBOM.setMaterial(material);
        productBOM.setEtc(request.getEtc());
        return productBOM;
    }
}
