package com.vosievskaya.controler;

import com.vosievskaya.web.Request;
import com.vosievskaya.web.ViewModel;
import com.vosievskaya.service.ProductService;

import java.util.Collections;

public class GetProductByIdController implements Controller {

    private final ProductService productService;

    public GetProductByIdController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ViewModel process(Request req) {
        String param = req.getParam("p_id")[0];
        Long id = Long.parseLong(param);
        return productService.getProductById(id)
                .map(p -> ViewModel.of("product").withAttribute("product", p))
                .orElseGet(() -> ViewModel.of("category").withAttribute("category", Collections.emptyList()));
    }
}
