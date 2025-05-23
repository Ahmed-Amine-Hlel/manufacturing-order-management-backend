package com.manufacturing.backend.controller;

import com.manufacturing.backend.common.ProductPayload;
import com.manufacturing.backend.model.Product;
import com.manufacturing.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieve all products.
     *
     * @return List of all products
     */
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(this.productService.findAllProducts());
    }

    /**
     * Create a new product.
     *
     * @param payload Payload to create
     * @return Created product
     */
    @PostMapping("/")
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody ProductPayload payload
    ) {
        Product savedProduct = this.productService.saveProduct(payload);
        return ResponseEntity.ok(savedProduct);
    }

    /**
     * Retrieve a product by its ID.
     *
     * @param id Product ID
     * @return ResponseEntity containing the product if found, or 404 Not Found
     */

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductPayload payload
    ) {
        return this.productService.updateProduct(id, payload)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a product by its ID.
     *
     * @param id Product ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        if (message.contains("ProductType")) {
            return Map.of("type", "Invalid product type. Must be one of: COMPONENT, MODULE, RESOURCE");
        }
        return Map.of("error", "Invalid request body: " + message);
    }
}
