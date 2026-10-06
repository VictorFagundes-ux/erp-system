CREATE TABLE inventories (
                             id BIGSERIAL PRIMARY KEY,
                             product_id BIGINT NOT NULL UNIQUE,
                             quantity INT NOT NULL DEFAULT 0,
                             min_quantity INT NOT NULL DEFAULT 5,
                             updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX idx_inventories_product_id ON inventories(product_id);