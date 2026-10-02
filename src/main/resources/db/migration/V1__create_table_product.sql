CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          description TEXT,
                          sku VARCHAR(50) UNIQUE NOT NULL,
                          price NUMERIC(10, 2) NOT NULL,
                          created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_sku ON products(sku);