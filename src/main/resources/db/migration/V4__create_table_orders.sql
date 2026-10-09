CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT REFERENCES users(id),
                        total_amount NUMERIC(12, 2) NOT NULL,
                        status VARCHAR(50) NOT NULL DEFAULT 'COMPLETED',
                        created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,
                             order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             product_id BIGINT NOT NULL REFERENCES products(id),
                             quantity INT NOT NULL,
                             unit_price NUMERIC(10, 2) NOT NULL
);

CREATE INDEX idx_orders_user_id ON orders(user_id);


